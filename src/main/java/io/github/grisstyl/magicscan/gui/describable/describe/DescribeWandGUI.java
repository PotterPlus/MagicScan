package io.github.grisstyl.magicscan.gui.describable.describe;

import io.github.grisstyl.api.misc.StringUtilities;
import io.github.grisstyl.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.grisstyl.magicscan.magic.MagicWand;
import org.bukkit.entity.HumanEntity;

import static io.github.grisstyl.api.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic wand.
 */
public class DescribeWandGUI extends DescribeDescribableGUI {

    public DescribeWandGUI(MagicWand wand, HumanEntity human) {
        super(wand.getController().getMessage("gui.describe_wand.title", StringUtilities.replaceMap("$key", wand.getKey())), wand, human);
    }
}
