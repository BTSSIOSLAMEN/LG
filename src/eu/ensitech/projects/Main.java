package eu.ensitech.projects;

import eu.ensitech.projects.classes.Game;
import eu.ensitech.projects.classes.Player;
import eu.ensitech.projects.gui.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main {
	private static final int MIN_PLAYER = 8;
	private static final int MAX_PLAYER = 18;

	private static JFrame frame;
	private static Game game;

	// Lancement de l'application
	public static void main(String[] args) {
		start();
	}

	// Démarrage du jeu
	public static void start() {
		game = new Game();

		// Création de la fenêtre
		if (frame == null) {
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			frame = new JFrame("Loup-Garou");
			frame.setBounds(0, 0, screen.width, screen.height);
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.getContentPane().setLayout(null);
			frame.setVisible(true);
		}

		new Home();
	}

	// Getters
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

	// Header de la fenêtre
	public static void setContentPane(JPanel contentPane) {
		JLabel title = new JLabel("Loup-Garou");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 15, frame.getWidth(), 30);
		title.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(title);

		JLabel gameInfo = new JLabel("Jour : " + game.getDayCount() + " | Nuit : " + game.getNightCount());
		gameInfo.setBounds(0, 15, frame.getWidth() - 35, 30);
		gameInfo.setFont(new Font("Tahoma", Font.PLAIN, 24));
		gameInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(gameInfo);

		frame.getContentPane().removeAll();
		frame.getContentPane().add(contentPane);
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	// Grille des boutons des joueurs
	public static JPanel getPlayerList(ActionListener listener) {
		JPanel panel = new JPanel();
		panel.setSize(frame.getWidth() - 70, 200);

		GridLayout layout = new GridLayout();
		layout.setHgap(70);
		layout.setRows(3);
		layout.setVgap(35);
		panel.setLayout(layout);

		int i = 1;
		for (Player player : Main.getGame().getPlayers()) {
			JButton button = new JButton("Joueur " + i + (!player.isAlive() ? " - " + player.getRole().getDisplayName() : ""));
			button.setForeground(player.isAlive() ? Color.BLACK : Color.RED);
			button.setBackground(player.isAlive() ? Color.WHITE : Color.GRAY);
			button.addActionListener(listener);
			button.setSize(70, 10);
			button.setActionCommand(String.valueOf(player.getId()));
			panel.add(button);
			i++;
		}

		return panel;
	}

	// Identification du joueur à partir du bouton
	public static Player parsePlayer(JButton btn) {
		String actionCommand = btn.getActionCommand();
		int id = Integer.parseInt(actionCommand);
		return Main.getGame().getPlayerById(id);
	}

	// Identification du bouton à partir du joueur
	public static JButton findButtonByPlayer(Player player, JPanel panel) {
		for (Component component : panel.getComponents()) {
			if (!(component instanceof JButton))
				continue;

			JButton btn = (JButton) component;
			Player parsePlayer = Main.parsePlayer(btn);
			if (player == null)
				continue;

			if (parsePlayer.getId() == player.getId())
				return btn;
		}

		return null;
	}
}
