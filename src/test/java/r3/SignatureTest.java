package r3;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignatureTest {
	@Test
	public void testSplit() {
		try {
			new Signature("ThisShouldFail.testFn");
		} catch (ClassNotFoundException e) {}
	}

	@Test
	public void testLookup() throws ClassNotFoundException {
		/*
		Integer i = 0;
		Signature s = new Signature("SignatureTest.toStringSampleFn", i.getClass());
		assertTrue("Signature method should be `toStringSampleFn`", s.method == "toStringSampleFn");
		*/
	}

	public String toStringSampleFn(Integer i) {
		return i.toString();
	}
}
