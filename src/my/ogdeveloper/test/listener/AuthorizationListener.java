package my.ogdeveloper.test.listener;

import my.ogdeveloper.test.db.object.DatabaseUpdate;
import my.ogdeveloper.test.TestPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class AuthorizationListener implements Listener {

    private TestPlugin plugin;

    public AuthorizationListener(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        DatabaseUpdate update = new DatabaseUpdate();
        update.addCondition("UUID", uuid.toString());
        update.addUpdate("UUID", uuid.toString());
        update.addUpdate("Money", 0);

        plugin.getDatabase().insertIfNotExists("user", update);
    }

}
