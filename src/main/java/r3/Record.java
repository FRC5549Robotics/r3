import java.io.*;

public class Record {
	public static String logfile = "logfile.log";
	public static Record instance = null;
	public boolean corrupted = false;
	private FileOutputStream binout;
	private ObjectOutputStream out;
	private long start;

	private Record() throws IOException, FileNotFoundException {
		out = new ObjectOutputStream(new FileOutputStream(logfile));
		start = System.currentTimeMillis();
	}

	private void recordCall(Signature sig, Object... args) {
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

	public static Record getInstance() throws IOException, FileNotFoundException {
		if (instance == null) {
			instance = new Record();
		}
		return instance;
	}
}
