
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
		int endingIndexRight = walkRight(i, necklace, numbeads);
		int countRight = countBeads(i, endingIndexRight, numbeads, 1) + 1;

		if (countRight == numbeads) {
			return countRight;
		}
		
		int endingIndexLeft = walkLeft(i, necklace, numbeads, endingIndexRight);
		int countLeft = countBeads(i, endingIndexLeft, numbeads, -1);

		return countRight + countLeft;
	}

	/**
	 * 
	 * @param i
	 *            - the starting index position
	 * @param necklace
	 *            - the necklace
	 * @param numbeads
	 *            - length of the necklace
	 * @return the ending index position
	 */
	static int walkRight(int i, char[] necklace, int numbeads) {
		return walkSameColor(i, necklace, numbeads, 1, i == 0 ? numbeads - 1 : i - 1);
	}

	/**
	 * 
	 * @param i
	 *            - the starting index position
	 * @param necklace
	 *            - the necklace
	 * @param numbeads
	 *            - length of the necklace
	 * @param stopIndex
	 *            - do not walk beyond this point (to prevent double counting
	 *            the same beads)
	 * @return the ending index position
	 */
	static int walkLeft(int i, char[] necklace, int numbeads, int stopIndex) {
		return walkSameColor(i, necklace, numbeads, -1, stopIndex);
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
	 * @return the ending index of the continuous sequence of same color beads
	 */
	static int walkSameColor(int i, char[] necklace, int numbeads, int direction, int stopIndex) {
		int endingIndex = i;
		char sequenceColor = 'w';
		int j = direction == 1 ? 0 : 1;
		int atIndex = (i + direction * j + numbeads) % numbeads;

		// decrement index and count until the color changes
		do {
			atIndex = (i + direction * j + numbeads) % numbeads;

			char atBead = necklace[atIndex];
			if (sequenceColor == 'w' || atBead == sequenceColor || atBead == 'w') {
				// increment count then keep going
				endingIndex = atIndex;

				// if we started with a 'w' and this is not a 'w' then set this
				// as the sequence color
				if (sequenceColor == 'w' && atBead != 'w') {
					sequenceColor = atBead;
				}
			} else {
				// we've reached a different color, stop counting
				return endingIndex;
			}

			j++;
		} while (atIndex != stopIndex);

		// the entire necklace is the same color
		return endingIndex;
	}

	/**
	 * @param startIndex
	 * @param endingIndex
	 * @param numbeads
	 * @return
	 */
	static int countBeads(int startIndex, int endingIndex, int numbeads, int direction) {
		if (direction == 1) {
			return (endingIndex + numbeads - startIndex) % numbeads;
		} else {
			return startIndex == endingIndex ? numbeads : (startIndex + numbeads - endingIndex) % numbeads;
		}
	}
}
