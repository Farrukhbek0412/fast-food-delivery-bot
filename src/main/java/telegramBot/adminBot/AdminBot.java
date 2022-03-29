package telegramBot.adminBot;

import com.vdurmont.emoji.EmojiParser;
import excel.ExcelFile;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import telegramBot.service.adminBotService;

import java.math.BigDecimal;

public class AdminBot extends TelegramLongPollingBot implements AdminInterface {

    private AdminState adminState;
    private final String adminPassword;
    private BigDecimal adminBalance;
    private boolean isAdminChecked;
    private String messageFromAdmin;
    private String messageFromBot;


    {
        adminPassword = ADMIN_PASSWORD;
    }

    @Override
    public String getBotUsername() {
        return ADMIN_USERNAME;
    }

    @Override
    public String getBotToken() {
        return ADMIN_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            System.out.println("Xabar keldi");
            messageFromAdmin = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            switch (messageFromAdmin) {
                case ADMIN_START -> {
                    adminState = AdminState.START;
                }
                case ADMIN_CHECK -> {
                    adminState = AdminState.CHECK_ADMIN;
                }
                case ADMIN_PASSWORD -> {
                    this.isAdminChecked = true;
                    messageFromBot = EmojiParser.parseToUnicode("Admin successfully verified, :white_check_mark::relaxed:");
                    sendMessageToUserWithReplyMarkup(chatId, messageFromBot, adminBotService.adminMENU());


                }
                case ADMIN_USER_LIST -> {
                    adminState = (isAdminChecked) ? AdminState.GET_USER_LIST : AdminState.UNKNOWN_COMMAND;
                }

                case ADMIN_FOOD_LIST -> {
                    adminState = (isAdminChecked) ? AdminState.GET_FOOD_LIST : AdminState.UNKNOWN_COMMAND;
                }
                case ADMIN_BALANCE -> {
                    adminState = (isAdminChecked) ? AdminState.BALANCE : AdminState.UNKNOWN_COMMAND;
                }
                case ADMIN_HISTORY -> {
                    adminState = (isAdminChecked) ? AdminState.HISTORY : AdminState.UNKNOWN_COMMAND;
                }
                case ADMIN_CATEGORY -> {
                    adminState = (isAdminChecked) ? AdminState.GET_CATEGORY_LIST : AdminState.UNKNOWN_COMMAND;
                }
                default -> {
                    this.isAdminChecked = false;
                    messageFromBot = EmojiParser.parseToUnicode("wrong password: :angry:");
                    sendMessageToUser(chatId, messageFromBot);
                }

            }
            executeAdminState(chatId, adminState);
        } else if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            switch (callData) {
                case ADMIN_BACK: {

                    switch (adminState) {

                    }
                }
            }
            executeAdminState(chatId, adminState);
        }
    }

    @SneakyThrows
    public void executeAdminState(String chatId, AdminState adminState) {
        if (adminState == null) adminState = AdminState.UNKNOWN_COMMAND;

        switch (adminState) {
            case START -> {
                messageFromBot = "Welcome, admin bro😊\n Please write /admin to continue";
                sendMessageToUser(chatId, messageFromBot);
            }
            case CHECK_ADMIN -> {
                messageFromBot = "Enter admin password: ";
                this.adminState = AdminState.PASSWORD_CHECK;
                sendMessageToUser(chatId, messageFromBot);
            }

            case GET_USER_LIST -> {
                messageFromBot ="User list";
                execute(adminBotService.sendDocUploadingAFile(chatId));
                sendMessageToUser(chatId, messageFromBot);
            }

            case GET_FOOD_LIST -> {
                messageFromBot = "Here you are food list";
                execute(adminBotService.sendFoodList(chatId));
                sendMessageToUser(chatId, messageFromBot);
            }
            case BALANCE -> {
                this.adminBalance=historyService.getBalance();
                messageFromBot = EmojiParser.parseToUnicode(":dollar: :dollar: :dollar:\n admin balance: " + this.adminBalance+" sum");
                sendMessageToUser(chatId, messageFromBot);
            }
            case HISTORY -> {
                messageFromBot = "Here you are";
                execute(adminBotService.sendHistoryFile(chatId));

                sendMessageToUser(chatId, messageFromBot);
            }

            case GET_CATEGORY_LIST -> {
                messageFromBot = "All categories";
                sendMessageToUser(chatId, messageFromBot);
                execute(adminBotService.sendCategoryList(chatId));
            }
            case UNKNOWN_COMMAND -> {
                messageFromBot = "unknown command 😥🤦‍♂";
                sendMessageToUser(chatId, messageFromBot);
            }
        }
    }

    @SneakyThrows
    public void sendMessageToUser(String chatId, String messageFromBot) {
        SendMessage sendMessage = new SendMessage(chatId, messageFromBot);
        execute(sendMessage);
    }

    @SneakyThrows
    public void sendMessageToUserWithReplyMarkup(String chatId, String messageFromBot, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage(chatId, messageFromBot);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        execute(sendMessage);
    }

//    @SneakyThrows
//    public void sendMessageToUserWithInlineMarkup(String chatId, String messageFromBot, InlineKeyboardMarkup inlineKeyboardMarkup) {
//        SendMessage sendMessage = new SendMessage(chatId, messageFromBot);
//        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//        execute(sendMessage);
//    }
}

