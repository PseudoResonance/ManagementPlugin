package com.github.pseudoresonance.resonantbot.management;

import java.io.File;
import java.util.Set;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.api.Plugin;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ReloadCommand implements Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		boolean reloaded = false;
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
							reloaded = true;
							m = null;
							PluginManager.reload(f, e.getChannel(), e.getGuild().getIdLong());
							return;
						}
					} else {
						Set<Plugin> plugins = PluginManager.getPlugins();
						File f = null;
						for (Plugin m : plugins) {
							if (m.getName().equalsIgnoreCase(input)) {
								f = PluginManager.getFile(m);
							}
						}
						if (f != null) {
							reloaded = true;
							PluginManager.reload(f, e.getChannel(), e.getGuild().getIdLong());
							return;
						}
					}
					if (!reloaded)
						e.getChannel().sendMessage("Unknown plugin! Please choose one from " + MessageListener.getPrefix(e.getGuild()) + "plugins!").queue();
				} else {
					e.getChannel().sendMessage("Please add a plugin to reload! Choose one from " + MessageListener.getPrefix(e.getGuild()) + "plugins!").queue();
				}
			} else {
				e.getChannel().sendMessage("Please add a plugin to reload! Choose one from " + MessageListener.getPrefix(e.getGuild()) + "plugins!").queue();
			}
		}
	}
	
	public String getDesc(long guildID) {
		return "Reloads specified plugin";
	}

	public boolean isHidden() {
		return true;
	}

}
