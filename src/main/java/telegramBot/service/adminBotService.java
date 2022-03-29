package telegramBot.service;

import excel.ExcelFile;
import model.Food;
import model.FoodType;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegramBot.adminBot.AdminInterface;
import telegramBot.userBot.UserInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class adminBotService implements AdminInterface {
    public static Boolean isAdmin(String phoneNumber){
        if(userService.isAdminCheckByNumber(phoneNumber))
            return true;
        else
            return false;
    }
    public static ReplyKeyboardMarkup adminMENU() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(ADMIN_USER_LIST);
        keyboardRowList.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add(ADMIN_FOOD_LIST);
        keyboardRow.add(ADMIN_CATEGORY);
        keyboardRowList.add(keyboardRow);

        keyboardRow = new KeyboardRow();
        keyboardRow.add(ADMIN_HISTORY);
        keyboardRow.add(ADMIN_BALANCE);
        keyboardRowList.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }
    public static SendMessage sendFoodList(String chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String text="";
        int index=1;
        for (Food food: foodService.getAll()) {
            if(food.isActiveFood()){
                text+=index++ +". Food name : "+food.getName()
                        +"\t Price : "+food.getPrice()
                        +"\nDescription : "+food.getDescription()+"\n\n";
            }
        }
        message.setText(text);
        return message;
    }
    public static SendMessage sendCategoryList(String chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        String text="";
        int index=1;
        for (FoodType foodType: foodTypeService.getAll()) {
            if(foodType.isActiveFoodType()){
                text+=index++ +". Category name : "+foodType.getCategoryName()
                        +"\n\n";
            }
        }
        message.setText(text);
        return message;
    }
    public static SendDocument sendDocUploadingAFile(String chatId){

        SendDocument sendDocumentRequest = new SendDocument();
        sendDocumentRequest.setChatId(chatId);
        ExcelFile.parseUserListToExcel(userListURL);
        sendDocumentRequest.setDocument(new InputFile(new File(userListURL)));
        return sendDocumentRequest;
    }
    public static SendDocument sendHistoryFile(String chatId){
        SendDocument document = new SendDocument();
        document.setChatId(chatId);
        ExcelFile.parseHistoryListToExcel(historyListURL);
        document.setDocument(new InputFile(new File(historyListURL)));

        return document;
    }

}
