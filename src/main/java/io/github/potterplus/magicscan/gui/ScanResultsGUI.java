package io.github.potterplus.magicscan.gui;

import io.github.potterplus.api.gui.PaginatedGUI;
import io.github.potterplus.api.gui.button.AutoGUIButton;
import io.github.potterplus.api.gui.button.GUIButton;
import io.github.potterplus.api.item.ItemStackBuilder;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.file.ConfigFile;
import io.github.potterplus.magicscan.magic.MagicSpell;
import io.github.potterplus.magicscan.scan.Scan;
import io.github.potterplus.magicscan.scan.Violation;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.ToIntFunction;

import static io.github.potterplus.api.misc.StringUtilities.replaceMap;

/**
 * TODO Write docs
 */
public class ScanResultsGUI extends PaginatedGUI {

    public enum SortBy {
        ALPHABETICAL,
        HIGHEST_TO_LOWEST;

        private static final SortBy[] vals = values();

        public SortBy next() {
            return vals[(this.ordinal()+1) % vals.length];
        }

        public String getName() {
            return StringUtils.capitalize(this.toString().toLowerCase().replace("_", " "));
        }
    }

    @NonNull
    private final MagicScanController controller;
    private final Scan scan;
    private SortBy sortBy;

    public Scan.Results getResults() {
        return scan.getResults();
    }

    public ScanResultsGUI(MagicScanController controller, Scan scan) {
        super(controller.getMessage("gui.scan_results.title"));

        this.controller = controller;
        this.scan = scan;
        this.sortBy = SortBy.ALPHABETICAL;

        this.refreshToolbar();
        this.refreshButtons();
    }

    public void update(HumanEntity human) {
        this.refreshButtons();
        this.refreshToolbar();
        this.refreshInventory(human);
    }

    public void update(InventoryClickEvent event) {
        this.update(event.getWhoClicked());
    }

    public void refreshToolbar() {
        ConfigFile config = controller.getConfig();
        Map<String, String> replace = replaceMap(
                "$sortBy", sortBy.getName(),
                "$elapsedTime", getResults().getElapsedTime() + "s",
                "$violationsCount", String.valueOf(getResults().getViolationsCount()),
                "$invalidSpellCount", String.valueOf(getResults().getInvalidSpellCount()),
                "$validSpellCount", String.valueOf(getResults().getValidSpellCount())
        );

        GUIButton sortBy = new GUIButton(
                ItemStackBuilder
                .of(config.getIcon("cycle", Material.MAP))
                .name(controller.getMessage("gui.scan_results.sort_by.name", replace))
                .lore(controller.getLore("gui.scan_results.sort_by.lore", replace))
        );

        sortBy.setListener(event -> {
            event.setCancelled(true);

            this.sortBy = this.sortBy.next();

            this.update(event);
        });

        GUIButton time = new AutoGUIButton(
                ItemStackBuilder
                        .of(config.getIcon("time", Material.CLOCK))
                        .name(controller.getMessage("gui.scan_results.time.name", replace))
        );

        GUIButton totals = new AutoGUIButton(
                ItemStackBuilder
                        .of(config.getIcon("attribute", Material.PAPER))
                        .name(controller.getMessage("gui.scan_results.totals.name", replace))
                        .lore(controller.getLore("gui.scan_results.totals.lore", replace))
        );

        if (config.isUsingPastebinIntegration() && getResults().getPastebinURL() != null) {
            GUIButton pastebinLink = new GUIButton(
                    ItemStackBuilder
                            .of(config.getIcon("attribute", Material.PAPER))
                            .name(controller.getMessage("gui.scan_results.pastebin_url.name"))
                            .lore(controller.getMessage("gui.scan_results.pastebin_url.lore"))
            );

            pastebinLink.setListener(event -> controller.sendMessage(event.getWhoClicked(), "pastebin_posted", replaceMap("$url", getResults().getPastebinURL())));

            this.setToolbarItem(6, pastebinLink);
        }

        this.setToolbarItem(0, sortBy);
        this.setToolbarItem(7, time);
        this.setToolbarItem(8, totals);
    }

    public void refreshButtons() {
        this.clearButtons();

        List<Map.Entry<MagicSpell, List<Violation>>> list = new ArrayList<>(getResults().getSpellViolations().entrySet());

        if (this.sortBy.equals(SortBy.ALPHABETICAL)) {
            list.sort(Map.Entry.comparingByKey());
        } else if (this.sortBy.equals(SortBy.HIGHEST_TO_LOWEST)) {
            list.sort(Comparator.comparingInt((ToIntFunction<Map.Entry<MagicSpell, List<Violation>>>) value -> value.getValue().size()).reversed());
        }

        for (Map.Entry<MagicSpell, List<Violation>> entry : list) {
            MagicSpell spell = entry.getKey();
            List<Violation> violations = entry.getValue();
            Optional<ItemStack> icon = spell.getIcon();

            if (icon.isPresent()) {
                ItemStackBuilder item = ItemStackBuilder
                        .of(icon.get())
                        .lore(controller.getLore("gui.scan_results.violation.lore", replaceMap("$violationsCount", String.valueOf(violations.size()))));

                for (Violation violation : violations) {
                    item.addLore(violation.toString());
                }

                GUIButton button = new AutoGUIButton(item);

                this.addButton(button);
            }
        }
    }
}
