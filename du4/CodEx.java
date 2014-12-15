/* Pouzijte implicitni nepojmenovany balicek, tj. nepouzijte "package" */

import java.io.*;
import java.util.*;

/** DU 4. !!! Nemente jmeno tridy !!!
  * 
  * @author !!! DOPLN SVE JMENO !!!
  */
public class CodEx {

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

	static String sloz(String[] arr) {
		String out = "";
		for (String s : arr) {
			out += s + " ";
		}
		return out;
	}

	/* |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 *											MAIN
	 *  |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 */

	static Queue<Integer> stack = new Queue<Integer>();
	static final String ERROR = "ERROR";
	static HashMap<String,Funkce> funMap = new HashMap<String,Funkce>();
	static HashMap<String,Double> varMap = new HashMap<String,Double>();

	public static void main(String[] argv) {

		final boolean test = true;
		String line;
		String[] lineArr;
		final double lastDefVal = 0.0;

		// =============== ZACATEK TESTU ============================
		line = "DEF foo(a,b,c) 2*a + 2*b -c";
		lineArr = parse(line);
		line = line.replaceAll("x", "" + 123);
		System.out.println(line);
		Funkce blah = new Funkce(lineArr);
		String[] args = parse("30 last 20");
		for (String s : args ) {
			debug(s);
		}
		System.out.println(blah.dosad(args));

		// =============== KONEC TESTU ============================
		if (!test) {

		varMap.put("last",lastDefVal);
		double val = lastDefVal;
		try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))){
			while ((line = input.readLine()) != null) {

				// osetreni prazdne radky
				if (line.trim().length() == 0)
					continue;

				lineArr = parse(line);

				if (lineArr[0].equals("DEF")) {
					// definijeme si funkci
					// TODO: dodelat ERROR v definici funkce
					// je potreba? nestacilo by pri kazdem volani spatne definovane fce hodit ERROR?
					funMap.put(zjistiJmeno(lineArr[1]), new Funkce(lineArr));
					varMap.put("last",lastDefVal);
					continue;
				}

				if (funMap.containsKey(lineArr[0])) {
					// nasli jsme identifikator ve funkcich, nyni misto funkce dosadime jeji definici

					// vytahneme to, co se dosazuje za parametry
					int parNum = funMap.get(lineArr[0]).getNumOfArgs();
					String[] params = new String[parNum];
					int j = 0;
					for (int i=2; i<parNum; i += 2) {
						params[j++] = lineArr[i];
					}

					// zavolame metodu na funkci, ktera dosadi za deklarovane argumenty konkretni parametry a vrati vyraz,
					// ktery odpovida zavolani funkce
					line = funMap.get(lineArr[0]).dosad(params);
				}

				// substituuj a definuj novy promenny

				line = sloz(lineArr);

				// nyni mame vyraz jako v minulem ukolu -> pouziju jeho reseni
				try {				
						// prevedu na postfix
						line = zpracujVyraz(line);

						// vyhodnotim a vypisu hodnotu
						val = vyhodnot(line);
						System.out.printf("%.5f\n", val);
						
					} catch (RuntimeException e) {
						//System.err.println("main error: " + e);
						System.out.println(ERROR);
						val = lastDefVal;
					}
					varMap.put("last", val);

			}
		} catch(IOException ioex) {

		}
		}
	}

	static String zjistiJmeno(String in) {
		return in.substring(0,in.indexOf('('));
	}

	/* |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 *											FUNCTION CLASS
	 *  |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	 */

	static class Funkce {
		String def;
		int argc;
		char[] argv;

		public Funkce(String[] definice) {
			int i=0; boolean chyba=true;
			for (i=0; i<definice.length ; ++i) {
				if(definice[i].equals(")")) {
					chyba = false;
					break;
				}
			}
			if (chyba)
				throw new RuntimeException("Wrong definition, missing bracket");

			argc = (i - 2)/2;
			argv = new char[argc];
			i = 3;
			for (int j=0; j<argc ; ++j) {
				argv[j] = definice[i].charAt(0);
				i+=2;
			}
			def = "";
			for (int k = i; k< definice.length ; ++k) {
				def = pridej(def,definice[k]);
			}

		}

		public String dosad(String[] args) {
			debug(argc + " " + args.length);
			if (args.length != argc)
				throw new RuntimeException("Wrong number of params");

			String out = def;

			for (int i=0; i<args.length ; ++i) {
				out = out.replaceAll(""+argv[i], args[i]);
			}
			return out;
		}

		public int getNumOfArgs(){
			return argc;
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

