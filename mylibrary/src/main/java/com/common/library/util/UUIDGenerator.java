package com.common.library.util;

import java.net.InetAddress;
import java.security.SecureRandom;

public class UUIDGenerator {

	private static SecureRandom seeder;
	private static String midValue;
	private static UUIDGenerator key;

	static {
		key = new UUIDGenerator();
	}
	public static String getUUID() {
		String sRet = null;
		try {
			long timeNow = System.currentTimeMillis();
			// get int value as unsigned
			int timeLow = (int) timeNow & 0xFFFFFFFF;
			// get next random value
			int node = seeder.nextInt();
			sRet = (key.hexFormat(timeLow, 8) + midValue + key.hexFormat(node,
					8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sRet;
	}

	public static void main(String arg[]) {
		try {
			System.out.println(System.currentTimeMillis());
			for (int i = 0; i < 4; i++) {
				System.out.println(UUIDGenerator.getUUID() + "  "
						+ UUIDGenerator.getUUID().length());
			}
			System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UUIDGenerator() {
		init();
	}

	private int getInt(byte abyte0[]) {
		int i = 0;
		int j = 24;
		for (int k = 0; j >= 0; k++) {
			int l = abyte0[k] & 0xff;
			i += l << j;
			j -= 8;
		}
		return i;
	}

	private String hexFormat(int i, int j) {
		String s = Integer.toHexString(i);
		return padHex(s, j) + s;
	}

	private void init() {
		try {
			// get the internet address
			InetAddress inet = InetAddress.getLocalHost();
			byte[] bytes = inet.getAddress();
			String hexInetAddress = hexFormat(getInt(bytes), 8);
			// get the hashcode for this object
			String thisHashCode = hexFormat(System.identityHashCode(this), 8);
			// set up mid value string
			this.midValue = hexInetAddress + thisHashCode;
			// load up the randomizer first
			seeder = new SecureRandom();
			int node = seeder.nextInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String padHex(String s, int i) {
		StringBuffer stringbuffer = new StringBuffer();
		if (s.length() < i) {
			for (int j = 0; j < i - s.length(); j++) {
				stringbuffer.append("0");
			}
		}
		return stringbuffer.toString();
	}
}
