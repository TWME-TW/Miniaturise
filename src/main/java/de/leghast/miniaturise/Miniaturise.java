package de.leghast.miniaturise;

import de.leghast.miniaturise.command.*;
import de.leghast.miniaturise.completer.PositionTabCompleter;
import de.leghast.miniaturise.completer.ScaleTabCompleter;
import de.leghast.miniaturise.completer.ToolTabCompleter;
import de.leghast.miniaturise.listener.PlayerQuitListener;
import de.leghast.miniaturise.listener.PlayerInteractListener;
import de.leghast.miniaturise.manager.ConfigManager;
import de.leghast.miniaturise.manager.MiniatureManager;
import de.leghast.miniaturise.manager.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class of the Miniaturise Minecraft paper plugin
 * @author GhastCraftHD
 * */
public final class Miniaturise extends JavaPlugin {

    private MiniatureManager miniatureManager;
    private RegionManager regionManager;

    public final String PREFIX = "§7[§eMiniaturise§7] ";

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);
        initialiseManagers();
        registerListeners();
        setCommands();
        setTabCompleters();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    private void initialiseManagers(){
        miniatureManager = new MiniatureManager(this);
        regionManager = new RegionManager(this);
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
    }

    private void setCommands(){
        getCommand("select").setExecutor(new SelectCommand(this));
        getCommand("scale").setExecutor(new ScaleCommand(this));
        getCommand("cut").setExecutor(new CutCommand(this));
        getCommand("tools").setExecutor(new ToolsCommand(this));
        getCommand("paste").setExecutor(new PasteCommand(this));
        getCommand("tool").setExecutor(new ToolCommand(this));
        getCommand("delete").setExecutor(new DeleteCommand(this));
        getCommand("copy").setExecutor(new CopyCommand(this));
        getCommand("position").setExecutor(new PositionCommand(this));
        getCommand("clear").setExecutor(new ClearCommand(this));
    }

    public void setTabCompleters(){
        getCommand("position").setTabCompleter(new PositionTabCompleter());
        getCommand("scale").setTabCompleter(new ScaleTabCompleter());
        getCommand("tool").setTabCompleter(new ToolTabCompleter());
    }

    /**
     * @return The MiniatureManager instance
     */
    public MiniatureManager getMiniatureManager(){
        return miniatureManager;
    }

    /**
     * @return The RegionManager instance
     */
    public RegionManager getRegionManager(){
        return regionManager;
    }

    public String getDimensionName(String string){
        switch (string){
            case "NORMAL" -> {
                return "minecraft:overworld";
            }
            case "NETHER" -> {
                return "minecraft:the_nether";
            }
            case "THE_END" -> {
                return "minecraft:the_end";
            }
            default -> {
                return "Invalid dimension";
            }
        }
    }
}