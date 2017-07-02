
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

public class beads {
	public static void main(String args[]) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("beads.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));

		int numbeads = Integer.parseInt((in.readLine()));
		char[] necklace = in.readLine().toCharArray();
		List<Integer> cuts = new ArrayList<Integer>();
		List<Integer> cutDiff = new ArrayList<Integer>();
		int countingDiff = 0;
		
		for (int i = 0; i < numbeads; i++){
			if (i == 0) {
				cuts.add(i);
			}else{
				if (necklace[i-1] != 'w'){
					if (necklace[i] != necklace[i - 1]) {
						cuts.add(i);
						cutDiff.add(countingDiff);
						countingDiff = 0;
					}else{
						countingDiff++;
					}
				}else{
					countingDiff++;
				}
			}
			
			System.out.println(cutDiff.get(0));
		}
		System.out.println(cuts);
		
		in.close();
		out.close();
	}
}
