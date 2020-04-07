package com.prigic.discordbot.chatlogger;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.prigic.discordbot.chatlogger.Config;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.Message.*;

public class ChatLog {

	private Message message;
	private User sender;
	private TextChannel channel;

	private Guild guild;

	private String ms;
	private File files;

	public ChatLog(Message message) {

		Config config;
		try {
			config = new Config();
		} catch (Exception e) {
			return;
		}

		this.message = message;
		this.sender = message.getAuthor();

		this.channel = message.getTextChannel();
		this.guild = message.getGuild();

		this.ms = message.getContentDisplay();

		this.files = new File(config.getFolder() + "/" + this.guild.getName() + "#" + this.guild.getId() + "/"
				+ channel.getName() + "#" + channel.getId());
	}

	public void write() {

		if (!files.exists())
			files.mkdirs();

		List<Attachment> list = message.getAttachments();

		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {

				File afile = new File(files + "/Files");

				if (!afile.exists())
					afile.mkdirs();

				String fileUrl = list.get(i).getUrl();

				String replace = fileUrl.replaceAll("^.*\\/\\/", "").replace("/", ".");

				ms = replace;
				stringWrite("파일");
				downLoad(fileUrl, afile, replace);
			}
			return;
		}
		stringWrite("채팅");

	}

	private void downLoad(String fileUrl, File afile, String replace) {
		try {

			URL fileurl = new URL(fileUrl);
			HttpURLConnection connection = (HttpURLConnection) fileurl.openConnection();
			connection.addRequestProperty("User-Agent", "");

			filedown(connection, afile, replace);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void filedown(HttpURLConnection connection, File afile, String replace) {

		File fe = new File(afile + "/" + replace);

		try (BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fe))) {

			byte[] buffer = new byte[1024];
			int f;
			while ((f = bis.read(buffer)) != -1)
				bos.write(buffer, 0, f);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private synchronized void stringWrite(String type) {
		Date day = new Date();
		SimpleDateFormat formatType = new SimpleDateFormat("yyyy-MM-dd");
		File file = new File(files.toString() + "/" + formatType.format(day) + ".txt");

		Calendar oCalendar = Calendar.getInstance();

		try (BufferedWriter logwriter = new BufferedWriter(new FileWriter(file, true))) {

			String str = "[" + type + "][" + oCalendar.get(Calendar.HOUR_OF_DAY) + "시" + oCalendar.get(Calendar.MINUTE)
					+ "분" + oCalendar.get(Calendar.SECOND) + "초] " + sender.getName() + "#" + sender.getDiscriminator()
					+ " (" + sender.getId() + ") : " + ms;
			logwriter.write(str);
			logwriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
