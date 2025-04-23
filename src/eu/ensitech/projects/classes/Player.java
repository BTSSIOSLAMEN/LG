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

    public Player(Role role) {
        this.id = idFactory.incrementAndGet();
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public Role getRole() {
        return role;
    }
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public static void resetIdFactory() {
        idFactory.set(-1);
    }

    public static class Seer extends Player {
        private final List<Player> seenPlayers = new ArrayList<>();

        public Seer() {
            super(Role.SEER);
        }

        public List<Player> getSeenPlayers() {
            return seenPlayers;
        }
        
        public boolean hasSeenAllAlive(List<Player> players) {
            return players.stream()
                          .filter(Player::isAlive)
                          .allMatch(seenPlayers::contains);
        }
    }
}
