package com.github.pseudoresonance.resonantbot.management;

import java.util.regex.Pattern;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.Language;
import com.github.pseudoresonance.resonantbot.api.Command;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StatusCommand implements Command {

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				if (args.length > 1) {
					String s = "";
					boolean first = true;
					try {
						Game.GameType type = Game.GameType.valueOf(args[0].toUpperCase());
						for (int i = 1; i < args.length; i++) {
							if (first) {
								s += args[i];
								first = false;
							} else
								s += " " + args[i];
						}
						if (type == Game.GameType.STREAMING) {
							String[] split = s.split(Pattern.quote("|"), 2);
							if (split.length > 1) {
								if (Game.isValidStreamingUrl(split[1])) {
									Config.setStatus(Game.GameType.STREAMING, s);
									Config.updateStatus();
									e.getChannel().sendMessage(Language.getMessage(e, "management.setStatusStreaming", split[0])).queue();
								} else {
									e.getChannel().sendMessage(Language.getMessage(e, "management.validStreamNameUrl")).queue();
								}
							} else {
								e.getChannel().sendMessage(Language.getMessage(e, "management.addStreamNameUrl")).queue();
							}
						} else {
							Config.setStatus(type, s);
							Config.updateStatus();
							if (type == Game.GameType.DEFAULT)
								e.getChannel().sendMessage(Language.getMessage(e, "management.setStatusPlaying", s)).queue();
							else if (type == Game.GameType.LISTENING)
								e.getChannel().sendMessage(Language.getMessage(e, "management.setStatusListening", s)).queue();
							else if (type == Game.GameType.WATCHING)
								e.getChannel().sendMessage(Language.getMessage(e, "management.setStatusWatching", s)).queue();
						}
					} catch (IllegalArgumentException ex) {
						e.getChannel().sendMessage(Language.getMessage(e, "management.validStatusType")).queue();
					}
				} else {
					if (args[0].equalsIgnoreCase("STREAMING")) {
						e.getChannel().sendMessage(Language.getMessage(e, "management.addStreamNameUrl")).queue();
					} else {
						e.getChannel().sendMessage(Language.getMessage(e, "management.addStatusMessage")).queue();
					}
				}
			} else {
				e.getChannel().sendMessage(Language.getMessage(e, "management.validStatusType")).queue();
			}
		}
	}
	
	public String getDesc(long id) {
		return Language.getMessage(id, "management.statusCommandDescription");
	}

	public boolean isHidden() {
		return true;
	}

}
