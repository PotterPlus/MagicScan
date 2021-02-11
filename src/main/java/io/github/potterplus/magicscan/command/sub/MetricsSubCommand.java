package io.github.potterplus.magicscan.command.sub;

import io.github.potterplus.api.command.CommandBase;
import io.github.potterplus.api.command.CommandContext;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.MagicScanPlugin;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

import static io.github.potterplus.api.string.StringUtilities.equalsAny;

/**
 * TODO Write docs
 */
@RequiredArgsConstructor
public class MetricsSubCommand extends CommandBase.SubCommand {

    @NonNull
    private final MagicScanController controller;

    public MagicScanPlugin getPlugin() {
        return controller.getPlugin();
    }

    private void doOverallMetrics(CommandContext context) {
        context.sendMessage("&8&m----------------------------------------");
        context.sendMessage(" &bMagicScan Metrics");
        context.sendMessage(" &7Some numbers about your Magic setup");
        context.sendMessage("&8&m----------------------------------------");

        final Map<String, String> replace = new HashMap<>();

        replace.put("%spells_tracking%", String.valueOf(controller.getSpells().size()));
        replace.put("%spells_all%", String.valueOf(controller.getAllSpells().size()));
        replace.put("%spell_cats%", String.valueOf(controller.getSpellCategories().size()));
        replace.put("%spell_actions%", String.valueOf(controller.getActions().size()));
        replace.put("%paths%", String.valueOf(controller.getPaths().size()));
        replace.put("%mobs%", String.valueOf(controller.getMobs().size()));
        replace.put("$wands%", String.valueOf(controller.getWands().size()));

        new BukkitRunnable() {
            @Override
            public void run() {
                context.sendMessage("");
                context.sendMessage(" &dSpells");
                context.sendMessage(" &8> &bMS &7is tracking &e%spells_tracking% &7out of &e%spells_all% &7spells.", replace);
                context.sendMessage(" &8> &e%spell_cats% &7spell categories.", replace);
                context.sendMessage(" &8> &e%spell_actions% &7spell actions.", replace);
            }
        }.runTaskLater(getPlugin(), 40);

        new BukkitRunnable() {
            @Override
            public void run() {
                context.sendMessage("");
                context.sendMessage(" &dPaths");
                context.sendMessage(" &8> &7There are &e%paths% &7paths.", replace);
            }
        }.runTaskLater(getPlugin(), 80);

        new BukkitRunnable() {
            @Override
            public void run() {
                context.sendMessage("");
                context.sendMessage(" &dMobs");
                context.sendMessage(" &8> &7There are &e%mobs% &7mobs.", replace);
            }
        }.runTaskLater(getPlugin(), 120);

        new BukkitRunnable() {
            @Override
            public void run() {
                context.sendMessage("");
                context.sendMessage(" &dWands");
                context.sendMessage(" &8> &7There are &e%wands% &7wands.", replace);
            }
        }.runTaskLater(getPlugin(), 160);
    }

    @Override
    public void execute(CommandContext context) {
        if (context.getArgs().length == 1) {
            doOverallMetrics(context);
        } else {
            String sub = context.getSub();

            if (equalsAny(sub, "spells")) {
                // TODO Do the rest of this subcommand
            }
        }
    }
}
