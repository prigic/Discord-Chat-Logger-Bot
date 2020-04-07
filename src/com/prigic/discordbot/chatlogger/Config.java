package com.prigic.discordbot.chatlogger;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Config {
	private String Token;
	private String logfolder;

	public Config() throws Exception {
		List<String> lines = Files.readAllLines(Paths.get("config.txt"));
		for (String str : lines) {
			String[] parts = str.split("=", 2);
			String key = parts[0].trim().toLowerCase();
			String value = parts.length > 1 ? parts[1].trim() : null;
			switch (key) {
			case "token":
				Token = value;
				break;
			case "folder":
				logfolder = value;
				break;

			}
		}
		if (Token == null)
			throw new Exception("Config에 봇 토큰이 없습니다!");
		if (logfolder == null)
			throw new Exception("Config에 로그를 저장 할 폴더 경로가 지정되어있지 않습니다.");
	}

	public String getToken() {
		return Token;
	}

	public String getFolder() {
		return logfolder;
	}
}
