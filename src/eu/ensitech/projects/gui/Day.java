package eu.ensitech.projects.gui;

import eu.ensitech.projects.Main;
import eu.ensitech.projects.classes.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day {
    private enum Step {
        DEBATE, VOTE, POST_VOTE, END
    }
    private Step step = Step.DEBATE;

    private JPanel contentPane;
    private JPanel playerList;
    private JLabel information;
    private JButton next;

    private Timer autoVoteTimer;
    private Player playerInVote;

    private Player votedPlayer;
    private final HashMap<Player, Integer> votes = new HashMap<>();

    public Day() {
        Main.getGame().addDay();
        initialize();
    }

    public void initialize() {
        if (Main.getGame().isFinish()) {
            new End();
            return;
        }

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setSize(Main.getFrame().getSize());
        Dimension size = Main.getFrame().getSize();
        contentPane.setSize(size);
        
        JLabel titleFrame = new JLabel("JOUR");
		titleFrame.setHorizontalAlignment(SwingConstants.CENTER);
		titleFrame.setFont(new Font("Tahoma", Font.BOLD, 28));
		titleFrame.setBounds(0, 60, size.width, 40);
		titleFrame.setBackground(Color.DARK_GRAY);
        titleFrame.setOpaque(true);
        titleFrame.setForeground(Color.WHITE);
        contentPane.add(titleFrame, BorderLayout.NORTH);

        information = new JLabel("Le village débat pour éliminer une personne !");
        information.setHorizontalAlignment(SwingConstants.CENTER);
        information.setBounds(0, 150, contentPane.getWidth(), 30);
        information.setFont(new Font("Tahoma", Font.PLAIN, 22));
        contentPane.add(information);
        
        next = new JButton("Finir le débat");
        next.setFont(new Font("Tahoma", Font.PLAIN, 24));
        next.setBounds(size.width / 2 - 150, size.height - 200, 300, 80);
        next.setEnabled(true);
        next.addActionListener(e -> nextStep());
        contentPane.add(next);
        
        if (Main.getGame().getVoteAutoTimer() != 0) {
        	next.setEnabled(false);
            autoVoteTimer = new Timer(1000, new ActionListener() {
                int i = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    information.setText("Le village débat pour éliminer une personne ! Temps restant : " + (Main.getGame().getVoteAutoTimer() - i) + "s");

                    if (++i > Main.getGame().getVoteAutoTimer()) {
                        autoVoteTimer.stop();
                        next.setEnabled(true);
                        nextStep();
                    }
                }
            });
            autoVoteTimer.start();
        }

        setPlayerList();
        Main.setContentPane(contentPane);
    }

    private void nextStep() {
        if (step == Step.DEBATE) {
            step = Step.VOTE;

            information.setText("Débat terminé ! Démarrage du vote...");
            next.setText("Voter");
            timedNextStep();
        } else if (step == Step.VOTE) {
            List<Player> alivePlayers = Main.getGame().getPlayers().stream().filter(Player::isAlive).collect(Collectors.toList());

            if (playerInVote == null) {
                playerInVote = alivePlayers.get(0);
            } else {
                if (!votes.containsKey(votedPlayer))
                    votes.put(votedPlayer, 0);
                votes.replace(votedPlayer, votes.get(votedPlayer) + 1);

                List<Player> remainingPlayers = alivePlayers.stream().filter(p -> p.getId() > playerInVote.getId()).collect(Collectors.toList());
                if (remainingPlayers.isEmpty()) {
                    step = Step.END;
                    information.setText("Vote terminé ! Calcul en cours...");
                    next.setEnabled(false);
                    timedNextStep();
                    return;
                }

                playerInVote = remainingPlayers.get(0);
            }

            information.setText("Au tour du joueur " + (playerInVote.getId() + 1) + " de voter");
            next.setEnabled(false);

            votedPlayer = null;
            setPlayerList();
        } else if (step == Step.END) {
            Player votedPlayer = null;
            int maxVote = 0;

            for (Map.Entry<Player, Integer> vote : votes.entrySet()) {
                if (vote.getValue() > maxVote) {
                    maxVote = vote.getValue();
                    votedPlayer = vote.getKey();
                }
            }

            if (votedPlayer == null)
                throw new IllegalStateException();

            int count = 0;
            for (Map.Entry<Player, Integer> vote: votes.entrySet())
                if (vote.getValue() == maxVote)
                    count++;

            if (count > 1) {
                information.setText("Egalité ! Le vote est annulé.");
            } else {
                votedPlayer.setAlive(false);
                setPlayerList();

                information.setText("Le joueur " + (votedPlayer.getId() + 1) + " a été tué");
            }

            if (Main.getGame().isFinish()) {
                new End();
                return;
            }

            Timer t = new Timer(4000, e -> {
                if (!Main.getGame().isFinish())
                    new Night();
                else
                    new End();
            });
            t.setRepeats(false);
            t.start();
        }
    }

    private void setPlayerList() {
        if (playerList != null)
            contentPane.remove(playerList);

        playerList = Main.getPlayerList(new PlayerButtonEvent(this));
        playerList.setLocation(25, 300);
        contentPane.add(playerList);
    }

    private void timedNextStep() {
        Timer t = new Timer(2000, e -> nextStep());
        t.setRepeats(false);
        t.start();
    }

    static class PlayerButtonEvent implements ActionListener {
        private final Day day;

        public PlayerButtonEvent(Day day) {
            this.day = day;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            Player player = Main.parsePlayer(button);
            if (!player.isAlive())
                return;

            if (day.step == Step.VOTE) {
                if (day.votedPlayer != null)
                    Main.findButtonByPlayer(day.votedPlayer, day.playerList).setBackground(Color.WHITE);

                button.setBackground(Color.YELLOW);
                day.next.setEnabled(true);
                day.votedPlayer = player;
            }
        }
    }
}
