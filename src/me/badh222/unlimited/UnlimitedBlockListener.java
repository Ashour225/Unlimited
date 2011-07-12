package me.badbh222.Unlimited;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class UnlimitedBlockListener extends BlockListener{
	
	private final Unlimited plugin;
	public UnlimitedBlockListener(final Unlimited plugin){
		
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public void onBlockPlace(BlockPlaceEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		Block block = event.getBlock();
		Player player = (Player)event.getPlayer();
		int ci = block.getTypeId();
		if (plugin.items.containsKey(ci)){
			ci = plugin.items.get(ci);
		}
		
		if (plugin.UB.containsKey(player.getName())){
			plugin.UBints = plugin.UB.get(player.getName());
			if (plugin.UBints.containsKey(ci)){
				player.getInventory().addItem(new ItemStack(ci, 1));
				player.updateInventory();
			}
		}
	}
	
	public void onBlockBreak(BlockBreakEvent event){
		
		if (event.isCancelled()){
			return;
		}
		
		Block block = event.getBlock();
		Player player = (Player)event.getPlayer();
		int ci = block.getTypeId();
		if (plugin.items.containsKey(ci)){
			ci = plugin.items.get(ci);
		}
		
		if (plugin.UB.containsKey(player.getName())){
			plugin.UBints = plugin.UB.get(player.getName());
			if (plugin.UBints.containsKey(ci)){
				block.setType(Material.AIR);
				event.setCancelled(true);
			}
		}
	}
}