package telegramBot.adminBot;

import model.Food;
import services.*;

public interface AdminInterface {
    BasketService basketService = new BasketService();
    FoodService foodService = new FoodService();
    FoodTypeService foodTypeService = new FoodTypeService();
    HistoryService historyService = new HistoryService();
    UserService userService = new UserService();
    Food food = new Food();

    String ADMIN_USERNAME = "https://t.me/fastFood_admin_bot";
    String ADMIN_TOKEN = "5085649353:AAE_TEMh52rKm-miGLDsBe61c7nv65Gnpq4";
    String ADMIN_PASSWORD = "Farrux";

    String historyListURL = "C:/Users/msi/Desktop/e-commerse1/historyList.xls";
    String userListURL = "C:/Users/msi/Desktop/e-commerse1/userList.xls";
    //admin hasMessage
    String ADMIN_USER_LIST = "user list";
    String ADMIN_FOOD_LIST = "food list";
    String ADMIN_HISTORY = "all histories";
    String ADMIN_BALANCE = "admin balance";
    String ADMIN_CHECK = "/admin";
    String ADMIN_START = "/start";
    String ADMIN_CATEGORY = "category list";
    String ADMIN_BACK = "back";
}
