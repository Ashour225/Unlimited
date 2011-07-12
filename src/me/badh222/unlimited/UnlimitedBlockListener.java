package me.badbh222.Unlimited;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class UnlimitedBlockListener extends BlockListener
{
  private final Unlimited plugin;

  public UnlimitedBlockListener(Unlimited plugin)
  {
    this.plugin = plugin;
  }

  public void onBlockPlace(BlockPlaceEvent event)
  {
    if (event.isCancelled()) {
      return;
    }

    Block block = event.getBlock();
    Player player = event.getPlayer();
    int ci = block.getTypeId();
    if (this.plugin.items.containsKey(Integer.valueOf(ci))) {
      ci = ((Integer)this.plugin.items.get(Integer.valueOf(ci))).intValue();
    }

    if (this.plugin.UB.containsKey(player.getName())) {
      this.plugin.UBints = ((HashMap)this.plugin.UB.get(player.getName()));
      if (this.plugin.UBints.containsKey(Integer.valueOf(ci))) {
        player.getInventory().addItem(new ItemStack[] { new ItemStack(ci, 1) });
        player.updateInventory();
      }
    }
  }

  public void onBlockBreak(BlockBreakEvent event)
  {
    if (event.isCancelled()) {
      return;
    }

    Block block = event.getBlock();
    Player player = event.getPlayer();
    int ci = block.getTypeId();
    if (this.plugin.items.containsKey(Integer.valueOf(ci))) {
      ci = ((Integer)this.plugin.items.get(Integer.valueOf(ci))).intValue();
    }

    if (this.plugin.UB.containsKey(player.getName())) {
      this.plugin.UBints = ((HashMap)this.plugin.UB.get(player.getName()));
      if (this.plugin.UBints.containsKey(Integer.valueOf(ci))) {
        block.setType(Material.AIR);
        event.setCancelled(true);
      }
    }
  }
}