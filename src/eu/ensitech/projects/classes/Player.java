package eu.ensitech.projects.classes;

import eu.ensitech.projects.utils.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {

    private static final AtomicInteger idFactory = new AtomicInteger(-1);

    private final int id;
    private final Role role;
    private boolean alive = true;

    // Constructeur
    public Player(Role role) {
        this.id = idFactory.incrementAndGet();
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }
    public Role getRole() {
        return role;
    }
    public boolean isAlive() {
        return alive;
    }

    // Setters
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // Réinitialise l'id des joueurs
    public static void resetIdFactory() {
        idFactory.set(-1);
    }

    // Sous-classe de Player pour la voyante
    public static class Seer extends Player {
        // Liste des joueurs déjà vus par la voyante
        private final List<Player> seenPlayers = new ArrayList<>();

        // Constructeur
        public Seer() {
            super(Role.SEER);
        }

        // Retourne les joueurs vu par la voyante
        public List<Player> getSeenPlayers() {
            return seenPlayers;
        }

        // Vérifie si la voyante a déjà vu tous les joueurs vivants
        public boolean hasSeenAllAlive(List<Player> players) {
            return players.stream()
                          .filter(Player::isAlive)
                          .allMatch(seenPlayers::contains);
        }
    }
}
