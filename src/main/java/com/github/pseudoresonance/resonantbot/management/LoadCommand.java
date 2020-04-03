package com.github.pseudoresonance.resonantbot.management;

import java.io.File;
import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

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
						Long id = 0L;
						if (e.getChannelType() == ChannelType.PRIVATE)
							id = e.getPrivateChannel().getIdLong();
						else
							id = e.getGuild().getIdLong();
						String result = PluginManager.load(f, true, id);
						e.getChannel().sendMessage(result).queue();
					} else
						e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.unknownPluginJar", name, MessageListener.getPrefix(e) + "pluginfiles")).queue();
				} else {
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addPluginJar", MessageListener.getPrefix(e) + "pluginfiles")).queue();
				}
			} else {
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addPluginJar", MessageListener.getPrefix(e) + "pluginfiles")).queue();
			}
		}
	}
	
	public String getDesc(long id) {
		return LanguageManager.getLanguage(id).getMessage("management.loadCommandDescription");
	}

	public boolean isHidden() {
		return true;
	}

}
