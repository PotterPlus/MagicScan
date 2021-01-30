package io.github.potterplus.magicscan.task;

import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.misc.Describable;
import io.github.potterplus.magicscan.misc.Utilities;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static io.github.potterplus.api.misc.StringUtilities.replaceMap;

/**
 * A task to describe a collection of Describables to a CommandSender.
 */
@RequiredArgsConstructor
public class DescribeCollectionTask extends BukkitRunnable {

    @NonNull
    private MagicScanController controller;

    @NonNull
    private List<Describable> describables;

    @NonNull
    private CommandSender to;

    @Override
    public void run() {
        controller.sendMessage(to, "describing_things", replaceMap("$amount", String.valueOf(describables.size())));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (to instanceof Player) {
                    for (Describable d : describables) {
                        Utilities.sendCompact((Player) to, d);
                    }
                } else if (to instanceof ConsoleCommandSender) {
                    int step = 0;

                    for (Describable d : describables) {
                        new DescribeTask(d, Bukkit.getConsoleSender())
                                .runTaskLater(controller.getPlugin(), step * controller.getConfig().getInterval() * 4);

                        step++;
                    }
                }
            }
        }.runTaskLater(controller.getPlugin(), 40);
    }
}
