package io.github.potterplus.magicscan.gui.describable.list;

import io.github.potterplus.api.gui.button.GUIButton;
import io.github.potterplus.api.item.ItemStackBuilder;
import io.github.potterplus.api.misc.PluginLogger;
import io.github.potterplus.api.misc.StringUtilities;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.gui.describable.ListDescribablesGUI;
import io.github.potterplus.magicscan.magic.MagicMob;
import io.github.potterplus.magicscan.misc.Describable;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A simple paginated GUI listing available Magic mobs.
 */
public class ListMobsGUI extends ListDescribablesGUI {

    public ListMobsGUI(HumanEntity target, MagicScanController controller) {
        super("Mobs", target, controller);
    }

    void refreshToolbar() {
        MagicScanController controller = this.getController();
        GUIButton clearAll = new GUIButton(
                ItemStackBuilder
                        .of(controller.getConfig().getIcon("empty", Material.BARRIER))
                        .name(controller.getMessage("gui.list_mobs.clear_all.name"))
                        .lore(controller.getLore("gui.list_mobs.clear_all.lore"))
                        .build()
        );

        clearAll.setListener(event -> {
            event.setCancelled(true);

            if (event.getWhoClicked() instanceof Player) {
                ((Player) event.getWhoClicked()).performCommand("mmob clear");
            }
        });

        this.setToolbarItem(0, clearAll);
    }

    void refreshEntries() {
        MagicScanController controller = getController();
        List<MagicMob> mobs = new ArrayList<>(controller.getFilteredMobs());

        Collections.sort(mobs);

        mobs.forEach(this::populate);

        boolean empty = getInventory().getItem(0) == null;
        Map<String, String> countReplaceMap = StringUtilities.replaceMap("$count", empty ? "&cNONE" : String.valueOf(getItems().size()));

        this.setTitle(controller.getMessage("gui.list_mobs.title", countReplaceMap));
    }

    @Override
    public void populate(Describable describable) {
        if (!(describable instanceof MagicMob)) {
            PluginLogger.atSevere()
                    .with("Cannot populate mob list GUI with incorrect describable type!")
                    .print();

            return;
        }

        MagicMob mob = (MagicMob) describable;
        GUIButton button = new GUIButton(describable.describeAsItem(this.getTarget()));

        button.setListener(event -> {
            event.setCancelled(true);

            if (getTarget() instanceof Player) {
                Player target = (Player) getTarget();

                switch (event.getClick()) {
                    case LEFT:
                        target.performCommand("mmob spawn " + mob.getKey());
                        break;
                    case RIGHT:
                        target.performCommand("mmob clear " + mob.getKey());
                        break;
                }
            }
        });

        this.addButton(button);
    }

    @Override
    public void initialize() {
        this.refreshToolbar();
        this.refreshEntries();
    }
}
