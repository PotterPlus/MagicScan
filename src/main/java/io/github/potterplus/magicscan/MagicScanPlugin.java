package io.github.potterplus.magicscan;

import io.github.potterplus.api.gui.GUI;
import io.github.potterplus.magicscan.command.MagicScanCommand;
import io.github.potterplus.magicscan.listener.QuitListener;
import io.github.potterplus.magicscan.scan.Scan;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.permission.ChildPermission;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(
        name = "MagicScan",
        version = "1.0.0"
)
@Description("Scans Magic configurations for potential issues.")
@Author("T0xicTyler")
@Website("https://github.com/PotterPlus/MagicScan")
@Dependency("Magic")
@ApiVersion(ApiVersion.Target.v1_13)
@Commands({
        @Command(
                name = "magicscan",
                desc = "Initiates a scan of Magic configurations.",
                usage = "/magicscan <sub>",
                aliases = {"ms", "mss"}
        )
})
@Permissions({
        @Permission(name = "magicscan.*", desc = "Wildcard permission for /magicscan", children = {
                @ChildPermission(name = "magicscan"),
                @ChildPermission(name = "magicscan.reload"),
                @ChildPermission(name = "magicscan.scan"),
                @ChildPermission(name = "magicscan.scan.clear")
        }),
        @Permission(name = "magicscan", desc = "Permits to use /magicscan"),
        @Permission(name = "magicscan.reload", desc = "Permits to use /magicscan reload"),
        @Permission(name = "magicscan.scan", desc = "Permits to use /magicscan scan"),
        @Permission(name = "magicscan.scan.clear", desc = "Permits to use /magicscan scan clear"),
        @Permission(name = "magicscan.scan.delete", desc = "Permits to use /magicscan scan delete on others")
})
public class MagicScanPlugin extends JavaPlugin {

    @Getter @NonNull
    private MagicScanPlugin plugin;

    @Getter @NonNull
    private MagicScanController controller;

    @Getter@NonNull
    private MagicScanCommand command;

    @Override
    public void onEnable() {
        plugin = this;
        controller = new MagicScanController(this);

        ConfigurationSerialization.registerClass(Scan.class, "Scan");

        Bukkit.getPluginManager().registerEvents(new QuitListener(controller), this);

        GUI.prepare(this);

        this.command = new MagicScanCommand(this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
