
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
		final BufferedReader in = new BufferedReader(new FileReader("beads.in"));
		final PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));

		final int numbeads = Integer.parseInt((in.readLine()));
		final char[] necklace = in.readLine().toCharArray();

		int maxLength = 0;

		for (int i = 0; i < numbeads; i++) {
			// iterate through each char at a potential cut point
			// try counting left and counting right
			// keep track of the combined score at each char
			final int result = countLeftAndRight(i, necklace, numbeads);

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
	// CLASS
	/////////////////////////
	static class Result {
		final int endingIndex;
		final int count;

		Result(final int endingIndex, final int count) {
			this.endingIndex = endingIndex;
			this.count = count;
		}
	}

	static enum Direction {
		LEFT(-1), RIGHT(1);

		final int value;

		private Direction(final int value) {
			this.value = value;
		}
	}

	/////////////////////////
	// METHODS
	/////////////////////////
	/**
	 * 
	 * @param startingIndex
	 *            - the starting index position
	 * @param necklace
	 *            - the necklace
	 * @param numbeads
	 *            - length of the necklace
	 * @return a count of the continuous sequence of same color beads
	 */
	static int countLeftAndRight(final int startingIndex, final char[] necklace, final int numbeads) {
		final Result resultRight = walkSameColor(startingIndex, startingIndex, necklace, numbeads, Direction.RIGHT);
		final Result resultLeft = walkSameColor(startingIndex, resultRight.endingIndex, necklace, numbeads, Direction.LEFT);

		return resultRight.count + resultLeft.count;
	}

	/**
	 * 
	 * @param startingIndex
	 *            - the current index position
	 * @param stopIndex
	 *            - the index *before* which to stop (i.e., the bead at this
	 *            index is excluded)
	 * @param necklace
	 *            - the necklace
	 * @param numbeads
	 *            - length of the necklace
	 * @param direction
	 *            - which direction to count? refer to enum Direction
	 * @param startColor
	 *            - the color of the starting bead for this count
	 * @return the ending index and a count of the continuous sequence of same
	 *         color beads
	 */
	static Result walkSameColor(final int startingIndex, final int stopIndex, final char[] necklace, final int numbeads, final Direction direction) {
		int endingIndex = startingIndex;
		int count = 0;
		char sequenceColor = 'w';
		
		// if walking right, start with the current element; if walking left,
		// start with the previous element
		int j = direction == Direction.RIGHT ? 0 : 1;
		int atIndex = nextIndex(startingIndex, j, direction, numbeads);

		// for walking left, stop immediately if we are already on the stop
		// index
		if (direction == Direction.LEFT && atIndex == stopIndex) {
			return new Result(endingIndex, count);
		}

		// walk index and count until the color changes
		do {
			char atBead = necklace[atIndex];

			// if sequence color has not been set or at 'w' or same color,
			// proceed
			if (sequenceColor == 'w' || atBead == sequenceColor || atBead == 'w') {
				// increment count then keep going
				endingIndex = atIndex;
				count++;

				// if sequence color has not been set and this is not a 'w' then
				// set it as the sequence color
				if (sequenceColor == 'w' && atBead != 'w') {
					sequenceColor = atBead;
				}
			} else {
				// we've reached a different color, stop counting and return
				return new Result(endingIndex, count);
			}

			j++;
			atIndex = nextIndex(startingIndex, j, direction, numbeads);
		} while (atIndex != stopIndex);

		// the entire walk is the same color
		return new Result(endingIndex, count);
	}

	/**
	 * @param startingIndex
	 * @param offset
	 * @param direction
	 * @param numbeads
	 * @return
	 */
	private static int nextIndex(final int startingIndex, int offset, final Direction direction, final int numbeads) {
		return (startingIndex + direction.value * offset + numbeads) % numbeads;
	}
}
