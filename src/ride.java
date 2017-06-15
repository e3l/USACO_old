/*
ID: ethan.w1
LANG: JAVA
TASK: ride
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class ride {
	private static final int MOD_DENOMINATOR = 47;
	public static final int ALPHABET_CHAR_OFFSET = 9;

	/**
	 * Main logic for comparison.
	 * 
	 * @param firstLine
	 * @param secondLine
	 * @return a boolean on whether or not the mods are equal: true = GO, false
	 *         = STAY
	 */
	static boolean compare(String firstLine, String secondLine) {
		int[] firstlinenums = new int[firstLine.length()];
		int[] secondlinenums = new int[secondLine.length()];
		int firstlineprod = 1;
		int secondlineprod = 1;

		for (int num = 0; num < firstLine.length(); num++) {
			firstlinenums[num] = Character.getNumericValue(firstLine.charAt(num)) - ALPHABET_CHAR_OFFSET;
			firstlineprod = firstlineprod * firstlinenums[num];
		}

		for (int num = 0; num < secondLine.length(); num++) {
			secondlinenums[num] = Character.getNumericValue(secondLine.charAt(num)) - ALPHABET_CHAR_OFFSET;
			secondlineprod = secondlineprod * secondlinenums[num];
		}

		return firstlineprod % MOD_DENOMINATOR == secondlineprod % MOD_DENOMINATOR;
	}

	/**
	 * Reads two lines from ride.in file and print the result to ride.out
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		// System.out.println(System.getProperty("user.dir"));
		
		BufferedReader in = new BufferedReader(new FileReader("ride.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));

		String firstLine = in.readLine();
		String secondLine = in.readLine();

		if (compare(firstLine, secondLine)) {
			out.println("GO");
		} else {
			out.println("STAY");
		}

		in.close();
		out.close();
	}

}
