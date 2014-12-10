package lec9.src.madam;

import java.io.*;
import java.util.*;
import java.net.*;

public class server {
	static class ServeCon extends Thread {
		private Socket s; 
		private BufferedReader in;
		private PrintWriter out;
		public ServeCon(Socket ss) throws IOException {
			s = ss;
			in = new BufferedReader(new InputStreamReader( s.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		}

		public void run() {
			String com; String[] args;
			try {
				com = in.readLine();
				args = com.split("\\s+");
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
			} catch (IOException e) {
				System.err.println(e.toString());
			}
			try {
				s.close();
			} catch (IOException e) {
				System.err.println("chyba pri zavirani socketu");
			}
		}
		void insert(String priority, String what) {
			processLine(what + " " + priority);
			write();
		}

		void list(int how) {
			Collections.sort(todos, (i1,i2) -> i1.priority - i2.priority * how);
			for (Item i : todos) {
				out.println("[] " + i.toString());
			}
		}

		synchronized void delete(String s) {
			ListIterator l = todos.listIterator();
			Item cur;
			while (l.hasNext()){
				cur = (Item)l.next();
				if (cur.what.equals(s))
					l.remove();
			}
			write();
		}

		synchronized void write() {
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

	static List<Item> todos;
	static String file;

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
        ServeCon sc;

		try (ServerSocket s = new ServerSocket(6969)) {
			while(true) {
				Socket soc = s.accept();
				try {
					sc = new ServeCon(soc);
					sc.start();
				} catch (IOException x) {
					System.err.println(x.toString());
				}
			}
		} catch (IOException ex) {
			System.err.println("Socket fail: " + ex.toString());
		}
	}
}

	
