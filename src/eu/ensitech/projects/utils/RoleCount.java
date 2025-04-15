package eu.ensitech.projects.utils;

public class RoleCount {
    private final int nbPlayer;

    public RoleCount(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public int getVillagersCount() {
        return nbPlayer - 3;
    }

    public int getWerewolfCount() {
        if (nbPlayer >= 12)
            return 3;

        return 2;
    }
}
