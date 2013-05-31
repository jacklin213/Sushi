package me.jacklin213.sushi;

import java.io.File;
import java.util.logging.Logger;

import me.jacklin213.sushi.commands.SushiCommand;
import me.jacklin213.sushi.commands.SushiSet;

import org.bukkit.plugin.java.JavaPlugin;

public class Sushi extends JavaPlugin {
	// define stuff
	public static Sushi plugin;
	
	public final Logger log = Logger.getLogger("Minecraft");
	public final SushiCommand sc = new SushiCommand(this);
	public final SushiSet ss = new SushiSet(this);

	// What to do onEnable
	public void onEnable() {
		log.info(String.format("[%s] Version: %s by jacklin213 has been enabled!", getDescription().getName(), getDescription().getVersion()));
		createconfig();
		getCommand("sushi").setExecutor(sc);
		getCommand("sushiset").setExecutor(ss);
	}

	// what to do onDisable
	public void onDisable() {
		log.info(String.format("[%s] by jacklin213 has been disabled!", getDescription().getName()));
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
