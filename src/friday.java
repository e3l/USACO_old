/*
 * ID: ethan.w1
 * LANG: Java
 * TASK: friday
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class friday {
	// @SuppressWarnings("serial")
	public static void main(String[] args) throws Exception {
		// Set up reader and writer
		BufferedReader in = new BufferedReader(new FileReader("friday.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));

		// sat, sun, mon, tue, wed, thur, fri
		int[] frequency = { 0, 0, 0, 0, 0, 0, 0 };

		// Set up year, month, and day
		int year = 1900;
		int day = 2; // 1900-1-1 is a monday
		int startMonth = 0;

		// Weekdays moved ahead per month
		int wkdaysMvdAhead = 0;

		// Read "n", which defines what year to stop at
		int n = Integer.parseInt(in.readLine());

		// Counts how many months
		int totalmonths = n * 12;

		// Counts the first jump to the 13th
		wkdaysMvdAhead = 12 % 7;
		day += wkdaysMvdAhead;
		day = day % 7;

		frequency[day]++;
		startMonth++;

		// Find out how many of each days of the week there is for the 13th.
		for (int x = 0; x < totalmonths - 1; x++) {
			// Check what day 13th is
			if (startMonth == 2) {
				// feb
				if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
					wkdaysMvdAhead = 29 % 7;
				} else {
					wkdaysMvdAhead = 28 % 7;
				}
			} else {
				// long short months
				if (startMonth == 4 || startMonth == 6 || startMonth == 9 || startMonth == 11) {
					wkdaysMvdAhead = 30 % 7;
				} else {
					wkdaysMvdAhead = 31 % 7;
				}
			}

			// Move forward week days (from mon-tues, tues-wed, etc.)
			day += wkdaysMvdAhead;
			day = day % 7;

			frequency[day]++;

			// Move up months
			if (startMonth == 11) {
				startMonth = 0;
				year++;
			} else {
				startMonth++;
			}
		}

		out.println(frequency[0] + " " + frequency[1] + " " + frequency[2] + " " + frequency[3] + " "
				+ frequency[4] + " " + frequency[5] + " " + frequency[6]);

		in.close();
		out.close();
	}
}
