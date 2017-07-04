
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
import java.util.ArrayList;
import java.util.List;

public class beadsDadOptimized {
	public static void main(String args[]) throws IOException {
		final BufferedReader in = new BufferedReader(new FileReader("beads.in"));
		final PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));

		final int numbeads = Integer.parseInt((in.readLine()));
		final char[] necklace = in.readLine().toCharArray();

		optimize(necklace, numbeads);

		final List<Integer> cuts = findCuts(necklace, numbeads);

		int maxLength = 0;

		Result lastRightResult = new Result(0, 0);

		for (final Integer cut : cuts) {
			// iterate through each bead at a potential cut point
			// try counting left and counting right
			// keep track of the combined score at each bead
			final Result resultRight = walkSameColor(cut, cut, necklace, numbeads, Direction.RIGHT);

			// System.out.println("cut: " + cut);
			// System.out.println(resultRight);

			final int result = resultRight.count + lastRightResult.count;

			if (result > maxLength) {
				maxLength = result;
			}

			if (resultRight.endingIndex != lastRightResult.endingIndex) {
				lastRightResult = resultRight;
			}
		}
		;

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

		@Override
		public String toString() {
			return "endingIndex: " + endingIndex + ", count: " + count;
		}
	}

	static enum Direction {
		LEFT(-1), RIGHT(1);

		final int value;

		private Direction(final int value) {
			this.value = value;
		}
	}

	static void optimize(char[] necklace, int numbeads) {
		final Result firstColorBead = nextColorBead(-1, 0, necklace, numbeads);

		if (firstColorBead.endingIndex == 0) {
			// all 'w'
			return;
		}

		int lastIndex = firstColorBead.endingIndex;
		char lastBeadColor = necklace[firstColorBead.endingIndex];

		do {
			final Result nextColorBead = nextColorBead(lastIndex, firstColorBead.endingIndex, necklace, numbeads);
			char atBead = necklace[nextColorBead.endingIndex];

			// change 'w' in between same colors to that color
			if (atBead == lastBeadColor) {
				for (int i = 1; i < nextColorBead.count; i++) {
					necklace[nextIndex(lastIndex, i, Direction.RIGHT, numbeads)] = lastBeadColor;
				}
			}

			lastIndex = nextColorBead.endingIndex;
			lastBeadColor = necklace[nextColorBead.endingIndex];
		} while (lastIndex != firstColorBead.endingIndex);
	}

	static List<Integer> findCuts(char[] necklace, int numbeads) {
		final List<Integer> cuts = new ArrayList<>();
		// always cut at index 0 (can be optimized later)
		cuts.add(0);

		final Result firstChange = nextChange(0, 0, necklace, numbeads);

		if (firstChange.endingIndex == 0) {
			// all same color
			return cuts;
		}

		cuts.add(firstChange.endingIndex);
		int lastIndex = firstChange.endingIndex;

		do {
			final Result next = nextChange(lastIndex, lastIndex, necklace, numbeads);
			cuts.add(next.endingIndex);
			lastIndex = next.endingIndex;
		} while (lastIndex != firstChange.endingIndex);

		return cuts;
	}

	/**
	 * Walk the chain and find the next definitive color bead (i.e., 'b' or 'r')
	 * 
	 * @param startingIndex
	 * @param stopIndex
	 * @param necklace
	 * @param numbeads
	 * @return
	 */
	static Result nextColorBead(int startingIndex, int stopIndex, char[] necklace, int numbeads) {
		for (int i = 1; i < numbeads; i++) {
			int atIndex = nextIndex(startingIndex, i, Direction.RIGHT, numbeads);
			char atBead = necklace[atIndex];

			if (atBead != 'w') {
				return new Result(atIndex, i);
			}
		}

		return new Result(startingIndex, 0);
	}

	static Result nextChange(int startingIndex, int stopIndex, char[] necklace, int numbeads) {
		final char startingBead = necklace[startingIndex];

		for (int i = 1; i < numbeads; i++) {
			int atIndex = nextIndex(startingIndex, i, Direction.RIGHT, numbeads);
			char atBead = necklace[atIndex];

			if (atBead != startingBead) {
				return new Result(atIndex, i);
			}
		}

		// all of the beads are the same
		return new Result(startingIndex, 0);
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
	static Result walkSameColor(final int startingIndex, final int stopIndex, final char[] necklace, final int numbeads,
			final Direction direction) {
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
