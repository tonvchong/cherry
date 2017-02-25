package util;

import org.junit.Test;

import com.tonvchong.core.util.security.SHA1Util;

public class SecurityTest {

	@Test
	public void test() {
		System.out.println("加密后：" + SHA1Util.encrypt("iamgood1111111111111111111222222"));
	}

}
