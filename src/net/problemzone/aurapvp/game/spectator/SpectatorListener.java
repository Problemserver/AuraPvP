package net.problemzone.aurapvp.game.spectator;


import net.problemzone.aurapvp.Main;
import net.problemzone.aurapvp.game.GameManager;
import net.problemzone.aurapvp.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SpectatorListener implements Listener {

    private final SpectatorManager spectatorManager;
    private final GameManager gameManager;

    public SpectatorListener(SpectatorManager spectatorManager, GameManager gameManager) {
        this.spectatorManager = spectatorManager;
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onSpectatorJoin(PlayerJoinEvent e){
        if(gameManager.getGameState() != GameState.WAITING && gameManager.getGameState() != GameState.STARTING){
            spectatorManager.setPlayerAsSpectator(e.getPlayer());
        }
    }

    @EventHandler
    public void onSpectatorRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());

        new BukkitRunnable() {
            @Override
            public void run() {
                spectatorManager.setPlayerAsSpectator(player);
            }
        }.runTaskLater(Main.getJavaPlugin(), 1);
    }

    @EventHandler
    public void onPlayerDeathDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();

        if(player.getHealth() - e.getFinalDamage() <= 0){
            e.setCancelled(true);
            spectatorManager.setPlayerAsSpectator(player);
            gameManager.removePlayer(player);
        }

    }

    @EventHandler
    public void onSpectatorDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && spectatorManager.isSpectator((Player)e.getDamager())) e.setCancelled(true);

        if(e.getEntity() instanceof Player && spectatorManager.isSpectator((Player)e.getEntity())) e.setCancelled(true);
    }

    @EventHandler
    public void onSpectatorArrowPickup(PlayerPickupArrowEvent e){
        if(spectatorManager.isSpectator(e.getPlayer())) e.setCancelled(true);
    }

    @EventHandler
    public void onSpectatorItemPickup(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player && spectatorManager.isSpectator((Player) e.getEntity())) e.setCancelled(true);
    }


}
