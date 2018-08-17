package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.Language;
import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.api.Plugin;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PluginsCommand implements Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
			String output = "";
			boolean first = true;
			for (Plugin p : PluginManager.getPlugins()) {
				if (first) {
					output += p.getName();
					first = false;
				} else
					output += ", " + p.getName();
			}
			e.getChannel().sendMessage(Language.getMessage(e, "management.loadedPlugins", output)).queue();
		}
	}
	
	public String getDesc(long id) {
		return Language.getMessage(id, "management.pluginsCommandDescription");
	}

	public boolean isHidden() {
		return true;
	}

}
