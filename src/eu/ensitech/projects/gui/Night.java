package eu.ensitech.projects.gui;

import eu.ensitech.projects.Main;
import eu.ensitech.projects.classes.Player;
import eu.ensitech.projects.utils.AudioUtils;
import eu.ensitech.projects.utils.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Night {

    // Étapes de la nuit
    private enum Step {
        START, SEER, SEER_END, WEREWOLF, WEREWOLF_END, END
    }
    private Step step = Step.START;

    private Player selectedPlayer;

    private JPanel contentPane;
    private JPanel playerList;
    private JLabel information;
    private JButton next;

    public Night() {
        Main.getGame().addNight();
        initialize();
    }

    private void initialize() {
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setSize(Main.getFrame().getSize());
        Dimension size = Main.getFrame().getSize();
        contentPane.setSize(size);
        
        JLabel titleFrame = new JLabel("NUIT");
		titleFrame.setHorizontalAlignment(SwingConstants.CENTER);
		titleFrame.setFont(new Font("Tahoma", Font.BOLD, 28));
		titleFrame.setBounds(0, 60, size.width, 40);
		titleFrame.setBackground(Color.DARK_GRAY);
        titleFrame.setOpaque(true);
        titleFrame.setForeground(Color.WHITE);
        contentPane.add(titleFrame, BorderLayout.NORTH);

        information = new JLabel("Le village s'endort !");
        information.setHorizontalAlignment(SwingConstants.CENTER);
        information.setBounds(0, 150, contentPane.getWidth(), 30);
        information.setFont(new Font("Tahoma", Font.PLAIN, 22));
        contentPane.add(information);

        next = new JButton("Suivant");
        next.setFont(new Font("Tahoma", Font.PLAIN, 24));
        next.setBounds(size.width / 2 - 150, size.height - 200, 300, 80);
        next.setEnabled(false);
        next.addActionListener(e -> nextStep());
        contentPane.add(next);

        setPlayerList();
        AudioUtils.playSound("sleep");
        Main.setContentPane(contentPane);
        timedNextStep();
    }

    private void nextStep() {

        // Étape de la voyante
        if (step == Step.START) {
        	Player.Seer seer = Main.getGame().getSeer();

            // Si la voyante est morte ou a déjà vu tous les joueurs vivants, on passe à l'étape suivante
        	if (!seer.isAlive() || seer.hasSeenAllAlive(Main.getGame().getPlayers())) {
        	    step = Step.SEER_END;
        	    nextStep();
        	    return;
        	}
            
            step = Step.SEER;

            information.setText("La voyante se réveille !");
            AudioUtils.playSound("seer");

            // On affiche le rôle de la voyante sur le bouton
            for (Player player : Main.getGame().getPlayers())
                if (player.getRole().equals(Role.SEER)) {
                    JButton button = Main.findButtonByPlayer(player, playerList);
                    displayRole(button, player);
                    button.setEnabled(false);
                }
            
            next.setText("Valider");

            // On affiche le rôle des joueurs déjà vus par la voyante
            for (Player seenPlayer : Main.getGame().getSeer().getSeenPlayers()) {
                if (seenPlayer.isAlive()) {
                	JButton button = Main.findButtonByPlayer(seenPlayer, playerList);
                    displayRole(Main.findButtonByPlayer(seenPlayer, playerList), seenPlayer);
                    button.setEnabled(false);
                }
            }

        } else if (step == Step.SEER) {
            step = Step.SEER_END;

            information.setText("La voyante se rendort !");
            selectedPlayer = null;
            setPlayerList();
            next.setEnabled(false);

            timedNextStep();

        // Étape des loups-garous
        } else if (step == Step.SEER_END) {
            step = Step.WEREWOLF;

            information.setText("Les loup-garous se réveillent !");
            AudioUtils.playSound("werewolf");

            // On affiche le rôle des loups-garous sur le bouton
            for (Player player : Main.getGame().getPlayers())
                if (player.getRole().equals(Role.WEREWOLF))
                    displayRole(Main.findButtonByPlayer(player, playerList), player);

        } else if (step == Step.WEREWOLF) {
            step = Step.WEREWOLF_END;

            information.setText("Les loup-garous se rendorment !");
            selectedPlayer.setAlive(false);
            selectedPlayer = null;
            setPlayerList();
            next.setEnabled(false);

            timedNextStep();

        // Fin de la nuit
        } else if (step == Step.WEREWOLF_END) {
            step = Step.END;

            information.setText("Le village se réveille !");
            AudioUtils.playSound("awake");

            timedNextStep();
        } else if (step == Step.END) {
            // Passer au jour
            new Day();
        }
    }

    // Afficher la liste des joueurs
    private void setPlayerList() {
        if (playerList != null)
            contentPane.remove(playerList);

        playerList = Main.getPlayerList(new PlayerButtonEvent(this));
        playerList.setLocation(25, 300);
        contentPane.add(playerList);
    }

    // Afficher le rôle du joueur sur le bouton
    private void displayRole(JButton btn, Player player) {
        btn.setText(btn.getText() + " - " + player.getRole().getDisplayName());
    }

    // Timer de 5 secondes
    private void timedNextStep() {
        Timer t = new Timer(5000, e -> nextStep());
        t.setRepeats(false);
        t.start();
    }

    // Événement pour les clicks sur les boutons des joueurs
    static class PlayerButtonEvent implements ActionListener {
        private final Night night;

        // Constructeur
        public PlayerButtonEvent(Night night) {
            this.night = night;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            Player player = Main.parsePlayer(button);

            // On ne fait rien si le joueur est mort
            if (!player.isAlive())
                return;

            if (night.step == Step.SEER) {
                Player.Seer seer = Main.getGame().getSeer();

                // Empêcher la voyante de voir les joueurs déjà vus ou elle-même
                if (night.selectedPlayer != null || seer.getSeenPlayers().contains(player) || player == Main.getGame().getSeer())
                    return;

                night.selectedPlayer = player;
                night.displayRole(button, player);
                seer.getSeenPlayers().add(player);
                night.next.setEnabled(true);

            } else if (night.step == Step.WEREWOLF) {

                if (night.selectedPlayer != null)
                    Main.findButtonByPlayer(night.selectedPlayer, night.playerList).setBackground(Color.WHITE);

                button.setBackground(Color.YELLOW);
                night.next.setEnabled(true);
                night.selectedPlayer = player;
            }
        }
    }
}
