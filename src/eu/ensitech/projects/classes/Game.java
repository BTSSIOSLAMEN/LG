package eu.ensitech.projects.classes;

import eu.ensitech.projects.managers.RoleManager;
import eu.ensitech.projects.utils.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int playerCount;
    private int voteAutoTimer;
    private final List<Player> players = new ArrayList<>();
    private RoleManager roleManager = new RoleManager(0);

    public int getPlayerCount() {
        return playerCount;
    }
    public int getVoteAutoTimer() {
        return voteAutoTimer;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public Player createPlayer() {
        //        We generate an int to get a role for the new player
        //        0 -> VILLAGER
        //        1 -> WEREWOLF
        //        2 -> SEER
        Random r = new Random();
        int randomRole = r.nextInt(3);
        Role role = Role.values()[randomRole];

        if (roleManager.roleIsEntirelyDispatch(role))
            return createPlayer();

        Player player = new Player(players.size(), role);
        players.add(player);
        roleManager.getDispatchedRoles().replace(role, roleManager.getDispatchedRoles().get(role) + 1);

        return player;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        this.roleManager = new RoleManager(playerCount);
    }
    public void setVoteAutoTimer(int voteAutoTimer) {
        this.voteAutoTimer = voteAutoTimer;
    }
}
