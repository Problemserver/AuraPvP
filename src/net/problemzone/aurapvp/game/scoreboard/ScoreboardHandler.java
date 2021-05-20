package net.problemzone.aurapvp.game.scoreboard;

import net.problemzone.aurapvp.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ScoreboardHandler {

    private final Map<Player, Integer> playerDeaths = new HashMap<>();
    private final Map<Player, Integer> playerKills = new HashMap<>();
    private final Map<Player, Integer> playerKillStreak = new HashMap<>();

    public void setScoreboard(Player player) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective obj = board.registerNewObjective("Infos", "dummy", ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " AURAPVP ");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        obj.getScore("").setScore(15);

        Score onlineName = obj.getScore(ChatColor.WHITE + "Spieler:");
        onlineName.setScore(14);
        Team onlineCounter = board.registerNewTeam("onlineCounter");
        onlineCounter.addEntry(ChatColor.BLUE + "" + ChatColor.WHITE);
        onlineCounter.setPrefix(ChatColor.GOLD + "" + Bukkit.getOnlinePlayers().size());
        obj.getScore(ChatColor.BLUE + "" + ChatColor.WHITE).setScore(13);

        obj.getScore(" ").setScore(12);

        Score kills = obj.getScore(ChatColor.WHITE + "Kills:");
        kills.setScore(11);
        Team killCounter = board.registerNewTeam("killCounter");
        killCounter.addEntry(ChatColor.GREEN + "" + ChatColor.WHITE);
        killCounter.setPrefix(ChatColor.GOLD + "" + playerKills.get(player));
        obj.getScore(ChatColor.GREEN + "" + ChatColor.WHITE).setScore(10);

        obj.getScore("  ").setScore(9);

        Score death = obj.getScore(ChatColor.WHITE + "Tode:");
        death.setScore(8);
        Team deathCounter = board.registerNewTeam("deathCounter");
        deathCounter.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        deathCounter.setPrefix(ChatColor.GOLD + "" + playerDeaths.get(player));
        obj.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(7);

        obj.getScore("   ").setScore(6);

        Score kd = obj.getScore(ChatColor.WHITE + "KD:");
        kd.setScore(5);
        Team kdCounter = board.registerNewTeam("kdCounter");
        kdCounter.addEntry(ChatColor.GOLD + "" + ChatColor.WHITE);
        kdCounter.setPrefix(ChatColor.GOLD + "GOD");
        obj.getScore(ChatColor.GOLD + "" + ChatColor.WHITE).setScore(4);

        obj.getScore("    ").setScore(3);

        Score killstreak = obj.getScore(ChatColor.WHITE + "Killstreak:");
        killstreak.setScore(2);
        Team killstreakCounter = board.registerNewTeam("ksCounter");
        killstreakCounter.addEntry(ChatColor.AQUA + "" + ChatColor.WHITE);
        killstreakCounter.setPrefix(ChatColor.GOLD + "0");
        obj.getScore(ChatColor.AQUA + "" + ChatColor.WHITE).setScore(1);

        player.setScoreboard(board);
    }

    private void updatePlayer(Player player) {
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("onlineCounter")).setPrefix(ChatColor.GOLD + "" + Bukkit.getOnlinePlayers().size());
    }

    private void updateDeath(Player player) {
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("deathCounter")).setPrefix(ChatColor.GOLD + "" + playerDeaths.get(player));
    }

    private void updateKill(Player player) {
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("killCounter")).setPrefix(ChatColor.GOLD + "" + playerKills.get(player));
    }

    private void updateKD(Player player) {
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("kdCounter")).setPrefix(ChatColor.GOLD + "" + (playerDeaths.get(player) == 0 ? "GOD" : Math.round((playerKills.get(player) / (double) playerDeaths.get(player)) * 100.0) / 100.0));
    }

    private void updateKillstreak(Player player) {
        Scoreboard board = player.getScoreboard();
        Objects.requireNonNull(board.getTeam("ksCounter")).setPrefix(ChatColor.GOLD + "" + playerKillStreak.get(player));
    }

    public void updateScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            updatePlayer(player);
            updateDeath(player);
            updateKill(player);
            updateKD(player);
            updateKillstreak(player);
        }
    }

    public void increaseKillCounter(Player player) {
        playerKills.put(player, playerKills.get(player) + 1);

        playerKillStreak.put(player, playerKillStreak.get(player) + 1);
        if (playerKillStreak.get(player) > 0 && playerKillStreak.get(player) % 5 == 0) {
            Bukkit.broadcastMessage(String.format(Language.GLOBAL_KILLSTREAK.getFormattedText()));
        }
    }

    public void increaseDeathCounter(Player player) {
        playerDeaths.put(player, playerDeaths.get(player) + 1);
        playerKillStreak.put(player, 0);
    }

    public void initPlayer(Player player) {
        playerKills.put(player, 0);
        playerDeaths.put(player, 0);
        playerKillStreak.put(player, 0);
    }

    public void removeScoreboard(Player player){
        player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
    }
}
