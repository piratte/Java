/* Pouzijte implicitni nepojmenovany balicek, tj. nepouzijte "package" */

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** DU 1. !!! Nemente jmeno tridy !!!
	* 
	* @author Martin ADAM
	*/
public class CodEx {
	// "globalni" promenne
	static BufferedReader input;
	static int rowLen;

	static void debug(String slovo) {
		System.out.println("[DEBUG]: " + slovo);
	}


	static class Queue<Item> implements Iterable<Item>{
		private int len;
		private Node first;
		private Node last;

		private class Node {
			private Item item;
			private Node next;
		}

		public Queue() {
			first = null;
			last = null;
			len = 0;
		}

		public boolean isEmpty() {
			return first == null;
		}

		public int size() {
			return len;
		}

		public void put(Item item) {
			Node x = new Node();
			x.item = item;
			if (isEmpty()) { first = x;     last = x; }
			else           { last.next = x; last = x; }
			len++;
		}

		public Item get() {
			if (isEmpty()) throw new RuntimeException("Queue is Empty");
			Item item = first.item;
			first = first.next;
			len--;
			// to avoid pointer panic
			if (isEmpty()) last = null;
			return item;
		}

		public Item popLast() {
			if (isEmpty()) throw new RuntimeException("Queue is Empty");
			Node cur = first;
			Item out;
			while (cur.next != last)
				cur = cur.next;
			// v cur je predposledni prvek
			cur.next = null;
			out = last.item;
			last = cur;
			len--;
			return out;
		}

		public void dump() {
			first = null;
			last = null;
			len = 0;
		}

		public int lineLen(Boolean sMez) {
			int out = 0;
			for (Item i : this) {
				out += i.toString().length();
			}
			if (sMez)
				out += len -1;
			return out;
		}

		public String toString() {
			String out = "";
			for (Item item : this) {
				out += item.toString() + " ";
			}
			// out = out.substring(out(0,out.length()-1));
			return out.substring(0,out.length()-1);
		}

		public void printOut() {
			//debug("kratke: " + this.toString() );
			if (!isEmpty()) {
				System.out.println(this.toString());
			}
		}

		 public Iterator<Item> iterator()  {
			return new Iterator<Item>() {
				private Node current = first;

				public boolean hasNext()  { return current != null;                     }
				public void remove()      { throw new UnsupportedOperationException();  }

				public Item next() {
					if (!hasNext()) throw new NoSuchElementException();
					Item item = current.item;
					current = current.next; 
					return item;
				}
			};

		}
	}

	static void DoplnMezeryATiskni(Queue<String> radek, int lineLen) {
		// pro jedno dlouhe slovo
		// debug("dlouhe: lineLen: " + lineLen + " radek: " + radek.toString());
		if (radek.size() == 1) {
			System.out.println(radek.toString());
			return;
		}

		String out = "";

		// kolik celkem znaku bude mezera
		int mezer = rowLen-lineLen;

		// kolik bude delka mezery (celkem / pocet mezer)
		int delMez = mezer / (radek.size() - 1);
		// debug("mezer: " + mezer + " delMez " + delMez);

		// kolikrat budu zleva pridavat
		int navic = mezer - (radek.size() - 1) * delMez;
		// debug("navic: " + navic);

		String mezery = "";

		for (int i=0; i<delMez; i++)
			mezery += " ";

		String slovo = "";
		int end = radek.size() - 1;
		for (int i=0; i < end; i++) {
			slovo = radek.get();
			out += slovo + mezery;
			if (navic-- > 0)
				out += " ";
		}
		out += radek.get();
		System.out.println(out);

	}

	public static void main(String[] argv) {
		rowLen = 0;
		try {
			input = new BufferedReader(new InputStreamReader(System.in));

			// nacteni delky radku
			try {
				rowLen = Integer.parseInt(input.readLine());
			} catch (NumberFormatException ex) {
				System.out.println("Error");
				System.exit(0);
			}
			// debug("rowLen: " + rowLen);

			int i; char ch; 
			Boolean bylNL = true;
			String slovo = "";

			Queue<String> radek = new Queue<String>();

			// while not EOF
			while ((i = input.read()) != -1) {
				// TODO!!!!
				ch = (char) i;
				if (!Character.isWhitespace(ch)) {
					slovo += ch;
					bylNL = false;
				} else /* ch je whitespace */ { 
					if (slovo.length() != 0)
						radek.put(slovo);

					// 2 newliny oddeleny jenom Ws
					if (ch == '\n' && bylNL) {
						// vypisu radek bez doplnovani mezer, pokud je prazdny, nic se nestane
						radek.printOut();
						System.out.println();
						radek.dump();
						continue;
					} else if (ch == '\n') {
						bylNL = true;
					} 

					// do radku pribylo slovo, jeli jiz dost dlouhy, vypis
					if (radek.lineLen(true) >= rowLen) {
						Boolean dontPop = (radek.lineLen(true) != rowLen);
						if (dontPop)
							radek.popLast();
						DoplnMezeryATiskni(radek, radek.lineLen(false));
						radek.dump();
						if (dontPop)
							radek.put(slovo);
					}
					slovo = "";
				}
			}

			// vypis posledni radek
			radek.printOut();
		} catch (IOException ex) {
			System.out.println("Nastala IOException");
		}
	}
}
