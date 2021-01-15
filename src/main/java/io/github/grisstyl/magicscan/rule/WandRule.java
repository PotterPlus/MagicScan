package io.github.grisstyl.magicscan.rule;

import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.magic.MagicWand;

/**
 * Represents a wand rule.
 */
public abstract class WandRule extends Rule<MagicWand> {

    public WandRule(MagicScanController controller) {
        super(controller);
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.WAND;
    }
}
