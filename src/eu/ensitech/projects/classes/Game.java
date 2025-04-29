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

    // Getters
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

    // Retourne le joueur correspondant à l'id
    public Player getPlayerById(int id) {
        for (Player player : players)
            if (player.getId() == id)
                return player;

        return null;
    }

    // Retourne le joueur étant la voyante
    public Player.Seer getSeer() {
        for (Player player : players)
            if (player.getRole().equals(Role.SEER))
                return (Player.Seer) player;

        throw new IllegalStateException();
    }

    // Création d'un joueur et attribution d'un rôle aléatoire
    public Player createPlayer() {
        //        We generate an int to get a role for the new player
        //        0 -> VILLAGER
        //        1 -> WEREWOLF
        //        2 -> SEER
        Random r = new Random();
        int randomRole = r.nextInt(3);
        Role role = Role.values()[randomRole];

        // Si le rôle est déjà dispatché, on en tire un autre
        if (roleManager.roleIsEntirelyDispatch(role))
            return createPlayer();

        // Création du joueur avec le rôle
        Player player;
        if (role.equals(Role.SEER)) {
            player = new Player.Seer();
        } else {
            player = new Player(role);
        }

        // On l'ajoute à la liste des joueurs
        players.add(player);

        // Mise à jour de la carte des rôles dispatchés
        roleManager.getDispatchedRoles().replace(role, roleManager.getDispatchedRoles().get(role) + 1);
        return player;
    }

    // Fin de la partie si un joueur a gagné
    public boolean isFinish() {
        return getWinner() != null;
    }

    // Vérifie si l'on a un gagnant
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
