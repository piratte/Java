package lec10.src.madam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Hello {
	private static void createAndShowGUI() {
		 JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("HelloWorldSwing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(200, 200));

		Container pane = frame.getContentPane();
		//pane.setLayout(new GridLayout(2, 2));
		
		JLabel label = new JLabel("Hello World");
		pane.add(label);


		JButton but = new JButton("Exit");
		but.addActionListener(e->System.exit(0));
		pane.add(but, BorderLayout.SOUTH);

		JMenuBar menu = new JMenuBar();
		JMenu m = new JMenu("Main");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(e->System.exit(0));
		m.add(exit);
		menu.add(m);
		frame.add(menu, BorderLayout.NORTH);

		frame.pack();
		frame.setVisible(true);
	}


	public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable()
		{
			public void run() {
				createAndShowGUI();
			}
		});
	}
}