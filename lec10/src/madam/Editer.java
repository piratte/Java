package lec10.src.madam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Editer {

	public static JTextArea textArea;
	public static String text;
	public static String file;
	public static boolean notsaved;
	public static JFrame frame;
	final public static String SAVED = "Editer";
	final public static String UNSAVED = "Editer *";

	public static void safe() {
		if (notsaved) {
			System.err.println("Ukladam...");
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				System.err.println("Zapisuji do " + file);
				bw.write(textArea.getText());
				frame.setTitle(SAVED);
			} catch (IOException e) {
				System.err.println("cannot open file for write");
			}
		}
	}

	private static void createAndShowGUI() {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame(SAVED);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setMinimumSize(new Dimension(1000, 500));

		Container pane = frame.getContentPane();
		//pane.setLayout(new GridLayout(2, 2));

		textArea = new JTextArea(text);
		textArea.setLineWrap(false);
		textArea.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					notsaved = true;
					frame.setTitle(UNSAVED);
				}
			});
		pane.add(new JScrollPane(textArea));

		JButton but = new JButton("Exit");
		but.addActionListener(e->{safe();System.exit(0);});
		pane.add(but, BorderLayout.SOUTH);

		JMenuBar menu = new JMenuBar();
		JMenu m = new JMenu("Main");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e->{safe();System.exit(0);});
		save.addActionListener(e->safe());
		m.add(save);
		m.add(exit);
		menu.add(m);
		frame.add(menu, BorderLayout.NORTH);

		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent winEv){
					safe();
					System.exit(0);
				}
			});
		frame.pack();
		frame.setVisible(true);
	}


	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("give me tha FILE!!!");
			System.exit(1);
		}
		text = "";
		String line = "";
		file = args[0];
		try(BufferedReader input = new BufferedReader(new FileReader(file))){
			while ((line = input.readLine()) != null) {
				text += line + "\n";
			}
		} catch (IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}



		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}