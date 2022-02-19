package r3;

import java.lang.*;
import java.lang.reflect.*;
import java.io.Serializable;

/**
 * Defines a Signature class to store a function's name and argument types; for internal use only,
 * unless you know what you're doing.
 *
 * Can be passed to functions like {@link Record#recordCall(Signature, Object...)}, but it is usually
 * not needed. It is used internally, though.
 *
 * @author Pradhyum Rajasekar
 * @see MethodCall
 * @since 1.0.0
 */
public class Signature implements Serializable {
	/** @serial the class of this method. */
	private Class<?> parentClass;
	/** @serial the name of this method. */
	private String method;
	/** @serial the types of the parameters this method takes. */
	private Class<?>[] params;
	/**
	 * Whether or not the classname was valid.
	 */
	public boolean valid = true;
	/**
	 * Create a Signature object from a {@link String} and a list of parameter types.
	 *
	 * When passing a string into this constructor, it should be of the format
	 * <i>ClassName.methodName</i>. The parameter types should be {@link Class} objects.
	 * 
	 * When the classname is invalid (error checking is not performed on the
	 * methodname; those errors occur at runtime), an error message is printed,
	 * and {@link #valid} is set to false.
	 *
	 * Note that fully qualified names (e.g. java.io.Serializable.writeObject) are not supported;
	 * only a classname and a method name are supported as of version 1.1.0.
	 *
	 * @param function a string representing the (semi) qualified name of a method
	 * @param classes a list of {@link Class} objects representing the parameter types of the method
	 */
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
	/**
	 * Run the method contained in a Signature object with the specified arguments.
	 *
	 * For ease of use, many exceptions are handled within the function. For an
	 * {@link IllegalAccessException} or a {@link NoSuchMethodException}, {@link #valid} is set to
	 * false and a simple error message is printed out. For an {@link InvocationTargetException},
	 * where an error occurs in the method, a stacktrace is printed out.
	 *
	 * @param args the arguments to be passed into the method
	 */
	public void invoke(Object... args) {
		try {
			parentClass.getMethod(method, params).invoke(parentClass.getMethod("getInstance").invoke(null), args);
		} catch (IllegalAccessException e) {
			System.out.println("Method is private or protected. Could not run method.");
			valid = false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("Methodname is invalid. Could not run method.");
			valid = false;
		}

	}
}
