package r3;

import java.lang.*;
import java.io.Serializable;

public class Signature implements Serializable {
	public Class<?> parentClass;
	public String method;
	public Class<?>[] params;
	public Signature(String function, Class<?>... classes) throws ClassNotFoundException {
		String components[] = function.split("\\.", 2);
		System.out.println(components[0]);
		parentClass = Class.forName(components[0]);
		method = components[1];
	}
}
