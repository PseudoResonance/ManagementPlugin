package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GuildPrefixCommand implements Command {

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (args.length == 0) {
			if (e.getChannelType() == ChannelType.PRIVATE) {
				e.getChannel().sendMessage("The prefix for DMs is `" + Config.getPrefix() + "`").queue();
				return;
			}
			e.getChannel().sendMessage("The prefix for " + e.getGuild().getName() + " is `" + MessageListener.getPrefix(e.getGuild()) + "`").queue();
		} else if (args.length == 1) {
			e.getChannel().sendMessage("Please specify a guild ID an prefix to set!").queue();
		} else if (args.length >= 2) {
			if (e.getAuthor().getIdLong() == Config.getOwner()) {
				try {
					long id = Long.valueOf(args[0]);
					if (!args[1].equals("")) {
						String prefix = "";
						for (int i = 1; i < args.length; i++) {
							prefix += args[i] + " ";
						}
						prefix = prefix.substring(0, prefix.length() - 1);
						Guild guild = e.getJDA().getGuildById(id);
						MessageListener.setPrefix(guild.getIdLong(), prefix);
						e.getChannel().sendMessage("The prefix for " + guild.getName() + " has been set to `" + MessageListener.getPrefix(guild) + "`").queue();
					} else {
						e.getChannel().sendMessage("Please add a prefix to set!").queue();
					}
				} catch (NumberFormatException ex) {
					e.getChannel().sendMessage("Please add a valid guild id!").queue();
				}
			} else {
				e.getChannel().sendMessage("The prefix for " + e.getGuild().getName() + " is `" + MessageListener.getPrefix(e.getGuild()) + "`").queue();
			}
		}
	}

	public String getDesc(long guildID) {
		return "Sets or gets a guild prefix";
	}

	public boolean isHidden() {
		return true;
	}

}
