package me.soldado.spawners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.attribute.AttributeStorage;

public class GUI implements Listener{
	
	public Main plugin;
	
	public GUI(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public UUID uuid = UUID.fromString("656b5336-46dc-4e93-89a5-975a3f1134e3");
	
	@EventHandler
	public void click(InventoryClickEvent event){
		String titulo = event.getInventory().getTitle();
		if(!titulo.contains("[Spawners]")) return;
		Player p = (Player) event.getWhoClicked();
		if(titulo.contains("Menu Principal")){
			ItemStack item = event.getCurrentItem();
			if(item != null && item.getType() != Material.AIR){
				if(item.getType().equals(Material.MOB_SPAWNER)) p.openInventory(menuSpawners());
				else if(item.getType().equals(Material.MONSTER_EGG)) p.openInventory(menuDefinidores());
			}
		}else if(titulo.contains("Menu de Spawners") || titulo.contains("Menu de Definidores")){
			ItemStack item = event.getCurrentItem();
			if(item == null || item.getType() == Material.AIR){
				p.openInventory(menuPrincipal());
			}else{
				if(p.getInventory().firstEmpty() > -1){
					p.getInventory().addItem(event.getCurrentItem().clone());
				}
			}
		}
		event.setCancelled(true);
	}
	
	public void abrir(Player p){
		p.openInventory(menuPrincipal());
	}
	
	Inventory menuPrincipal(){
		Inventory inv = Bukkit.getServer().createInventory(null, 9, "§a[Spawners] Menu Principal");
		
		ItemStack item1 = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta item1m = item1.getItemMeta();
		item1m.setDisplayName(ChatColor.GREEN + "Menu de Spawners");
		item1.setItemMeta(item1m);
		
		ItemStack item2 = new ItemStack(Material.MONSTER_EGG);
		ItemMeta item2m = item2.getItemMeta();
		item2m.setDisplayName(ChatColor.GREEN + "Menu de Definidores");
		item2.setItemMeta(item2m);

		inv.setItem(3, item1);
		inv.setItem(5, item2);
		return inv;
	}
	
	Inventory menuSpawners(){
		Inventory inv = Bukkit.getServer().createInventory(null, 36, "§a[Spawners] Menu de Spawners");
		
		for(String s : plugin.nm.lang.getKeys(false)){
			
			try{
				EntityType tipo = EntityType.valueOf(s);
				ItemStack item = new ItemStack(Material.MOB_SPAWNER);
				ItemMeta mitem = item.getItemMeta();
				mitem.setDisplayName(plugin.cfg.prefixospawner + " " + plugin.nm.getNome(tipo));
				item.setItemMeta(mitem);
				AttributeStorage itemattr = AttributeStorage.newTarget(item, uuid);
				itemattr.setData(tipo.toString());
				inv.addItem(itemattr.getTarget());
			}catch(Exception e){}
			
		}
		
		return inv;
	}
	
	@SuppressWarnings("deprecation")
	Inventory menuDefinidores(){
		Inventory inv = Bukkit.getServer().createInventory(null, 36, "§a[Spawners] Menu de Definidores");
		
		for(String s : plugin.nm.lang.getKeys(false)){
			
			try{
				EntityType tipo = EntityType.valueOf(s);
				ItemStack item = new ItemStack(Material.MONSTER_EGG, 1, (byte) tipo.getTypeId());
				ItemMeta mitem = item.getItemMeta();
				mitem.setDisplayName(plugin.cfg.prefixodefinidor + " " + plugin.nm.getNome(tipo));
				item.setItemMeta(mitem);
				AttributeStorage itemattr = AttributeStorage.newTarget(item, uuid);
				itemattr.setData(tipo.toString());
				inv.addItem(itemattr.getTarget());
			}catch(Exception e){}
			
		}
		
		return inv;
	}
	
	public void registrar(){
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
