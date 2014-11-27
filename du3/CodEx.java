/* Pouzijte implicitni nepojmenovany balicek, tj. nepouzijte "package" */

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** DU 3. 
	* 
	* @author Martin ADAM
	*/
public class CodEx {
	static BufferedReader input;
	static Queue<Integer> stack = new Queue<Integer>();
	static final String ERROR = "ERROR";
	static final int newln = (int)'\n';

	/* pouziji mirne osekanou Queue z minuleho reseni, kdyz uz jsem ji psal pro
	 * obecny typ
	 */
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
			if (len == 1) {
				Item out = first.item;
				this.dump();
				return out;
			}
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

		public String toString() {
			String out = "stack: ";
			for (Item item : this) {
				out += item.toString() + " ";
			}
			return out.substring(0,out.length()-1);
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

	static void debug(String slovo) {
		System.out.println("[DEBUG]: " + slovo);
	}

	static String pridej(String kam, String co) {
		return kam + " " + co; // + ""
	}

	static String zpracujVyraz(String vyraz) {
		String[] split = vyraz.split("\\s+");
		String out = "";
		char op = ' ';
		double d = 0.0;
		for (String atom : split) {
			try {
				d = Double.parseDouble(atom);
				// vime ze na vstupu je cislo, to staci napsat na vystup
				out = pridej(out, atom);
			} catch (NumberFormatException e) {
				// mame na vstupu operator
				if (atom.length() > 1)
					throw new RuntimeException("dvojmistnej operator");

				// mame na vstupu validni operato TODO

				op = atom[0];
				switch (op) {
					case
					
				}
			}
		}

		return vyraz;
	}

	static double vyhodnot(String vyraz) {

		return 1;
	}

	public static void main(String[] argv) {
		try {
			input = new BufferedReader(new InputStreamReader(System.in));
			int i;
			char ch;
			String vyraz = "";

			while ((i = input.read()) != -1) {
				ch = (char) i;
				if (ch == '\n') {
					try {
						// prevedu na postfix
						vyraz = zpracujVyraz(vyraz);
						// vyhodnotim a vypisu hodnotu
						System.out.printf("%.5f\n", vyhodnot(vyraz));
					} catch (RuntimeException e) {
						System.out.println(ERROR);
					}
					System.out.println(vyraz);
					vyraz = "";
				} else 
					vyraz += ch;
			}


			if (!vyraz.equals("")) {
				try {
					System.out.printf("%.5f\n", vyhodnot(vyraz));
				} catch (RuntimeException e) {
					System.out.println(ERROR);
				}
			}
			input.close();
			
		} catch (IOException ex) {
			System.out.println("Nastala IOException");
		} 
	}
}