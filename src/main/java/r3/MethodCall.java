package r3;

import java.io.Serializable;

class MethodCall implements Serializable {
	public long time;
	private Signature sig;
	private Object[] args;

	public MethodCall(long ftime, Signature fsig, Object... fargs) {
		time = ftime;
		sig = fsig;
		args = fargs;
	}

	public void run() {
		sig.invoke(args);
	}
}
