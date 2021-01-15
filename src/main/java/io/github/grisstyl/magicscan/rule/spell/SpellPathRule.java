package io.github.grisstyl.magicscan.rule.spell;

import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.magic.MagicSpell;
import io.github.grisstyl.magicscan.rule.SpellRule;
import io.github.grisstyl.magicscan.scan.Violation;
import io.github.grisstyl.magicscan.scan.Violations;

import java.util.Collection;

/**
 * A rule that verifies that the spell is contained in a wand upgrade path as a regular, required, or extra spell.
 */
public class SpellPathRule extends SpellRule {

    public SpellPathRule(MagicScanController controller) {
        super(controller);
    }

    @Override
    public String getKey() {
        return "path";
    }

    @Override
    public Collection<Violation> validate(MagicSpell spell) {
        if (spell.isLeveledVariant() || spell.hasTag("default") || spell.getContainingPath().isPresent()) {
            return Violations.none();
        } else {
            return Violations.forRule(this);
        }
    }
}
