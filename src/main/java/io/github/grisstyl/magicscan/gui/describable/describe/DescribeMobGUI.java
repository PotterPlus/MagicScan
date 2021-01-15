package io.github.grisstyl.magicscan.gui.describable.describe;

import io.github.grisstyl.ppapi.misc.StringUtilities;
import io.github.grisstyl.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.grisstyl.magicscan.magic.MagicMob;
import org.bukkit.entity.HumanEntity;

import static io.github.grisstyl.ppapi.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic mob.
 */
public class DescribeMobGUI extends DescribeDescribableGUI {

    public DescribeMobGUI(MagicMob mob, HumanEntity human) {
        super(mob.getController().getMessage("gui.describe_mob.title", StringUtilities.replaceMap("$key", mob.getKey())), mob, human);
    }
}
