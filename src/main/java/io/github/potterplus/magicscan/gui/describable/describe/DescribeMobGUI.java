package io.github.potterplus.magicscan.gui.describable.describe;

import io.github.potterplus.api.misc.StringUtilities;
import io.github.potterplus.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.potterplus.magicscan.magic.MagicMob;
import org.bukkit.entity.HumanEntity;

import static io.github.potterplus.api.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic mob.
 */
public class DescribeMobGUI extends DescribeDescribableGUI {

    public DescribeMobGUI(MagicMob mob, HumanEntity human) {
        super(mob.getController().getMessage("gui.describe_mob.title", StringUtilities.replaceMap("$key", mob.getKey())), mob, human);
    }
}
