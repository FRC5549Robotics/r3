package r3;

import java.lang.*;
import java.lang.reflect.*;
import java.io.Serializable;

public class Signature implements Serializable {
	private Class<?> parentClass;
	private String method;
	private Class<?>[] params;
	public boolean valid = true;
	public Signature(String function, Class<?>... classes) {
		try {
			String components[] = function.split("\\.", 2);
			parentClass = Class.forName(components[0]);
			params = classes;
			method = components[1];
		} catch (ClassNotFoundException e) {
			System.out.println("Classname is invalid. Call not recorded.");
			valid = false;
		}
	}

	public void invoke(Object... args) {
		try {
			parentClass.getMethod(method, params).invoke(parentClass.getMethod("getInstance").invoke(null), args);
		} catch (IllegalAccessException e) {
			System.out.println("Method is private or protected. Could not run method.");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("Methodname is invalid. Could not run method.");
			valid = false;
		}

	}
}
