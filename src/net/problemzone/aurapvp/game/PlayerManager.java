package net.problemzone.aurapvp.game;

import net.problemzone.aurapvp.game.spectator.SpectatorManager;
import net.problemzone.aurapvp.util.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;

public class PlayerManager {

    private final SpectatorManager spectatorManager;

    public PlayerManager(SpectatorManager spectatorManager) {
        this.spectatorManager = spectatorManager;
    }

    public void intiiateGame(Player player){
        //TODO: Choose World
        player.teleport(Objects.requireNonNull(Bukkit.getWorld("AuraClassic")).getSpawnLocation());
        equip(player);
    }

    public void wrapUpGame(Player player){
        player.getInventory().clear();
        spectatorManager.removePlayerFromSpectator(player);
        player.teleport(Objects.requireNonNull(Bukkit.getWorld("Lobby")).getSpawnLocation());
    }

    public void announceWin(Player player){
        player.sendMessage(String.format(Language.WIN_MESSAGE.getFormattedText(), player.getName()));
    }

    private void equip(Player player){

        player.getInventory().clear();

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        ItemStack stock = new ItemStack(Material.STICK, 1);
        ItemMeta stockItemMeta = stock.getItemMeta();
        assert stockItemMeta != null;
        stockItemMeta.addEnchant(Enchantment.KNOCKBACK, 5, true);stockItemMeta.setDisplayName(ChatColor.RED + "Knüppel");
        stock.setItemMeta(stockItemMeta);stockItemMeta.setUnbreakable(true);

        ItemStack rod = new ItemStack(Material.FISHING_ROD, 1);
        ItemMeta rodMeta = rod.getItemMeta();
        assert rodMeta != null;
        rodMeta.setUnbreakable(true);

        player.getInventory().setItem(0, stock);
        player.getInventory().setItem(1, rod);
        player.getInventory().addItem(new ItemStack(Material.SNOWBALL, 16));
        player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16389));
        player.getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16386));
        player.getInventory().addItem(new ItemStack(Material.CREEPER_SPAWN_EGG, 1));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1));
        player.getInventory().addItem(new ItemStack(Material.PUMPKIN_PIE, 10));
        player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
    }
}
