import r3.Record;
import r3.Replay;
import r3.Signature;
import java.lang.*;
import java.io.*;

public class Main {
	static boolean recording = false;
	public static void main(String[] argv) throws ClassNotFoundException, IOException {
		if (recording) {
			Record.start();
			Main.printExample();
			Record.stop();
		} else {
			Replay.replay("recording.bin");
		}
	}

	public static void printExample() throws ClassNotFoundException, IOException {
		Record.recordCall(new Signature("Main.printExample"));
		if (recording) {
			System.out.println("In example");
		} else {
			System.out.println("In example (replaying)");
		}
	}

	public static Main getInstance() {
		return null;
	}	
}
