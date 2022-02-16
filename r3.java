import java.lang.reflect.*;
import java.lang.StringBuilder;
import java.io.*;
import java.lang.*;

public class Signature {
	public Class<?> parentClass;
	public String method;
	public Class<?>[] params;
	public Signature(String function, Class<?>... classes) {
		String[2] components = function.split(".");
		parentClass = Class.forName(components[0]);
		method = components[1];
	}
}

public class Record {
	public static String logfile = "logfile.log";
	public static Record instance = null;
	private FileOutputStream binout;
	private ObjectOutputStream out;
	private int start;

	private Record() {
		obj = new ObjectOutputStream(new FileOutputStream(logfile));
		start = System.currentTimeMillis();
	}

	private void recordCall(Signature sig, Object... args) {
		out.writeLong(System.currentTimeMillis() - start);
		out.writeObject(sig);
		out.writeInt(args.length);
		for (Object o : args)
			out.writeObject(o);
	}

	public static Record getInstance() {
		if (instance == null) {
			instance = new Record();
		}
		return instance;
	}
}

public class Replay {
	public static void replay(String logfile) throws IOException {
		int start = System.currentTimeMillis();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(logfile));
		try {
			while (true) {
				long nextTime = in.readLong() + start;
				Signature nextSig = (Signature)in.readObject();
				int numOfArgs = in.readInt();
				Object[numOfArgs] args;
				for (int i = 0; i < numOfArgs; i++)
					args[i] = in.readObject();
				Thread.sleep(nextTime - System.currentTimeMillis());
				// Execute method
				// TODO: Check and probably fix varargs execution
				Method m = nextSig.parentClass.getMethod(sig.method, sig.params);
				m.invoke(nextSig.parentClass.getMethod("getInstance").invoke(null), args);
			}
		} catch (EOFError) {}
	}
}
