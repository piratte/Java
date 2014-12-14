/* Pouzijte implicitni nepojmenovany balicek, tj. nepouzijte "package" */

import java.io.*;
import java.util.*;

/** DU 4. !!! Nemente jmeno tridy !!!
  * 
  * @author !!! DOPLN SVE JMENO !!!
  */
public class CodEx {

	static Queue<Integer> stack = new Queue<Integer>();
	static final String ERROR = "ERROR";
	static HashMap<String,Funkce> funMap = new HashMap<String,Funkce>();


	static String[] parse(String str) {
		String[] strs = str.split("(?<=[^\\.a-zA-Z\\d])|(?=[^\\.a-zA-Z\\d])");
		ArrayList<String> parts = new ArrayList<String>();
		Collections.addAll(parts, strs);
		Iterator<String> sIter = parts.iterator();
		while (sIter.hasNext()) {
			String string = sIter.next();
			if (string.trim().length() == 0) {
				sIter.remove();
			}
		}
		int i = 0;
		String[] out = new String [parts.size()];
		for (String s : parts) {
			out[i++] = s;
		}
		return out;
	}

		static void debug(String slovo) {
		System.out.println("[DEBUG]: " + slovo);
	}

	static String pridej(String kam, String co) {
		return kam + " " + co; // + ""
	}

		static String pridej(String kam, char co) {
		return kam + " " + co; // + ""
	}

	static String zpracujVyraz(String vyraz) {
		String[] split = vyraz.split("\\s+");
		String out = "";
		char op = ' ';
		char last;
		double d = 0.0;
		Queue<Character> stack = new Queue<Character>();
		for (String atom : split) {
			try {
				d = Double.parseDouble(atom);
				// vime ze na vstupu je cislo, to staci napsat na vystup
				out = pridej(out, atom);
			} catch (NumberFormatException e) {
				// mame na vstupu operator
				if (atom.length() > 1)
					throw new RuntimeException("dvojmistnej operator");

				// mame na vstupu validni operato
				op = atom.charAt(0);
				switch (op) {
					// operatory s nejnizsi prioritou
					case '+':
					case '-':
						// pokud je na vrcholu operator s vyssi prioritou, vypiseme
						try {
							while (stack.showLast() == '/' || stack.showLast() == '*')
								out = pridej(out, stack.popLast());
						} catch (RuntimeException ee) {} finally {
							stack.put(op);
						}
						break;

					// operatory s vyssi prioritou
					case '*':
					case '/':
						stack.put(op); 
						break;

					// zavorky
					case '(': stack.put(op); break;
					case ')':
						while((last = stack.popLast()) != '(')
							out = pridej(out, last);
						break;
					default: throw new UnsupportedOperationException("neplatny operator");
				}
			}
		}
		// docetli jsme vstup, vypiseme operatory na vystup
		
		while (!stack.isEmpty()) {
			if ((last = stack.popLast()) == '(') {
				throw new RuntimeException("spatne uzavorkovani");
			} else {
				out = pridej(out, last);
			}
		}

		return out;
	}

	static double vyhodnot(String vyraz) {
		String[] split = vyraz.split("\\s+");
		Queue<Double> stack = new Queue<Double>();
		double d = 0.0;
		char op = ' ';

		for (int i = 1; i < split.length; i++) {
			try {
				//debug("test " + split[i]);
				d = Double.parseDouble(split[i]);
				stack.put(d);
			} catch (NumberFormatException e) {
				// dalsi cur je operator, vime ze validni
				//debug("vyhodnot: " + stack.toString());
				op = split[i].charAt(0);
				try {
					double y = stack.popLast();
					double x = stack.popLast();
					switch (op) {
						case '+': stack.put(x + y); break;
						case '-': stack.put(x - y); break;
						case '*': stack.put(x * y); break;
						case '/': stack.put(x / y); break;
						default: throw new UnsupportedOperationException();
					}
				} catch (RuntimeException ee) {
					//System.err.println("vyhodnot: " + ee);
					throw new RuntimeException("Nedostatek operandu");
				}
			}
		}
		if (stack.size() > 1)
			throw new RuntimeException("Stack neni prazdny");

		return stack.popLast();
	}

	public static void main(String[] argv) {


		
		String line;
		String[] lineArr;
		try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
			while ((line = input.readLine()) != null) {
				lineArr = parse(line);

				if (lineArr[0].equals("DEF")) {
					
				}
			}
		} catch(IOException ioex) {

		}
		
	}

	/* |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 *											FUNCTION CLASS
	 *  |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 */

	static String zjistiJmeno(String in) {
		return in.substring(0,in.indexOf('('));
	}

	static class Funkce {
		String def;
		int argc;
		char[] argv;

		public Funkce(String[] definice) {
			def = "";
			for (int i = 2; i< definice.length ; ++i) {
				def = pridej(def,definice[i]);
			}
			String argy = definice[1].substring(definice[1].indexOf('('));

			argc = definice[1].split(",").length;
			argv = new char[argc];
			int j = 0;
			for (int i=1; i < argc*2; i+=2) {
				argv[j] = argy.charAt(i);
				++j;
			}

		}

		public String Dosad(double[] args) {
			// zkontroluj spravny pocet argumentu

			// vymen argumenty z definice za cisla z args
			return "fuck";
		}
	}
	/* |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 *											QUEUE CLASS
	 *  |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
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
			if (isEmpty()) throw new RuntimeException("Queue is Empty, method: get()");
			Item item = first.item;
			first = first.next;
			len--;
			// to avoid pointer panic
			if (isEmpty()) last = null;
			return item;
		}

		public Item popLast() {
			if (isEmpty()) throw new RuntimeException("Queue is Empty, method: popLast()");
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

		public Item showLast() {
			if (isEmpty()) throw new RuntimeException("Queue is Empty, method: showLast()");
			return last.item;
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
}

