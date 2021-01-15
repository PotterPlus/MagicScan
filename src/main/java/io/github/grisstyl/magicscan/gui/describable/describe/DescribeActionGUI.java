package io.github.grisstyl.magicscan.gui.describable.describe;

import io.github.grisstyl.ppapi.misc.StringUtilities;
import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.grisstyl.magicscan.magic.spell.SpellAction;
import org.bukkit.entity.HumanEntity;

import static io.github.grisstyl.ppapi.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic action.
 */
public class DescribeActionGUI extends DescribeDescribableGUI {

    public DescribeActionGUI(MagicScanController controller, SpellAction action, HumanEntity human) {
        super(controller.getMessage("gui.describe_action.title", StringUtilities.replaceMap("$name", action.getName())), action, human);
    }
}
