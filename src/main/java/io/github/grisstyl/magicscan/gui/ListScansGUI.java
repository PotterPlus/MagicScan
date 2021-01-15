package io.github.grisstyl.magicscan.gui;

import io.github.grisstyl.ppapi.gui.button.AutoGUIButton;
import io.github.grisstyl.ppapi.gui.button.GUIButton;
import io.github.grisstyl.ppapi.misc.ItemStackBuilder;
import io.github.grisstyl.ppapi.misc.PluginLogger;
import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.command.MagicScanCommand;
import io.github.grisstyl.magicscan.file.ConfigFile;
import io.github.grisstyl.magicscan.gui.describable.ListDescribablesGUI;
import io.github.grisstyl.magicscan.gui.prompt.ClearScansConfirmPrompt;
import io.github.grisstyl.magicscan.misc.Describable;
import io.github.grisstyl.magicscan.scan.Scan;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Write docs
 */
public class ListScansGUI extends ListDescribablesGUI {

    @Getter
    private Map<String, Scan> scans;

    public ListScansGUI(Player target, MagicScanController controller) {
        super(controller.getMessage("gui.list_scans.title"), target, controller);
    }

    public void refreshScans() {
        if (scans == null) {
            scans = new HashMap<>();
        } else {
            scans.clear();
        }

        for (Map.Entry<String, Scan> entry : getController().getScanController().getQueuedScans().entrySet()) {
            String name = entry.getKey();
            Scan scan = entry.getValue();

            if (!name.equals(getTarget().getName())) {
                scans.put(name, scan);
            }
        }
    }

    public GUIButton createButton(Scan scan) {
        ItemStackBuilder item = ItemStackBuilder.of(scan.describeAsItem(getTarget()));
        boolean isOwned = scan.getSender().equals(getTarget());

        if (isOwned) {
            item.addLore("&8> &aLeft-click &7to manage");
            item.addLore("&8> &cRight-click &7to cancel");
        } else {
            if (getTarget().hasPermission(MagicScanCommand.PERMISSION_SCAN_DELETE)) {
                item.addLore("&8> &cRight-click &7to force delete");
            }
        }

        GUIButton button = new GUIButton(item);

        button.setListener(event -> {
            event.setCancelled(true);

            if (!(getTarget() instanceof Player)) {
                return;
            }

            Player target = (Player) getTarget();
            ClickType click = event.getClick();

            if (click.equals(ClickType.LEFT)) {
                if (isOwned) {
                    target.performCommand("magicscan scan manage");
                    target.closeInventory();
                }
            } else if (click.equals(ClickType.RIGHT)) {
                if (isOwned) {
                    target.performCommand("magicscan scan delete");

                    this.refreshInventory(event.getWhoClicked());
                } else {
                    target.performCommand("magicscan scan delete " + scan.getSender().getName());

                    this.refreshInventory(event.getWhoClicked());
                }
            }
        });

        return button;
    }

    @Override
    public void populate(Describable describable) {
        if (!(describable instanceof Scan)) {
            PluginLogger.atSevere()
                    .with("Cannot populate scan list GUI with incorrect type!")
                    .print();

            return;
        }

        Scan scan = (Scan) describable;
        GUIButton button = this.createButton(scan);

        this.addButton(button);
    }

    public void update(HumanEntity human) {
        this.refreshScans();
        this.refreshToolbar();
        this.resetPage();
        this.refreshEntries();
        this.refreshInventory(human);
    }

    public void update(InventoryClickEvent event) {
        this.update(event.getWhoClicked());
    }

    public void refreshToolbar() {
        ConfigFile config = getController().getConfig();

        if (getTarget().hasPermission(MagicScanCommand.PERMISSION_SCAN_CLEAR)) {
            GUIButton clear = new GUIButton(
                    ItemStackBuilder
                            .of(config.getIcon("empty", Material.BARRIER))
                            .name("&cClear all scans")
                            .lore("&8> &7Click to clear all scans")
            );

            clear.setListener(event -> {
                event.setCancelled(true);

                HumanEntity human = event.getWhoClicked();

                human.closeInventory();

                if (human instanceof Player) {
                    Player player = (Player) human;

                    if (player.hasPermission(MagicScanCommand.PERMISSION_SCAN_CLEAR)) {
                        new ClearScansConfirmPrompt(getController(), this).activate(player);
                    }
                }
            });

            this.setToolbarItem(0, clear);
        }

        GUIButton create = getController().getScanController().hasScan(getTarget())
                ? createButton(getController().getScanController().getQueuedScan(getTarget()))
                : new GUIButton(
                ItemStackBuilder
                        .start(Material.EMERALD)
                        .name("&aCreate new scan")
                        .lore("&8> &7Click to create a new scan")
        );

        if (!getController().getScanController().hasScan(getTarget())) {
            create.setListener(event -> {
                event.setCancelled(true);

                HumanEntity human = event.getWhoClicked();

                if (human instanceof Player) {
                    Player player = (Player) human;

                    player.performCommand("magicscan scan create");
                }

                this.update(event);
            });
        }

        this.setToolbarItem(8, create);
    }

    public void refreshEntries() {
        this.getScans().values().forEach(this::populate);
    }

    @Override
    public void initialize() {
        this.refreshScans();
        this.refreshToolbar();
        this.refreshEntries();

        if (getInventory().getItem(0) == null) {
            ItemStackBuilder item = ItemStackBuilder.start(Material.BARRIER);

            if (getController().getScanController().hasScan(getTarget())) {
                item.name("&cNo other scans found");
            } else {
                item.name("&cNo scans found");
            }

            this.addButton(new AutoGUIButton(item));
        }
    }
}
