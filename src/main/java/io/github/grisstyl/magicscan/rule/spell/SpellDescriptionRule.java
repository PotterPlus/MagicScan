package io.github.grisstyl.magicscan.rule.spell;

import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.magic.MagicSpell;
import io.github.grisstyl.magicscan.rule.SpellRule;
import io.github.grisstyl.magicscan.scan.Violation;
import io.github.grisstyl.magicscan.scan.Violations;

import java.util.Collection;

/**
 * A rule to validate that a spell has a description.
 */
public class SpellDescriptionRule extends SpellRule {

    public SpellDescriptionRule(MagicScanController controller) {
        super(controller);
    }

    @Override
    public String getKey() {
        return "description";
    }

    @Override
    public Collection<Violation> validate(MagicSpell spell) {
        return spell.getDescription().isPresent() ? Violations.none() : Violations.forRule(this);
    }
}
