ackage me.badbh222.Unlimited;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Unlimited extends JavaPlugin
{
  HashMap<String, HashMap<Integer, Boolean>> UB = new HashMap();
  HashMap<Integer, Boolean> UBints = new HashMap();
  HashMap<Integer, Integer> items = new HashMap();
  ArrayList<Integer> UPitems = new ArrayList();
  int[] NAitems = { 0, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 322, 325, 326, 327, 328, 329, 332, 333, 334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 357, 358, 359, 2256, 2257 };
  ArrayList<Integer> banneditems = new ArrayList();
  Logger log = Logger.getLogger("Minecraft");
  public static PermissionHandler permissionHandler;
  public boolean UsePermissions;
  private final UnlimitedBlockListener blockListener = new UnlimitedBlockListener(this);

  public void onEnable()
  {
    this.items.put(Integer.valueOf(64), Integer.valueOf(324)); this.items.put(Integer.valueOf(71), Integer.valueOf(330)); this.items.put(Integer.valueOf(55), Integer.valueOf(331)); this.items.put(Integer.valueOf(92), Integer.valueOf(354)); this.items.put(Integer.valueOf(26), Integer.valueOf(355)); this.items.put(Integer.valueOf(93), Integer.valueOf(356)); this.items.put(Integer.valueOf(63), Integer.valueOf(323)); this.items.put(Integer.valueOf(68), Integer.valueOf(323));
    for (int i : this.NAitems) {
      this.UPitems.add(Integer.valueOf(i));
    }
    File unlimDir = getDataFolder();
    if (!unlimDir.exists()) {
      this.log.info("[Unlimited] Config folder is missing, creating...");
      try {
        unlimDir.mkdir();
        this.log.info("done.");
      } catch (Exception ex) {
        this.log.info("failed.");
      }
    }
    File config = new File("plugins/Unlimited/Config.yml");
    if (!config.exists()) {
      this.log.info("[Unlimited] Config.yml is missing, creating...");
      try {
        config.createNewFile();
        this.log.info("done.");
        this.log.info("[Unlimited] Writing default config...");
        try {
          write("Banned item IDs", Integer.valueOf(46));
          this.log.info("done.");
        } catch (Exception ex) {
          this.log.info("failed.");
        }
      } catch (Exception ex) {
        this.log.info("failed.");
      }
    }
    loadConfig();
    getConfigData();
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvent(Event.Type.BLOCK_PLACE, this.blockListener, Event.Priority.Normal, this);
    pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.Normal, this);
    this.log.info("Unlimited v0.1 Enabled");
    setupPermissions();
  }

  public void onDisable()
  {
    this.log.info("Unlimited v0.1 Disabled");
  }

  public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
  {
    if (commandLabel.equalsIgnoreCase("unlimited")) {
      if ((sender instanceof Player)) {
        Player player = (Player)sender;
        int ci = player.getItemInHand().getTypeId();
        if (this.UsePermissions)
        {
          if (args.length == 1) {
            if (args[0].equalsIgnoreCase("enable")) {
              if (permissionHandler.has(player, "unlimited.use")) {
                if (this.UPitems.contains(Integer.valueOf(ci))) {
                  player.sendMessage(ChatColor.RED + "You can not get unlimited " + player.getItemInHand().getType());
                }
                else if ((this.banneditems.contains(Integer.valueOf(ci))) && (!permissionHandler.has(player, "unlimited.bypass." + ci))) {
                  player.sendMessage(ChatColor.RED + "That block is banned!");
                }
                else if (this.UB.containsKey(player.getName())) {
                  this.UBints = ((HashMap)this.UB.get(player.getName()));
                  if (!this.UBints.containsKey(Integer.valueOf(ci))) {
                    this.UBints.put(Integer.valueOf(ci), Boolean.valueOf(true));
                    this.UB.remove(player.getName());
                    this.UB.put(player.getName(), this.UBints);
                    player.sendMessage(ChatColor.GREEN + "You now have unlimited " + player.getItemInHand().getType());
                  } else {
                    player.sendMessage(ChatColor.RED + "You already have unlimited " + player.getItemInHand().getType());
                  }
                } else {
                  this.UBints.clear();
                  this.UBints.put(Integer.valueOf(ci), Boolean.valueOf(true));
                  this.UB.put(player.getName(), this.UBints);
                  player.sendMessage(ChatColor.GREEN + "You now have unlimited " + player.getItemInHand().getType());
                }
              }
              else
              {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
              }
            } else if (args[0].equalsIgnoreCase("disable")) {
              if (permissionHandler.has(player, "unlimited.use")) {
                if (this.UPitems.contains(Integer.valueOf(ci))) {
                  player.sendMessage(ChatColor.RED + "You can not get unlimited " + player.getItemInHand().getType());
                }
                else if ((this.banneditems.contains(Integer.valueOf(ci))) && (!permissionHandler.has(player, "unlimited.bypass." + ci))) {
                  player.sendMessage(ChatColor.RED + "That block is banned!");
                }
                else if (this.UB.containsKey(player.getName())) {
                  this.UBints = ((HashMap)this.UB.get(player.getName()));
                  if (this.UBints.containsKey(Integer.valueOf(ci))) {
                    this.UBints.remove(Integer.valueOf(ci));
                    this.UB.remove(player.getName());
                    this.UB.put(player.getName(), this.UBints);
                    player.sendMessage(ChatColor.RED + "You disabled unlimited " + player.getItemInHand().getType());
                  } else {
                    player.sendMessage(ChatColor.RED + "You do not have unlimited " + player.getItemInHand().getType());
                  }
                } else {
                  player.sendMessage(ChatColor.RED + "You have not got any items to disable");
                }
              }
              else
              {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
              }
            } else if (args[0].equalsIgnoreCase("reload")) {
              if (permissionHandler.has(player, "unlimited.reload")) {
                loadConfig();
                getConfigData();
                player.sendMessage(ChatColor.GREEN + "Config has been reloaded successfully.");
              } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
              }
            }
            else helpMsg(player);
          }
          else {
            helpMsg(player);
          }

        }
        else if ((this.banneditems.contains(Integer.valueOf(ci))) && (!player.isOp())) {
          player.sendMessage(ChatColor.RED + "That block is banned!");
        }
        else if (args.length == 1) {
          if (args[0].equalsIgnoreCase("enable")) {
            if (player.isOp()) {
              if (this.UPitems.contains(Integer.valueOf(ci))) {
                player.sendMessage(ChatColor.RED + "You can not get unlimited " + player.getItemInHand().getType());
              }
              else if ((this.banneditems.contains(Integer.valueOf(ci))) && (!permissionHandler.has(player, "unlimited.bypass." + ci))) {
                player.sendMessage(ChatColor.RED + "That block is banned!");
              }
              else if (this.UB.containsKey(player.getName())) {
                this.UBints = ((HashMap)this.UB.get(player.getName()));
                if (!this.UBints.containsKey(Integer.valueOf(ci))) {
                  this.UBints.put(Integer.valueOf(ci), Boolean.valueOf(true));
                  this.UB.remove(player.getName());
                  this.UB.put(player.getName(), this.UBints);
                  player.sendMessage(ChatColor.GREEN + "You now have unlimited " + player.getItemInHand().getType());
                } else {
                  player.sendMessage(ChatColor.RED + "You already have unlimited " + player.getItemInHand().getType());
                }
              } else {
                this.UBints.clear();
                this.UBints.put(Integer.valueOf(ci), Boolean.valueOf(true));
                this.UB.put(player.getName(), this.UBints);
                player.sendMessage(ChatColor.GREEN + "You now have unlimited " + player.getItemInHand().getType());
              }
            }
            else
            {
              player.sendMessage(ChatColor.RED + "You must be an Op to use this command!");
            }
          } else if (args[0].equalsIgnoreCase("disable")) {
            if (player.isOp()) {
              if (this.UPitems.contains(Integer.valueOf(ci))) {
                player.sendMessage(ChatColor.RED + "You can not get unlimited " + player.getItemInHand().getType());
              }
              else if ((this.banneditems.contains(Integer.valueOf(ci))) && (!permissionHandler.has(player, "unlimited.bypass." + ci))) {
                player.sendMessage(ChatColor.RED + "That block is banned!");
              }
              else if (this.UB.containsKey(player.getName())) {
                this.UBints = ((HashMap)this.UB.get(player.getName()));
                if (this.UBints.containsKey(Integer.valueOf(ci))) {
                  this.UBints.remove(Integer.valueOf(ci));
                  this.UB.remove(player.getName());
                  this.UB.put(player.getName(), this.UBints);
                  player.sendMessage(ChatColor.RED + "You disabled unlimited " + player.getItemInHand().getType());
                } else {
                  player.sendMessage(ChatColor.RED + "You do not have unlimited " + player.getItemInHand().getType());
                }
              } else {
                player.sendMessage(ChatColor.RED + "You have not got any items to disable");
              }
            }
            else
            {
              player.sendMessage(ChatColor.RED + "You must be an Op to use this command!");
            }
          } else if (args[0].equalsIgnoreCase("reload")) {
            if (player.isOp()) {
              loadConfig();
              getConfigData();
              player.sendMessage(ChatColor.GREEN + "Config has been reloaded successfully.");
            } else {
              player.sendMessage(ChatColor.RED + "You must be an Op to use this command!");
            }
          }
          else helpMsg(player);
        }
        else {
          helpMsg(player);
        }

      }

      return true;
    }
    return false;
  }

  public void setupPermissions()
  {
    Plugin UnlimitedPlugin = getServer().getPluginManager().getPlugin("Permissions");
    if (permissionHandler == null)
      if (UnlimitedPlugin != null) {
        permissionHandler = ((Permissions)UnlimitedPlugin).getHandler();
        this.log.info("[Unlimited] Permissions plugin found!");
        this.UsePermissions = true;
      } else {
        this.log.info("[Unlimited] Permissions plugin not found, only Ops can use Unlimited.");
        this.UsePermissions = false;
      }
  }

  public void helpMsg(Player player)
  {
    player.sendMessage(ChatColor.WHITE + "--------------------" + ChatColor.GREEN + "Unlimited Help" + ChatColor.WHITE + "--------------------");
    player.sendMessage(ChatColor.GREEN + "/Unlimited enable" + ChatColor.WHITE + " - Grants you unlimited of your current item");
    player.sendMessage(ChatColor.GREEN + "/Unlimited disable" + ChatColor.WHITE + " - Revokes unlimited of your current item");
    player.sendMessage(ChatColor.GREEN + "/Unlimited reload" + ChatColor.WHITE + " - Reloads the configuration file");
    player.sendMessage(ChatColor.GREEN + "/Unlimited help" + ChatColor.WHITE + " - Displays this help message");
  }

  public Configuration loadConfig()
  {
    try {
      File conf = new File("plugins/Unlimited/Config.yml");
      Configuration config = new Configuration(conf);
      config.load();
      return config;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public void getConfigData()
  {
    Configuration config = loadConfig();
    String bi = config.getString("Banned item IDs");
    String[] split = bi.split(",");
    for (String s : split)
      try {
        int i = Integer.parseInt(s.trim());
        this.banneditems.add(Integer.valueOf(i));
      } catch (Exception ex) {
        this.log.info("Could not parse " + s.trim() + " to an item ID");
      }
  }

  public void write(String root, Object x)
  {
    Configuration config = loadConfig();
    config.setProperty(root, x);
    config.save();
  }
}