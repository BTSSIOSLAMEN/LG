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
        contentPane.setLocation(0, 0);
        contentPane.setSize(Main.getFrame().getSize());

        JLabel information = new JLabel(Main.getGame().getWinner().equals(Role.WEREWOLF) ? "Les loup-garous ont gagné !" : "Le village a gagné !");
        information.setHorizontalAlignment(SwingConstants.CENTER);
        information.setBounds(0, 120, contentPane.getWidth(), 30);
        information.setFont(new Font("Tahoma", Font.PLAIN, 22));
        contentPane.add(information);

        JButton replay = new JButton("Rejouer");
        replay.setBounds(contentPane.getWidth() / 2 - 100, 800, 175, 100);
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
