package io.github.grisstyl.magicscan.rule;

import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.magic.MagicSpell;

/**
 * Represents a spell rule.
 */
public abstract class SpellRule extends Rule<MagicSpell> {

    public SpellRule(MagicScanController controller) {
        super(controller);
    }

    @Override
    public RuleType getRuleType() {
        return RuleType.SPELL;
    }
}
