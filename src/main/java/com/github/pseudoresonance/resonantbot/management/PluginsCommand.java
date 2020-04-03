package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.api.Plugin;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PluginsCommand implements Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
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
	
	public String getDesc(long id) {
		return LanguageManager.getLanguage(id).getMessage("management.pluginsCommandDescription");
	}

	public boolean isHidden() {
		return true;
	}

}
