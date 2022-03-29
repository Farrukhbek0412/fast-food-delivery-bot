package telegramBot.userBot;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public enum UserState {
    START_NEW_USER,
    START_CONTACT_SHARED,
    START_LOCATION,
    MAIN_MENU,
    CATEGORIES,
    FOODS,
    FOOD_INFO,
    ADD_TO_CART,
    MY_CART,
    CHANGE_PRODUCT;
}