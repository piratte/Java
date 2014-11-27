package lec4.src.madam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class caesar {
	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(args[0]));
		} 
		catch(FileNotFoundException ex) {
			System.out.println("First param is filename, second is shift");
		}
		final int velABC = 'z' - 'a';
 		int in; char c, por;
 		int posun = Integer.parseInt(args[1]);
 		if (posun < 0)
 			posun = velABC - posun;

 		try {
			while ((in = br.read()) != -1) { 
				c = (char) in;
				if (!Character.isLetter(c))
					continue;
				if (in >= 'a')
					por = 'a';
				else 
					por = 'A';
				if in -= 'A';
				in += posun;

			}

			br.close();
		}
		catch (IOException ex) {
			System.out.println("IOException");
		}
	}
}