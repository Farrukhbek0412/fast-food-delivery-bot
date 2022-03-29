package front;

import model.Basket;
import model.Food;
import model.History;
import model.user.User;
import model.user.UserRole;
import services.BasketService;
import services.HistoryService;
import services.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

import static front.SuperAdminFront.*;
import static response.BaseResponse.*;

public class UserFront {
    static UserService userService = new UserService();
    static BasketService basketService = new BasketService();
    static HistoryService historyService = new HistoryService();
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);

    public static void UserLogin() {
        int stepCode = 100;
        System.out.println(USER_LOGIN_MENU);
        while (stepCode != 0) {
            try{
                stepCode = scannerInt.nextInt();
            }catch (Exception e){
                e.printStackTrace();
            }
            switch (stepCode) {
                case 1 -> {
                    loginUser();
                }

                case 2 -> {
                    System.out.println("Enter phone number : +998 xx- xxx-xx-xx");
                    String phoneNumber = scannerStr.nextLine();
                    boolean isValidPhoneNumber = phoneNumber.length() == 9;
                    if (isValidPhoneNumber) {
                        if (userService.checkUserByNumber(phoneNumber)) {
                            System.out.println("You have already signed up! Please, sign in");
                        } else {
                            User user = new User();
                            user.setPhoneNumber(phoneNumber);
                            user.setSmsCode();
                            System.out.println(ENTER_SMS_CODE + user.getSmsCode() + ". \nDon't allow to see anybody else");
                            int smsCode = scannerInt.nextInt();
                            if (smsCode == user.getSmsCode()) {
                                System.out.println("Verified successfully ✔");
                                userService.add(signUp(user, scannerStr));
                            } else System.out.println("wrong sms code");

                            UserLogin();
                        }
                    } else System.out.println("Please enter valid phone number");
                }
                default -> System.out.println(("Unexpected value: " + stepCode));
            }
        }
        UserLogin();
    }

    public static void loginUser() {
        System.out.println(USER_MENU);
        int stepCode0 = scannerInt.nextInt();
        switch (stepCode0) {
            case 1 -> {
                loginWithEmail();
            }
            case 2 -> {
                loginWithPhoneNumber();
            }
            default -> {
                UserLogin();
            }
        }
    }

    public static void loginWithEmail() {
        System.out.println(ENTER_EMAIL);
        String email = scannerStr.nextLine();
        boolean check = userService.checkEmail(email);
        if (check) {
            System.out.print(ENTER_EMAIL_PASS);
            String tempPass = scannerStr.nextLine();
            User tempUser = userService.loginWithEmail(email, tempPass);
            if (tempUser != null && userService.isAdminCheckByEmail(email, tempPass)) {
                superAdminFront();
            }
            if (tempUser != null && !userService.isAdminCheckByEmail(email, tempPass)) {
                userFront(tempUser);
            } else {
                System.out.println(ERR_ACCESS_PASS);
                UserLogin();
            }
        } else {
            System.out.println(ERR_USER_NOT_FOUND);
            UserLogin();
        }
    }

    public static void loginWithPhoneNumber() {
        System.out.println(ENTER_PHONE_NUMBER);
        String number = scannerStr.nextLine();
        boolean check = userService.checkPhoneNumber(number);
        if (check) {
            System.out.print(ENTER_PHONE_PASSWORD);
            String tempPass = scannerStr.nextLine();
            User tempUser = userService.loginWithNumber(number, tempPass);
            if (tempUser != null && userService.isAdminCheckByNumber(number, tempPass)) {
                superAdminFront();
            }
            if (tempUser != null && !userService.isAdminCheckByNumber(number, tempPass)) {
                userFront(tempUser);
            } else {
                System.out.println(ERR_ACCESS_PASS);
                UserLogin();
            }
        } else {
            System.out.println(ERR_USER_NOT_FOUND);
            UserLogin();
        }
    }

    public static User signUp(User user, Scanner scannerStr) {
        System.out.println("Your FullName: ");
        user.setFullName(scannerStr.nextLine());
        System.out.println("Enter email");
        user.setEmail(scannerStr.nextLine());
        System.out.println("Create password: ");
        String password1 = scannerStr.nextLine();
        user.setUserRole(UserRole.USER);

 //       if (passwordCheck(password1)) {
            user.setPassword(password1);
//        } else {
//            System.out.println("Password is invalid");
//        }
        System.out.println("Enter your current address : Ex:(Region/City/Street/Home");
        user.setAddress(scannerStr.nextLine());
        System.out.println(SUCCESS);
        return user;
    }

