
/*
ID: ethan.w1
LANG: JAVA
TASK: beads
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class beadsDad {
	public static void main(String args[]) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("beads.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));

		int numbeads = Integer.parseInt((in.readLine()));
		char[] necklace = in.readLine().toCharArray();

		int maxLength = 0;

		for (int i = 0; i < numbeads; i++) {
			// iterate through each char at a potential cut point
			// try counting left and counting right
			// keep track of the combined score at each char
			int result = countLeftAndRight(i, necklace, numbeads);

			if (result > maxLength) {
				maxLength = result;
			}
		}

		System.out.println(maxLength);

		// return the index of the maximum count
		out.println(maxLength);

		in.close();
		out.close();
	}

	/////////////////////////
	// PRIVATE METHODS
	/////////////////////////
	static int countLeftAndRight(int i, char[] necklace, int numbeads) {
		return countLeft(i, necklace, numbeads) + countRight(i, necklace, numbeads);
	}

	static int countRight(int i, char[] necklace, int numbeads) {
		char startColor = necklace[i];
		// 1 is for the startBead
		return 1 + count(i, startColor, necklace, numbeads, 1);
	}

	static int countLeft(int i, char[] necklace, int numbeads) {
		// counting left does not care about starting color
		return count(i, 'w', necklace, numbeads, -1);
	}

	/**
	 * 
	 * @param i
	 *            - the current index position
	 * @param startColor
	 *            - the color of the starting bead for this count
	 * @param necklace
	 *            - the necklace
	 * @param numbeads
	 *            - length of the necklace
	 * @param direction
	 *            - which direction to count? left=-1, right=1
	 * @return the length of the continuous sequence of same color beads
	 */
	static int count(int i, char startColor, char[] necklace, int numbeads, int direction) {
		int count = 0;
		char sequenceColor = startColor;

		// decrement index and count until the color changes
		for (int j = 1; j <= numbeads; j++) {
			int atIndex = (i + direction * j + numbeads) % numbeads;
			char atBead = necklace[atIndex];
			if (sequenceColor == 'w' || atBead == sequenceColor || atBead == 'w') {
				// increment count then keep going
				count++;

				// if we started with a 'w' and this is not a 'w' then set this
				// as the sequence color
				if (sequenceColor == 'w' && atBead != 'w') {
					sequenceColor = atBead;
				}
			} else {
				// we've reached a different color, stop counting
				return count;
			}
		}

		// the entire necklace is the same color
		return count;
	}
}
