package ifmo.practice;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@Log4j2
public class Bot extends TelegramLongPollingBot{

    String name;
    String token;

    static Chatbot homebot;

    @SneakyThrows
    public Bot() {
        val properties = new Properties();
        try (InputStream inputStream = Bot.class.getResourceAsStream("/settings.properties")) {
            properties.load(inputStream);
        }
        name = Optional.ofNullable(properties.getProperty("name"))
                .orElseThrow(() -> new RuntimeException(("Нет имени. Добавьте имя в файл settings.properties")));
        token = Optional.ofNullable(properties.getProperty("token"))
                .orElseThrow(() -> new RuntimeException(("Нет токена. Добавьте токен в файл settings.properties")));

        homebot = new Chatbot();
    }

    @SneakyThrows
    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        log.info("All OK!");
        val botapi = new TelegramBotsApi();
            botapi.registerBot(new Bot());
    }

    @Override
    public String getBotUsername() {
        return name;
        //возвращаем юзера
    }

    @Override
    public String getBotToken() {
        return token;
        //Токен бота
    }


    @Override
    public void onUpdateReceived(Update e) {
        Message msg = e.getMessage();
        String txt = msg.getText();
        sendMsg(msg, homebot.getAnswer(txt));
    }


    @SneakyThrows
    @SuppressWarnings("deprecation")
    private void sendMsg(Message msg, String text) {
        val s = new SendMessage()
                .setChatId(msg.getChatId())
                .setText(text);
            sendMessage(s);
    }

}

