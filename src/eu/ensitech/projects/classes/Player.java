package eu.ensitech.projects.classes;

import eu.ensitech.projects.utils.Role;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int id;
    private final Role role;
    private boolean alive = true;

    public Player(int id, Role role) {
        this.id = id;
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

    public static class Seer extends Player {
        private final List<Player> seenPlayers = new ArrayList<>();

        public Seer(int id) {
            super(id, Role.SEER);
        }

        public List<Player> getSeenPlayers() {
            return seenPlayers;
        }
    }
}
