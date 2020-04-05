package com.github.pseudoresonance.resonantbot.management;

import java.util.HashSet;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LanguageCommand extends Command {

	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
		if (userPermissions.contains(PermissionGroup.BOT_OWNER)) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("reset")) {
					if (args.length == 1) {
						if (LanguageManager.resetLanguage())
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.languageFilesResetAll")).queue();
						else
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("main.errorOccurred")).queue();
						return;
					} else if (args.length >= 2) {
						if (args[1].length() > 0) {
							if (LanguageManager.resetLanguage(args[1]))
								e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.languageFilesReset", LanguageManager.getValidLanguageName(args[1]))).queue();
							else
								e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validLanguage", "http://www.lingoes.net/en/translator/langcode.htm")).queue();
							return;
						} else {
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validLanguage", "http://www.lingoes.net/en/translator/langcode.htm")).queue();
							return;
						}
					}
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.languageFilesReset", Config.getLang())).queue();
					return;
				} else if (args[0].equalsIgnoreCase("update")) {
					if (args.length == 1) {
						if (LanguageManager.reloadLanguage())
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.languageFilesUpdatedAll")).queue();
						else
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("main.errorOccurred")).queue();
						return;
					} else if (args.length >= 2) {
						if (LanguageManager.reloadLanguage(args[1]))
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.languageFilesUpdated", LanguageManager.getValidLanguageName(args[1]))).queue();
						else
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validLanguage", "http://www.lingoes.net/en/translator/langcode.htm")).queue();
						return;
					}
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validLanguage", "http://www.lingoes.net/en/translator/langcode.htm")).queue();
					return;
				}
				String lang = LanguageManager.getValidLanguageName(args[0]);
				if (lang != null) {
					Config.setLang(lang);
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.globalLanguageSet", Config.getLang())).queue();
				} else
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validLanguage", "http://www.lingoes.net/en/translator/langcode.htm")).queue();
				return;
			}
		}
		e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.globalLanguage", Config.getLang())).queue();
	}

}
