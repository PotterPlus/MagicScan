package io.github.potterplus.magicscan.gui.describable.describe;

import io.github.potterplus.api.misc.StringUtilities;
import io.github.potterplus.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.potterplus.magicscan.magic.MagicWand;
import org.bukkit.entity.HumanEntity;

import static io.github.potterplus.api.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic wand.
 */
public class DescribeWandGUI extends DescribeDescribableGUI {

    public DescribeWandGUI(MagicWand wand, HumanEntity human) {
        super(wand.getController().getMessage("gui.describe_wand.title", StringUtilities.replaceMap("$key", wand.getKey())), wand, human);
    }
}
