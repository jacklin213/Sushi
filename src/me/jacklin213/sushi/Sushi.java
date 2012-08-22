package me.jacklin213.sushi;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Sushi extends JavaPlugin implements Listener {
	// define stuff
	PluginDescriptionFile pdfFile;
	public static Sushi plugin;
	public final Logger log = Logger.getLogger("Minecraft");
	public final CommandListener cl = new CommandListener(this);
	public static Permission Permissions = null;

	// What to do onEnable
	public void onEnable() {
		this.pdfFile = getDescription();
		this.getLogger().info(this.pdfFile.getName() + " By "
				+ this.pdfFile.getAuthors() + " is now enabled!.");
		createconfig();
		getCommand("sushi").setExecutor(cl);
		getCommand("sushiset").setExecutor(cl);
	}

	// what to do onDisable
	public void onDisable() {
		this.pdfFile = getDescription();
		this.getLogger().info(this.pdfFile.getName() + " is now disabled.");
	}

	

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
