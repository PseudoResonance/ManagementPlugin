package com.github.pseudoresonance.resonantbot.management;

import java.io.File;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PluginFilesCommand implements Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (e.getAuthor().getIdLong() == Config.getOwner()) {
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
	
	public String getDesc(long id) {
		return LanguageManager.getLanguage(id).getMessage("management.pluginFilesCommandDescription");
	}

	public boolean isHidden() {
		return true;
	}

}
