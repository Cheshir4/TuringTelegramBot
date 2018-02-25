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

@Log4j2
public class Bot extends TelegramLongPollingBot{

    @SneakyThrows
    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        log.info("All OK!");
        val botapi = new TelegramBotsApi();
            botapi.registerBot(new Bot());
    }

    @Override
    public String getBotUsername() {
        return "Cheshir2Bot";
        //возвращаем юзера
    }

    @Override
    public String getBotToken() {
        return "499534491:AAFGLiD1jAdEn0DKbod__9IVosYCqs2W-QM";
        //Токен бота
    }


    @Override
    public void onUpdateReceived(Update e) {
        Message msg = e.getMessage();
        String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, "Hello, " + getUsName(msg) + "! This is simple bot!");
        }
    }


    @SneakyThrows
    @SuppressWarnings("deprecation")
    private void sendMsg(Message msg, String text) {
        val s = new SendMessage()
                .setChatId(msg.getChatId())
                .setText(text);
            sendMessage(s);
    }

    /**
     * магия
     * @param msg сообщение
     * @return имя пользователя
     */
    private String getUsName(Message msg) {
        return msg.getChat().getFirstName()
                + (msg.getChat().getLastName()!= null ? " " + msg.getChat().getLastName() : "");
    }
}

