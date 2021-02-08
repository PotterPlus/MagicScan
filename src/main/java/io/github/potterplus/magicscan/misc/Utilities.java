package io.github.potterplus.magicscan.misc;

import io.github.potterplus.api.command.CommandContext;
import io.github.potterplus.api.string.StringUtilities;
import io.github.potterplus.magicscan.magic.MagicSpell;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Various utility methods.
 */
public class Utilities {

    public static CommandSender resolveCommandSenderByName(String name) {
        if (name.equalsIgnoreCase("console")) {
            return Bukkit.getConsoleSender();
        } else {
            return Bukkit.getPlayer(name);
        }
    }

    public static void sendMessages(CommandSender to, List<String> messages) {
        if (isMessageable(to)) {
            messages.stream().map(StringUtilities::color).forEach(to::sendMessage);
        }
    }

    public static void sendMessages(CommandContext context, List<String> messages) {
        sendMessages(context.getSender(), messages);
    }

    public static void describe(CommandSender sender, Describable describable) {
        sendMessages(sender, describable.describe(sender));
    }

    public static void describe(CommandContext context, Describable describable) {
        sendMessages(context, describable.describe(context.getSender()));
    }

    public static BaseComponent[] createCompactTextView(Player player, Describable describable) {
        List<String> description = describable.describe(player);
        String key = StringUtilities.color(description.get(0));

        description = StringUtilities.color(description.subList(1, description.size()));

        Content[] contents = new Content[description.size()];

        for (int i = 0; i < description.size(); i++) {
            contents[i] = new Text(TextComponent.fromLegacyText(description.get(i) + "\n"));
        }

        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, contents);
        return new ComponentBuilder(new TextComponent(TextComponent.fromLegacyText(key)))
                .event(event)
                .create();
    }

    public static void sendCompact(Player player, Describable describable) {
        player.spigot().sendMessage(createCompactTextView(player, describable));
    }

    public static String spellList(List<MagicSpell> spells) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < spells.size(); i++) {
            builder.append(spells.get(i).getKey());

            if (i != spells.size()) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    public static List<String> removeEntries(List<String> list, String... entries) {
        List<String> list1 = new ArrayList<>(list);
        List<String> list2  = new ArrayList<>(list);

        for (int i = 0; i < list1.size(); i++) {
            list1.set(i, list1.get(i).replace(" ", "")); // TODO Maybe use String#trim here?
        }

        for (int i = 0; i < list2.size(); i++) {
            for (String entry : entries) {
                if (list2.get(i).contains(entry)) {
                    list2.remove(i);
                }
            }
        }

        return list2;
    }

    public static boolean isMessageable(CommandSender sender) {
        return sender instanceof ConsoleCommandSender || (sender instanceof Player && ((Player) sender).isOnline());
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());

        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();

        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static List<String> formatMultiLineStrings(Collection<String> spells) {
        StringBuilder builder = new StringBuilder();

        int count = 0;

        for (String spell : spells) {
            if (count == 4) {
                count = 0;
                builder.append("\n");
            }

            builder.append(ChatColor.GRAY).append(spell)
                    .append(ChatColor.DARK_GRAY).append(", ");

            count++;
        }

        String str = builder.toString();

        str = str.substring(0, str.length() - 1);

        String[] split = str.split("\n");
        List<String> list = new ArrayList<>();

        for (String s : split) {
            list.add("    " + s);
        }

        return list;
    }
}
