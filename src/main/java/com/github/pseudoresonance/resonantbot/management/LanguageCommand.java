package com.github.pseudoresonance.resonantbot.management;

import java.util.regex.Pattern;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.Language;
import com.github.pseudoresonance.resonantbot.api.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LanguageCommand implements Command {

	private static final Pattern pattern = Pattern.compile("[^A-Za-z0-9-]");

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("reset")) {
					if (args.length >= 2) {
						String replaced = pattern.matcher(args[1]).replaceAll("").substring(0, 5);
						if (replaced.length() > 0) {
							Language.resetLang(replaced);
							e.getChannel().sendMessage("`" + replaced + "` language files have been reset!").queue();
							return;
						} else {
							e.getChannel().sendMessage("Please specify a valid language! http://www.lingoes.net/en/translator/langcode.htm").queue();
							return;
						}
					}
					Language.updateLang(Config.getLang(), true);
					e.getChannel().sendMessage("`" + Config.getLang() + "` language files have been reset!").queue();
					return;
				} else if (args[0].equalsIgnoreCase("update")) {
					if (args.length >= 2) {
						String replaced = pattern.matcher(args[1]).replaceAll("").substring(0, 5);
						if (replaced.length() > 0) {
							Language.updateGuildLang(replaced);
							e.getChannel().sendMessage("`" + replaced + "` language files have been updated!").queue();
							return;
						} else {
							e.getChannel().sendMessage("Please specify a valid language! http://www.lingoes.net/en/translator/langcode.htm").queue();
							return;
						}
					}
					e.getChannel().sendMessage("Please specify a valid language! http://www.lingoes.net/en/translator/langcode.htm").queue();
					return;
				}
				String replaced = pattern.matcher(args[0]).replaceAll("").substring(0, 5);
				if (replaced.length() > 0) {
					Config.setLang(replaced);
					Language.updateLang(Config.getLang());
					e.getChannel().sendMessage("Set global bot language to `" + Config.getLang() + "`").queue();
					return;
				} else {
					e.getChannel().sendMessage("Please specify a valid language! http://www.lingoes.net/en/translator/langcode.htm").queue();
					return;
				}
			}
		}
		e.getChannel().sendMessage("The current global language is `" + Config.getLang() + "`").queue();
	}

	public String getDesc(long guildID) {
		return "Global bot language management";
	}

	public boolean isHidden() {
		return false;
	}

}
