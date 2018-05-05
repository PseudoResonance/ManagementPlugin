package com.github.pseudoresonance.resonantbot.management;

import java.io.File;
import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class LoadCommand implements Command {

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				if (!args[0].equals("")) {
					String name = args[0];
					if (!name.endsWith(".jar"))
						name += ".jar";
					File f = new File(PluginManager.getDir(), name);
					if (f.isFile()) {
						String result = PluginManager.load(f, true);
						e.getChannel().sendMessage(result).queue();
					} else
						e.getChannel().sendMessage("Unknown plugin jar: " + name + " Please choose one from " + MessageListener.getPrefix(e.getGuild()) + "pluginfiles!").queue();
				} else {
					e.getChannel().sendMessage("Please add a plugin jar to load! Choose one from " + MessageListener.getPrefix(e.getGuild()) + "pluginfiles!").queue();
				}
			} else {
				e.getChannel().sendMessage("Please add a plugin jar to load! Choose one from " + MessageListener.getPrefix(e.getGuild()) + "pluginfiles!").queue();
			}
		}
	}
	
	public String getDesc() {
		return "Loads specified plugin jar";
	}

	public boolean isHidden() {
		return true;
	}

}
