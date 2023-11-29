package de.leghast.miniaturise.command;

import de.leghast.miniaturise.Miniaturise;
import de.leghast.miniaturise.instance.Miniature;
import de.leghast.miniaturise.instance.PlacedMiniature;
import de.leghast.miniaturise.instance.Region;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CopyCommand implements CommandExecutor {

    private Miniaturise main;

    public CopyCommand(Miniaturise main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
                try{
                    Region region = new Region(main.getRegionManager().getSelectedLocations(player.getUniqueId()));
                    if(main.getRegionManager().hasRegion(player.getUniqueId())) {
                        main.getRegionManager().getRegions().replace(player.getUniqueId(), region);
                    }else{
                        main.getRegionManager().addRegion(player.getUniqueId(), region);
                    }
                    List<BlockDisplay> blockDisplays = new ArrayList<>();
                    for(Chunk chunk : player.getWorld().getLoadedChunks()){
                        for(Entity entity : chunk.getEntities()){
                            if(entity instanceof BlockDisplay && region.contains(entity.getLocation())){
                                blockDisplays.add((BlockDisplay) entity);
                            }
                        }
                    }

                    if(!blockDisplays.isEmpty()){
                        Miniature miniature = new Miniature(new PlacedMiniature(blockDisplays), player.getLocation(), blockDisplays.get(0).getTransformation().getScale().x());
                        if(main.getMiniatureManager().hasMiniature(player.getUniqueId())){
                            main.getMiniatureManager().getMiniatures().replace(player.getUniqueId(), miniature);
                        }else{
                            main.getMiniatureManager().addMiniature(player.getUniqueId(), miniature);
                        }
                        player.sendMessage(main.PREFIX + "§aThe placed miniature was copied §e(" + miniature.getBlocks().size() + " blocks)");
                    }else{
                        player.sendMessage(main.PREFIX + "§cThere is no miniature in the selected region");
                    }
                }catch(IllegalArgumentException e){
                    player.sendMessage(main.PREFIX + "§c" + e.getMessage());
                    return false;
                }
        }
        return false;
    }
}