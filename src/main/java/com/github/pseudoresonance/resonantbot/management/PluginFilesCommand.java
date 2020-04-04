package com.github.pseudoresonance.resonantbot.management;

import java.io.File;
import java.util.HashSet;

import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PluginFilesCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
		String output = "";
		boolean first = true;
		for (File f : PluginManager.getDir().listFiles()) {
			if (f.getName().endsWith(".jar")) {
				if (first) {
					output += f.getName();
					first = false;
				} else
					output += ", " + f.getName();
			}
		}
		e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.pluginJars", output)).queue();
	}

}
