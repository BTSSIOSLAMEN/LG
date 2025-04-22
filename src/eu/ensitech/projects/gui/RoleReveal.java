package eu.ensitech.projects.gui;

import eu.ensitech.projects.Main;

import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;

public class RoleReveal {
	private JButton nextBtn;
	private JLabel player;

	public RoleReveal() {
		initialize();
	}

	private void initialize() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setLocation(0, 0);
		contentPane.setSize(Main.getFrame().getSize());
		
		JLabel titleFrame = new JLabel("Attribution des rôles");
		titleFrame.setForeground(new Color(0, 0, 0));
		titleFrame.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		titleFrame.setHorizontalAlignment(SwingConstants.CENTER);
		titleFrame.setBounds(6, 0, 988, 80);
		contentPane.add(titleFrame);
		
		JButton roleRevealBtn = new JButton("Cliquez ici pour découvrir votre rôle");
		roleRevealBtn.setBounds(300, 300, 400, 100);
		contentPane.add(roleRevealBtn);
		roleRevealBtn.addActionListener(e -> {
            JFrame roleFrame = new JFrame("Votre rôle");
            roleFrame.setSize(400, 200);
            roleFrame.setLocationRelativeTo(null);
            roleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            roleFrame.setLayout(new BorderLayout());

            JLabel roleLbl = new JLabel("Vous êtes " + Main.getGame().getPlayers().get(Main.getGame().getPlayers().size() - 1).getRole().getDisplayName() + " !");
            roleLbl.setFont(new Font("Lucida Grande", Font.BOLD, 18));
            roleLbl.setHorizontalAlignment(SwingConstants.CENTER);
            roleFrame.add(roleLbl, BorderLayout.CENTER);

            JButton closeBtn = new JButton("Fermer");
            closeBtn.addActionListener(e1 -> roleFrame.dispose());
            roleFrame.add(closeBtn, BorderLayout.SOUTH);

            roleFrame.setVisible(true);
            nextBtn.setEnabled(true);
        });

		player = new JLabel();
		player.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		player.setHorizontalAlignment(SwingConstants.CENTER);
		player.setBounds(6, 200, 988, 60);
		
		nextBtn = new JButton("Suivant");
		nextBtn.setBounds(300, 420, 400, 50);
		nextBtn.addActionListener(e -> reloadVisual());

		reloadVisual();
		contentPane.add(nextBtn);
		contentPane.add(player);
		
		JLabel msgLbl = new JLabel("Les autres joueurs : fermez les yeux !");
		msgLbl.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		msgLbl.setHorizontalAlignment(SwingConstants.CENTER);
		msgLbl.setBounds(6, 500, 988, 80);
		contentPane.add(msgLbl);

		Main.setContentPane(contentPane);
	}

	private void reloadVisual() {
		if (Main.getGame().getPlayers().size() < Main.getGame().getPlayerCount()) {
			Main.getGame().createPlayer();

			player.setText("Joueur " + (Main.getGame().getPlayers().size()));
			nextBtn.setEnabled(false);
			if (Main.getGame().getPlayers().size() == Main.getGame().getPlayerCount()) {
				nextBtn.setText("Commencer le jeu");
			}
		} else {
			// The game start here
			new Night();
		}
	}
}
