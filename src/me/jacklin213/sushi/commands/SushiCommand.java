package me.jacklin213.sushi.commands;

import java.util.ArrayList;
import java.util.List;

import me.jacklin213.sushi.Sushi;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SushiCommand implements CommandExecutor {
	public static Sushi plugin;

	public SushiCommand(Sushi instance) {
		plugin = instance;
	}

	private List<String> cantDoCommand = new ArrayList<String>();

	@Override
	// Start of whole command proccess
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		int cdTime = (getCooldownTime() * 20);
		if (commandLabel.equalsIgnoreCase("sushi")) {
			if (args.length >= 2) {
				sender.sendMessage(ChatColor.RED
						+ "Too Many argurments, type /sushi help for help");
				return true;
			}
			if (args.length == 1) {
				// sushi info command (start)
				if (args[0].equalsIgnoreCase("info")) {
					sender.sendMessage(ChatColor.DARK_GREEN
							+ "+------------------------------+");
					sender.sendMessage(ChatColor.DARK_AQUA
							+ "      Sushi: Spawn me Sushi!");
					sender.sendMessage(ChatColor.GREEN + "      By jacklin213");
					sender.sendMessage(ChatColor.YELLOW + "      Version: "
							+ plugin.getDescription().getVersion());
					sender.sendMessage(ChatColor.DARK_GREEN
							+ "+------------------------------+");
					return true;
				}
				// sushi info command (end)
				// sushi reload command (start)
				if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("sushi.reload")) {
						sender.sendMessage(ChatColor.RED
								+ "You DO NOT have the permission to do this");
						return true;
					} else {
						plugin.reloadConfig();
						sender.sendMessage(ChatColor.RED + "[Sushi]"
								+ ChatColor.GREEN + " Config reloaded!");
						return true;
					}
				}
				// sushi reload command (end)
				// sushi help command (start)
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.GREEN
							+ "Type /help sushi to see the help commands !");
					return true;
				}// Not a valid command (needs to be the else of the last
					// args.length == 1 command)
				else {
					sender.sendMessage(ChatColor.RED
							+ "This is not a valid command !");
					sender.sendMessage(ChatColor.GREEN
							+ "Type /help sushi for help");
					return true;
				}
				// sushi help command (end)
			}
			// end of sushi args[1] commands
			// Continue to check if the command was typed by a player
			if (!sender.hasPermission("sushi.use")) {
				sender.sendMessage(ChatColor.RED
						+ "You DO NOT have the permission to do this");
				return true;
			} else {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					final String playerName = player.getName();
					if (!cantDoCommand.contains(playerName)) {
						try {
							int value = Integer.parseInt(plugin.getConfig()
									.getString("Sushi.RawFishAmount"));
							ItemStack itemstack = new ItemStack(
									Material.RAW_FISH, value);
							PlayerInventory inventory = player.getInventory();
							inventory.addItem(itemstack);
							sender.sendMessage(ChatColor.BLUE
									+ "You have been given Fish!");
						} catch (NumberFormatException e) {
							sender.sendMessage("Error in config, Please set the RawFishAmount to a number");
							return true;
						}
						cantDoCommand.add(playerName);
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
								new Runnable() {
									public void run() {
										cantDoCommand.remove(playerName);
									}
								}, cdTime);
						return true;

					} else {
						player.sendMessage("The command is on cooldown");
						return true;
					}

				} else {
					sender.sendMessage("This command can only be used by players");
					return true;
				}
			}

		}
		return false;
	} // End of whole command

	public int getCooldownTime() {
		int cdTime;
		try {
			cdTime = Integer.parseInt(plugin.getConfig().getString(
					"Sushi.Cooldown"));
			return cdTime;
		} catch (NumberFormatException e) {
			plugin.log.info(String.format(
					"[%s] Error in loading the Cooldown value in the config",
					plugin.getDescription().getName()));
			plugin.log.info(String.format(
					"[%s] Please fix and reload the plugin", plugin
							.getDescription().getName()));
		}
		return 0;
	}
}
