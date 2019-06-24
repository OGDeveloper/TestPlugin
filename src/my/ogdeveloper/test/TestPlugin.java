package my.ogdeveloper.test;

import my.ogdeveloper.test.command.TestCommand;
import my.ogdeveloper.test.db.SQLiteDatabase;
import my.ogdeveloper.test.db.object.DatabaseUpdate;
import my.ogdeveloper.test.listener.AuthorizationListener;
import my.ogdeveloper.test.user.SQLiteStorage;
import my.ogdeveloper.test.user.SimpleUser;
import my.ogdeveloper.test.user.Storage;
import my.ogdeveloper.test.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Properties;
import java.util.UUID;

public class TestPlugin extends JavaPlugin {

    private static TestPlugin instance;
    private SQLiteDatabase database;
    private Storage storage;

    @Override
    public void onEnable() {
        instance = this;

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        Properties properties = new Properties();
        properties.setProperty("fileName", getDataFolder().getPath() + File.separator + "test");

        database = new SQLiteDatabase(properties);
        storage = new SQLiteStorage(database);

        registerCommands();
        registerListeners();
        createTable();

        for (Player p: Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();

            DatabaseUpdate update = new DatabaseUpdate();
            update.addCondition("UUID", uuid.toString());
            update.addUpdate("UUID", uuid.toString());
            update.addUpdate("Money", 0);

            database.insertIfNotExists("user", update);
        }
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        AuthorizationListener authorizationListener = new AuthorizationListener(this);

        Bukkit.getPluginManager().registerEvents(authorizationListener, this);
    }

    private void registerCommands() {
        TestCommand testCommand = new TestCommand(this);

        getCommand("test").setExecutor(testCommand);
    }

    private void createTable() {
        DatabaseUpdate update = new DatabaseUpdate();

        update.addUpdate("UUID", "VARCHAR(100)");
        update.addUpdate("Money", "INTEGER");

        database.createTable("user", update);
    }

    public static TestPlugin getInstance() {
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public Storage getStorage() {
        return storage;
    }

    public User getUser(UUID uuid) {
        return new SimpleUser(uuid, storage);
    }

}
