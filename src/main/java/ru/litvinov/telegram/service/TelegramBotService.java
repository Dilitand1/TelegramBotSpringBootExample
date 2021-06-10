package ru.litvinov.telegram.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotService {

    File chatIdFile = null;
    List<String> chatIds = new ArrayList<>();

    public void addNewChatId(String chatId) {
        Path path = Paths.get(chatIdFile.getAbsolutePath());
        try {
            if (!chatIds.contains(chatId)) {
                FileWriter writer = new FileWriter(chatIdFile);
                for (String s : chatIds) {
                    writer.write(chatId + "\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() throws IOException {
        chatIdFile = new File(TelegramBotService.class.getClassLoader().getResource("chatIds.txt").getPath());
        Files.lines(Paths.get(chatIdFile.getAbsolutePath())).forEach(line -> chatIds.add(line));
    }
}
