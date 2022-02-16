package r3;

import java.io.Serializable;

class MethodCall implements Serializable {
	long time;
	Signature sig;
	Object[] args;

	public MethodCall(long ftime, Signature fsig, Object... fargs) {
		time = ftime;
		sig = fsig;
		args = fargs;
	}
}
