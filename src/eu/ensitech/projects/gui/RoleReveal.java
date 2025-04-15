package eu.ensitech.projects.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;

public class RoleReveal {

	private JFrame frame;
	private JButton nextBtn;
	private JLabel player;
	
	private int currentPlayerIndex = 1;
	private int totalPlayers = 4;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoleReveal window = new RoleReveal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RoleReveal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Attribution des rôles");
		frame.setSize(1000, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel titleFrame = new JLabel("Attribution des rôles");
		titleFrame.setForeground(new Color(0, 0, 0));
		titleFrame.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		titleFrame.setHorizontalAlignment(SwingConstants.CENTER);
		titleFrame.setBounds(6, 0, 988, 80);
		frame.getContentPane().add(titleFrame);
		
		player = new JLabel("Joueur " + currentPlayerIndex);
		player.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		player.setHorizontalAlignment(SwingConstants.CENTER);
		player.setBounds(6, 200, 988, 60);
		frame.getContentPane().add(player);
		
		JButton roleRevealBtn = new JButton("Cliquez ici pour découvrir votre rôle");
		roleRevealBtn.setBounds(300, 300, 400, 100);
		frame.getContentPane().add(roleRevealBtn);
		roleRevealBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	JFrame roleFrame = new JFrame("Votre rôle");
		    	roleFrame.setSize(400, 200);
		    	roleFrame.setLocationRelativeTo(null);
		    	roleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    	roleFrame.setLayout(new BorderLayout());

		        JLabel roleLbl = new JLabel("Vous êtes ... !");
		        roleLbl.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		        roleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		        roleFrame.add(roleLbl, BorderLayout.CENTER);

		        JButton closeBtn = new JButton("Fermer");
		        closeBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	roleFrame.dispose();
		            }
		        });
		        roleFrame.add(closeBtn, BorderLayout.SOUTH);

		        roleFrame.setVisible(true);
		        nextBtn.setEnabled(true);
		    }
		});
		
		nextBtn = new JButton("Suivant");
		nextBtn.setBounds(300, 420, 400, 50);
		nextBtn.setEnabled(false);
		frame.getContentPane().add(nextBtn);
		nextBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        currentPlayerIndex++;
		        if (currentPlayerIndex <= totalPlayers) {
		            player.setText("Joueur " + currentPlayerIndex);
		            nextBtn.setEnabled(false);
		            if (currentPlayerIndex == totalPlayers) {
		                nextBtn.setText("Commencer le jeu");
		            }
		        } else {
		            // The game start here
		        	frame.dispose();
		        }
		    }
		});
		
		JLabel msgLbl = new JLabel("Les autres joueurs : fermez les yeux !");
		msgLbl.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		msgLbl.setHorizontalAlignment(SwingConstants.CENTER);
		msgLbl.setBounds(6, 500, 988, 80);
		frame.getContentPane().add(msgLbl);
	}
}
