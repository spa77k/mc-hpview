package dev.spa.hpview;

import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 隔離Paperで、攻撃後のアクションバー文字列・色・倒したときの0表示・オンオフの保存を確かめる。
 * 実クライアントでの見た目（Java版・統合版）は別に確かめる。
 */
public final class HPViewProbe extends JavaPlugin {

    private final List<Component> actionBars = new ArrayList<>();
    private final UUID viewerId = UUID.randomUUID();
    private Player viewer;
    private HPViewPlugin hpview;

    @Override
    public void onEnable() {
        hpview = (HPViewPlugin) Bukkit.getPluginManager().getPlugin("HPView");
        viewer = fakePlayer();
        Bukkit.getScheduler().runTaskLater(this, this::stepDamage, 40);
    }

    private void stepDamage() {
        try {
            World world = Bukkit.getWorlds().get(0);
            // プレイヤーがいないので、ゾンビを置くチャンクを読み込んだままにする
            world.getChunkAt(0, 0).addPluginChunkTicket(this);
            Zombie zombie = (Zombie) world.spawnEntity(new Location(world, 0, -60, 0), EntityType.ZOMBIE);
            zombie.setAI(false);
            zombie.setHealth(15);
            fire(zombie);
            Bukkit.getScheduler().runTaskLater(this, () -> {
                try {
                    expect(actionBars.size() == 1, "攻撃後にアクションバーが1回出る");
                    expectBar(0, "15.0/20.0", TextColor.color(0x55FF55));
                    zombie.setHealth(4);
                    fire(zombie);
                    Zombie named = (Zombie) world.spawnEntity(new Location(world, 2, -60, 0), EntityType.ZOMBIE);
                    named.setAI(false);
                    named.customName(Component.text("ボス太郎"));
                    named.setHealth(0.03);
                    fire(named);
                    Zombie removed = (Zombie) world.spawnEntity(new Location(world, 4, -60, 0), EntityType.ZOMBIE);
                    removed.remove();
                    fire(removed);
                    Bukkit.getScheduler().runTaskLater(this, () -> stepToggle(zombie), 2);
                } catch (Throwable error) {
                    fail(error);
                }
            }, 2);
        } catch (Throwable error) {
            fail(error);
        }
    }

    private void stepToggle(Zombie zombie) {
        try {
            expect(actionBars.size() == 4, "4回の攻撃で4回出る: " + actionBars.size());
            expectBar(1, "4.0/20.0", TextColor.color(0xFF5555));
            expect(plain(actionBars.get(2)).startsWith("ボス太郎 "), "名前付きMobは付けた名前: " + plain(actionBars.get(2)));
            expect(plain(actionBars.get(2)).contains("0.1/20.0"), "端数は切り上げる: " + plain(actionBars.get(2)));
            expect(plain(actionBars.get(3)).contains("0.0/20.0"), "倒した相手は0: " + plain(actionBars.get(3)));

            HPViewCommand command = new HPViewCommand(hpview);
            command.onCommand(viewer, null, "hpview", new String[0]);
            expect(!hpview.isEnabledFor(viewerId), "/hpview でオフになる");
            String saved = Files.readString(hpview.getDataFolder().toPath().resolve("players.yml"));
            expect(saved.contains(viewerId.toString()), "オフにした人が players.yml に残る");

            World world = zombie.getWorld();
            Zombie other = (Zombie) world.spawnEntity(new Location(world, 2, -60, 0), EntityType.ZOMBIE);
            fire(other);
            Bukkit.getScheduler().runTaskLater(this, () -> {
                try {
                    expect(actionBars.size() == 4, "オフの間は出ない");
                    command.onCommand(viewer, null, "hpview", new String[] {"on"});
                    expect(hpview.isEnabledFor(viewerId), "/hpview on でオンに戻る");
                    getLogger().info("HPVIEW_PROBE_PASS");
                } catch (Throwable error) {
                    fail(error);
                } finally {
                    Bukkit.shutdown();
                }
            }, 2);
        } catch (Throwable error) {
            fail(error);
            Bukkit.shutdown();
        }
    }

    private void fire(Entity target) {
        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(viewer, target,
                DamageCause.ENTITY_ATTACK, DamageSource.builder(DamageType.PLAYER_ATTACK).build(), 1));
    }

    private void expectBar(int index, String text, TextColor color) {
        Component bar = actionBars.get(index);
        expect(plain(bar).contains(text), "表示に " + text + " が入る: " + plain(bar));
        boolean colored = bar.children().stream().anyMatch(child -> color.equals(child.color())
                && plain(child).contains(text));
        expect(colored, "HPの色が " + color.asHexString());
        getLogger().info("ok: " + plain(bar));
    }

    private static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    private static void expect(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private void fail(Throwable error) {
        getLogger().log(Level.SEVERE, "HPVIEW_PROBE_FAIL", error);
        Bukkit.shutdown();
    }

    private Player fakePlayer() {
        return (Player) Proxy.newProxyInstance(getClassLoader(), new Class<?>[] {Player.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "sendActionBar" -> {
                        actionBars.add((Component) args[0]);
                        yield null;
                    }
                    case "getUniqueId" -> viewerId;
                    case "getName" -> "Probe";
                    case "hasPermission", "isOnline" -> true;
                    case "equals" -> proxy == args[0];
                    case "hashCode" -> System.identityHashCode(proxy);
                    case "sendMessage" -> null;
                    default -> throw new UnsupportedOperationException(method.getName());
                });
    }
}
