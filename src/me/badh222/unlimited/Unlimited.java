package me.badbh222.Unlimited;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;


public class Unlimited extends JavaPlugin{
	
	HashMap<String, HashMap<Integer, Boolean>> UB = new HashMap<String, HashMap<Integer, Boolean>>();
	HashMap<Integer, Boolean> UBints = new HashMap<Integer, Boolean>();
	HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
	ArrayList<Integer> UPitems = new ArrayList<Integer>();
	int[] NAitems = {0, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 322, 325, 326, 327, 328, 329, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 357, 358, 359, 2256, 2257};
	ArrayList<Integer> banneditems = new ArrayList<Integer>();
	Logger log = Logger.getLogger("Minecraft");

	private final UnlimitedBlockListener blockListener = new UnlimitedBlockListener(this);

	public void onEnable(){
		
		items.put(64, 324); items.put(71, 330); items.put(55, 331); items.put(92, 354); items.put(26, 355); items.put(93, 356); items.put(63, 323); items.put(68, 323);
		for (int i : NAitems){
			UPitems.add(i);
		}
		File unlimDir = getDataFolder();
		if (!unlimDir.exists()){
			System.out.print("[Unlimited] Config folder is missing, creating...");
			try{
				unlimDir.mkdir();
				System.out.println("done.");
			}catch (Exception ex){
				System.out.println("failed.");
			}
		}
		File config = new File("plugins/Unlimited/Config.yml");
		if (!config.exists()){
			System.out.print("[Unlimited] Config.yml is missing, creating...");
			try{
				config.createNewFile();
				System.out.println("done.");
				System.out.print("[Unlimited] Writing default config...");
				try{
					write("Banned item IDs", 46);
					System.out.println("done.");
				}catch (Exception ex){
					System.out.println("failed.");
				}
			}catch (Exception ex){
				System.out.println("failed.");
			}
		}
		loadConfig();
		getConfigData();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
		log.info("Unlimited v0.1 Enabled");
	}

	public void onDisable(){
		
		log.info("Unlimited v0.1 Disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		
		if (commandLabel.equalsIgnoreCase("unlimited")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				int ci = player.getItemInHand().getTypeId();
				if (banneditems.contains(ci)){
					player.sendMessage(ChatColor.RED + "That block is banned!");
				}else{
					if (UPitems.contains(ci)){
						player.sendMessage(ChatColor.RED + "You can not get unlimited of this item");
					}else{
						if (args[0].equalsIgnoreCase("enable")){
							if (UB.containsKey(player.getName())){
								UBints = UB.get(player.getName());
								if (!UBints.containsKey(ci)){
									UBints.put(ci, true);
									UB.remove(player.getName());
									UB.put(player.getName(), UBints);
									player.sendMessage("You now have unlimited " + player.getItemInHand().getType());
								}else{
									player.sendMessage("You already have unlimited " + player.getItemInHand().getType());
								}
							}else{
								UBints.clear();
								UBints.put(ci, true);
								UB.put(player.getName(), UBints);
								player.sendMessage("You now have unlimited " + player.getItemInHand().getType());
							}
						}else if (args[0].equalsIgnoreCase("disable")){
							if (UB.containsKey(player.getName())){
								UBints = UB.get(player.getName());
								if (UBints.containsKey(ci)){
									UBints.remove(ci);
									UB.remove(player.getName());
									UB.put(player.getName(), UBints);
									player.sendMessage("You disabled unlimited " + player.getItemInHand().getType());
								}else{
									player.sendMessage("You do not have unlimited " + player.getItemInHand().getType());
								}
							}else{
								player.sendMessage("You have not got any items to disable");
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public Configuration loadConfig(){

		try{
			File conf = new File("plugins/Unlimited/Config.yml");
			Configuration config = new Configuration(conf);
			config.load();
			return config;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public void getConfigData(){
		
		Configuration config = loadConfig();
		String bi = config.getString("Banned item IDs");
		String split[] = bi.split(",");
		for (String s : split){
			try{
				int i = Integer.parseInt(s.trim());
				banneditems.add(i);
			}catch (Exception ex){
				System.out.println("Could not parse " + s.trim() + " to an item ID");
			}
		}
	}
	
	public void write(String root, Object x){
		
		Configuration config = loadConfig();
		config.setProperty(root, x);
		config.save();
	}
}