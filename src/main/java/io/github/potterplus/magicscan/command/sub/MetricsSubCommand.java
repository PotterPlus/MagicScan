package io.github.potterplus.magicscan.command.sub;

import io.github.potterplus.api.command.CommandBase;
import io.github.potterplus.api.command.CommandContext;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.MagicScanPlugin;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static io.github.potterplus.api.string.StringUtilities.equalsAny;

/**
 * TODO Write docs
 */
@RequiredArgsConstructor
public class MetricsSubCommand extends CommandBase.SubCommand {

    @NonNull
    private MagicScanController controller;

    public MagicScanPlugin getPlugin() {
        return controller.getPlugin();
    }

    @Override
    public void execute(CommandContext context) {
        if (context.getArgs().length == 1) {
            context.sendMessage("&7MS is tracking &e" + controller.getSpells().size() + " &7out of &e" + controller.getAllSpells().size() + " &7spells in this configuration across &e" + controller.getSpellCategories().size() + " &7categories&8.");
            context.sendMessage("&7There are &e" + controller.getPaths().size() + " &7paths&8.");
            context.sendMessage("&7There are &e" + controller.getActions().size() + " &7actions&8.");
            context.sendMessage("&7There are &e" + controller.getMobs().size() + " &7mobs&8.");
            context.sendMessage("&7There are &e" + controller.getWands().size() + " &7wands&8.");
        } else {
            String sub = context.getSub();

            if (equalsAny(sub, "spells")) {
                // TODO Do the rest of this subcommand
            }
        }
    }
}
