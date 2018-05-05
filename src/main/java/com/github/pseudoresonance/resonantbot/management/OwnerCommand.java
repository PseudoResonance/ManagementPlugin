package com.github.pseudoresonance.resonantbot.management;

import java.util.HashMap;
import org.apache.commons.lang3.RandomStringUtils;

import com.github.pseudoresonance.resonantbot.Config;
import com.github.pseudoresonance.resonantbot.ResonantBot;
import com.github.pseudoresonance.resonantbot.api.Command;
import com.github.pseudoresonance.resonantbot.listeners.MessageListener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class OwnerCommand extends ListenerAdapter implements Command {
	
	HashMap<Long, String> secretKeys = new HashMap<Long, String>();
	HashMap<Long, Long> timeout = new HashMap<Long, Long>();

	public void onCommand(MessageReceivedEvent e, String command, String[] args) {
		if (Config.getOwner() == e.getAuthor().getIdLong()) {
			e.getChannel().sendMessage("You are already the bot owner!").queue();
			return;
		}
		if (timeout.containsKey(e.getAuthor().getIdLong())) {
			if (System.currentTimeMillis() - timeout.get(e.getAuthor().getIdLong()) <= 3600000) {
				e.getChannel().sendMessage("Sorry, you have already run this command too recently!").queue();
				return;
			}
		}
		String key = RandomStringUtils.randomAlphanumeric(100);
		ResonantBot.getLogger().info("Paste into Chat to Transfer Ownership: " + key);
		secretKeys.put(e.getAuthor().getIdLong(), key);
		e.getChannel().sendMessage("Secret key has been logged in the console. Please paste the key to take ownership of the bot.").queue();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot()) {
			return;
		}
		if (secretKeys.containsKey(e.getAuthor().getIdLong())) {
			String message = e.getMessage().getContentRaw();
			if (message.startsWith(MessageListener.getPrefix(e.getGuild()) + "owner"))
				return;
			String key = secretKeys.remove(e.getAuthor().getIdLong());
			if (message.equals(key)) {
				timeout.remove(e.getAuthor().getIdLong());
				e.getChannel().sendMessage(e.getAuthor().getAsMention() + " is now the owner of " + Config.getName() + "!").queue();
				Config.setOwner(e.getAuthor().getIdLong());
				Config.save();
			} else {
				e.getChannel().sendMessage("Sorry, that key is invalid!").queue();
				timeout.put(e.getAuthor().getIdLong(), System.currentTimeMillis());
			}
		}
	}
	
	public String getDesc() {
		return "Transfers bot ownership";
	}

	public boolean isHidden() {
		return false;
	}

}
