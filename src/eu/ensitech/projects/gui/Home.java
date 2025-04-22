package eu.ensitech.projects.gui;

import eu.ensitech.projects.Main;

import javax.swing.*;
import java.awt.*;

public class Home {
    public Home() {
        initialize();
    }

    private void initialize() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setLocation(0, 0);
        contentPane.setSize(Main.getFrame().getSize());

        JLabel welcome = new JLabel("Bienvenue sur le jeu du Loup-Garou");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBounds(0, 120, contentPane.getWidth(), 30);
        welcome.setFont(new Font("Tahoma", Font.PLAIN, 22));
        contentPane.add(welcome);

        JLabel playerCountChooserLabel = new JLabel("Nombre de joueurs :");
        playerCountChooserLabel.setBounds(contentPane.getWidth() / 2 - 70, 450, 250, 20);
        playerCountChooserLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(playerCountChooserLabel);

        JComboBox<Integer> playerCountChooser = new JComboBox<>();
        DefaultComboBoxModel<Integer> playerCountChoices = new DefaultComboBoxModel<>();
        for (int i = Main.getMinPlayer(); i <= Main.getMaxPlayer(); i++) {
            playerCountChoices.addElement(i);
        }
        playerCountChooser.setModel(playerCountChoices);
        playerCountChooser.setBounds(contentPane.getWidth() / 2 - 40, 480, 100, 20);
        playerCountChooser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(playerCountChooser);

        JLabel maxVoteTimeLabel = new JLabel("Temps maximum de vote en secondes (optionnel) :");
        maxVoteTimeLabel.setBounds(contentPane.getWidth() / 2 - 180, 550, 450, 20);
        maxVoteTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(maxVoteTimeLabel);

        JTextField maxVoteTime = new JTextField();
        maxVoteTime.setBounds(contentPane.getWidth() / 2 - 40, 580, 100, 20);
        maxVoteTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
        contentPane.add(maxVoteTime);

        JLabel maxVoteTimeError = new JLabel("La valeur entrée doit être un nombre entier supérieur à 0");
        maxVoteTimeError.setBounds(contentPane.getWidth() / 2 - 100, 600, 450, 20);
        maxVoteTimeError.setForeground(Color.RED);
        maxVoteTimeError.setVisible(false);
        contentPane.add(maxVoteTimeError);

        JButton play = new JButton("JOUER");
        play.setFont(new Font("Tahoma", Font.PLAIN, 27));
        play.setBounds(contentPane.getWidth() / 2 - 150, 800, 300, 150);
        play.addActionListener(event -> {
            Main.getGame().setPlayerCount((Integer) playerCountChooser.getSelectedItem());
            if (!maxVoteTime.getText().isEmpty()) {
                try {
                    int val = Integer.parseInt(maxVoteTime.getText());
                    if (val <= 0) {
                        maxVoteTimeError.setVisible(true);
                        return;
                    }

                    Main.getGame().setVoteAutoTimer(Integer.parseInt(maxVoteTime.getText()));
                } catch (NumberFormatException exception) {
                    maxVoteTimeError.setVisible(true);
                }
            }
            new RoleReveal();
        });
        contentPane.add(play);

        Main.setContentPane(contentPane);
    }
}
