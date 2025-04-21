package eu.ensitech.projects.utils;

public enum Role {
    VILLAGER("Villageois"),
    WEREWOLF("Loup-Garou"),
    SEER("Voyante");

    private final String displayName;
    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
