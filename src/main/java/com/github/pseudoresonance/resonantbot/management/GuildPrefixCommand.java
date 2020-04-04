package com.github.pseudoresonance.resonantbot.management;

import java.util.HashSet;

import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.data.Data;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GuildPrefixCommand extends Command {

	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
		if (args.length == 0) {
			if (e.getChannelType() == ChannelType.PRIVATE)
				e.getChannel().sendMessage(LanguageManager.getLanguage(e.getPrivateChannel().getIdLong()).getMessage("main.privatePrefix", Data.getGuildPrefix(e.getPrivateChannel().getIdLong()))).queue();
			else
				e.getChannel().sendMessage(LanguageManager.getLanguage(e.getGuild().getIdLong()).getMessage("main.prefix", e.getGuild().getName(), Data.getGuildPrefix(e.getGuild().getIdLong()))).queue();
		} else if (args.length == 1) {
			try {
				long id = Long.valueOf(args[0]);
				Guild guild = e.getJDA().getGuildById(id);
				if (guild == null) {
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validGuildPrefix")).queue();
				} else
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("main.prefix", guild.getName(), Data.getGuildPrefix(guild.getIdLong()))).queue();
			} catch (NumberFormatException ex) {
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validGuildPrefix")).queue();
			}
		} else if (args.length >= 2) {
			try {
				long id = Long.valueOf(args[0]);
				if (args[1].equalsIgnoreCase("reset")) {
					Guild guild = e.getJDA().getGuildById(id);
					Data.setGuildPrefix(guild.getIdLong(), null);
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.prefixReset", guild.getName(), Data.getGuildPrefix(guild.getIdLong()))).queue();
				} else if (!args[1].equals("")) {
					String prefix = "";
					for (int i = 1; i < args.length; i++) {
						prefix += args[i] + " ";
					}
					prefix = prefix.substring(0, prefix.length() - 1);
					Guild guild = e.getJDA().getGuildById(id);
					Data.setGuildPrefix(guild.getIdLong(), prefix);
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.prefixSet", guild.getName(), Data.getGuildPrefix(guild.getIdLong()))).queue();
				} else {
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addPrefix")).queue();
				}
			} catch (NumberFormatException ex) {
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validGuild")).queue();
			}
		}
	}

}
