package r3;

import java.io.*;
import java.lang.*;

/**
 * Allows users to record and control the recording of function calls.
 *
 * @author Pradhyum Rajasekar
 * @since 1.0.0
 * @see Replay
 * @see Signature
 */
public class Record {
	private static Record instance = null;
	/**
	 * Specifies whether the recording file is corrupted or not.
	 */
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

	/**
	 * Starts recording function calls into a file titled "recording.bin".
	 * <p>
	 * {@link #recordCall} has no effect when recording is not on. This function enables
	 * that effect. It initializes the file and the start time relative to which all other
	 * times are recorded.
	 */
	public static void start() {
		Record.getInstance()._start("recording.bin");
	}

	/**
	 * Starts recording function calls into a file.
	 * <p>
	 * {@link #recordCall} has no effect when recording is not on. This function enables
	 * that effect. It initializes the file and the start time relative to which all other
	 * times are recorded.
	 *
	 * @param recordingFile the file into which data should be written
	 */
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

	/**
	 * Records a function call.
	 * <p>
	 * If recording is enabled using {@link #start}, this function records the calling
	 * function's name and its arguments, which is then persisted into a file.
	 * <p>
	 * Make sure the arguments are serializable, otherwise the stream will be corrupted.
	 *
	 * @param args a list of arguments
	 */
	public static void recordCall(Object... args) {
		StackTraceElement tb = (new Throwable()).getStackTrace()[1];
		Class<?>[] argTypes = new Class<?>[args.length];
		int i = 0;
		for (Object x : args)
			argTypes[i++] = x.getClass();
		recordCall(new Signature(tb.getClassName() + "." + tb.getMethodName(), argTypes), args);
	}

	/**
	 * Records a function call.
	 * <p>
	 * If recording is enabled using {@link #start}, this function records a
	 * function's name and its arguments, which is then persisted into a file.
	 * <p>
	 * Make sure the arguments are serializable, otherwise the recording file will
	 * be corrupted.
	 *
	 * @param sig a {@link Signature} object representing the function
	 * @param args a list of arguments
	 */
	public static void recordCall(Signature sig, Object... args) {
		Record.getInstance()._recordCall(sig, args);
	}

	private void _stop() {
		try {
			out.flush();
		} catch (IOException e) {}
		recording = false;
	}

	/**
	 * Stops recording.
	 *
	 * This function flushes the file stream and disables the {@link #recordCall} method
	 * from doing anything.
	 */
	public static void stop() {
		Record.getInstance()._stop();
	}

	private static Record getInstance() {
		if (instance == null)
			instance = new Record();
		return instance;
	}

	private boolean _isRecording() {
		return recording;
	}

	/**
	 * Returns whether function calls are recorded are not.
	 * <p>
	 * Recording is started by {@link #start()} and is stopped by {@link #stop}. This method
	 * returns whether recording has started or not. This can be used to change behavior, or
	 * even just to check whether recording has started or not.
	 *
	 * @return a boolean representing whether or not function calls are recorded
	 * @since 1.1.1
	 * @see Replay#isReplaying
	 */
	public static boolean isRecording() {
		return Record.getInstance()._isRecording();
	}
}
