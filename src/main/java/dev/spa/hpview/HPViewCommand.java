package dev.spa.hpview;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/** /hpview [on|off|reload] */
public final class HPViewCommand implements TabExecutor {

    private final HPViewPlugin plugin;

    public HPViewCommand(HPViewPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String action = args.length == 0 ? "toggle" : args[0].toLowerCase();
        if (action.equals("reload")) {
            if (!sender.hasPermission("hpview.reload")) {
                sender.sendMessage(Component.text("権限がありません。", NamedTextColor.RED));
                return true;
            }
            plugin.reloadSettings();
            sender.sendMessage(Component.text("HPViewの設定を読み直しました。", NamedTextColor.GREEN));
            return true;
        }
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("プレイヤーだけが使えます。", NamedTextColor.RED));
            return true;
        }
        if (!List.of("on", "off", "toggle").contains(action)) {
            sender.sendMessage(Component.text("使い方: /" + label + " [on|off]", NamedTextColor.RED));
            return true;
        }
        boolean enabled = action.equals("toggle") ? !plugin.isEnabledFor(player.getUniqueId()) : action.equals("on");
        plugin.setEnabledFor(player.getUniqueId(), enabled);
        player.sendMessage(enabled
                ? Component.text("攻撃した相手のHP表示をオンにしました。", NamedTextColor.GREEN)
                : Component.text("攻撃した相手のHP表示をオフにしました。", NamedTextColor.GRAY));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) {
            return List.of();
        }
        List<String> options = sender.hasPermission("hpview.reload")
                ? List.of("on", "off", "reload") : List.of("on", "off");
        return options.stream().filter(option -> option.startsWith(args[0].toLowerCase())).toList();
    }
}
