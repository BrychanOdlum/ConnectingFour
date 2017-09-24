package net.brychan.connect4;

import java.util.Random;

public class Util {

	private static final Random RANDOM = new Random();

	public static String generateKey() {
		StringBuilder sb = new StringBuilder();
		char[] chars = {
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
		};
		for (int i = 0; i < 10; i++) {
			char c = chars[RANDOM.nextInt(chars.length)];
			sb.append(c);
		}

		return sb.toString();
	}

}
