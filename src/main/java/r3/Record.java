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
				if (corrupted) throw new IOException();
				out.writeLong(System.currentTimeMillis() - start);
				out.writeObject(sig);
				out.writeInt(args.length);
				for (Object o : args)
					out.writeObject(o);
			} catch (IOException e) {
				System.out.println("Corrupted.");
				corrupted = true;
			}
		}
	}

	public static void recordCall(Signature sig, Object... args) {
		try {
			Record.getInstance()._recordCall(sig, args);
		} catch (IOException e) {}
	}

	private void _stop() {
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
