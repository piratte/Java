package lec9.src.madam;

import java.net.*;
import java.io.*;

public class client {
	public static void main(String[] args) {
		InetAddress addr;
		try {
			addr = InetAddress.getByName(null);
		} catch (UnknownHostException uh) {
			System.err.println(uh.toString())
			return;
		}

		try (Socket socket = new Socket(addr, 6666)){

		} catch
	}
}
