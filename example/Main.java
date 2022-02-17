import r3.Record;
import r3.Replay;
import r3.Signature;
import java.lang.*;
import java.io.*;

public class Main {
	public static void main(String[] argv) throws ClassNotFoundException, IOException {
		if (false) {
			Record.start();
			Main.printExample();
			Record.stop();
		} else {
			Replay.replay("recording.bin");
		}
	}

	public static void printExample() throws ClassNotFoundException, IOException {
		Record.recordCall(new Signature("Main.printExample"));
		System.out.println("In example");
	}

	public static Main getInstance() {
		return null;
	}	
}
