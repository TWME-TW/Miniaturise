package de.leghast.miniaturise.command;

import de.leghast.miniaturise.Miniaturise;
import de.leghast.miniaturise.constant.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearCommand implements CommandExecutor {

    private final Miniaturise main;

    public ClearCommand(Miniaturise main){
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return false;
        if(!player.hasPermission(Miniaturise.PERMISSION)) return false;

        if(main.getRegionManager().hasRegion(player.getUniqueId()) || main.getMiniatureManager().hasMiniature(player.getUniqueId())){
            main.getRegionManager().removeClipboard(player.getUniqueId());
            main.getMiniatureManager().removeClipboard(player.getUniqueId());
            player.sendMessage(Message.CLEARED_CLIPBOARD);
        }else{
            player.sendMessage(Message.EMPTY_CLIPBOARD);
        }
        return true;
    }
}
