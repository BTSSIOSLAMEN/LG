package eu.ensitech.projects.classes;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int playerCount;
    private int voteAutoTimer;
    private final List<Player> players = new ArrayList<>();

    public int getPlayerCount() {
        return playerCount;
    }
    public int getVoteAutoTimer() {
        return voteAutoTimer;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
    public void setVoteAutoTimer(int voteAutoTimer) {
        this.voteAutoTimer = voteAutoTimer;
    }
}
