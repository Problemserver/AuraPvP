package net.problemzone.aurapvp.game.scoreboard;


import net.problemzone.aurapvp.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class ScoreboardListener implements Listener {

    private final ScoreboardHandler scoreboardHandler;

    public ScoreboardListener(ScoreboardHandler scoreboardHandler) {
        this.scoreboardHandler = scoreboardHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        scoreboardHandler.initPlayer(p);
        scoreboardHandler.setScoreboard(p);
        scoreboardHandler.updateScoreboard();
    }

    @EventHandler
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                e.setCancelled(true);
                player.setHealth(0);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        event.setDeathMessage(String.format(Language.PLAYER_DEATH.getFormattedText(), event.getEntity().getName()));
        Bukkit.broadcast(Language.CURRENT_PLAYERS.getFormattedText(), String.valueOf(Bukkit.getOnlinePlayers()));

        if(Objects.requireNonNull(event.getEntity().getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (event.getEntity().getKiller() != null) {
                scoreboardHandler.increaseKillCounter(event.getEntity().getKiller());
                event.setDeathMessage(String.format(Language.PLAYER_DEATH_BY_PLAYER.getFormattedText(), event.getEntity().getName(), event.getEntity().getKiller().getName()));
                Bukkit.broadcast(Language.CURRENT_PLAYERS.getFormattedText(), String.valueOf(Bukkit.getOnlinePlayers()));
            }

            scoreboardHandler.increaseDeathCounter(event.getEntity());
            scoreboardHandler.updateScoreboard();
        }
    }
}

