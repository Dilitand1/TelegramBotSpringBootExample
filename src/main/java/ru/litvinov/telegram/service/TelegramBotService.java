package ru.litvinov.telegram.service;

import org.springframework.stereotype.Service;
import ru.litvinov.telegram.utils.FileUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class TelegramBotService {

    File chatIdFile = null;
    Set<String> chatIds = new HashSet<>();

    public void addNewChatId(Long chatId) {
        try {
            if (!chatIds.contains(String.valueOf(chatId))) {
                chatIds.add(String.valueOf(chatId));
                FileUtils.addStringToTheEndOfRandomFile(String.valueOf(chatId),chatIdFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() throws IOException {
        chatIdFile = new File(("chatIds.txt"));
        Files.lines(Paths.get(chatIdFile.getAbsolutePath())).forEach(line -> chatIds.add(line));
    }
}
