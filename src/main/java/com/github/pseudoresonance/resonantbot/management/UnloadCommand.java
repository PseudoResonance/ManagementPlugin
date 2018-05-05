package com.github.pseudoresonance.resonantbot.management;

import java.io.File;
import java.util.Set;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.api.Plugin;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UnloadCommand implements Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		boolean unloaded = false;
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
			if (args.length >= 1) {
				String input = "";
				for (int i = 0; i < args.length; i++) {
					if (i > 0)
						input += " " + args[i];
					else
						input = args[i];
				}
				if (!input.equals("")) {
					if (input.endsWith(".jar")) {
						File f = new File(PluginManager.getDir(), input);
						Plugin m = PluginManager.getPlugin(f);
						if (m != null) {
							unloaded = true;
							String name = m.getName();
							m = null;
							boolean result = PluginManager.unload(name);
							if (result)
								e.getChannel().sendMessage("Unloaded plugin jar: " + f.getName()).queue();
							else
								e.getChannel().sendMessage("Failed to unload plugin jar: " + f.getName()).queue();
						}
					} else {
						Set<Plugin> plugins = PluginManager.getPlugins();
						Plugin unload = null;
						for (Plugin m : plugins) {
							if (m.getName().equalsIgnoreCase(input)) {
								unload = m;
							}
						}
						if (unload != null) {
							unloaded = true;
							File f = PluginManager.getFile(unload);
							String name = unload.getName();
							unload = null;
							boolean result = PluginManager.unload(name);
							if (result)
								e.getChannel().sendMessage("Unloaded plugin jar: " + f.getName()).queue();
							else
								e.getChannel().sendMessage("Failed to unload plugin jar: " + f.getName()).queue();
						}
					}
					if (!unloaded)
						e.getChannel().sendMessage("Unknown plugin! Please choose one from " + MessageListener.getPrefix(e.getGuild()) + "plugins or specify the jar name!").queue();
				} else {
					e.getChannel().sendMessage("Please add a plugin to unload! Choose one from " + MessageListener.getPrefix(e.getGuild()) + "plugins!").queue();
				}
			} else {
				e.getChannel().sendMessage("Please add a plugin to unload! Choose one from " + MessageListener.getPrefix(e.getGuild()) + "plugins!").queue();
			}
		}
	}
	
	public String getDesc() {
		return "Unloads specified plugin";
	}

	public boolean isHidden() {
		return true;
	}

}
