package io.github.potterplus.magicscan.file;

import io.github.potterplus.api.misc.StringUtilities;
import io.github.potterplus.api.storage.flatfile.PluginYamlFile;
import io.github.potterplus.magicscan.MagicScanController;
import io.github.potterplus.magicscan.MagicScanPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * A simple handler for a messages.yml file.
 */
public class MessagesFile extends PluginYamlFile<MagicScanPlugin> {

    private final MagicScanController controller;

    public MessagesFile(MagicScanController controller) {
        super(controller.getPlugin(),"messages.yml");

        this.controller = controller;
    }

    public String getRawMessage(String key) {
        String str = getFileConfiguration().getString(key);

        if (str == null) {
            InputStream def = controller.getPlugin().getResource("messages.yml");

            if (def != null) {
                Reader reader = new InputStreamReader(def);
                YamlConfiguration yaml = YamlConfiguration.loadConfiguration(reader);

                str = yaml.getString(key);

                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (str == null) {
                throw new IllegalArgumentException(String.format("Could not resolve message from key '%s'", key));
            }
        }

        return str;
    }

    public String getMessage(String key) {
        return StringUtilities.color(getRawMessage(key));
    }

    public String getStrippedMessage(String key) {
        return ChatColor.stripColor(getMessage(key));
    }
}
