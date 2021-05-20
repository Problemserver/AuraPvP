package net.problemzone.aurapvp;


import net.problemzone.aurapvp.game.GameManager;
import net.problemzone.aurapvp.game.PlayerManager;
import net.problemzone.aurapvp.game.commands.start;
import net.problemzone.aurapvp.game.scoreboard.ScoreboardHandler;
import net.problemzone.aurapvp.game.spectator.SpectatorManager;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class Main extends JavaPlugin {

    private static JavaPlugin JavaPlugin;

    private final ScoreboardHandler scoreboardManager = new ScoreboardHandler();
    private final SpectatorManager spectatorManager = new SpectatorManager();
    private final PlayerManager playerManager = new PlayerManager(scoreboardManager, spectatorManager);
    private final GameManager gameManager = new GameManager(scoreboardManager, playerManager);


    public static JavaPlugin getJavaPlugin() {
        return JavaPlugin;
    }


    public void onEnable(){

        getLogger().info("AuraPvP wird geladen.");
        JavaPlugin = this;

        getLogger().info("Welten werden geladen.");
        loadWorlds();

        getLogger().info("Commands werden erstellt.");
        registerCommands();

        getLogger().info("Listener werden geladen.");
        registerListeners();

        getLogger().info("AuraPvP wurde erfolgreich geladen.");
    }

    private void registerListeners() {
        //Event Listeners
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("start")).setExecutor(new start(gameManager));
    }

    private void loadWorlds() {
        getServer().createWorld(new WorldCreator("ClassicAura"));
    }
}

