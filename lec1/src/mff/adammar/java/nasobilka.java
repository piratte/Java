package mff.adammar.java;
import mff.adammar.java.*;

public class nasobilka {
	public static void main(String[] args) {
		String radek;
		Integer cur;

		int hranice = 10; 
		if (args.length == 1)
			hranice = Integer.parseInt(args[0]);
		for (int i=1; i <= hranice ; i++ ) {
			radek = "";
			for (int j=1; j <= hranice; j++) {
				cur = i*j;
				if (cur > 99)
					radek += " " + cur.toString() + "|";
				else {
					if (cur > 9)
						radek += "  " + cur.toString() + "|";
					else
						radek += "   " + cur.toString() + "|";
				}
			}
			printer.print(radek);
		}
	}
}