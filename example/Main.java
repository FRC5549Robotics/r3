import r3.Record;
import r3.Signature;
import java.lang.*;

public class Main {
	public static void main(String[] argv) throws ClassNotFoundException {
		Main.printExample();
	}

	public static void printExample() throws ClassNotFoundException {
		Record.recordCall(new Signature("Main.printExample"));
		System.out.println("In example");
	}

	public static Main getInstance() {
		return null;
	}	
}
