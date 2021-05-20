package net.problemzone.aurapvp.game;

import net.problemzone.aurapvp.game.scoreboard.ScoreboardHandler;
import net.problemzone.aurapvp.game.spectator.SpectatorManager;
import net.problemzone.aurapvp.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerManager {

    private final ScoreboardHandler scoreboardManager;
    private final SpectatorManager spectatorManager;

    public PlayerManager(ScoreboardHandler scoreboardManager, SpectatorManager spectatorManager) {
        this.scoreboardManager = scoreboardManager;
        this.spectatorManager = spectatorManager;
    }

    public void initiateGame(Player player){
        //TODO: Choose World
        player.teleport(Objects.requireNonNull(Bukkit.getWorld("Skeld")).getSpawnLocation());
    }

    public void wrapUpGame(Player player){
        player.getInventory().clear();
        scoreboardManager.removeScoreboard(player);
        spectatorManager.removePlayerFromSpectator(player);
        player.teleport(Objects.requireNonNull(Bukkit.getWorld("Lobby")).getSpawnLocation());
    }

    public void announceWin(Player player, String winner){
        player.sendTitle(String.format(Language.WIN_MESSAGE.getText(), winner), "", 10, 60, 10);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.AMBIENT, 1, 1.3F);
    }

}
