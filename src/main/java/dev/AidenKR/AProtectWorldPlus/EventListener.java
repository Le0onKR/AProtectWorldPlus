package dev.AidenKR.AProtectWorldPlus;

import dev.AidenKR.AProtectWorldPlus.data.ProtectedType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public final class EventListener implements Listener {

    private final AProtectWorldPlus plugin;
    private final String tip = "§l§c[!] §r§f";

    public EventListener(AProtectWorldPlus plugin) {
        this.plugin = plugin;
    }

    public void checkLimit(Cancellable cancellable, Player player, ProtectedType type) {
        checkLimit(cancellable, player, type, "");
    }

    private void checkLimit(Cancellable cancellable, Player player, ProtectedType type, String message) {
        if (player.isOp()) {
            return;
        }
        String worldName = player.getWorld().getName().toLowerCase();
        String typeName = type.getName();

        if (!plugin.hasProtectWorld(worldName, typeName))return;

        if (!plugin.getProtectWorld(worldName, typeName).equals("off")) {
            cancellable.setCancelled(true);
            if (Objects.equals(message, "")) {
                return;
            }
            player.sendActionBar(Component.text(tip + message));
        }
    }

    @EventHandler
    public void onInter(PlayerInteractEvent event) {
        checkLimit(event, event.getPlayer(), ProtectedType.INTER);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        checkLimit(event, event.getPlayer(), ProtectedType.DROPITEM, "해당 월드에서는 아이템을 버릴 수 없습니다.");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        checkLimit(event, event.getPlayer(), ProtectedType.BREAK, "해당 월드에서는 블럭을 부술 수 없습니다.");
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        checkLimit(event, event.getPlayer(), ProtectedType.PLACE, "해당 월드에서는 블럭을 설치할 수 없습니다.");
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if ((event.getEntity() instanceof Player player)) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                checkLimit(event, player, ProtectedType.FALLDAMAGE);
            }
        }
    }

    @EventHandler
    public void onPVP(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if ((event.getDamager() instanceof Player attacker)) {
            checkLimit(event, attacker, ProtectedType.PVP, "해당 월드에서는 전투가 금지되어있습니다.");
        }

        if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player shooter) {
                checkLimit(event, shooter, ProtectedType.PVP, "해당 월드에서는 전투가 금지되어있습니다.");
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName().toLowerCase();
        String typeName = ProtectedType.INVENSAVE.getName();

        if (player.isOp()) return;
        if (!plugin.hasProtectWorld(worldName, typeName)) return;

        if (!plugin.getProtectWorld(worldName, typeName).equals("off")) {
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            String worldName = player.getWorld().getName().toLowerCase();
            String typeName = ProtectedType.EXHAUST.getName();

            if (player.isOp()) return;

            if (plugin.hasProtectWorld(worldName, typeName)) {
                if (!plugin.getProtectWorld(worldName, typeName).equals("off")) {
                    event.setCancelled(true);
                    player.setFoodLevel(20);
                    player.setSaturation(20.0f);
                }
            }
        }
    }
}
