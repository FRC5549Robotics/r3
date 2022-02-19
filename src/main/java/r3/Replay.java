package r3;

import java.lang.reflect.*;
import java.io.*;

/**
 * Allows recording made using {@link Record} to be replayed in real time.
 *
 * @author Pradhyum Rajasekar
 * @since 1.0.0
 * @see Record
 */
public class Replay {
	private Replay() {}

	/**
	 * Replays a recording in real time.
	 *
	 * This function takes a filename which points to a recording made with
	 * {@link Record} and replays it in real time. That is, all time delays
	 * are preserved, including but not limited to sleeping and user delays.
	 *
	 * Since the function code is not preserved, the function could have been
	 * changed when replaying, meaning different results could occur.
	 *
	 * @param logfile the file to which a recording was made
	 * @throws IOException if an unrecoverable error occurs when reading from the file stream
	 */
	public static void replay(String logfile) throws IOException {
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
	}
}
