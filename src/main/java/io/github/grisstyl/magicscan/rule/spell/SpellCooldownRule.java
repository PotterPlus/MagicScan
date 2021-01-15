package io.github.grisstyl.magicscan.rule.spell;

import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.magic.MagicSpell;
import io.github.grisstyl.magicscan.rule.SpellRule;
import io.github.grisstyl.magicscan.scan.Violation;
import io.github.grisstyl.magicscan.scan.Violations;

import java.util.Collection;

/**
 * A simple rule which verifies that the spell has a cooldown.
 */
public class SpellCooldownRule extends SpellRule {

    public SpellCooldownRule(MagicScanController controller) {
        super(controller);
    }

    @Override
    public String getKey() {
        return "cooldown";
    }

    @Override
    public Collection<Violation> validate(MagicSpell spell) {
        return spell.getCooldown().isPresent()
                ? Violations.none()
                : Violations.forRule(this);
    }
}
