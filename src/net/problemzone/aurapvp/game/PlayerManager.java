package net.problemzone.aurapvp.game;

import net.problemzone.aurapvp.game.spectator.SpectatorManager;
import net.problemzone.aurapvp.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerManager {

    private final SpectatorManager spectatorManager;

    public PlayerManager(SpectatorManager spectatorManager) {
        this.spectatorManager = spectatorManager;
    }

    public void intiiateGame(Player player){
        //TODO: Choose World
        player.teleport(Objects.requireNonNull(Bukkit.getWorld("AuraClassic")).getSpawnLocation());
    }

    public void wrapUpGame(Player player){
        player.getInventory().clear();
        spectatorManager.removePlayerFromSpectator(player);
        player.teleport(Objects.requireNonNull(Bukkit.getWorld("Lobby")).getSpawnLocation());
    }

    public void announceWin(Player player){
        player.sendMessage(String.format(Language.WIN_MESSAGE.getFormattedText(), player.getName()));
    }

}
