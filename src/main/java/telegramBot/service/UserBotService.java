package telegramBot.service;

import telegramBot.userBot.UserInterface;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import model.Basket;
import model.Food;
import model.FoodType;
import model.History;
import model.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserBotService implements UserInterface {

    public SendMessage userMainMenu(String chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("\uD83D\uDCCB Categories");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("\uD83D\uDED2 My Cart");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("\uD83D\uDCCB History");
        keyboardRows.add(row);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        SendMessage sendMessage = new SendMessage(chatId, "MAIN MENU");
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public List<FoodType> read() {
        List<FoodType> list = new ArrayList<>();
        try {
            list = objectMapper.readValue(new File("foodTypeList.json"),
                    new TypeReference<ArrayList<FoodType>>() {
                    });
        } catch (Exception | NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return list;
    }

    public SendMessage categories(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "\uD83D\uDCCB Categories");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtonsRows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        List<FoodType> categories = read();

        if (categories.size() == 0) {
            sendMessage.setText("❗️NO CATEGORY ❗️");
            return sendMessage;
        }
        for (FoodType category : categories) {
            if (category.isActiveFoodType()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(category.getCategoryName());
                button.setCallbackData(category.getCategoryName());

                row.add(button);

                if (row.size() == 2) {
                    inlineButtonsRows.add(row);
                    row = new ArrayList<>();
                }
            }
        }

        if (row.size() != 0) {
            inlineButtonsRows.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(inlineButtonsRows);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    public EditMessageText editToCategories(Integer messageId, String chatId) {
        EditMessageText backToCategories = new EditMessageText();
        backToCategories.setText("\uD83D\uDCCB Categories");
        backToCategories.setReplyMarkup(categoriesInlineMarkup());
        backToCategories.setMessageId(messageId);
        backToCategories.setChatId(chatId);

        return backToCategories;
    }

    public InlineKeyboardMarkup categoriesInlineMarkup() {
        InlineKeyboardMarkup categoriesMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inlineButtonsRows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        List<FoodType> categories = foodTypeService.read();

        if (categories.size() == 0) {
            return null;
        }
        for (FoodType category : categories) {
            if (category.isActiveFoodType()) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(category.getCategoryName());
                button.setCallbackData(category.getId().toString());

                row.add(button);

                if (row.size() == 2) {
                    inlineButtonsRows.add(row);
                    row = new ArrayList<>();
                }
            }
        }

        if (row.size() != 0) {
            inlineButtonsRows.add(row);
        }

        categoriesMarkup.setKeyboard(inlineButtonsRows);

        return categoriesMarkup;
    }

    public SendMessage getHistoryById(UUID id ,String chatId){
        SendMessage message1 = new SendMessage();
        message1.setChatId(chatId);
        String text = "";
        for (History history : historyService.getHistoryById(id)) {
              text=text+"\n\nDate : "+history.getDate()
                    + "\n Your Phone Number : "+history.getCustomer().getPhoneNumber()
                    +"\n Your orders :" + history.getProduct().getFoodName()
                    +"\n Price :"+history.getProduct().getAmount()
                    +" x "+history.getProduct().getPrice()
                     +"\n\t ***\t***\t***\t***\t";
        }
        text+="\n\n Overall :"+historyService.getTotalPrice(id);
        message1.setText(text);
        return message1;
    }

    public SendMessage addHistory(User user,String chatId) {
        SendMessage message = new SendMessage();
        message.setText("Added history successfully");
        message.setChatId(chatId);
        History history = new History();

        for (Basket basket : basketService.read()) {
            if (basket.getUserId().equals(user.getId())) {
                history.setCustomer(user);
                history.setProduct(basket);
                historyService.add(history);
            }
        }
      return message;
    }

    public EditMessageText foods(String categoryName, Integer messageId, String chatId) {
        EditMessageText editCategoryToProduct = new EditMessageText();
        editCategoryToProduct.setText("==== FOODS ====");
        editCategoryToProduct.setChatId(chatId);
        editCategoryToProduct.setMessageId(messageId);


        UUID categoryId = UUID.randomUUID();

        for (FoodType category : foodTypeService.getAll()) {
            if (category.getCategoryName().equals(categoryName) ||
                    category.getId().toString().equals(categoryName))
                categoryId = category.getCategoryId();
        }
        InlineKeyboardMarkup productMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        int i = 0;

        List<Food> foods = foodService.getByCategoryId(categoryId);

        if (foods.size() == 0) {
            editCategoryToProduct.setText("❗️ NO PRODUCT EXIST IN THIS CATEGORY ❗️");
        } else {
            for (Food product : foods) {
                if (product.getFoodId().equals(categoryId)) {
                    i++;

                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(product.getName()+" ( "+ product.getPrice()+ " sum ) ");
                    button.setCallbackData(product.getName());

                    row.add(button);

                    if (i == 2) {
                        rows.add(row);
                        i = 0;
                        row = new ArrayList<>();
                    }
                }
            }

            if (row.size() != 0) {
                rows.add(row);
            }
        }

        row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("⬅️ back");
        button.setCallbackData("backToCategories");

        row.add(button);

        rows.add(row);

        productMarkup.setKeyboard(rows);
        editCategoryToProduct.setReplyMarkup(productMarkup);

        return editCategoryToProduct;
    }

    public SendMessage getFoodInfo( String data,String chatId) {
        SendMessage info = new SendMessage();
        info.setChatId(chatId);
        info.setText("Description : " +foodService.get(data).getDescription()
                + "\n\nEnter amount? \uD83D\uDD22");
        info.setReplyMarkup(addToCartAmount(foodService.get(data).getFoodId().toString(), data));
        return info;
    }

    public SendMessage productAdded(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "SUCCESSFULLY ADDED ✅");
        sendMessage.setReplyMarkup(productAddedButton());

        return sendMessage;
    }

    public SendPhoto sendFoodPhoto(String foodId, Integer messageId, String chatId) {
        SendPhoto msg = new SendPhoto();
        msg.setChatId(chatId);
        switch (foodId) {
            case "cheesepizza" -> {
                msg.setPhoto(new InputFile(cheesePizzaURL));
                msg.setCaption("Cheese Pizza 32 sm");
            }
            case "Big" -> {
                msg.setPhoto(new InputFile(bigPizzaURL));
                msg.setCaption("Big pizza 42sm");
            }
            case "fri" -> {
                msg.setPhoto(new InputFile(friURL));
                msg.setCaption("Fries");

            }
            case "Nuggets" ->{
                msg.setPhoto(new InputFile(nuggetsURL));
                msg.setCaption("Nuggets");
            }
            case "StreetBurger" -> {
                msg.setPhoto(new InputFile(bigBurgerURL));
                msg.setCaption("Street77 special burger");
            }
            case "cheeseburger" -> {
                msg.setPhoto(new InputFile(cheeseBurgerURL));
                msg.setCaption("Cheese burger");
            }
            case "Longer" ->{
                msg.setPhoto(new InputFile(longerURL));
                msg.setCaption("Longer");
            }
            case "Canadian"->{
                msg.setPhoto(new InputFile(canadianURL));
                msg.setCaption("Canadian hot dog");
            }
            case "Pepsi" ->{
                msg.setPhoto(new InputFile(pepsiURL));
                msg.setCaption("PEPSI");
            }
            case "Cola" ->{
                msg.setPhoto(new InputFile(colaURL));
                msg.setCaption("Coca-cola");
            }
            default -> foods(foodId, messageId, chatId);
        }

        return msg;
    }

    private InlineKeyboardMarkup addToCartAmount(String categoryId,String foodName) {
        InlineKeyboardMarkup amounts = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button;
        for (int i = 1; i <10; i++) {
            button = new InlineKeyboardButton("" + i);
            button.setCallbackData(foodName+" " + i);
            row.add(button);

            if (row.size() == 3) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        amounts.setKeyboard(rows);
        return amounts;
    }

    private InlineKeyboardMarkup productAddedButton() {
        InlineKeyboardMarkup buttons = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("\uD83D\uDED2 My Cart");
        button.setCallbackData("myCart");
        row.add(button);

        button = new InlineKeyboardButton();
        button.setText("\uD83D\uDCCB Back to Categories");
        button.setCallbackData("backToCategories");
        row.add(button);

        rows.add(row);

        buttons.setKeyboard(rows);

        return buttons;
    }

    public SendMessage myCart(String chatId, UUID userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(myCartButtons(basketService.getBasketById(userId)));
        sendMessage.setText(getCartText(basketService.getBasketById(userId)));

        return sendMessage;
    }

    private InlineKeyboardMarkup myCartButtons(List<Basket> myCartList) {
        InlineKeyboardMarkup products = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (Basket cart : myCartList) {
//            InlineKeyboardButton button = new InlineKeyboardButton("➖");
//            button.setCallbackData("decrement");
//            row.add(button);

            InlineKeyboardButton button = new InlineKeyboardButton("❌"+ cart.getFoodName());
            button.setCallbackData(cart.getFoodName());
            row.add(button);

//            button = new InlineKeyboardButton("➕");
//            button.setCallbackData("increment");
//            row.add(button);

            rows.add(row);
            row = new ArrayList<>();
        }
        InlineKeyboardButton button1 = new InlineKeyboardButton("✔ Order now");
        button1.setCallbackData("Order now");
        row.add(button1);
        rows.add(row);

        products.setKeyboard(rows);
        return products;
    }

    public SendMessage deleteFromCart(String data, Integer messageId, String chatId, UUID id) {
        SendMessage info = new SendMessage();
        info.setChatId(chatId);
        info.setText(basketService.removeByName(data,id));
        return info;
    }

    private String getCartText(List<Basket> myBasketList) {
        String text = "\uD83D\uDCDC FOODS IN YOUR CART \uD83D\uDCDC\n\n";
        BigDecimal overall = BigDecimal.valueOf(0);
        //📜📌💸

        for (Basket cart : myBasketList) {
            text = text + "✔" + cart.getFoodName() + "\n  |  " + cart.getAmount()+" x " + cart.getPrice()
                    + "  =  \uD83D\uDCB8" + (cart.getPrice().multiply(BigDecimal.valueOf(cart.getAmount())))
                    + "\n***\t***\t***\n";
            overall = overall.add(cart.getPrice().multiply(BigDecimal.valueOf(cart.getAmount())));
        }

        text += "\nOverall: " + overall;
        return text;
    }


    @SneakyThrows
    public SendMessage sharePhoneNumber(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Welcome to Street77, \nPlease, Sign up to continue\uD83D\uDE0A\nSend your phone number");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setText("Share your phone number 📞 ->");
        button.setRequestContact(true);

        keyboardRow.add(button);
        keyboard.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return sendMessage;
    }

    @SneakyThrows
    public SendMessage shareLocation(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Send your location >");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setText("Share location 🗺️ ->");
        button.setRequestLocation(true);

        keyboardRow.add(button);
        keyboard.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return sendMessage;
    }
}
