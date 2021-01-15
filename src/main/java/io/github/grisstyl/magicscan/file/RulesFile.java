package io.github.grisstyl.magicscan.file;

import io.github.grisstyl.api.file.PluginYamlFile;
import io.github.grisstyl.magicscan.MagicScanController;
import io.github.grisstyl.magicscan.MagicScanPlugin;

/**
 * A handler for the rules.yml file.
 */
public class RulesFile extends PluginYamlFile<MagicScanPlugin> {

    private MagicScanController controller;

    public RulesFile(MagicScanController controller) {
        super(controller.getPlugin(), "rules.yml");

        this.controller = controller;
    }
}
