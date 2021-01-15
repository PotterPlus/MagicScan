package io.github.grisstyl.magicscan.gui.describable.describe;

import io.github.grisstyl.magicscan.gui.describable.DescribeDescribableGUI;
import io.github.grisstyl.magicscan.magic.MagicPath;
import org.bukkit.entity.HumanEntity;

import static io.github.grisstyl.api.misc.StringUtilities.replaceMap;

/**
 * A GUI describing a specific Magic path.
 */
public class DescribePathGUI extends DescribeDescribableGUI {

    public DescribePathGUI(MagicPath path, HumanEntity human) {
        super(path.getController().getMessage("gui.describe_path.title", replaceMap("$key", path.getKey())), path, human);
    }
}
