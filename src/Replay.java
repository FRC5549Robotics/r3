package r3;

import java.lang.reflect.*;
import java.io.*;

/**
 * Allows recording made using {@link Record} to be replayed in real time.
 * <p>
 * <b>Important note! When recording classmethod, you <i>must</i> define a static method called getInstance.</b>
 * This method should return an instance of the class on which all methods can be run. This is the largest limitation
 * of this library: methods called on different objects are not preserved. This will be amended in the future.
 *
 * @author Pradhyum Rajasekar
 * @since 1.0.0
 * @see Record
 */
public class Replay {
	private static boolean replaying = false;
	private Replay() {}

	/**
	 * Replays a recording in real time.
	 * <p>
	 * This function takes a filename which points to a recording made with
	 * {@link Record} and replays it in real time. That is, all time delays
	 * are preserved, including but not limited to sleeping and user delays.
	 * <p>
	 * Since the function code is not preserved, the function could have been
	 * changed when replaying, meaning different results could occur.
	 *
	 * @param logfile the file to which a recording was made
	 * @throws IOException if an unrecoverable error occurs when reading from the file stream
	 */
	public static void replay(String logfile) throws IOException {
		replaying = true;
		long start = System.currentTimeMillis();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(logfile));
		try {
			while (true) {
				try {
					MethodCall mc = (MethodCall)in.readObject();
					long millis = start + mc.getTime() - System.currentTimeMillis();
					if (millis < 0) millis = 0;
					Thread.sleep(millis);
					mc.run();
				} catch (ClassNotFoundException e)    { System.out.println("Error: ClassNotFound"); }
				  catch (InterruptedException e)      { System.out.println("Error: Interrupted"); }
			}
		} catch (EOFException e) {}
		replaying = false;
	}

	/**
	 * Returns whether or not a recording is being replayed.
	 * <p>
	 * This function can be used to change behavior depending on whether a
	 * replay is occuring or not. In the example project, this function is
	 * used to show whether of not a replay is currently happening.
	 *
	 * @return a boolean representing whether or not a recording is being replayed.
	 * @see Record#isRecording
	 */
	public static boolean isReplaying() {
		return replaying;
	}
}
