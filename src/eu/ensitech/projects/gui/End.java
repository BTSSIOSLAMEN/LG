package eu.ensitech.projects.gui;

import eu.ensitech.projects.Main;
import eu.ensitech.projects.classes.Player;
import eu.ensitech.projects.utils.Role;

import javax.swing.*;
import java.awt.*;

public class End {
    public End() {
        initialize();
    }

    public void initialize() {
    	JPanel contentPane = new JPanel();
    	contentPane.setLayout(null);
    	Dimension size = Main.getFrame().getSize();
    	contentPane.setSize(size);
		
		JLabel titleFrame = new JLabel("Fin de la partie");
		titleFrame.setHorizontalAlignment(SwingConstants.CENTER);
		titleFrame.setFont(new Font("Tahoma", Font.BOLD, 28));
		titleFrame.setBounds(0, 60, size.width, 40);
		titleFrame.setBackground(Color.DARK_GRAY);
        titleFrame.setOpaque(true);
        titleFrame.setForeground(Color.WHITE);
        contentPane.add(titleFrame, BorderLayout.NORTH);

        JLabel information = new JLabel(Main.getGame().getWinner().equals(Role.WEREWOLF) ? "Les loup-garous ont gagné !" : "Le village a gagné !");
        information.setHorizontalAlignment(SwingConstants.CENTER);
        information.setBounds(0, 150, contentPane.getWidth(), 30);
        information.setFont(new Font("Tahoma", Font.BOLD, 24));
        contentPane.add(information);

        JButton replay = new JButton("Rejouer");
        replay.setBounds(size.width / 2 - 150, size.height - 200, 300, 80);
        replay.setFont(new Font("Tahoma", Font.PLAIN, 24));
        replay.addActionListener((e) -> {
            Player.resetIdFactory();
            Main.start();
        });
        contentPane.add(replay);

        setPlayerList(contentPane);
        Main.setContentPane(contentPane);
    }

    private void setPlayerList(JPanel contentPane) {
        JPanel playerList = Main.getPlayerList(e -> {});
        playerList.setLocation(25, 300);
        contentPane.add(playerList);
    }
}
