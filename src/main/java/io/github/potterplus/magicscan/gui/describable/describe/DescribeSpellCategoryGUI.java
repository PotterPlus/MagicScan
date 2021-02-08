package io.github.potterplus.magicscan.gui.describable.describe;

import io.github.potterplus.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.potterplus.magicscan.magic.spell.SpellCategory;
import org.bukkit.entity.HumanEntity;

import static io.github.potterplus.api.string.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic spell category.
 */
public class DescribeSpellCategoryGUI extends DescribeDescribableGUI {

    public DescribeSpellCategoryGUI(SpellCategory spellCategory, HumanEntity human) {
        super(spellCategory.getController().getMessage("gui.describe_spell_category.title", replaceMap("$key", spellCategory.getKey())), spellCategory, human);
    }
}
