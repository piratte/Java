package lec4.src.madam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class palindrom {
	public static void main(String[] args) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			int c; char k; String in = ""; String reverse = "";
			Boolean pal = true;
			// ws musime pridat kvuli konci souboru
			while ((c = input.read()) != -1) {
				k = (char) c;
				if (Character.isLetterOrDigit(k))
					in += k;
			}
			in = in.toLowerCase();
			int len = in.length();

			for (int ind = len -1; ind >= 0 ; ind-- ) {
				reverse += in.charAt(ind);
			}
			System.out.println(reverse);
			System.out.println(in);
			if (reverse.equals(in))
				System.out.println("Je palindrom!");
			else
				System.out.println("Neni palindrom!");

		} catch (IOException ex) {
			System.out.println("Nastala IOException");
		}
	}
}