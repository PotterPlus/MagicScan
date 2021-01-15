package io.github.grisstyl.magicscan.gui.describable.list;

import io.github.grisstyl.ppapi.gui.button.GUIButton;
import io.github.grisstyl.ppapi.misc.ItemStackBuilder;
import io.github.grisstyl.ppapi.misc.StringUtilities;
import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.file.ConfigFile;
import io.github.grisstyl.magicscan.gui.describable.ListDescribablesGUI;
import io.github.grisstyl.magicscan.magic.MagicWand;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;

import static io.github.grisstyl.ppapi.misc.StringUtilities.replaceMap;

/**
 * A GUI listing Magic wands.
 */
public class ListWandsGUI extends ListDescribablesGUI {

    private boolean showHidden;

    public ListWandsGUI(HumanEntity target, MagicScanController controller) {
        super("Wands", target, controller);

        this.showHidden = false;
    }

    public void update(HumanEntity human) {
        this.refreshToolbar();
        this.resetPage();
        this.refreshEntries();
        this.refreshInventory(human);
    }

    public void update(InventoryClickEvent event) {
        this.update(event.getWhoClicked());
    }

    void refreshToolbar() {
        MagicScanController controller = getController();
        ConfigFile config = controller.getConfig();
        ItemStackBuilder enabled = ItemStackBuilder.of(config.getIcon("enabled", Material.GREEN_STAINED_GLASS));
        ItemStackBuilder disabled = ItemStackBuilder.of(config.getIcon("disabled", Material.RED_STAINED_GLASS));

        GUIButton showHidden = new GUIButton(
                this.showHidden
                        ? enabled.name(controller.getMessage("gui.list_wands.show_hidden_enabled.name")).lore(controller.getLore("gui.list_wands.show_hidden_enabled.lore"))
                        : disabled.name(controller.getMessage("gui.list_wands.show_hidden_disabled.name")).lore(controller.getLore("gui.list_wands.show_hidden_disabled.lore"))
        );

        showHidden.setListener(event -> {
            event.setCancelled(true);

            this.showHidden = !this.showHidden;
            this.update(event);
        });

        this.setToolbarItem(0, showHidden);
    }

    void refreshEntries() {
        MagicScanController controller = getController();
        List<MagicWand> wands = controller.getFilteredWands();

        wands.forEach(this::populate);

        boolean empty = getInventory().getItem(0) == null;
        Map<String, String> countReplaceMap = StringUtilities.replaceMap("$count", empty ? "&cNONE" : String.valueOf(getItems().size()));

        this.setTitle(controller.getMessage("gui.list_wands.title", countReplaceMap));
    }

    @Override
    public void initialize() {
        this.refreshToolbar();
        this.refreshEntries();
    }
}
