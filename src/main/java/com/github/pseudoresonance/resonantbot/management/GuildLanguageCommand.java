package com.github.pseudoresonance.resonantbot.management;

import java.util.regex.Pattern;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.Language;
import com.github.pseudoresonance.resonantbot.api.Command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

public class GuildLanguageCommand implements Command {
	
	private static final Pattern pattern = Pattern.compile("[^A-Za-z0-9-]");

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getChannelType() == ChannelType.PRIVATE || PermissionUtil.checkPermission(e.getTextChannel(), e.getMember(), Permission.ADMINISTRATOR) || e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("reset")) {
					if (e.getChannelType() == ChannelType.PRIVATE) {
						Language.unsetGuildLang(e.getPrivateChannel().getIdLong());
						e.getChannel().sendMessage(Language.getMessage(e, "management.privateLanguageReset", Language.getGuildLang(e.getPrivateChannel().getIdLong()))).queue();
					} else {
						Language.unsetGuildLang(e.getGuild().getIdLong());
						e.getChannel().sendMessage(Language.getMessage(e, "management.languageReset", Language.getGuildLang(e.getGuild().getIdLong()))).queue();
					}
					return;
				}
				String replaced = pattern.matcher(args[0]).replaceAll("");
				if (replaced.length() >= 5)
					replaced = replaced.substring(0, 5);
				if (replaced.length() > 0) {
					if (e.getChannelType() == ChannelType.PRIVATE) {
						Language.setGuildLang(e.getPrivateChannel().getIdLong(), replaced);
						e.getChannel().sendMessage(Language.getMessage(e, "management.privateLanguageSet", Language.getGuildLang(e.getPrivateChannel().getIdLong()))).queue();
					} else {
						Language.setGuildLang(e.getGuild().getIdLong(), replaced);
						e.getChannel().sendMessage(Language.getMessage(e, "management.languageSet", Language.getGuildLang(e.getGuild().getIdLong()))).queue();
					}
					return;
				} else {
					e.getChannel().sendMessage(Language.getMessage(e, "management.validLanguage", "http://www.lingoes.net/en/translator/langcode.htm")).queue();
					return;
				}
			}
		}
		if (e.getChannelType() == ChannelType.PRIVATE) {
			e.getChannel().sendMessage(Language.getMessage(e, "management.privateLanguage", Language.getGuildLang(e.getPrivateChannel().getIdLong()))).queue();
		} else {
			e.getChannel().sendMessage(Language.getMessage(e, "management.language", Language.getGuildLang(e.getGuild().getIdLong()))).queue();
		}
	}

	public String getDesc(long id) {
		return Language.getMessage(id, "management.guildLanguageCommandDescription");
	}

	public boolean isHidden() {
		return false;
	}

}
