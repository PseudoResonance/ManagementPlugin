package com.github.pseudoresonance.resonantbot.management;

import java.util.HashSet;

import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.api.Plugin;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PluginsCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
		String output = "";
		boolean first = true;
		for (Plugin p : PluginManager.getPlugins()) {
			if (first) {
				output += p.getName() + " " + LanguageManager.getLanguage(e).getMessage("main.version", p.getVersion());
				first = false;
			} else
				output += ", " + p.getName() + " " + LanguageManager.getLanguage(e).getMessage("main.version", p.getVersion());
		}
		e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.loadedPlugins", output)).queue();
	}

}
