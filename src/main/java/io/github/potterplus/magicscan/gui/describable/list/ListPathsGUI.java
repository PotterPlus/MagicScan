package io.github.potterplus.magicscan.gui.describable.list;

import io.github.potterplus.api.misc.StringUtilities;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.gui.describable.ListDescribablesGUI;
import io.github.potterplus.magicscan.magic.MagicPath;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.potterplus.api.misc.StringUtilities.replaceMap;

/**
 * A simple paginated GUI listing available Magic paths.
 */
public class ListPathsGUI extends ListDescribablesGUI {

    public ListPathsGUI(HumanEntity target, MagicScanController controller) {
        super("Paths", target, controller);
    }

    void refreshToolbar() {

    }

    void refreshEntries() {
        MagicScanController controller = this.getController();
        List<MagicPath> paths = new ArrayList<>(controller.getFilteredPaths());

        paths.forEach(this::populate);

        boolean empty = getInventory().getItem(0) == null;
        Map<String, String> countReplaceMap = StringUtilities.replaceMap("$count", empty ? "&cNONE" : String.valueOf(getItems().size()));

        this.setTitle(controller.getMessage("gui.list_paths.title", countReplaceMap));
    }

    @Override
    public void initialize() {
        this.refreshToolbar();
        this.refreshEntries();
    }
}
