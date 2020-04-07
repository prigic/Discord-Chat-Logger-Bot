package com.prigic.discordbot.chatlogger;

import java.util.concurrent.*;

import javax.security.auth.login.*;

import com.prigic.discordbot.chatlogger.Config;

import net.dv8tion.jda.core.*;

public class Main {

	public static net.dv8tion.jda.core.JDA JDA;
	public static final ExecutorService executorService = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		Config config;
		try {
			config = new Config();
		} catch (Exception e) {
			return;
		}
		try {
			JDA = new JDABuilder(AccountType.BOT).setToken(config.getToken()).addEventListener(new Listener())
					.buildAsync();
			System.out.println(JDA.getPing() + "ms");
		} catch (LoginException | IllegalArgumentException e) {
		}
	}
}
