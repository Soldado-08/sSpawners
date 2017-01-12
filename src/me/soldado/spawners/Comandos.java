package me.soldado.spawners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Comandos implements CommandExecutor{

	public Main plugin;
	
	public Comandos(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("spawners")){
				if(!p.hasPermission(plugin.cfg.permmenu)){
					p.sendMessage(plugin.msg.semperm);
					return true;
				}
				plugin.gui.abrir(p);
				return true;
			}
		}
		return false;
	}
	
	public void iniciarComandos(){
		plugin.getCommand("spawners").setExecutor(this);
	}
}
