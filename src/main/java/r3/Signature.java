import java.lang.*;

public class Signature {
	public Class<?> parentClass;
	public String method;
	public Class<?>[] params;
	public Signature(String function, Class<?>... classes) throws ClassNotFoundException {
		String components[] = function.split(".", 2);
		parentClass = Class.forName(components[0]);
		method = components[1];
	}
}