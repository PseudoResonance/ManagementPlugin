package com.github.pseudoresonance.resonantbot.management;

import java.util.regex.Pattern;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.Language;
import com.github.pseudoresonance.resonantbot.api.Command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

public class GuildLanguageCommand implements Command {
	
	private static final Pattern pattern = Pattern.compile("[^A-Za-z0-9-]");

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (PermissionUtil.checkPermission(e.getTextChannel(), e.getMember(), Permission.ADMINISTRATOR) || e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("reset")) {
					Language.unsetGuildLang(e.getGuild().getIdLong());
					e.getChannel().sendMessage("Guild language has been reset to `" + Language.getGuildLang(e.getGuild().getIdLong()) + "`!").queue();
					return;
				}
				Language.setGuildLang(e.getGuild().getIdLong(), pattern.matcher(args[0]).replaceAll("").substring(0, 5));
				e.getChannel().sendMessage("Set guild language to `" + Language.getGuildLang(e.getGuild().getIdLong()) + "`").queue();
				return;
			}
		}
		e.getChannel().sendMessage("The current guild language is `" + Language.getGuildLang(e.getGuild().getIdLong()) + "`").queue();
	}

	public String getDesc(long guildID) {
		return "Guild language";
	}

	public boolean isHidden() {
		return false;
	}

}
