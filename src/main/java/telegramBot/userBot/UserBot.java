package telegramBot.userBot;

import telegramBot.service.UserBotService;
import telegramBot.service.adminBotService;
import model.Basket;
import model.Food;
import model.user.User;
import model.user.UserRole;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;

public class UserBot extends TelegramLongPollingBot implements UserInterface {
    public HashMap<Integer, String> categoryMessage = new HashMap<>();
    public HashMap<Integer, String> productMessage = new HashMap<>();


    UserBotService userBotService = new UserBotService();

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            System.out.println("Xabar keldi");
            Message message = update.getMessage();
            String chatId = update.getMessage().getChatId().toString();
            User currentUser = userService.loginByChatId(chatId);
            UserState userState = UserState.MAIN_MENU;
            if (message.hasText() && !message.getText().equals("/start")) {
                String text = message.getText();

                switch (text) {
                    case "📋 Categories" -> userState = UserState.CATEGORIES;
                    case "\uD83D\uDED2 My Cart" -> userState = UserState.MY_CART;
                    case "📋 History" -> {
                        try {
                            execute(userBotService.getHistoryById(currentUser.getId(), chatId));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        currentUser.setUserState(UserState.MAIN_MENU);
                        userService.editByChatId(chatId, currentUser);
                    }
                }
                switch (userState) {
                    case CATEGORIES -> {
                        currentUser.setUserState(UserState.FOODS);
                        userService.editByChatId(chatId, currentUser);
                        try {
                            execute(userBotService.categories(chatId));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }

                }

            } else if (message.hasContact()) {
                userState = UserState.START_CONTACT_SHARED;
            } else if (message.hasLocation()) {
                userState = UserState.START_LOCATION;
            } else if (currentUser == null) {
                userState = UserState.START_NEW_USER;
            } else if (currentUser.getLocation() == null) {
                try {
                    execute(userBotService.shareLocation(chatId));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                return;
            } else
                userState = currentUser.getUserState();
            switch (userState) {
                case START_NEW_USER -> {
                    try {
                        execute(userBotService.sharePhoneNumber(chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case START_CONTACT_SHARED -> {
                    Contact contact = message.getContact();

                    User user = new User();
                    user.setUserRole(UserRole.USER);
                    user.setPhoneNumber(contact.getPhoneNumber());
                    user.setChatId(chatId);
                    user.setFullName((contact.getFirstName()!=null) ? contact.getFirstName() : ""+" "+((contact.getLastName()!=null)? contact.getLastName() : ""));
                    user.setUserState(UserState.START_LOCATION);
                    userService.add(user);
                    try {
                        execute(userBotService.shareLocation(chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case START_LOCATION -> {
                    Location location = message.getLocation();
                    currentUser.setLocation(location);
                    currentUser.setUserState(UserState.MAIN_MENU);
                    userService.editByChatId(chatId, currentUser);

                    try {
                        execute(userBotService.userMainMenu(chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
                case MAIN_MENU -> {
                    try {
                        execute(userBotService.userMainMenu(chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case MY_CART -> {
                    try {
                        execute(userBotService.myCart(chatId, currentUser.getId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + userState);
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();

            String data = callbackQuery.getData();
            Integer messageId = callbackQuery.getMessage().getMessageId();
            String chatId = callbackQuery.getMessage().getChatId().toString();
            User currentUser = userService.loginByChatId(chatId);

            UserState userState = currentUser.getUserState();

            if (data.equals("backToCategories")) {
                userState = UserState.CATEGORIES;

            } else if (data.equals("backToProductList")) {
                userState = UserState.FOODS;
                data = categoryMessage.get(messageId);
            } else if (data.equals("myCart")) {
                userState = UserState.MY_CART;
            }
            switch (userState) {
                case FOODS -> {
                    try {
                        categoryMessage.put(messageId, data);
                        currentUser.setUserState(UserState.FOOD_INFO);
                        userService.editByChatId(currentUser.getChatId(), currentUser);
                        execute(userBotService.foods(data, messageId, chatId));


                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case CATEGORIES -> {
                    currentUser.setUserState(UserState.FOODS);
                    userService.editByChatId(chatId, currentUser);
                    try {
                        execute(userBotService.editToCategories(messageId, chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
                case FOOD_INFO -> {

                    productMessage.put(messageId, data);
                    currentUser.setUserState(UserState.ADD_TO_CART);
                    userService.editByChatId(chatId, currentUser);

                    try {
                        execute(userBotService.sendFoodPhoto(data, messageId, chatId));
                        execute(userBotService.getFoodInfo(data, chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case ADD_TO_CART -> {
                    String FoodName = data.split(" ")[0];
                    int amount = Integer.parseInt(data.split(" ")[1]);
                    Food food = foodService.checkByName(FoodName);
                    Basket myCart = new Basket();
                    if (!basketService.isExistFood(food.getName())) {
                        myCart.setPrice(food.getPrice());
                        myCart.setUserId(currentUser.getId());
                        myCart.setFoodName(food.getName());
                        myCart.setAmount(amount);
                        myCart.setBasketId(food.getFoodId());
                        basketService.add(myCart);
                    } else {
                        List<Basket> tempBasket = basketService.getBasketById(currentUser.getId());
                        for (Basket basket : tempBasket) {
                            if (basket.getFoodName().equals(food.getName()))
                                basket.setAmount(basket.getAmount() + amount);
                        }
                        basketService.write(tempBasket);
                    }

                    currentUser.setUserState(UserState.MY_CART);
                    userService.editByChatId(chatId, currentUser);
                    try {
                        execute(userBotService.foods(categoryMessage.get(messageId), messageId, chatId));
                        execute(userBotService.productAdded(chatId));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
                case MY_CART -> {
                    currentUser.setUserState(UserState.CHANGE_PRODUCT);
                    userService.editByChatId(chatId, currentUser);
                    try {
                        execute(userBotService.myCart(chatId, currentUser.getId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case CHANGE_PRODUCT -> {

                    if (!data.equals("Order now")) {
                        try {
                            execute(userBotService.deleteFromCart(data, messageId, chatId, currentUser.getId()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        try {
                            execute(userBotService.myCart(chatId, currentUser.getId()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    if (data.equals("Order now")) {
                        try {
                            execute(userBotService.addHistory(currentUser, chatId));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        basketService.clearBasket(currentUser.getId());
                        SendMessage message = new SendMessage();
                        message.setText("✔✔ \nYour order accepted, Please wait a minute, We will call you\n");
                        message.setChatId(chatId);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}