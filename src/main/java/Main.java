import telegramBot.adminBot.AdminBot;
import telegramBot.userBot.UserBot;
import com.vdurmont.emoji.EmojiParser;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static front.UserFront.UserLogin;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new UserBot());
        botsApi.registerBot(new AdminBot());

        System.out.println("Welcome to Street77");
        System.out.println(EmojiParser.parseToUnicode("Bot is running bro :smile:"));
        UserLogin();
    }
}

