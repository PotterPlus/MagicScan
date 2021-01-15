package io.github.grisstyl.magicscan.gui.describable.describe;

import io.github.grisstyl.ppapi.gui.GUI;
import io.github.grisstyl.ppapi.gui.button.AutoGUIButton;
import io.github.grisstyl.ppapi.gui.button.GUIButton;
import io.github.grisstyl.ppapi.misc.ItemStackBuilder;
import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.magic.MagicSpell;
import io.github.grisstyl.magicscan.magic.spell.SpellProgression;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;

import java.util.List;

import static io.github.grisstyl.ppapi.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic spell's progression.
 */
public class DescribeSpellProgressionGUI extends GUI {

    private HumanEntity target;

    public DescribeSpellProgressionGUI(MagicScanController controller, SpellProgression progression, HumanEntity target) {
        super(controller.getMessage("gui.describe_spell_progression.title", replaceMap("$key", progression.getOriginSpell().getKey())), 27);

        this.target = target;

        final GUIButton arrow = new AutoGUIButton(
                ItemStackBuilder
                .of(controller.getConfig().getIcon("right", Material.ARROW))
                .name(controller.getMessage("gui.describe_spell_progression.arrow.name"))
        );

        List<MagicSpell> progress = progression.getProgression();

        for (int i = 0; i < progress.size(); i++) {
            MagicSpell spell = progress.get(i);
            GUIButton button = new AutoGUIButton(spell.describeAsItem(target));

            this.addButton(button);

            if (i < progress.size()) {
                this.addButton(arrow);
            }
        }

        for (MagicSpell spell : progress) {
            GUIButton button = new AutoGUIButton(spell.describeAsItem(target));

            this.addButton(button);

            if (progress.indexOf(spell) != progress.size()) {
                this.addButton(arrow);
            }
        }
    }

    public void activate() {
        this.activate(target);
    }
}
