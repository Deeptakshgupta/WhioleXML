package com.citi.WebConfiguratorService.common;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DecodeIdentity {
	public static char[] decode(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		byte[] kbytes = "jaas is the way".getBytes();
		SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");
		BigInteger n = new BigInteger(secret, 16);
		byte[] encoding = n.toByteArray();
		// SECURITY-344: fix leading zeros
		if (encoding.length % 8 != 0) {
			int length = encoding.length;
			int newLength = ((length / 8) + 1) * 8;
			int pad = newLength - length; // number of leading zeros
			byte[] old = encoding;
			encoding = new byte[newLength];
			for (int i = old.length - 1; i >= 0; i--) {
				encoding[i + pad] = old[i];
			}
			if (n.signum() == -1) {
				for (int i = 0; i < newLength - length; i++) {
					encoding[i] = (byte) -1;
				}
			}
		}
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decode = cipher.doFinal(encoding);
		return new String(decode).toCharArray();
	}
}
