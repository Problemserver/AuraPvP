package net.problemzone.aurapvp.game;


import net.problemzone.aurapvp.Main;
import net.problemzone.aurapvp.util.Countdown;
import net.problemzone.aurapvp.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class GameManager {

    private static final int STARTING_LOBBY_TIME = 60;
    private static final int WARM_UP_TIME = 30;
    private static final int FINAL_LOBBY_TIME = 20;
    private static final int MIN_PLAYERS = 3;


    private final PlayerManager playerManager;


    private List<Player> possiblePlayers;
    private BukkitTask currentScheduledTask;

    private GameState gameState = GameState.WAITING;

    public GameManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void initiateGame() {
        initiateGame(STARTING_LOBBY_TIME);
    }

    //Starts Lobby Countdown
    public void initiateGame(int seconds) {

        if (gameState != GameState.WAITING && gameState != GameState.STARTING) return;

        gameState = GameState.STARTING;

        //Initialize Countdown
        Countdown.createXpBarCountdown(seconds);
        Countdown.createLevelCountdown(seconds, Language.GAME_START_TITLE);
        Countdown.createChatCountdown(seconds, Language.GAME_START);

        if (currentScheduledTask != null && !currentScheduledTask.isCancelled()) currentScheduledTask.cancel();

        currentScheduledTask = new BukkitRunnable() {
            @Override
            public void run() {
                startWarmUp();
            }
        }.runTaskLater(Main.getJavaPlugin(), seconds * 20L);

    }

    public void cancelGameInitiation() {
        Countdown.cancelXpBarCountdown();
        Countdown.cancelLevelCountdown();
        Countdown.cancelChatCountdown();
        if (currentScheduledTask != null && !currentScheduledTask.isCancelled()) currentScheduledTask.cancel();
    }

    //Starts Warm Up Phase
    private void startWarmUp() {
        if (Bukkit.getOnlinePlayers().size() < MIN_PLAYERS) {
            Bukkit.broadcastMessage(Language.NOT_ENOUGH_PLAYERS.getFormattedText());
            gameState = GameState.WAITING;
            return;
        }

        possiblePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

        //Teleport to Map
        possiblePlayers.forEach(playerManager::intiiateGame);

        gameState = GameState.WARM_UP;

        //Initialize Countdown
        Countdown.createChatCountdown(WARM_UP_TIME, Language.WARM_UP);

        //Set GameState to Countdown
        new BukkitRunnable() {
            @Override
            public void run() {
                startGame();
            }
        }.runTaskLater(Main.getJavaPlugin(), WARM_UP_TIME * 20L);
    }

    //Assigns Roles and starts the Game
    private void startGame() {

        if (possiblePlayers.size() < MIN_PLAYERS) {
            gameState = GameState.FINISHED;

            Bukkit.broadcastMessage(Language.NOT_ENOUGH_PLAYERS.getFormattedText());
            possiblePlayers.forEach(playerManager::wrapUpGame);
            return;
        }

        gameState = GameState.RUNNING;
    }

    //Winning Related Methods
    public void removePlayer(Player player) {
        if(possiblePlayers != null) possiblePlayers.remove(player);
        if (gameState == GameState.RUNNING) checkForWin();
    }

    private void checkForWin() {

    }

    private void finishGame() {
        gameState = GameState.FINISHED;

        //Teleport to Lobby
        Bukkit.getOnlinePlayers().forEach(playerManager::wrapUpGame);

        //Announce Winner
        Bukkit.broadcastMessage(String.format(Language.WIN_MESSAGE.getFormattedText()));
        Bukkit.getOnlinePlayers().forEach(player -> playerManager.announceWin(player));

        Countdown.createChatCountdown(FINAL_LOBBY_TIME * 20, Language.SERVER_CLOSE);

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().shutdown();
            }
        }.runTaskLater(Main.getJavaPlugin(), FINAL_LOBBY_TIME * 20L + 1);

    }

    //Public getter methods
    public GameState getGameState() {
        return gameState;
    }
}