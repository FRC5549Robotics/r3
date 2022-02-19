package r3;

import org.junit.Test;
import static org.junit.Assert.*;

public class SignatureTest {
	@Test
	public void testSplit() {
		/*
		Signature sig = new Signature("ThisShouldFail.testFn");
		assertFalse("sig.valid should be false", sig.valid);*/
	}

	@Test
	public void testLookup() throws ClassNotFoundException {
		/*
		Signature sig = new Signature("SignatureTest.toStringSampleFn", Integer.class);
		assertTrue("sig.valid should be true", sig.valid);
		assertEquals("Signature class should be SignatureTest", sig.parentClass, SignatureTest.class);
		assertEquals("Signature method should be `toStringSampleFn`", sig.method, "toStringSampleFn");
		*/
	}

	public String toStringSampleFn(Integer i) {
		return i.toString();
	}
}
