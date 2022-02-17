package r3;

import java.io.*;

public class Record {
	public static Record instance = null;
	public boolean corrupted = false;
	private ObjectOutputStream out;
	private long start;
	private boolean recording = false;

	private Record() {}

	private void _start(String recordingFile) {
		try {
			out = new ObjectOutputStream(new FileOutputStream(recordingFile));
			start = System.currentTimeMillis();
			recording = true;
		} catch (FileNotFoundException e) { System.out.println("File could not be opened"); }
		  catch (IOException i) { System.out.println("IOException: " + i.getMessage()); }
	}

	public static void start() {
		Record.getInstance()._start("recording.bin");
	}

	public static void start(String recordingFile) {
		Record.getInstance()._start(recordingFile);
	}

	private void _recordCall(Signature sig, Object... args) {
		if (recording) {
			try {
				if (corrupted) return;
				if (!sig.valid) return;
				out.writeObject(new MethodCall(System.currentTimeMillis() - start, sig, args));
			} catch (IOException e) {
				System.out.println(System.currentTimeMillis() + " CORRUPTED: " + e.toString());
				corrupted = true;
			}
		}
	}

	public static void recordCall(Signature sig, Object... args) {
		Record.getInstance()._recordCall(sig, args);
	}

	private void _stop() {
		try {
			out.flush();
		} catch (IOException e) {}
		recording = false;
	}

	public static void stop() {
		Record.getInstance()._stop();
	}

	public static Record getInstance() {
		if (instance == null)
			instance = new Record();
		return instance;
	}
}
