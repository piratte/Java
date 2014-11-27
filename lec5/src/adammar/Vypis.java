package src.adammar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Vypis {
	public static void main(String[] args) {
 		try {
 			if (args.length != 2) {
 				System.out.println("Usage: Vypis FILENAME LINELENTH");	
 				System.exit(0);
 			}
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			final int lineLen = Integer.parseInt(args[1]);
			
			String line;
			char ch;
			String out = //MyString out = new MyString();
			String word;
 			while ((line = br.readLine()) != null) {
				for (int i=0; i < line.length(); i++) {
					ch = line.charAt(i);
					if (Character.isWhitespace(ch) && word.length() > 0) {
						if (out.length() + word.length() > lineLen){
							System.out.println(out);
							out = word;
						}
						else
							out += " " + word;
						word = "";
					}
					else
						word += ch;
				}
			}


		} catch (IOException ex) {
			System.out.println("Nastala IOException: " + ex);
		}
				
 	}
 }