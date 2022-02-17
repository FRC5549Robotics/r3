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
					// Thread.sleep(mc.time + System.currentTimeMillis() - start);
					// Execute method
					// TODO: Check and probably fix varargs execution
					Method m = mc.sig.parentClass.getMethod(mc.sig.method, mc.sig.params);
					m.invoke(mc.sig.parentClass.getMethod("getInstance").invoke(null), mc.args);
				} catch (ClassNotFoundException e) { System.out.println("Error: ClassNotFound"); }
				  // catch (InterruptedException e)   { System.out.println("Error: Interrupted"); }
				  catch (NoSuchMethodException e)  { System.out.println("Error: NoSuchMethod"); }
				  catch (IllegalAccessException e) { System.out.println("Error: IllegalAccess"); }
				  catch (InvocationTargetException e) { System.out.println("Error: InvocationTarget"); }
			}
		} catch (EOFException e) {}
		System.out.println("REPLAY: Done.");
	}
}
