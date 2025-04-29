package eu.ensitech.projects.managers;

import eu.ensitech.projects.utils.Role;

import java.util.HashMap;
import java.util.Map;

public class RoleManager {
    private final int nbPlayer;
    private final Map<Role, Integer> dispatchedRoles = new HashMap<>();

    // Constructeur
    public RoleManager(int nbPlayer) {
        this.nbPlayer = nbPlayer;
        for (Role role : Role.values())
            dispatchedRoles.put(role, 0);
    }

    // Nombre de villageois
    public int getVillagersCount() {
        return nbPlayer - 3;
    }

    // Nombre de loups-garous
    public int getWerewolfCount() {
        if (nbPlayer >= 12)
            return 3;

        return 2;
    }

    // Vérifie si tous les rôles sont dispatchés
    public boolean roleIsEntirelyDispatch(Role role) {
        switch (role) {
            case VILLAGER: return dispatchedRoles.get(Role.VILLAGER) == getVillagersCount();
            case WEREWOLF: return dispatchedRoles.get(Role.WEREWOLF) == getWerewolfCount();
            case SEER: return dispatchedRoles.get(Role.SEER) == 1;
            default: throw new IllegalArgumentException();
        }
    }

    // Renvoie la carte des rôles dispatchés
    public Map<Role, Integer> getDispatchedRoles() {
        return dispatchedRoles;
    }
}
