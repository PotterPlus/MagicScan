package io.github.potterplus.magicscan.gui.spell;

import io.github.potterplus.api.gui.GUI;
import io.github.potterplus.api.gui.button.AutoGUIButton;
import io.github.potterplus.api.gui.button.GUIButton;
import io.github.potterplus.api.item.Icon;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.magic.MagicSpell;
import org.bukkit.Material;

public class SpellBreakdownGUI extends GUI {

    private MagicScanController controller;
    private MagicSpell spell;

    public SpellBreakdownGUI(MagicScanController controller, MagicSpell spell) {
        super("Creating spell breakdown...", 54);

        this.controller = controller;
        this.spell = spell;

        this.refreshButtons();

        setTitle("&7Breakdown of spell &e" + spell.getKey());
    }

    public void refreshButtons() {
        this.clearButtons();

        Icon textElements = Icon
                .start(Material.PAPER)
                .name("&7Text Components")
                .lore(
                        "&7Display Name&8: &r" + spell.getNameString(),
                        "&7Description&8: &r" + spell.getDescriptionString()
                );

        setButton(0, new AutoGUIButton(textElements));

        boolean hasPrev = spell.getPreviousLevel().isPresent();
        boolean hasNext = spell.getNextLevel().isPresent();

        if (!hasPrev && !hasNext) {
            Icon icon = Icon
                    .start(Material.BARRIER)
                    .name("&cThis spell only has one level");
            setButton(53, new AutoGUIButton(icon));
        } else {
            if (hasPrev) {
                MagicSpell prev = spell.getPreviousLevel().get();
                Icon icon = Icon
                        .start(Material.ARROW)
                        .name("&d&l<&d&l&m-- &7Previous level")
                        .lore(
                                "&8> &eClick &7to break down the previous level",
                                "",
                                "  &7Current level&8: &e" + spell.getCurrentLevel()
                        );
                GUIButton button = new GUIButton(icon);

                button.setListener((event -> {
                    new SpellBreakdownGUI(controller, prev).activate(event.getWhoClicked());
                }));

                setButton(52, button);
            } else {
                Icon icon = Icon
                        .start(Material.BARRIER)
                        .name("&cNo previous level");
                setButton(52, new AutoGUIButton(icon));
            }

            if (hasNext) {
                MagicSpell next = spell.getNextLevel().get();
                Icon icon = Icon
                        .start(Material.ARROW)
                        .name("&d&l&m--&d&l> &7Next level")
                        .lore(
                                "&8> &eClick &7to break down the next level",
                                "",
                                "  &7Current level&8: &e" + spell.getCurrentLevel()
                        );
                GUIButton button = new GUIButton(icon);

                button.setListener((event -> {
                    new SpellBreakdownGUI(controller, next).activate(event.getWhoClicked());
                }));

                setButton(53, button);
            } else {
                Icon icon = Icon
                        .start(Material.BARRIER)
                        .name("&cNo next level");
                setButton(53, new AutoGUIButton(icon));
            }
        }
    }
}
