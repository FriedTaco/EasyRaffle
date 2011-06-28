package com.FriedTaco.taco.EasyRaffle;



	import java.io.File;
	import java.io.FileReader;
	import java.util.logging.Logger;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Map;
	import org.bukkit.entity.Player;
	import org.bukkit.event.Event.Priority;
	import org.bukkit.event.Event;
	import org.bukkit.event.player.PlayerLoginEvent;
	import org.bukkit.plugin.PluginDescriptionFile;
	import org.bukkit.plugin.java.JavaPlugin;
	import org.bukkit.plugin.PluginManager;

	import com.nijiko.permissions.PermissionHandler;
	import com.nijikokun.bukkit.Permissions.Permissions;
	import org.bukkit.plugin.Plugin;
	import org.bukkit.util.config.Configuration;
	import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;




	public class EasyRaffle extends JavaPlugin {
		@SuppressWarnings("unused")
		private Logger log;
	    private final EasyRafflePlayerListener playerListener  = new EasyRafflePlayerListener(this);
	    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();  
		public static PermissionHandler Permissions;
		public Configuration config = null;
		public static ArrayList<Player> onlinePlayers = new ArrayList<Player>();
		private static Yaml yaml = new Yaml(new SafeConstructor());
		public static boolean raffleInProgress;

	   
		 private void setupPermissions() {
		      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

		      if (EasyRaffle.Permissions == null) 
		      {
		          if (test != null) {
		              EasyRaffle.Permissions = ((Permissions)test).getHandler();
		          } else {
		             System.out.println("It seems you don't have permissions installed. Ops will have priority with EasyRaffle now!");
		          }
		      }
		  }
		 
		 public void loadSettings() throws Exception
		 {
		    	if (!this.getDataFolder().exists())
		    	{
		    		this.getDataFolder().mkdirs();
		    	}
		    	final String dir = "plugins";
		    	File raffle = new File(this.getDataFolder(), "EasyRaffle.yml");
		        if (!raffle.exists())
		        	raffle.createNewFile();
		        config = new Configuration(raffle);
		        @SuppressWarnings("unchecked")
				Map<String, Object> data = (Map<String, Object>)yaml.load(new FileReader(raffle));
		        //Add config later on.
		        }
	    
	    public void onDisable() {
	    }
	    @Override
	    public void onEnable() {
	    	raffleInProgress = false;
	    	getCommand("raffle").setExecutor(new RaffleCommand(this));
	    	try {
				loadSettings();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        PluginManager pm = getServer().getPluginManager();
	        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
	        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
	        for(Player p : this.getServer().getOnlinePlayers())
	        	onlinePlayers.add(p);
	        PluginDescriptionFile pdfFile = this.getDescription();
	        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	        setupPermissions();
	    }

	    public boolean isDebugging(final Player player) {
	        if (debugees.containsKey(player)) {
	            return debugees.get(player);
	        } else {
	            return false;
	        }
	    }

	    public void setDebugging(final Player player, final boolean value) {
	        debugees.put(player, value);
	    }
		public void recordEvent(PlayerLoginEvent event) {
			// TODO Auto-generated method stub
			
		}
	}




