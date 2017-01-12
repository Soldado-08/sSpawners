package me.soldado.spawners;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	Core core;
	Comandos cmd;
	Config cfg;
	Mensagens msg;
	Nome nm;
	GUI gui;
	
	public void onEnable(){
		core = new Core(this);
		cmd = new Comandos(this);
		cfg = new Config(this);
		msg = new Mensagens(this);
		nm = new Nome(this);
		gui = new GUI(this);

		core.registrarEventos();
		gui.registrar();
		
		cfg.iniciarConfig();
		msg.iniciarMensagens();
		nm.iniciarNomes();
		cmd.iniciarComandos();

		this.getLogger().info("sSpawner ativado!!!");
		this.getLogger().info("Autor: Soldado_08");
	}
	
	public void onDisable(){
		this.getLogger().info("sSpawner desativado!!!");
		this.getLogger().info("Autor: Soldado_08");
	}
}
