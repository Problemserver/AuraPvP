package net.problemzone.aurapvp.util;

import org.bukkit.ChatColor;

public enum Language {
    GAME_START("Das Spiel startet in: " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sekunden"),
    GAME_START_TITLE(ChatColor.LIGHT_PURPLE + "Aura " + ChatColor.GRAY + "startet in: " + ChatColor.WHITE),
    GAME_START_CANCEL("Der Spielstart wurde manuell " + ChatColor.RED + "abgebrochen"),
    WARM_UP("Die Schutzzeit endet in " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sekunden"),
    SERVER_CLOSE(ChatColor.RED + "Der Server schließt in: " + ChatColor.YELLOW + "%d" + ChatColor.RED + " Sekunden"),
    NOT_ENOUGH_PLAYERS(ChatColor.RED + "Aura benötigt mehr Spieler!"),
    PLAYER_JOIN(ChatColor.GREEN + "» " + ChatColor.WHITE),
    PLAYER_LEAVE(ChatColor.RED + "« " + ChatColor.WHITE),
    JOIN_MESSAGE(ChatColor.GRAY + "erfolgreich beigetreten"),
    GLOBAL_KILLSTREAK(ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + "%s " + ChatColor.GREEN + "hat eine Killstreak von " + ChatColor.GOLD + "%d " + ChatColor.GREEN + "erreicht!"),
    PLAYER_DEATH_BY_PLAYER(ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + "%s " + ChatColor.GRAY + "wurde von " + ChatColor.GOLD + "%s " + ChatColor.GRAY + "getötet!"),
    CURRENT_PLAYERS(ChatColor.GRAY + "Noch " + ChatColor.GOLD + "%s " + ChatColor.GRAY + "sind am leben."),
    PLAYER_DEATH(ChatColor.GRAY + "Der Spieler " + ChatColor.GOLD + "%s " + ChatColor.GRAY + "ist gestorben."),
    WIN_MESSAGE(ChatColor.GOLD + "%s " + ChatColor.GREEN + "hat gewonnen!");

    private static final String SYSTEM_PREFIX = ChatColor.LIGHT_PURPLE + "Aura " + ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "";

    private final String text;

    Language(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getFormattedText() {
        return SYSTEM_PREFIX + ChatColor.GRAY + text;
    }
}