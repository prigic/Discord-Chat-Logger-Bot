package com.prigic.discordbot.chatlogger;

import net.dv8tion.jda.core.events.guild.member.*;
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.hooks.*;

public class Listener extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		Runnable runnable = new Thread(() -> {
			ChatLog chatlog = new ChatLog(event.getMessage());
			chatlog.write();
		});
		Main.executorService.submit(runnable);

	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Runnable runnable = new Thread(() -> {

			try {
				EventLog eventLog = new EventLog(event.getUser(), event.getGuild(), "서버 입장");
				eventLog.write();
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		Main.executorService.submit(runnable);
	}

	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		Runnable runnable = new Thread(() -> {

			EventLog eventLog = new EventLog(event.getUser(), event.getGuild(), "서버 퇴장");
			eventLog.write();

		});

		Main.executorService.submit(runnable);
	}
}
