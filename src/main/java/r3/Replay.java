package r3;

import java.lang.reflect.*;
import java.io.*;

public class Replay {
	public static void replay(String logfile) throws IOException {
		long start = System.currentTimeMillis();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(logfile));
		try {
			while (true) {
				try {
					MethodCall mc = (MethodCall)in.readObject();
					long millis = start + mc.time - System.currentTimeMillis();
					if (millis < 0) millis = 0;
					Thread.sleep(millis);
					mc.run();
				} catch (ClassNotFoundException e)    { System.out.println("Error: ClassNotFound"); }
				  catch (InterruptedException e)      { System.out.println("Error: Interrupted"); }
			}
		} catch (EOFException e) {}
		System.out.println("REPLAY: Done.");
	}
}
