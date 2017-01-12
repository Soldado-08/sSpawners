package me.soldado.spawners;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.attribute.AttributeStorage;

public class Core implements Listener {

	public Main plugin;
	
	public Core(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public UUID uuid = UUID.fromString("656b5336-46dc-4e93-89a5-975a3f1134e3");
	
	@EventHandler
	public void pegarSpawner(PlayerInteractEvent event){
		Player p = event.getPlayer();
		if(!p.getGameMode().equals(GameMode.CREATIVE)) return;
		if(!p.isSneaking()) return;
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(event.getClickedBlock().getType().equals(Material.MOB_SPAWNER)){
			Block b = event.getClickedBlock();
			CreatureSpawner cs = (CreatureSpawner) b.getState();
			EntityType tipo = cs.getSpawnedType();
			
			ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
			ItemMeta meta = spawner.getItemMeta();
			meta.setDisplayName(plugin.cfg.prefixospawner +" "+ plugin.nm.getNome(tipo));
			spawner.setItemMeta(meta);
			AttributeStorage attr = AttributeStorage.newTarget(spawner, uuid);
			attr.setData(tipo.toString());
			
			if(p.getInventory().firstEmpty() > -1) p.getInventory().addItem(attr.getTarget());
		}
	}
	
	@EventHandler
	public void quebrarSpawner(BlockBreakEvent event){
		
		if(event.getBlock().getType().equals(Material.MOB_SPAWNER)){
			Player p = event.getPlayer();
			Block b = event.getBlock();
			if(!checkSilkTouch(p)) return;
			if(!(p.hasPermission(plugin.cfg.permspawner) && plugin.cfg.permparasilktouch)){
				String s = plugin.msg.sempermparaspawner;
				if(s.length() > 1) p.sendMessage(s);
				return;
			}
			CreatureSpawner cs = (CreatureSpawner) b.getState();
			EntityType tipo = cs.getSpawnedType();
			
			ItemStack spawner = new ItemStack(Material.MOB_SPAWNER);
			ItemMeta meta = spawner.getItemMeta();
			meta.setDisplayName(plugin.cfg.prefixospawner +" "+ plugin.nm.getNome(tipo));
			spawner.setItemMeta(meta);
			AttributeStorage attr = AttributeStorage.newTarget(spawner, uuid);
			attr.setData(tipo.toString());
			
			event.setExpToDrop(0);
			event.getBlock().getLocation().getWorld().dropItem(event.getBlock().getLocation(), attr.getTarget());
		}
		
	}
	
	@EventHandler
	public void colocarSpawner(BlockPlaceEvent event){
		
		if(event.getBlock().getType().equals(Material.MOB_SPAWNER)){
			AttributeStorage attr = AttributeStorage.newTarget(event.getItemInHand(), uuid);
			String tipostring = attr.getData(null);
			
			if(tipostring != null){
				try{
					Block b = event.getBlock();
					EntityType tipo = EntityType.valueOf(tipostring);
					CreatureSpawner cs = (CreatureSpawner) b.getState();
					cs.setSpawnedType(tipo);
				}catch(Exception e){
					e.printStackTrace();
					return;
				}
			}else if(plugin.cfg.blockspawnnulo) event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void definirSpawner(PlayerInteractEvent event){
		
		ItemStack item = event.getItem();
		if(item == null || item.getType() == Material.AIR) return;
		if(item.getType() != Material.MONSTER_EGG) return;
		AttributeStorage attr = AttributeStorage.newTarget(item, uuid);
		if(attr.getData(null) == null) return;
		if(event.getClickedBlock() == null || event.getClickedBlock().getType() == Material.AIR) return;
		if(event.getClickedBlock().getType() != Material.MOB_SPAWNER){
			String s = plugin.msg.comousardefinidor;
			if(s.length() > 1) event.getPlayer().sendMessage(s);
			event.setCancelled(true);
		}
		
	}
	
	boolean checkSilkTouch(Player p){
		ItemStack item = p.getItemInHand();
		if(!plugin.cfg.silktouch) return true;
		if(item.containsEnchantment(Enchantment.SILK_TOUCH)) return true;
		else return false;
	}
	
	String getString(EntityType tipo){
		return tipo.toString();
	}
	
	EntityType getTipo(String s){
		try{
			EntityType tipo = EntityType.valueOf(s);
			return tipo;
		}catch(Exception e){
			return null;
		}
	}

    public void registrarEventos(){
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
