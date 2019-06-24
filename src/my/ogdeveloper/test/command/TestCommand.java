package my.ogdeveloper.test.command;

import my.ogdeveloper.test.user.User;
import my.ogdeveloper.test.TestPlugin;
import my.ogdeveloper.test.user.Money;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    private TestPlugin plugin;

    public TestCommand(TestPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can use only players!");
            return true;
        }

        Player p = (Player) sender;

        User user = plugin.getUser(p.getUniqueId());
        Money money = user.getMoney();

        switch (args.length) {
            case 1: {
                int moneyAdd;

                try {
                    moneyAdd = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    p.sendMessage("Usage: /test %number");
                    return true;
                }

                if (moneyAdd <= 0) {
                    p.sendMessage("Money must be more than " + 0);
                    return true;
                }

                money.setMoney(money.getMoney() + moneyAdd);
                p.sendMessage("You successful add " + moneyAdd + " money!");
                return true;
            }

            case 0: {
                p.sendMessage("You have " + money.getMoney());
                return true;
            }

            default: {
                p.sendMessage("No such arguments were found.");
                return true;
            }
        }
    }

}
