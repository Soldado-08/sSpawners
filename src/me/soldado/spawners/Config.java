package me.soldado.spawners;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config{
	
	public Main plugin;
	
	public Config(Main plugin)
	{
		this.plugin = plugin;
	}
	
	File configFile;
	FileConfiguration config;
	
	String permspawner;
	String prefixospawner;
	String prefixodefinidor;
	String permmenu;
	boolean blockspawnnulo;
	boolean silktouch;
	boolean permparasilktouch;
	
	private void iniciarValores(){
		permspawner = getString("PegarSpawner");
		prefixospawner = getString("PrefixoSpawner");
		permmenu = getString("PermissaoMenuGUI");
		prefixodefinidor = getString("PrefixoDefinidor");
		blockspawnnulo = getBoolean("BlockSpawnerNulo");
		silktouch = getBoolean("SilkTouch");
		permparasilktouch = getBoolean("PermParaSilkTouch");
	}
	
	public void iniciarConfig(){

		if (configFile == null) {
			configFile = new File(plugin.getDataFolder(), "config.yml");
		}
		if (!configFile.exists()) {
			plugin.saveResource("config.yml", false);
		}
		config = YamlConfiguration.loadConfiguration(configFile);
		iniciarValores();
	}
	
	private String getString(String s){
		return config.getString(s).replace("&", "§");
	}
	
	@SuppressWarnings("unused")
	private int getInt(String s){
		return config.getInt(s);
	}
	
	private boolean getBoolean(String s){
		return config.getBoolean(s);
	}
}
