package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.data.Data;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

public class GuildLanguageCommand implements Command {

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getChannelType() == ChannelType.PRIVATE || PermissionUtil.checkPermission(e.getTextChannel(), e.getMember(), Permission.ADMINISTRATOR) || e.getAuthor().getIdLong() == Config.getOwner()) {
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
		if (e.getChannelType() == ChannelType.PRIVATE) {
			e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.privateLanguage", Data.getGuildLanguage(e.getPrivateChannel().getIdLong()))).queue();
		} else {
			e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.language", Data.getGuildLanguage(e.getGuild().getIdLong()))).queue();
		}
	}

	public String getDesc(long id) {
		return LanguageManager.getLanguage(id).getMessage("management.guildLanguageCommandDescription");
	}

	public boolean isHidden() {
		return false;
	}

}