//    public static boolean passwordCheck(String password1) {
//        System.out.println("Verify your password");
//        String password2 = scannerStr.nextLine();
//        if (password2.equals(password1)) {
//            return true;
//        } else {
//            System.out.println("Password is not valid, Please check and try again!");
//            passwordCheck(password1);
//        }
//        return false;
//    }

    private static void userFront(User user) {
        System.out.println(USER_MAIN_MENU);
        int stepcode1 = 3;

        stepcode1 = scannerInt.nextInt();
        switch (stepcode1) {
            case 1 -> {
                userMenu(user);
            }
            case 2 -> {
                basket(user);
            }

            default -> {
                System.out.println(("Unexpected value: " + stepcode1));
                UserLogin();
            }
        }

    }

    public static void basket(User user) {
        List<Basket> tempBasketList = (basketService.getBasketById((user.getId())));
        System.out.println(tempBasketList.toString());
        System.out.println(basketService.getTotalPrice(user));
        System.out.println("|>");
        int stepCode = 100;

            System.out.println(BASKET_MENU);
            stepCode = scannerInt.nextInt();
            switch (stepCode) {
                case 2 -> {
                    System.out.println(tempBasketList);
                    System.out.println("|>");

                    System.out.println(ENTER_FOOD_NAME);
                    basketService.removeByName(scannerStr.nextLine(),user.getId());
                }
                case 1 -> {
                    payType(user, tempBasketList);
                }
                case 0 -> userFront(user);
                default -> basket(user);

            }



    }

    public static void payType(User user, List<Basket> tempBasket) {
        System.out.println(tempBasket);
        System.out.println("|>");
        System.out.println(PAY_TYPE_MENU);
        switch (scannerInt.nextInt()) {
            case 1 -> {
                paymentService(user, tempBasket);
            }
            case 2 -> {
                System.out.println("Do you want to order now (Yes,No) :");
                if (scannerStr.nextLine().equals("Yes")) {
                    addHistory(user, tempBasket);

                    System.out.println(basketService.clearBasket(user.getId()));
                    System.out.println(BY_CASH_RESPONSE);
                    userFront(user);
                } else
                    payType(user, tempBasket);
            }
            case 0 -> {
                basket(user);
            }
            default -> {
                System.out.println("Unexpected value, Please enter correctly");
                payType(user, tempBasket);
            }
        }
    }

    public static void paymentService(User user, List<Basket> tempBasket) {
        System.out.println(CHOOSE_PAYMENT_SERVICE);
        switch (scannerInt.nextInt()) {

            case 1:
            case 2: {
                System.out.println(ADD_CARD_NUMBER);
                String cardNumber = scannerStr.nextLine();
                boolean checkNumber = historyService.isValidNumber(cardNumber);
                if (checkNumber) {
                    System.out.println(ADD_CARD_PASSWORD);
                    String password = scannerStr.nextLine();
                    System.out.println("Do you want to pay now(Yes,No) : " + basketService.getTotalPrice(user));
                    if (scannerStr.nextLine().equals("Yes")) {
                        System.out.println(BY_CARD_RESPONSE);
                        addHistory(user, tempBasket);
                        System.out.println(basketService.clearBasket(user.getId()));
                        userFront(user);
                    } else {
                        paymentService(user, tempBasket);
                    }
                } else System.out.println("Unexpected number, Please enter correctly");
            }
            case 0: {
                paymentService(user, tempBasket);
            }
            default:
                System.out.println("Unexpected value, Please enter correctly");

        }
    }

    public static void addHistory(User user, List<Basket> tempBasket) {
        for (Basket basket : tempBasket) {
            History history = new History();
            history.setCustomer(user);
            history.setProduct(basket);
            //history.setDate(LocalDate.now());
            historyService.add(history);
        }

    }

    public static void userMenu(User user) {
        showFoodCategoriesForFood();
        System.out.println("0-> Exit");
        int stepCode = scannerInt.nextInt();
        if (stepCode == 0) userFront(user);
        UUID tempCategoryId = getFoodTypeByIndex(stepCode);
        List<Food> foodList = foodService.getAll();
        int foodListLength = showFoodList(tempCategoryId, foodList);
        stepCode = scannerInt.nextInt();
        if (stepCode == 0) userMenu(user);
        else if (stepCode > foodListLength) {
            System.out.println("Unexpected value, Please enter correctly");
            userMenu(user);
        } else {
            if (getFoodByIndex(stepCode,tempCategoryId) == null) {
                System.out.println("Unexpected value, Please enter correctly");
                userMenu(user);
            } else {
                Food tempFood = getFoodByIndex(stepCode,tempCategoryId);
                System.out.print("Enter Food count: ");
                int foodCount = scannerInt.nextInt();
                System.out.println(SUCCESS);
                Basket basket1 = new Basket();
                basket1.setBasketId(user.getId());
                basket1.setAmount(foodCount);
                basket1.setFoodName((Objects.requireNonNull(tempFood)).getName());
                basket1.setPrice(tempFood.getPrice().multiply(BigDecimal.valueOf(foodCount)));
                basketService.add(basket1);
                userMenu(user);
            }
        }
    }

    public static Food getFoodByIndex(int index,UUID categoryId) {
        int count = 1;
        for (Food food : foodService.read()) {
            if (food.isActiveFood() && food.getFoodId().equals(categoryId)) {
                if (count == index) {
                    return food;
                }
                count++;
            }
        }
        return null;
    }

    public static int showFoodList(UUID categoryId, List<Food> foodList) {
        int index = 1;
        for (Food food : foodList) {
            if (food.getFoodId().equals(categoryId)) {
                System.out.println("{\n" + index + ") Food name:" + food.getName() + "\nFood price: "
                        + food.getPrice() + "\nDescription: " + food.getDescription() + "\n}");
                ;
                index++;
            }
        }
        return index-1;
    }


}
