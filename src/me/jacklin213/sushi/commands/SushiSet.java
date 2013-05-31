package me.jacklin213.sushi.commands;

import me.jacklin213.sushi.Sushi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SushiSet implements CommandExecutor{
	
	public static Sushi plugin;
	
	public SushiSet(Sushi instance){
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// Start of sushiset commands
		if (commandLabel.equalsIgnoreCase("sushiset") || (commandLabel.equalsIgnoreCase("ss"))) {
			
			sender.sendMessage(ChatColor.AQUA + "-------------------------");
			sender.sendMessage(ChatColor.GREEN
					+ "Welcome to the sushiset section");
			sender.sendMessage(ChatColor.GOLD + "Type in /sushiset help");
			sender.sendMessage(ChatColor.AQUA + "-------------------------");
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage("Type /help sushi and look for the /sushiset commands");
					return true;
				} 
			}
			
			if (args[0].equalsIgnoreCase("rawfishamount") || (args[0].equalsIgnoreCase("rfa"))) {
				if (sender.hasPermission("sushi.setrawfishamount")) {
					if (args.length <= 1) {
						sender.sendMessage(ChatColor.RED
								+ "Not enough arguments, Correct usage /sushiset rfa [Amount]");
						return true;
					} else {
						try {
							int rfavalue = Integer.parseInt(args[1]);
							plugin.getConfig().set("Sushi.RawFishAmount", rfavalue);
							plugin.saveConfig();
							sender.sendMessage("RawFishAmount has been changed to "	+ rfavalue);
							return true;
						} catch (NumberFormatException e){
							sender.sendMessage("Not a valid number");
							return true;
						}
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Sorry you do not have the permission to do this");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED
						+ "This command isn't recongnised. Try using /ss rfa [Amount]");
				return true;
			}
		}

		return false;
	}

}
