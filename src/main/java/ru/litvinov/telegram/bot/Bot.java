package ru.litvinov.telegram.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.litvinov.telegram.service.TelegramBotService;

import java.util.ArrayList;
import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private TelegramBotService telegramBotService;

    @Override
    public void onUpdateReceived(Update update) {
        //Из чата бота
        Message message = update.getMessage();
        //Если бот добавлен в другой чат как админ, то используем getChannelPost
        if (message == null) {
            message = update.getChannelPost();
        }
        try {
            if ("/start".equalsIgnoreCase(message.getText())) {
                telegramBotService.addNewChatId(message.getChatId());
                sendMsg(message, "куку");
            } else {
                sendMsg(message,"unrecognized command");
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) throws TelegramApiException {
        //TODO обработка входящих сообщений
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        execute(sendMessage);
    }

    //Пример реализации кнопок
    public void setButtons(SendMessage sendMessage, Message message, String[] questions) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyRow1 = new KeyboardRow();
        KeyboardRow keyRow2 = new KeyboardRow();

        if (message.getText() != null
                && (message.getText().equalsIgnoreCase("/start")
                || message.getText().equalsIgnoreCase("/restart")
                || message.getText().equalsIgnoreCase("/Пройти опрос заново"))) {
            KeyboardButton kb1 = new KeyboardButton("/Пройти опрос");
            kb1.setRequestContact(true);
            keyRow1.add(kb1);
        } else if (questions != null) {
            for (int i = 1; i < questions.length; i++) {
                keyRow1.add(new KeyboardButton(questions[i]));
            }
            keyRow2.add(new KeyboardButton("/Пройти опрос заново"));
        } else {
            KeyboardButton kb2 = new KeyboardButton("/Пройти опрос");
            kb2.setRequestContact(true);
            keyRow1.add(kb2);
        }
        keyboardRows.add(keyRow1);
        keyboardRows.add(keyRow2);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    // Геттеры, которые необходимы для наследования от TelegramLongPollingBot
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Autowired
    public void setTelegramBotService(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }
}