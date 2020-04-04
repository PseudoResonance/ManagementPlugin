package com.github.pseudoresonance.resonantbot.management;

import java.io.File;
import java.util.HashSet;

import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LoadCommand extends Command {

	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
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
