import java.lang.reflect.*;
import java.lang.StringBuilder;
import java.io.*

public class Record {
	public static final String logfile = "logfile.log";
	public static Record instance = null;
	private FileOutputStream binout;
	private ObjectOutputStream out;
	private int start;

	private Record() {
		binout = new FileOutputStream(logfile);
		obj = new ObjectOutputStream(binout);
		start = System.currentTimeMillis();
	}

	private void recordCall(String function, Object... args) {
		out.writeLong(System.currentTimeMillis());
		out.writeChars(function);
		out.writeInt(args.length);
		for (Object o : args)
			objout.writeObject(o);
	}

	public static Record getInstance() {
		if (instance == null) {
			instance = new Record();
		}
		return instance;
	}
}
