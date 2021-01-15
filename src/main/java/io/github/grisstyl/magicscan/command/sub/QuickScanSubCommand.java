package io.github.grisstyl.magicscan.command.sub;

import io.github.grisstyl.api.command.CommandBase;
import io.github.grisstyl.api.command.CommandContext;
import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.task.QuickScanTask;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * TODO Write docs
 */
@RequiredArgsConstructor
public class QuickScanSubCommand extends CommandBase.SubCommand {

    @NonNull
    private MagicScanController controller;

    @Override
    public void execute(CommandContext context) {
        if (context.isPlayer()) {
            new QuickScanTask(controller, context.getPlayer()).run();
        } else if (context.isConsole()) {
            if (context.hasFlag("gui")) {
                controller.sendMessage(context, "no_gui_console");
            } else {
                new QuickScanTask(controller, context.getConsole()).run();
            }
        }
    }
}
