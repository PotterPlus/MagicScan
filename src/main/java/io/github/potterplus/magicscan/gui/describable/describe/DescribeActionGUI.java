package io.github.potterplus.magicscan.gui.describable.describe;

import io.github.potterplus.api.string.StringUtilities;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.potterplus.magicscan.magic.spell.SpellAction;
import org.bukkit.entity.HumanEntity;

import static io.github.potterplus.api.string.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic action.
 */
public class DescribeActionGUI extends DescribeDescribableGUI {

    public DescribeActionGUI(MagicScanController controller, SpellAction action, HumanEntity human) {
        super(controller.getMessage("gui.describe_action.title", StringUtilities.replaceMap("$name", action.getName())), action, human);
    }
}
