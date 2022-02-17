package r3;

import java.lang.*;
import java.io.Serializable;

public class Signature implements Serializable {
	public Class<?> parentClass;
	public String method;
	public Class<?>[] params;
	boolean valid = true;
	public Signature(String function, Class<?>... classes) {
		try {
			String components[] = function.split("\\.", 2);
			method = components[1];
			parentClass = Class.forName(components[0]);
		} catch (ClassNotFoundException e) {
			System.out.println("Classname is invalid. Call not recorded.");
			valid = false;
		}
	}
}
