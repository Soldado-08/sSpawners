package me.soldado.spawners;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

public class Nome {

	public Main plugin;
	
	public Nome(Main plugin)
	{
		this.plugin = plugin;
	}
	
	File langFile;
	FileConfiguration lang;
	
	public String getNome(EntityType tipo){
		try{
			String tp = tipo.toString();
			String returnval = getString(tp);
			return returnval;
		}catch(Exception e){
			return "null";
		}
	}
	
	public void iniciarNomes(){

		if (langFile == null) {
			langFile = new File(plugin.getDataFolder(), "lang.yml");
		}
		if (!langFile.exists()) {
			plugin.saveResource("lang.yml", false);
		}
		lang = YamlConfiguration.loadConfiguration(langFile);
	}
	
	public String getString(String s){
		return lang.getString(s);
	}
	
}
