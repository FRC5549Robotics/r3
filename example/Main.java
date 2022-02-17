import r3.Record;
import r3.Replay;
import r3.Signature;
import java.lang.*;
import java.io.*;

public class Main {
	static boolean recording = true;
	int n;
	static Main instance = null;

	public Main() {
		n = 0;
	}

	public static void main(String[] argv) throws IOException {
		if (recording) {
			Record.start();
			Main m = new Main();
			m.printAndSetNum(5);
			m.incAndPrint();
			Record.stop();
		} else {
			Replay.replay("recording.bin");
		}
	}

	public void printAndSetNum(Integer i) {
		Record.recordCall(new Signature("Main.printAndSetNum", Integer.class), i);
		if (recording) {
			System.out.printf("%d%n", i);
		} else {
			System.out.printf("Replay: %d%n", i);
		}
		n = i;
	}

	public void incAndPrint() {
		Record.recordCall(new Signature("Main.incAndPrint"));
		n++;
		if (recording) {
			System.out.printf("%d%n", n);
		} else {
			System.out.printf("Replay: %d%n", n);
		}
	}

	public static Main getInstance() {
		if (instance == null)
			instance = new Main();
		return instance;
	}	
}
