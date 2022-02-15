import java.lang.reflect.*;
import java.lang.StringBuilder;
import java.io.*

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

	private void recordCall(String function, Object... args) {
		out.writeLong(System.currentTimeMillis() - start);
		out.writeObject(function);
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
				String nextFunctionName = (String)in.readObject();
				int numOfArgs = in.readInt();
				Object[numOfArgs] args;
				for (int i = 0; i < numOfArgs; i++)
					args[i] = in.readObject();
				Thread.sleep(nextTime - System.currentTimeMillis());
				// Execute method
			}
		} catch (EOFError) {}
	}
}
