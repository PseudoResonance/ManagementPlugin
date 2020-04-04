package com.github.pseudoresonance.resonantbot.management;

import java.util.HashSet;

import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.data.Data;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GuildLanguageCommand extends Command {

	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
		if (args.length == 0) {
			if (e.getChannelType() == ChannelType.PRIVATE) {
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.privateLanguage", Data.getGuildLanguage(e.getPrivateChannel().getIdLong()))).queue();
			} else {
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.language", Data.getGuildLanguage(e.getGuild().getIdLong()))).queue();
			}
		}
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("reset")) {
				if (e.getChannelType() == ChannelType.PRIVATE) {
					Data.setGuildLanguage(e.getPrivateChannel().getIdLong(), null);
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.privateLanguageReset", Data.getGuildLanguage(e.getPrivateChannel().getIdLong()))).queue();
				} else {
					Data.setGuildLanguage(e.getGuild().getIdLong(), null);
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.languageReset", Data.getGuildLanguage(e.getGuild().getIdLong()))).queue();
				}
				return;
			}
			String lang = LanguageManager.getValidLanguageName(args[0]);
			if (lang != null) {
				if (e.getChannelType() == ChannelType.PRIVATE) {
					Data.setGuildLanguage(e.getPrivateChannel().getIdLong(), lang);
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.privateLanguageSet", Data.getGuildLanguage(e.getPrivateChannel().getIdLong()))).queue();
				} else {
					Data.setGuildLanguage(e.getGuild().getIdLong(), lang);
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.languageSet", Data.getGuildLanguage(e.getGuild().getIdLong()))).queue();
				}
			} else
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validLanguage", "http://www.lingoes.net/en/translator/langcode.htm")).queue();
			return;
		}
	}

}
