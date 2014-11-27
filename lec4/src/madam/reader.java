package lec4.src.madam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class reader {
	static int  znaku, radku, ws, slov;
	static char prev = ' ';

	private static void zapocitej(int i) {
		char ch = (char) i;

		if (Character.isWhitespace(ch))
			ws++;
		if (Character.isLetterOrDigit(ch))
			znaku++;
		if (ch == '\n')
			radku++;
		if (Character.isLetterOrDigit(prev) && Character.isWhitespace(ch))
			slov++;
		prev = ch;
 	}

 	private static void vypis() {
 		System.out.println("Znaku bylo " + znaku);
 		System.out.println("Slov bylo " + slov);
 		System.out.println("Bilych bylo " + ws);
 		System.out.println("Radku bylo " + radku);
 	}

 	public static void main(String[] args) {
 		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			int c;
			// ws musime pridat kvuli konci souboru
			znaku = 0; radku = 0; ws = 1; slov = 1;

			while ((c = input.read()) != -1) {
				zapocitej(c);
			}


		} catch (IOException ex) {
			System.out.println("Nastala IOException");
		}
		vypis();
 		
 	}
	
}