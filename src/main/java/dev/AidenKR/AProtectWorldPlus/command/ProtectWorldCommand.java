package dev.AidenKR.AProtectWorldPlus.command;

import dev.AidenKR.AProtectWorldPlus.AProtectWorldPlus;
import dev.AidenKR.AProtectWorldPlus.data.ProtectedType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class ProtectWorldCommand extends Command {

    private final AProtectWorldPlus plugin;

    public ProtectWorldCommand(AProtectWorldPlus plugin) {
        super("월드보호");
        setPermission("protect.world.command");
        this.plugin = plugin;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
        if (args.length == 1) {
            return List.of("설정", "정보");
        }

        if (args.length == 2) {
            return Bukkit.getWorlds().stream()
                    .map(World::getName)
                    .filter(name -> name.startsWith(args[1].toLowerCase()))
                    .toList();
        }

        if (args.length == 3) {
            return Arrays.stream(ProtectedType.values())
                    .map(ProtectedType::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase()))
                    .toList();
        }

        if (args.length == 4) {
            return List.of("on", "off").stream()
                    .filter(name -> name.startsWith(args[3].toLowerCase()))
                    .toList();
        }
        return List.of();
    }

    public void getUsage(Player player) {
        player.sendMessage(AProtectWorldPlus.prefix + "/월드보호 정보 <월드명> - 월드의 모든 보호 설정조회");
        player.sendMessage(AProtectWorldPlus.prefix + "/월드보호 정보 <월드명> <타입> - 특정 설정 조회");
        player.sendMessage(AProtectWorldPlus.prefix + "/월드보호 설정 <월드명> <타입> <on/off> - 월드 설정 변경");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 0) {
                getUsage(player);
                return true;
            }

            World world = null;
            ProtectedType type = null;

            for (World w : Bukkit.getWorlds()) {
                if (w.getName().equalsIgnoreCase(args[1])) {
                    world = w;
                    break;
                }
            }

            if (args.length >= 3) {
                for (ProtectedType t : ProtectedType.values()) {
                    if (t.getName().equalsIgnoreCase(args[2])) {
                        type = t;
                        break;
                    }
                }
            }

            if (world == null) {
                player.sendMessage(AProtectWorldPlus.prefix + "존재하지 않는 월드입니다.");
                return true;
            }

            String worldName = world.getName().toLowerCase();

            switch (args[0]) {
                case "설정":
                    if (args.length != 4) {
                        player.sendMessage(AProtectWorldPlus.prefix + "올바른 사용법: /월드보호 설정 <월드명> <타입> <on/off>");
                        return true;
                    }

                    if (type == null) {
                        player.sendMessage(AProtectWorldPlus.prefix + "존재하지 않는 타입입니다.");
                        return true;
                    }

                    String value = args[3].toLowerCase();
                    if (!value.equals("on") && !value.equals("off")) {
                        player.sendMessage(AProtectWorldPlus.prefix + "값은 'on' 혹은 'off' 으로 만 입력가능합니다. ");
                        return true;
                    }
                    plugin.setProtectWorld(worldName, type.getName(), value);
                    String color = value.equals("on") ? "§a(허용)" : "§c(비허용)";
                    player.sendMessage(AProtectWorldPlus.prefix + worldName + " 월드의 " + type.getName() + " 설정이 " + color + "로 변경되었습니다.");
                    break;

                case "정보":
                    if (args.length == 2) {
                        player.sendMessage(AProtectWorldPlus.prefix + worldName + " 월드보호 정보");
                        for (ProtectedType t : ProtectedType.values()) {
                            String val = plugin.getProtectWorld(worldName, t.getName());
                            String col = val.equals("on") ? "§a" : "§c";
                            player.sendMessage("§7- " + t.getName() + ": " + col + val);
                        }
                    } else if (args.length == 3) {
                        if (type == null) {
                            player.sendMessage(AProtectWorldPlus.prefix + "존재하지 않는 타입입니다.");
                            return true;
                        }
                        String val = plugin.getProtectWorld(worldName, type.getName());
                        String col = val.equals("on") ? "§a" : "§c";
                        player.sendMessage(AProtectWorldPlus.prefix + worldName + " 월드의 " + type.getName() + " 정보");
                        player.sendMessage("§7- " + type.getName() + ": " + col + val);
                    } else {
                        getUsage(player);
                    }
                    break;

                default:
                    getUsage(player);
                    break;
            }
        }
        return true;
    }
}
