package dev.spa.hpview;

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.ConfigurationSection;

/** 相手の名前とHPから、アクションバーに出す文字列を作る。 */
public final class HealthFormatter {

    private final String format;
    private final double high;
    private final double low;
    private final TextColor highColor;
    private final TextColor midColor;
    private final TextColor lowColor;
    private final int decimals;

    public HealthFormatter(ConfigurationSection config) {
        format = config.getString("format", "<white><name></white> <color>❤ <hp>/<max></color>");
        high = config.getDouble("thresholds.high", 0.5);
        low = config.getDouble("thresholds.low", 0.25);
        highColor = color(config.getString("colors.high"), TextColor.color(0x55FF55));
        midColor = color(config.getString("colors.mid"), TextColor.color(0xFFFF55));
        lowColor = color(config.getString("colors.low"), TextColor.color(0xFF5555));
        decimals = Math.max(0, Math.min(2, config.getInt("decimals", 1)));
    }

    public Component render(Component name, double health, double maxHealth) {
        return MiniMessage.miniMessage().deserialize(format, TagResolver.resolver(
                Placeholder.component("name", name),
                Placeholder.unparsed("hp", number(Math.max(0, health))),
                Placeholder.unparsed("max", number(maxHealth)),
                TagResolver.resolver("color", Tag.styling(colorFor(health, maxHealth)))));
    }

    public TextColor colorFor(double health, double maxHealth) {
        double ratio = maxHealth > 0 ? Math.max(0, health) / maxHealth : 0;
        return ratio >= high ? highColor : ratio >= low ? midColor : lowColor;
    }

    private String number(double value) {
        // 0.1未満の端数でも生きている相手が「0」と表示されないよう切り上げる
        return BigDecimal.valueOf(value).setScale(decimals, RoundingMode.CEILING).toPlainString();
    }

    private static TextColor color(String value, TextColor fallback) {
        TextColor parsed = value == null ? null : TextColor.fromHexString(value);
        return parsed == null ? fallback : parsed;
    }
}
