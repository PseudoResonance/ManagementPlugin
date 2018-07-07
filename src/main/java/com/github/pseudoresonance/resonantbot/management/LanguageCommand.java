package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.Language;
import com.github.pseudoresonance.resonantbot.api.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LanguageCommand implements Command {

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("reset")) {
					if (args.length >= 2) {
						Language.resetLang(args[1]);
						e.getChannel().sendMessage("`" + args[1] + "` language files have been reset!").queue();
						return;
					}
					Language.updateLang(Config.getLang(), true);
					e.getChannel().sendMessage("`" + Config.getLang() + "` language files have been reset!").queue();
					return;
				} else if (args[0].equalsIgnoreCase("update")) {
					if (args.length >= 2) {
						Language.updateGuildLang(args[1]);
						e.getChannel().sendMessage("`" + args[1] + "` language files have been updated!").queue();
						return;
					}
					e.getChannel().sendMessage("Please specify a language to update!").queue();
					return;
				}
				Config.setLang(args[0]);
				Language.updateLang(Config.getLang());
				e.getChannel().sendMessage("Set global bot language to `" + Config.getLang() + "`").queue();
				return;
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
