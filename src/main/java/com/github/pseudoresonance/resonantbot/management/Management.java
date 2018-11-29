package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.CommandManager;
import com.github.pseudoresonance.resonantbot.ResonantBot;
import com.github.pseudoresonance.resonantbot.api.Plugin;

public class Management extends Plugin {
	
	OwnerCommand ownerCommand = null;

	public void onEnable() {
		ownerCommand = new OwnerCommand();
		CommandManager.registerCommand("load", new LoadCommand(), this);
		CommandManager.registerCommand("unload", new UnloadCommand(), this);
		CommandManager.registerCommand("reload", new ReloadCommand(), this);
		CommandManager.registerCommand("plugins", new PluginsCommand(), this);
		CommandManager.registerCommand("status", new StatusCommand(), this);
		CommandManager.registerCommand("pluginfiles", new PluginFilesCommand(), this);
		CommandManager.registerCommand("guildprefix", new GuildPrefixCommand(), this);
		CommandManager.registerCommand("owner", ownerCommand, this);
		ResonantBot.getJDA().addEventListener(ownerCommand);
		CommandManager.registerCommand("language", new LanguageCommand(), this);
		CommandManager.registerCommand("guildlanguage", new GuildLanguageCommand(), this);
	}
	
	public void onDisable() {
	}
	
}
