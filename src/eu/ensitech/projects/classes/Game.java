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

    private int nightCount = 0;
    private int dayCount = 0;

    public int getPlayerCount() {
        return playerCount;
    }
    public int getVoteAutoTimer() {
        return voteAutoTimer;
    }
    public List<Player> getPlayers() {
        return players;
    }

    public int getNightCount() {
        return nightCount;
    }
    public int getDayCount() {
        return dayCount;
    }

    public Player getPlayerById(int id) {
        for (Player player : players)
            if (player.getId() == id)
                return player;

        return null;
    }

    public Player.Seer getSeer() {
        for (Player player : players)
            if (player.getRole().equals(Role.SEER))
                return (Player.Seer) player;

        throw new IllegalStateException();
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

        Player player;
        if (role.equals(Role.SEER)) {
            player = new Player.Seer();
        } else {
            player = new Player(role);
        }
        players.add(player);

        roleManager.getDispatchedRoles().replace(role, roleManager.getDispatchedRoles().get(role) + 1);
        return player;
    }

    public boolean isFinish() {
        return getWinner() != null;
    }

    public Role getWinner() {
        int aliveWerewolfCount = 0;
        int aliveOtherCount = 0;

        for (Player player : players) {
            if (!player.isAlive()) continue;

            if (player.getRole().equals(Role.WEREWOLF))
                aliveWerewolfCount++;
            else
                aliveOtherCount++;
        }

        if (aliveWerewolfCount == 0)
            return Role.VILLAGER;
        else if (aliveWerewolfCount >= aliveOtherCount)
            return Role.WEREWOLF;

        return null;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        this.roleManager = new RoleManager(playerCount);
    }
    public void setVoteAutoTimer(int voteAutoTimer) {
        this.voteAutoTimer = voteAutoTimer;
    }

    public void addNight() {
        this.nightCount++;
    }
    public void addDay() {
        this.dayCount++;
    }
}
