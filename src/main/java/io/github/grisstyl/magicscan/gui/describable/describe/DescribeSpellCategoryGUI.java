package io.github.grisstyl.magicscan.gui.describable.describe;

import io.github.grisstyl.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.grisstyl.magicscan.magic.spell.SpellCategory;
import org.bukkit.entity.HumanEntity;

import static io.github.grisstyl.ppapi.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic spell category.
 */
public class DescribeSpellCategoryGUI extends DescribeDescribableGUI {

    public DescribeSpellCategoryGUI(SpellCategory spellCategory, HumanEntity human) {
        super(spellCategory.getController().getMessage("gui.describe_spell_category.title", replaceMap("$key", spellCategory.getKey())), spellCategory, human);
    }
}
