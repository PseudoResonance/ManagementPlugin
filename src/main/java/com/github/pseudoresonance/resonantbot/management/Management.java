package com.github.pseudoresonance.resonantbot.management;

import com.github.pseudoresonance.resonantbot.CommandManager;
import com.github.pseudoresonance.resonantbot.ResonantBot;
import com.github.pseudoresonance.resonantbot.api.Plugin;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

public class Management extends Plugin {
	
	OwnerCommand ownerCommand = null;

	public void onEnable() {
		CommandManager.registerCommand(this, new LoadCommand(), "load", "management.loadCommandDescription", PermissionGroup.BOT_ADMIN);
		CommandManager.registerCommand(this, new UnloadCommand(), "unload", "management.unloadCommandDescription", PermissionGroup.BOT_ADMIN);
		CommandManager.registerCommand(this, new ReloadCommand(), "reload", "management.reloadCommandDescription", PermissionGroup.BOT_ADMIN);
		CommandManager.registerCommand(this, new PluginsCommand(), "plugins", "management.pluginsCommandDescription", PermissionGroup.BOT_ADMIN);
		CommandManager.registerCommand(this, new StatusCommand(), "status", "management.statusCommandDescription", PermissionGroup.BOT_ADMIN);
		CommandManager.registerCommand(this, new PluginFilesCommand(), "pluginfiles", "management.pluginFilesCommandDescription", PermissionGroup.BOT_ADMIN);
		CommandManager.registerCommand(this, new GuildPrefixCommand(), "guildprefix", "management.guildPrefixCommandDescription", PermissionGroup.BOT_ADMIN);
		ownerCommand = new OwnerCommand();
		CommandManager.registerCommand(this, ownerCommand, "owner", "management.ownerCommandDescription", PermissionGroup.BOT_ADMIN);
		ResonantBot.getBot().getJDA().addEventListener(ownerCommand);
		CommandManager.registerCommand(this, new LanguageCommand(), "language", "management.languageCommandDescription");
		CommandManager.registerCommand(this, new GuildLanguageCommand(), "guildlanguage", "management.guildLanguageCommandDescription", PermissionGroup.BOT_ADMIN);
	}
	
	public void onDisable() {
	}
	
}
