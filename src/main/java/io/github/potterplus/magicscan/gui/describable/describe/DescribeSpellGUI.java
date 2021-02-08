package io.github.potterplus.magicscan.gui.describable.describe;

import io.github.potterplus.api.string.StringUtilities;
import io.github.potterplus.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.potterplus.magicscan.magic.MagicSpell;
import org.bukkit.entity.HumanEntity;

import static io.github.potterplus.api.string.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic spell.
 */
public class DescribeSpellGUI extends DescribeDescribableGUI {

    public DescribeSpellGUI(MagicSpell spell, HumanEntity human) {
        super(spell.getController().getMessage("gui.describe_spell.title", StringUtilities.replaceMap("$key", spell.getKey())), spell, human);
    }
}
