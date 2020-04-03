package com.github.pseudoresonance.resonantbot.management;

import java.util.HashMap;
import org.apache.commons.lang3.RandomStringUtils;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.ResonantBot;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.language.LanguageManager;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OwnerCommand extends ListenerAdapter implements Command {
	
	HashMap<Long, String> secretKeys = new HashMap<Long, String>();
	HashMap<Long, Long> timeout = new HashMap<Long, Long>();

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
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
	public void onMessageReceived(MessageReceivedEvent e) {
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
	
	public String getDesc(long id) {
		return LanguageManager.getLanguage(id).getMessage("management.ownerCommandDescription");
	}

	public boolean isHidden() {
		return false;
	}

}
