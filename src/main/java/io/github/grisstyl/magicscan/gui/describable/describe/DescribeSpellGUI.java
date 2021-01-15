package io.github.grisstyl.magicscan.gui.describable.describe;

import io.github.grisstyl.api.misc.StringUtilities;
import io.github.grisstyl.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.grisstyl.magicscan.magic.MagicSpell;
import org.bukkit.entity.HumanEntity;

import static io.github.grisstyl.api.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic spell.
 */
public class DescribeSpellGUI extends DescribeDescribableGUI {

    public DescribeSpellGUI(MagicSpell spell, HumanEntity human) {
        super(spell.getController().getMessage("gui.describe_spell.title", StringUtilities.replaceMap("$key", spell.getKey())), spell, human);
    }
}
