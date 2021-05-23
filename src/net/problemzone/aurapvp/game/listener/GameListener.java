package net.problemzone.aurapvp.game.listener;

import net.problemzone.aurapvp.Main;
import net.problemzone.aurapvp.game.GameManager;
import net.problemzone.aurapvp.game.GameState;
import net.problemzone.aurapvp.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GameListener implements Listener {

    private static final int PLAYER_START_COUNT = 5;

    private final GameManager gameManager;

    public GameListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        //General Settings
        e.setQuitMessage("");
        gameManager.removePlayer(e.getPlayer());

        //Lobby Settings
        if (gameManager.getGameState() != GameState.WAITING && gameManager.getGameState() != GameState.STARTING) return;
        e.setQuitMessage(Language.PLAYER_LEAVE.getText() + e.getPlayer().getDisplayName());

        if (Bukkit.getOnlinePlayers().size() >= PLAYER_START_COUNT) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size() == 1 ? Language.PLAYERS_NEEDED_ONE.getFormattedText() : String.format(Language.PLAYERS_NEEDED.getFormattedText(), PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size()));
            }
        }.runTaskLater(Main.getJavaPlugin(), 5);


        if (gameManager.getGameState() != GameState.STARTING) return;
        gameManager.cancelGameInitiation();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        //General Settings
        Player player = e.getPlayer();

        e.setJoinMessage("");
        e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());

        //Lobby Settings
        if (gameManager.getGameState() != GameState.WAITING && gameManager.getGameState() != GameState.STARTING) return;
        e.setJoinMessage(Language.PLAYER_JOIN.getText() + e.getPlayer().getDisplayName());

        if (gameManager.getGameState() != GameState.WAITING) return;

        if (Bukkit.getOnlinePlayers().size() < PLAYER_START_COUNT) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage(PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size() == 1 ? Language.PLAYERS_NEEDED_ONE.getFormattedText() : String.format(Language.PLAYERS_NEEDED.getFormattedText(), PLAYER_START_COUNT - Bukkit.getOnlinePlayers().size()));
                }
            }.runTaskLater(Main.getJavaPlugin(), 5);
            return;
        }

        gameManager.initiateGame();
    }

    @EventHandler
    //Disable PVP until Game
    public void preGameDamageListener(EntityDamageEvent e) {
        if (gameManager.getGameState() == GameState.RUNNING) return;
        e.setCancelled(true);
    }

}
