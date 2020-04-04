package com.github.pseudoresonance.resonantbot.management;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.RandomStringUtils;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.ResonantBot;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;
import com.github.pseudoresonance.resonantbot.permissions.PermissionGroup;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class OwnerCommand extends Command implements EventListener {
	
	HashMap<Long, String> secretKeys = new HashMap<Long, String>();
	HashMap<Long, Long> timeout = new HashMap<Long, Long>();

	public void onCommand(MessageReceivedEvent e, String command, HashSet<PermissionGroup> userPermissions, String[] args) {
		if (Config.getOwner() == e.getAuthor().getIdLong()) {
			e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.alreadyOwner")).queue();
			return;
		}
		if (timeout.containsKey(e.getAuthor().getIdLong())) {
			if (System.currentTimeMillis() - timeout.get(e.getAuthor().getIdLong()) <= 3600000) {
				secretKeys.remove(e.getAuthor().getIdLong());
				e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.runCommandTooRecently")).queue();
				return;
			}
		}
		String key = RandomStringUtils.randomAlphanumeric(100);
		ResonantBot.getBot().getLogger().info(LanguageManager.getLanguage(e).getMessage("management.pasteIntoChat", key));
		secretKeys.put(e.getAuthor().getIdLong(), key);
		timeout.put(e.getAuthor().getIdLong(), System.currentTimeMillis());
		e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.secretKeyLogged")).queue();
	}

	@Override
	public void onEvent(GenericEvent event) {
		if (event instanceof MessageReceivedEvent) {
			MessageReceivedEvent e = (MessageReceivedEvent) event;
			if (e.getAuthor().isBot()) {
				return;
			}
			if (secretKeys.containsKey(e.getAuthor().getIdLong())) {
				String message = e.getMessage().getContentRaw();
				if (message.startsWith(MessageListener.getPrefix(e) + "owner"))
					return;
				String key = secretKeys.remove(e.getAuthor().getIdLong());
				if (message.equals(key)) {
					timeout.remove(e.getAuthor().getIdLong());
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.newBotOwner", e.getAuthor().getAsMention(), Config.getName())).queue();
					Config.setOwner(e.getAuthor().getIdLong());
				} else {
					e.getChannel().sendMessage(LanguageManager.getLanguage(e).getMessage("management.invalidKey")).queue();
				}
			}
		}
	}

}
