package me.jacklin213.sushi;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Sushi extends JavaPlugin implements Listener {
	// define stuff
	PluginDescriptionFile pdfFile;
	public static Sushi plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	public static Permission Permissions = null;

	// What to do onEnable
	public void onEnable() {
		this.pdfFile = getDescription();
		this.log.info(this.pdfFile.getName() + " By "
				+ this.pdfFile.getAuthors() + " is now enabled!.");
		createconfig();
	}

	// what to do onDisable
	public void onDisable() {
		this.pdfFile = getDescription();
		this.log.info(this.pdfFile.getName() + " is now disabled.");
	}

	// Start of whole command proccess
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
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
							+ pdfFile.getVersion());
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
						createconfig();
						this.reloadConfig();
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
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command can only be used by players");
				return true;
			}
			if (!sender.hasPermission("sushi.use")) {
				sender.sendMessage(ChatColor.RED
						+ "You DO NOT have the permission to do this");
				return true;
			} else {
				int value = getConfig().getInt("Sushi.RawFishAmount");
				ItemStack itemstack = new ItemStack(Material.RAW_FISH, value);
				Player player = (Player) sender;
				PlayerInventory inventory = player.getInventory();
				inventory.addItem(itemstack);
				sender.sendMessage(ChatColor.BLUE + "You have been givin Fish!");
				return true;
			}
		}

		// Start of sushiset commands
		if (commandLabel.equalsIgnoreCase("sushiset")
				|| (commandLabel.equalsIgnoreCase("ss"))) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("rawfishamount")
						|| (args[0].equalsIgnoreCase("rfa"))) {
					if (sender.hasPermission("sushi.setrawfishamount")) {
						if (args.length <= 1) {
							sender.sendMessage(ChatColor.RED
									+ "Not enough arguments, Correct usage /sushiset rfa [Amount]");
							return true;
						} else {
							String rfa = args[1];
							int rfavalue = Integer.parseInt(rfa);
							this.getConfig().set("Sushi.RawFishAmount",
									rfavalue);
							this.saveConfig();
							sender.sendMessage("RawFishAmount has been changed to "
									+ rfavalue);
							return true;
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
			//end of sushiset args.length >= 1
			if (args.length == 1){
				if (args[0].equalsIgnoreCase("help")) {
					sender.sendMessage("Type /help sushi and look for the /sushiset commands");
					return true;
				}else{
					sender.sendMessage(ChatColor.RED + "Not a valid command");
					sender.sendMessage(ChatColor.GREEN + "Try doing /sushi help or /sushi help");
					return true;
				}
				
			}
		sender.sendMessage(ChatColor.AQUA + 
				"-------------------------");
		sender.sendMessage(ChatColor.GREEN + "Welcome to the sushiset section");
		sender.sendMessage(ChatColor.GOLD + "Type in /sushiset help");
		sender.sendMessage(ChatColor.AQUA + 
				"-------------------------");
		} else {
			sender.sendMessage(ChatColor.RED
					+ "This command isn't recongnised. Try doing /sushiset help or /ss help");
			return true;
		}

		return false;
	} // End of whole command

	public void createconfig() {
		// Creates config.yml
		File file = new File(getDataFolder() + File.separator + "config.yml");
		// If config.yml doesnt exit
		if (!file.exists()) {
			// Tells console its creating a config.yml
			this.getLogger().info("You don't have a config file!!!");
			this.getLogger().info("Generating config.yml.....");
			this.getConfig().options().copyDefaults(true);
			this.saveDefaultConfig();
		}

	}
	// End of whole plugin
}
