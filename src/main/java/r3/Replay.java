import java.lang.reflect.*;
import java.io.*;

public class Replay {
	public static void replay(String logfile) throws IOException {
		long start = System.currentTimeMillis();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(logfile));
		try {
			while (true) {
				try {
					long nextTime = in.readLong() + start;
					Signature nextSig = (Signature)in.readObject();
					int numOfArgs = in.readInt();
					Object args[] = new Object[numOfArgs];
					for (int i = 0; i < numOfArgs; i++)
						args[i] = in.readObject();
					Thread.sleep(nextTime - System.currentTimeMillis());
					// Execute method
					// TODO: Check and probably fix varargs execution
					Method m = nextSig.parentClass.getMethod(nextSig.method, nextSig.params);
					m.invoke(nextSig.parentClass.getMethod("getInstance").invoke(null), args);
				} catch (ClassNotFoundException e) { System.out.println("Error: ClassNotFound"); }
				  catch (InterruptedException e)   { System.out.println("Error: Interrupted"); }
				  catch (NoSuchMethodException e)  { System.out.println("Error: NoSuchMethod"); }
				  catch (IllegalAccessException e) { System.out.println("Error: IllegalAccess"); }
				  catch (InvocationTargetException e) { System.out.println("Error: InvocationTarget"); }
			}
		} catch (EOFException e) {}
	}
}
