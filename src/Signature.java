package r3;

import java.lang.*;
import java.lang.reflect.*;
import java.io.Serializable;
import java.util.*;

/**
 * Defines a Signature class to store a function's name and argument types; for internal use only,
 * unless you know what you're doing.
 * <p>
 * Can be passed to functions like {@link Record#recordCall(Signature, Object...)}, but it is usually
 * not needed. It is used internally, though.
 *
 * @author Pradhyum Rajasekar
 * @see MethodCall
 * @since 1.0.0
 */
public class Signature implements Serializable {
	private static HashMap<Class<?>, Object> classToObj = new HashMap<Class<?>, Object>();
	/** @serial the class of this method. */
	private Class<?> parentClass;
	/** @serial the name of this method. */
	private String method;
	/** @serial the object on which the method should be run. Can be null. */
	private Object instance;
	/** @serial whether or not to use getInstance to obtain an instance of this class. */
	private boolean useGetInstance;
	/** @serial the types of the parameters this method takes. */
	private Class<?>[] params;
	/** Whether or not the classname was valid. */
	public boolean valid = true;
	/**
	 * Create a Signature object from a {@link String} and a list of parameter types.
	 * <p>
	 * When passing a string into this constructor, it should be of the format
	 * <i>ClassName.methodName</i>. The parameter types should be {@link Class} objects.
	 * <p>
	 * When the classname is invalid (error checking is not performed on the
	 * methodname; those errors occur at runtime), an error message is printed,
	 * and {@link #valid} is set to false.
	 * <p>
	 * Note that fully qualified names (e.g. java.io.Serializable.writeObject) are not supported;
	 * only a classname and a method name are supported as of version 1.1.0.
	 *
	 * @param obj the instance on which the method is to be run. Can be null to get using getInstance.
	 * @param function a string representing the (semi) qualified name of a method
	 * @param classes a list of {@link Class} objects representing the parameter types of the method
	 */
	public Signature(Object obj, String function, Class<?>... classes) {
		if (!Serializable.class.isAssignableFrom(obj.getClass())) {
			instance = null;
			useGetInstance = true;
		} else
			instance = obj;
		params = classes;
		method = function;
		parentClass = obj.getClass();
	}
	
	private void configureInstance() {
		try {
			if (useGetInstance) {
				// Can't use getOrDefault because of eager evaluation. Think about it further.
				instance = classToObj.containsKey(parentClass)
					? classToObj.get(parentClass)
					: parentClass.getMethod("getInstance").invoke(null);
				classToObj.putIfAbsent(parentClass, instance);
			}
		} catch (NoSuchMethodException e) {
			System.out.println("getInstance was not defined.");
			valid = false;
		} catch (IllegalAccessException e) {
			System.out.println("getInstance is private or protected.");
			valid = false;
		} catch (InvocationTargetException e) {
			System.out.println("getInstance raised an error.");
			valid = false;
		}
	}

	/**
	 * Run the method contained in a Signature object with the specified arguments.
	 * <p>
	 * For ease of use, many exceptions are handled within the function. For an
	 * {@link IllegalAccessException} or a {@link NoSuchMethodException}, {@link #valid} is set to
	 * false and a simple error message is printed out. For an {@link InvocationTargetException},
	 * where an error occurs in the method, a stacktrace is printed out.
	 *
	 * @param args the arguments to be passed into the method
	 */
	public void invoke(Object... args) {
		try {
			configureInstance();
			if (!valid) throw new UnsupportedOperationException();
			instance.getClass().getMethod(method, params).invoke(instance, args);
		} catch (IllegalAccessException e) {
			System.out.println("Method is private or protected. Could not run method.");
			valid = false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println("Methodname is invalid. Could not run method.");
			valid = false;
		} catch (UnsupportedOperationException e) {
			System.out.println("Method was not valid for a past reason.");
		}
	}
}
