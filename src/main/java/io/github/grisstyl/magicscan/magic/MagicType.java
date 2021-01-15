package io.github.grisstyl.magicscan.magic;

import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.misc.Describable;
import lombok.*;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents a template from Magic (spells, wands, paths, etc.)
 */
@RequiredArgsConstructor
public abstract class MagicType<T> implements Describable {

    @Getter @NonNull
    private MagicScanController controller;

    @Getter @NonNull
    private String key;

    @Getter @Setter(AccessLevel.PROTECTED)
    private T template;

    public FileConfiguration getSpellsDefaults() {
        return getController().getSpellsDefaults().getFileConfiguration();
    }

    public FileConfiguration getMessagesDefaults() {
        return getController().getMessagesDefaults().getFileConfiguration();
    }

    public FileConfiguration getPathsDefaults() {
        return getController().getPathsDefaults().getFileConfiguration();
    }

    public FileConfiguration getWandsDefaults() {
        return getController().getWandsDefaults().getFileConfiguration();
    }
}
