package com.github.pseudoresonance.resonantbot.management;

import java.util.regex.Pattern;

import com.github.pseudoresonance.resonantbot.Config;
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
									e.getChannel().sendMessage("Set status to streaming: " + split[0]).queue();
								} else {
									e.getChannel().sendMessage("Please add a valid stream name and url separated by a `|`!").queue();
								}
							} else {
								e.getChannel().sendMessage("Please add a stream name and url separated by a `|`!").queue();
							}
						} else {
							Config.setStatus(type, s);
							Config.updateStatus();
							e.getChannel().sendMessage("Set status to " + type.toString().toLowerCase() + ": " + s).queue();
						}
					} catch (IllegalArgumentException ex) {
						e.getChannel().sendMessage("Please use a valid status type: `DEFAULT`, `LISTENING`, `STREAMING` or `WATCHING`!").queue();
					}
				} else {
					if (args[0].equalsIgnoreCase("STREAMING")) {
						e.getChannel().sendMessage("Please add a stream name and url!").queue();
					} else {
						e.getChannel().sendMessage("Please add a message to set the status to!").queue();
					}
				}
			} else {
				e.getChannel().sendMessage("Please add a status type: `DEFAULT`, `LISTENING`, `STREAMING` or `WATCHING`!").queue();
			}
		}
	}
	
	public String getDesc(long guildID) {
		return "Changes bot status";
	}

	public boolean isHidden() {
		return true;
	}

}
