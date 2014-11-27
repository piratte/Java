/* Pouzijte implicitni nepojmenovany balicek, tj. nepouzijte "package" */

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** DU 2. 
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

	public static void main(String[] argv) {
		try {
			input = new BufferedReader(new InputStreamReader(System.in));

			int i, tmp, num;
			char ch;
			boolean wasPushed = true;
			boolean docti = false;

			num = 0; tmp = 0;
			while ((i = input.read()) != -1) {
				if (docti && i!= newln) 
					continue;
				
				docti = false;
				ch = (char) i;

				if (ch == '\n') {
					if (!wasPushed) {
						stack.put(num);
						wasPushed = true;
					}
					if (stack.isEmpty())
						continue;
					if (stack.size()>1) 
						System.out.println(ERROR);
					 else {
						// byl konec radku a vse je v poradku, vypisujeme
						System.out.println(stack.popLast().toString());
					}
					stack.dump();
					continue;
				}

				if (Character.isWhitespace(ch)) {
					if (!wasPushed) {
						stack.put(num);
						wasPushed = true;
					}
					continue;
				}

				if (Character.isDigit(ch)) {
					if (wasPushed) {
						num = 0;
						wasPushed = false;
					}
					tmp = Character.getNumericValue(ch);
					num *= 10; num += tmp;
					continue;
				}

				if (ch == '+' || ch == '-' || ch == '*' || ch == '/' ) {
					if (!wasPushed) {
						System.out.println(ERROR);
						docti = true;
						continue;
					}
					try {
						int y = stack.popLast();
						int x = stack.popLast();
						switch (ch) {
							case '+': stack.put(x + y); break;
							case '-': stack.put(x - y); break;
							case '*': stack.put(x * y); break;
							case '/': stack.put(x / y); break;
							default: throw new UnsupportedOperationException();
						}
					} catch (RuntimeException e) {
						System.out.println(ERROR);
					}
					continue;
				}
				// pokud se beh programu dostane az sem, je nacten spatny znak 
				System.out.println(ERROR);
				docti = true;
			}

			if (!stack.isEmpty())
				System.out.println(ERROR);



			input.close();
		} catch (IOException ex) {
			System.out.println("Nastala IOException");
		} 
	}
}