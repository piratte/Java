package src.madam;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class soubory {

	private static void VypisStreamy(String from, String to) throws IOException {
		FileInputStream in = null; FileOutputStream out = null;
		try {
			in = new FileInputStream(from);
			out = new FileOutputStream(to);

			int c;

			while ((c = in.read()) != -1) 
				out.write(c);
		}
		finally {
				if (in != null) 
	            	in.close();
	         	if (out != null) 
	                out.close();
	         
		}
	}

	private static void VypisNeo(String from, String to) throws FileNotFoundException, IOException {
		RandomAccessFile in = new RandomAccessFile(from, "r");
		RandomAccessFile out =  new RandomAccessFile(to, "rw");

		FileChannel inChan = in.getChannel();
		FileChannel outChan = out.getChannel();

		long start = 0; long end = inChan.size();
		outChan.transferFrom(inChan, start, end);
	}

	private static void Copy(String from, String to) throws IOException {
		File source = new File(from);
		File dest = new File(to);
		Files.copy(source.toPath(), dest.toPath());
	}

	public static void main(String[] args) throws IOException {
		if (args.length < 3) {
			System.err.println("Usage: ./soubory FROM TO [1 or 2 or 3]");
		}
		String from, to;
		int method = Integer.parseInt(args[2]);
		from = args[0]; to = args[1];

		switch (method) {
			case 1: VypisStreamy(from, to);
					break;
			case 2: VypisNeo(from, to);
					break;
			case 3: Copy(from, to);
					break;
			default:  System.err.println("Unsupported method");
		}
		
	}
}