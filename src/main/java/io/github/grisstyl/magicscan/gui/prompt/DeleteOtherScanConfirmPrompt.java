package io.github.grisstyl.magicscan.gui.prompt;

import io.github.grisstyl.api.gui.ConfirmPrompt;
import io.github.grisstyl.api.misc.ItemStackBuilder;
import io.github.grisstyl.magicscan.MagicScanController;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import static io.github.grisstyl.api.misc.StringUtilities.replaceMap;

/**
 * Copyright (c) 2013-2020 Tyler Grissom
 */
public class DeleteOtherScanConfirmPrompt extends ConfirmPrompt {

    private MagicScanController controller;
    private CommandSender target, initiator;

    public DeleteOtherScanConfirmPrompt(MagicScanController controller, CommandSender target, CommandSender initiator) {
        super("Delete &e" + target.getName() + "'s Scan?", ItemStackBuilder
                .start(Material.NAME_TAG)
                .name("&7Are you sure you want to delete &e" + target.getName() + "'s &7scan?")
                .lore("&4Warning: &cThis cannot be undone!"));

        this.controller = controller;
        this.target = target;
        this.initiator = initiator;
    }

    @Override
    public void onConfirm(InventoryClickEvent event) {
        controller.getScanController().removeScan(target);
        controller.sendMessage(initiator, "scan_deleted_other", replaceMap("$name", target.getName()));
        controller.sendMessage(target, "scan_cleared", replaceMap("$name", initiator.getName()));
    }

    @Override
    public void onCancel(Player player) {
        controller.sendMessage(initiator, "scan_not_deleted_other", replaceMap("$name", target.getName()));
    }
}
