package eu.ensitech.projects.gui;

import eu.ensitech.projects.Main;

import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class RoleReveal {
	private JButton nextBtn;
	private JLabel player;

	public RoleReveal() {
		initialize();
	}

	private void initialize() {
		JPanel contentPane = new JPanel();
    	contentPane.setLayout(null);
    	Dimension size = Main.getFrame().getSize();
    	contentPane.setSize(size);
		
		JLabel titleFrame = new JLabel("Attribution des rôles");
		titleFrame.setHorizontalAlignment(SwingConstants.CENTER);
		titleFrame.setFont(new Font("Tahoma", Font.BOLD, 28));
		titleFrame.setBounds(0, 60, size.width, 40);
		titleFrame.setBackground(Color.DARK_GRAY);
        titleFrame.setOpaque(true);
        titleFrame.setForeground(Color.WHITE);
        contentPane.add(titleFrame, BorderLayout.NORTH);
		
		JButton roleRevealBtn = new JButton("Cliquez ici pour découvrir votre rôle");
		roleRevealBtn.setBounds(size.width / 2 - 250, 280, 500, 100);
		roleRevealBtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		roleRevealBtn.setForeground(Color.DARK_GRAY);
		roleRevealBtn.setBackground(Color.WHITE);
		roleRevealBtn.setOpaque(true);
		roleRevealBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, true));
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
		player.setFont(new Font("Tahoma", Font.BOLD, 20));
		player.setHorizontalAlignment(SwingConstants.CENTER);
		player.setBounds(0, 200, size.width, 40);
		
		nextBtn = new JButton("Suivant");
		nextBtn.setBounds(size.width / 2 - 150, size.height - 200, 300, 80);
		nextBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		nextBtn.addActionListener(e -> reloadVisual());

		reloadVisual();
		contentPane.add(nextBtn);
		contentPane.add(player);
		
		JLabel msgLbl = new JLabel("Les autres joueurs : fermez les yeux !");
		msgLbl.setFont(new Font("Tahoma", Font.ITALIC, 20));
		msgLbl.setHorizontalAlignment(SwingConstants.CENTER);
		msgLbl.setBounds(0, 400, size.width, 40);
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
