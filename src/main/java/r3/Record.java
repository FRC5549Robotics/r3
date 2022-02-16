package r3;

import java.io.*;

public class Record {
	public static String recordingFile = "recording.bin";
	public static Record instance = null;
	public boolean corrupted = false;
	private FileOutputStream binout;
	private ObjectOutputStream out;
	private long start;
	private boolean recording = false;

	private Record() throws IOException, FileNotFoundException {
		out = new ObjectOutputStream(new FileOutputStream(recordingFile));
	}

	private void _start() {
		start = System.currentTimeMillis();
		recording = true;
	}

	public static void start() throws IOException, FileNotFoundException {
		Record.getInstance()._start();
	}

	private void _recordCall(Signature sig, Object... args) {
		if (!recording) {
			try {
				if (corrupted) return;
				out.writeObject(new MethodCall(System.currentTimeMillis() - start, sig, args));
			} catch (IOException e) {
				System.out.println(System.currentTimeMillis() + " CORRUPTED: " + e.toString());
				corrupted = true;
			}
		}
	}

	public static void recordCall(Signature sig, Object... args) throws IOException {
		Record.getInstance()._recordCall(sig, args);
	}

	private void _stop() throws IOException {
		out.flush();
		recording = false;
	}

	public static void stop() {
		try {
			Record.getInstance()._stop();
		} catch (IOException e) {}
	}

	public static Record getInstance() throws IOException, FileNotFoundException {
		if (instance == null) {
			instance = new Record();
		}
		return instance;
	}
}
