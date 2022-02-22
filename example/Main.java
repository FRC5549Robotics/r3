import r3.Record;
import r3.Replay;
import r3.Signature;
import java.lang.*;
import java.io.*;

public class Main {
	int n;

	public Main() {
		n = 0;
	}

	public static void main(String[] argv) throws IOException, InterruptedException {
		if (false) {
			Record.start();   // Following lines are recorded:
				Main m = new Main();
				m.printAndSetNum(5);
				Thread.sleep(2000);
				m.incAndPrint();
			Record.stop();
		} else {
			Replay.replay("recording.bin");
		}
	}

	public void printAndSetNum(Integer i) {
		Record.recordCall(this, i);
		if (Record.isRecording()) {
			System.out.printf("%d%n", i);
		} else {
			System.out.printf("Replay: %d%n", i);
		}
		n = i;
	}

	public void incAndPrint() {
		Record.recordCall(this);
		n++;
		if (!Replay.isReplaying()) {
			System.out.printf("%d%n", n);
		} else {
			System.out.printf("Replay: %d%n", n);
		}
	}

	public static Main getInstance() {
		return new Main();
	}
}
