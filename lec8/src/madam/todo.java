package lec8.src.madam;

import java.io.*;
import java.util.*;

public class todo {
	static List<Item> todos;
	static String file;

	private static class Item {
		public int priority;
		public String what;

		public Item(String what, int priority) {
			this.what = what;
			this.priority = priority;
		}

		public String toString() {
			return what + " " + priority;
		}
	}

	static void processLine(String line) {
		String[] arr = line.split("\\s+");
		int p = 0; 
		try {
			p = Integer.parseInt(arr[1]);
		} catch (Exception e) {
			System.err.println("spatna radka");
		}
		todos.add(new Item(arr[0], p));
	}

	public static void main(String[] args) {
		todos = new ArrayList<Item>();
		String dir = System.getProperty("user.home");
		file = dir + "/.todojava";
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        }
        catch (FileNotFoundException x) {
            File f = new File(file);
            try {
				f.createNewFile();
			} catch (IOException xx) {
	        	System.err.println(xx.toString() + " " + file);
	        }
        } catch (IOException x) {
        	System.err.println(x.toString());
        }

		char sw = args[0].charAt(1);
		switch (sw) {
			case 'a': insert(args[2], args[1]); break;
			case 'l': list(-1); break;
			case 'r': list(1); break;
			case 'd': delete(args[1]); break;
			default:
				System.err.println("Invalid parameter");
				break;
		}
	}

	static void insert(String priority, String what) {
		processLine(what + " " + priority);
		write();
	}

	static void list(int how) {
		Collections.sort(todos, (i1,i2) -> i1.priority - i2.priority * how);
		for (Item i : todos) {
			System.out.println("[] " + i.toString());
		}
	}

	static void delete(String s) {
		ListIterator l = todos.listIterator();
		Item cur;
		while (l.hasNext()){
			cur = (Item)l.next();
			if (cur.what.equals(s))
				l.remove();
		}
		write();
	}

	static void write() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (Item i : todos ) {
				bw.write(i.toString());
				bw.write("\n");
			}
		} catch (IOException e) {
			System.err.println("cannot open file for write");
			System.exit(1);
		}
	}

}