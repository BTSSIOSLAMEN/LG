package eu.ensitech.projects;

import eu.ensitech.projects.classes.Game;
import eu.ensitech.projects.gui.Home;

import javax.swing.*;
import java.awt.*;

public class Main {
	private static final int MIN_PLAYER = 8;
	private static final int MAX_PLAYER = 18;

	private static JFrame frame;
	private static Game game;

	public static void main(String[] args) {
		game = new Game();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame("Loup-Garou");
		frame.setBounds(0, 0, screen.width, screen.height);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		new Home();
	}

	public static int getMinPlayer() {
		return MIN_PLAYER;
	}
	public static int getMaxPlayer() {
		return MAX_PLAYER;
	}

	public static JFrame getFrame() {
		return frame;
	}
	public static Game getGame() {
		return game;
	}

	public static void setContentPane(JPanel contentPane) {
		JLabel title = new JLabel("Loup-Garou");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 0, frame.getWidth(), 30);
		title.setFont(new Font("Tahoma", Font.PLAIN, 27));
		contentPane.add(title);

		frame.getContentPane().removeAll();
		frame.getContentPane().add(contentPane);
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
}
