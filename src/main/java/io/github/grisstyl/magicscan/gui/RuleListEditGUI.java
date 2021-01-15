package io.github.grisstyl.magicscan.gui;

import com.google.common.collect.ImmutableMap;
import io.github.grisstyl.ppapi.gui.GUI;
import io.github.grisstyl.ppapi.gui.button.AutoGUIButton;
import io.github.grisstyl.ppapi.gui.button.GUIButton;
import io.github.grisstyl.ppapi.misc.BooleanFormat;
import io.github.grisstyl.ppapi.misc.ItemStackBuilder;
import io.github.grisstyl.ppapi.misc.PluginLogger;
import io.github.grisstyl.ppapi.misc.StringUtilities;
import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.file.ConfigFile;
import io.github.grisstyl.magicscan.rule.SpellRule;
import io.github.grisstyl.magicscan.scan.Scan;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

import static io.github.grisstyl.ppapi.misc.StringUtilities.replaceMap;

/**
 * TODO Write docs
 */
public class RuleListEditGUI extends GUI {

    @NonNull
    private MagicScanController controller;

    private ManageScanGUI returnTo;

    public RuleListEditGUI(MagicScanController controller, ManageScanGUI returnTo) {
        super(controller.getMessage("gui.rule_list_edit.title"), 54);

        this.controller = controller;
        this.returnTo = returnTo;

        this.refreshButtons();
    }

    public void update(InventoryClickEvent event) {
        this.refreshButtons();
        this.refreshInventory(event.getWhoClicked());
    }

    public void refreshButtons() {
        this.clearButtons();

        ConfigFile config = controller.getConfig();

        ItemStackBuilder enabled = ItemStackBuilder.of(config.getIcon("enabled", Material.GREEN_STAINED_GLASS));
        ItemStackBuilder disabled = ItemStackBuilder.of(config.getIcon("disabled", Material.RED_STAINED_GLASS));

        Player owner = returnTo.getOwner();
        Scan scan = controller.getScanController().getQueuedScan(owner);

        if (scan == null) {
            PluginLogger.atSevere()
                    .with("Cannot refresh rule list GUI buttons from null scan!")
                    .print();

            return;
        }

        ItemStackBuilder spells = ItemStackBuilder
                .of(config.getIcon("info", Material.NAME_TAG))
                .name(controller.getMessage("gui.rule_list_edit.spell_rules.name"));

        for (SpellRule rule : controller.getSpellRules()) {
            Map<String, String> replace = ImmutableMap.of(
                    "$rule", rule.getKey(),
                    "$bool", BooleanFormat.ENABLED_DISABLED.format(!scan.isOverriden(rule.getKey()))
            );

            spells.addLore(controller.getMessage("gui.rule_list_edit.spell_rules.lore_line_format", replace));
        }

        this.addButton(new AutoGUIButton(spells));

        for (SpellRule rule : controller.getSpellRules()) {
            Map<String, String> replace = StringUtilities.replaceMap("$rule", rule.getKey());
            GUIButton button = new GUIButton(
                    scan.isOverriden(rule.getKey())
                            ? disabled
                            .name(controller.getMessage("gui.rule_list_edit.spell_rule_disabled.name", replace))
                            .lore(controller.getLore("gui.rule_list_edit.spell_rule_disabled.lore"))
                            : enabled
                            .name(controller.getMessage("gui.rule_list_edit.spell_rule_enabled.name", replace))
                            .lore(controller.getLore("gui.rule_list_edit.spell_rule_enabled.lore"))
            );

            button.setListener(event -> {
                event.setCancelled(true);

                HumanEntity human = event.getWhoClicked();

                if (human instanceof Player) {
                    Player player = (Player) human;

                    ClickType click = event.getClick();

                    if (click.equals(ClickType.LEFT)) {
                        player.performCommand("magicscan scan override " + rule.getKey());
                    } else if (click.equals(ClickType.RIGHT)) {
                        for (SpellRule r : controller.getSpellRules()) {
                            if (r.getKey().equals(rule.getKey())) {
                                player.performCommand("magicscan scan enable " + rule.getKey());

                                continue;
                            }

                            player.performCommand("magicscan scan disable " + r.getKey());
                        }
                    }
                }

                this.update(event);
            });

            this.addButton(button);
        }

        GUIButton enableAll = new GUIButton(
                ItemStackBuilder
                .of(config.getIcon("enable", Material.EMERALD))
                .name(controller.getMessage("gui.rule_list_edit.enable_all.name"))
                .lore(controller.getLore("gui.rule_list_edit.enable_all.lore"))
        );

        enableAll.setListener(event -> {
            event.setCancelled(true);

            HumanEntity human = event.getWhoClicked();

            if (human instanceof Player) {
                Player player = (Player) human;

                for (SpellRule rule : controller.getSpellRules()) {
                    player.performCommand("magicscan scan enable " + rule.getKey());
                }

                this.update(event);
            }
        });

        GUIButton disableAll = new GUIButton(
                ItemStackBuilder
                        .of(config.getIcon("disable", Material.REDSTONE))
                        .name(controller.getMessage("gui.rule_list_edit.disable_all.name"))
                        .lore(controller.getLore("gui.rule_list_edit.disable_all.lore"))
        );

        disableAll.setListener(event -> {
            event.setCancelled(true);

            HumanEntity human = event.getWhoClicked();

            if (human instanceof Player) {
                Player player = (Player) human;

                for (SpellRule rule : controller.getSpellRules()) {
                    player.performCommand("magicscan scan disable " + rule.getKey());
                }

                this.update(event);
            }
        });

        GUIButton toggleAll = new GUIButton(
                ItemStackBuilder
                        .of(config.getIcon("toggle", Material.NETHER_STAR))
                        .name(controller.getMessage("gui.rule_list_edit.toggle_all.name"))
                        .lore(controller.getLore("gui.rule_list_edit.toggle_all.lore"))
        );

        toggleAll.setListener(event -> {
            event.setCancelled(true);

            HumanEntity human = event.getWhoClicked();

            if (human instanceof Player) {
                Player player = (Player) human;

                for (SpellRule rule : controller.getSpellRules()) {
                    player.performCommand("magicscan scan override " + rule.getKey());
                }

                this.update(event);
            }
        });

        if (returnTo != null) {
            GUIButton returnToPage = new GUIButton(
                    ItemStackBuilder
                            .of(config.getIcon("back", Material.ARROW))
                            .name(controller.getMessage("gui.rule_list_edit.return.name"))
                            .lore(controller.getLore("gui.rule_list_edit.return.lore"))
            );

            returnToPage.setListener(event -> returnTo.update(event.getWhoClicked()));

            this.setButton(this.getSize() - 1, returnToPage);
        }

        this.setButton(45, enableAll);
        this.setButton(46, disableAll);
        this.setButton(47, toggleAll);
    }
}
