package dev.spa.hpview;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.Projectile;

/** 攻撃のあと、攻撃したプレイヤーに相手の残りHPを見せる。 */
public final class DamageListener implements Listener {

    private final HPViewPlugin plugin;

    public DamageListener(HPViewPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity target) || target instanceof ArmorStand) {
            return;
        }
        if (target instanceof Player && !plugin.showPlayers()) {
            return;
        }
        Player viewer = attacker(event.getDamager());
        if (viewer == null || viewer.equals(target)
                || !viewer.hasPermission("hpview.use") || !plugin.isEnabledFor(viewer.getUniqueId())) {
            return;
        }
        // ダメージが反映された後のHPを見るため、1tick後に表示する
        plugin.getServer().getScheduler().runTask(plugin, () -> show(viewer, target));
    }

    static Player attacker(Entity damager) {
        if (damager instanceof Player player) {
            return player;
        }
        if (damager instanceof Projectile projectile) {
            ProjectileSource shooter = projectile.getShooter();
            if (shooter instanceof Player player) {
                return player;
            }
            if (shooter instanceof Entity entity) {
                return attacker(entity);
            }
            return null;
        }
        if (damager instanceof Tameable pet) {
            AnimalTamer owner = pet.getOwner();
            return owner instanceof Player player && player.isOnline() ? player : null;
        }
        return null;
    }

    private void show(Player viewer, LivingEntity target) {
        if (!viewer.isOnline()) {
            return;
        }
        double health = target.isValid() && !target.isDead() ? target.getHealth() : 0;
        AttributeInstance max = target.getAttribute(Attribute.MAX_HEALTH);
        double maxHealth = max != null ? max.getValue() : health;
        viewer.sendActionBar(plugin.formatter().render(target.name(), health, maxHealth));
    }
}
