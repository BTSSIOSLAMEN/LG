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
    	Dimension size = Main.getFrame().getSize();
    	contentPane.setSize(size);

    	JLabel welcome = new JLabel("Bienvenue sur le jeu du Loup-Garou ");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setFont(new Font("Tahoma", Font.BOLD, 28));
        welcome.setBounds(0, 60, size.width, 40);
        contentPane.add(welcome);
        
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/eu/ensitech/projects/assets/img/werewolf.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel werewolfImg = new JLabel(resizedIcon);
        werewolfImg.setBounds(size.width / 2 - 125, 90, 250, 250);
        contentPane.add(werewolfImg);

        JLabel playerCountChooserLabel = new JLabel("Nombre de joueurs :");
        playerCountChooserLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        playerCountChooserLabel.setBounds(size.width / 2 - 100, 360, 200, 30);
        contentPane.add(playerCountChooserLabel);

        JComboBox<Integer> playerCountChooser = new JComboBox<>();
        for (int i = Main.getMinPlayer(); i <= Main.getMaxPlayer(); i++) {
            playerCountChooser.addItem(i);
        }
        playerCountChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
        playerCountChooser.setBounds(size.width / 2 - 50, 400, 100, 30);
        contentPane.add(playerCountChooser);

        JLabel maxVoteTimeLabel = new JLabel("Temps maximum de vote (secondes, optionnel) :");
        maxVoteTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        maxVoteTimeLabel.setBounds(size.width / 2 - 200, 460, 400, 30);
        contentPane.add(maxVoteTimeLabel);

        JTextField maxVoteTime = new JTextField();
        maxVoteTime.setFont(new Font("Tahoma", Font.PLAIN, 16));
        maxVoteTime.setBounds(size.width / 2 - 50, 500, 100, 30);
        contentPane.add(maxVoteTime);

        JLabel maxVoteTimeError = new JLabel("⚠ La valeur doit être un entier supérieur à 0 ⚠");
        maxVoteTimeError.setFont(new Font("Tahoma", Font.PLAIN, 14));
        maxVoteTimeError.setForeground(Color.RED);
        maxVoteTimeError.setBounds(size.width / 2 - 150, 540, 300, 20);
        maxVoteTimeError.setVisible(false);
        contentPane.add(maxVoteTimeError);

        JButton play = new JButton("JOUER");
        play.setFont(new Font("Tahoma", Font.BOLD, 28));
        play.setBounds(size.width / 2 - 150, size.height - 200, 300, 80);
        contentPane.add(play);
        
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

        Main.setContentPane(contentPane);
    }
}
