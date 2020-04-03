package com.github.pseudoresonance.resonantbot.management;

import java.util.regex.Pattern;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StatusCommand implements Command {

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				if (args.length > 1) {
					String s = "";
					boolean first = true;
					try {
						Activity.ActivityType type = Activity.ActivityType.valueOf(args[0].toUpperCase());
						for (int i = 1; i < args.length; i++) {
							if (first) {
								s += args[i];
								first = false;
							} else
								s += " " + args[i];
						}
						if (type == Activity.ActivityType.STREAMING) {
							String[] split = s.split(Pattern.quote("|"), 2);
							if (split.length > 1) {
								if (Activity.isValidStreamingUrl(split[1])) {
									Config.setStatus(Activity.ActivityType.STREAMING, s);
									Config.updateStatus();
									e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.setStatusStreaming", split[0])).queue();
								} else {
									e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validStreamNameUrl")).queue();
								}
							} else {
								e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addStreamNameUrl")).queue();
							}
						} else {
							Config.setStatus(type, s);
							Config.updateStatus();
							if (type == Activity.ActivityType.DEFAULT)
								e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.setStatusPlaying", s)).queue();
							else if (type == Activity.ActivityType.LISTENING)
								e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.setStatusListening", s)).queue();
							else if (type == Activity.ActivityType.WATCHING)
								e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.setStatusWatching", s)).queue();
						}
					} catch (IllegalArgumentException ex) {
						e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validStatusType")).queue();
					}
				} else {
					if (args[0].equalsIgnoreCase("STREAMING")) {
						e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addStreamNameUrl")).queue();
					} else {
						e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addStatusMessage")).queue();
					}
				}
			} else {
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.validStatusType")).queue();
			}
		}
	}
	
	public String getDesc(long id) {
		return LanguageManager.getLanguage(id).getMessage("management.statusCommandDescription");
	}

	public boolean isHidden() {
		return true;
	}

}
