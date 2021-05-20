package net.problemzone.aurapvp.game.listener;

import net.problemzone.aurapvp.util.Language;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Language.JOIN_MESSAGE.getFormattedText());
        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
        event.setJoinMessage(Language.PLAYER_JOIN.getFormattedText());
    }
}
