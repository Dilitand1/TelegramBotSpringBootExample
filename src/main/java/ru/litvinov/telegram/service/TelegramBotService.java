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
            String chatIdString = String.valueOf(chatId);
            if (!chatIds.contains(chatIdString)) {
                chatIds.add(chatIdString);
                FileUtils.addStringToTheEndOfRandomFile(chatIdString,chatIdFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set getChatIds() {
        return new HashSet<>(chatIds);
    }

    @PostConstruct
    public void init() throws IOException {
        chatIdFile = new File(("chatIds.txt"));
        Files.lines(Paths.get(chatIdFile.getAbsolutePath())).forEach(line -> chatIds.add(line));
    }
}
