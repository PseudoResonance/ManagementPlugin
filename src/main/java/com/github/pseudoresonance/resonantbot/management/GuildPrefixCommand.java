package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.data.Data;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GuildPrefixCommand implements Command {

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
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
			if (e.getAuthor().getIdLong() == Config.getOwner()) {
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
			} else {
				if (e.getChannelType() == ChannelType.PRIVATE) {
					e.getChannel().sendMessage(LanguageManager.getLanguage(e.getPrivateChannel().getIdLong()).getMessage("main.privatePrefix", Data.getGuildPrefix(e.getPrivateChannel().getIdLong()))).queue();
				} else {
					e.getChannel().sendMessage(LanguageManager.getLanguage(e.getGuild().getIdLong()).getMessage("main.prefix", e.getGuild().getName(), Data.getGuildPrefix(e.getGuild().getIdLong()))).queue();
				}
			}
		}
	}

	public String getDesc(long id) {
		return LanguageManager.getLanguage(id).getMessage("management.guildPrefixCommandDescription");
	}

	public boolean isHidden() {
		return true;
	}

}
