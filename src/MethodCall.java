package r3;

import java.io.Serializable;

/**
 * Defines a MethodCall object to be used to serialize a method call; for internal use only.
 * <p>
 * A MethodCall object contains information about a specific method call which occured in the
 * code.
 *
 * @author Pradhyum Rajasekar
 * @see Signature
 * @since 1.0.0
 */
public class MethodCall implements Serializable {
	/** @serial the time in milliseconds relative to some starting point when this method was invoked */
	private long time;
	/** @serial The {@link Signature} object for this method. */
	private Signature sig;
	/** @serial The arguments which were passed to this method call. */
	private Object[] args;

	/**
	 * Create a MethodCall object.
	 * <p>
	 * A MethodCall object contains information about when a method was invoked, its
	 * signature (represented by a {@link Signature} object), and the arguments which
	 * were passed into it.
	 *
	 * @param ftime the time (relative to some starting point in milliseconds) when this method was invoked
	 * @param fsig the {@link Signature} object for this method
	 * @param fargs the arguments passed into this method
	 */
	public MethodCall(long ftime, Signature fsig, Object... fargs) {
		time = ftime;
		sig = fsig;
		args = fargs;
	}

	/**
	 * Rerun a method call.
	 * <p>
	 * This calls the method with the specified args.
	 */
	public void run() {
		sig.invoke(args);
	}

	/**
	 * Gets the relative time (ftime).
	 * <p>
	 * @return the time relative to some starting point
	 */
	public long getTime() {
		return time;
	}
}
