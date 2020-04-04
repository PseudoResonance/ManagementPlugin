package com.github.pseudoresonance.resonantbot.management;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.github.pseudoresonance.resonantbot.PluginManager;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.api.Plugin;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class UnloadCommand extends Command {

	@Override
	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
		boolean unloaded = false;
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
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.unloadedJar", f.getName())).queue();
						else
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.failedUnload", f.getName())).queue();
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
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.unloadedJar", f.getName())).queue();
						else
							e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.failedUnload", f.getName())).queue();
					}
				}
				if (!unloaded)
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.unknownPlugin", MessageListener.getPrefix(e) + "plugins")).queue();
			} else {
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addPluginUnload", MessageListener.getPrefix(e) + "plugins")).queue();
			}
		} else {
			e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.addPluginUnload", MessageListener.getPrefix(e) + "plugins")).queue();
		}
	}

}
