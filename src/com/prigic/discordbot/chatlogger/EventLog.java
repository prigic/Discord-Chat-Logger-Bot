package com.prigic.discordbot.chatlogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.dv8tion.jda.core.entities.*;

public class EventLog {

	private User user;
	private Guild guild;

	private String message;
	private File files;

	public EventLog(User user, Guild guild, String message) {
		Config config;
		try {
			config = new Config();
		} catch (Exception e) {
			return;
		}

		this.user = user;
		this.guild = guild;
		this.message = message;
		this.files = new File(config.getFolder() + "/" + this.guild.getName() + "#" + this.guild.getId() + "/Eventlog");
	}

	public void write() {

		if (!files.exists())
			files.mkdirs();

		userEvent();

	}

	private synchronized void userEvent() {
		Date day = new Date();
		SimpleDateFormat formatType = new SimpleDateFormat("yyyy-MM-dd");
		File file = new File(files.toString() + "/" + formatType.format(day) + ".txt");

		try (BufferedWriter logwriter = new BufferedWriter(new FileWriter(file, true))) {

			Calendar oCalendar = Calendar.getInstance();
			String str = "[" + oCalendar.get(Calendar.HOUR_OF_DAY) + "시" + oCalendar.get(Calendar.MINUTE) + "분"
					+ oCalendar.get(Calendar.SECOND) + "초] " + user.getName() + "#" + user.getDiscriminator() + " ("
					+ user.getId() + ") : " + message;
			logwriter.write(str);
			logwriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
