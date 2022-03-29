package response;

public interface BaseResponse {
    String ERR_ACCESS_PASS = "=============Wrong password!=============";
    String ERR_USER_ALREADY_EXISTS = "=============User already exists!=============";
    String ERR_USER_NOT_FOUND = "=============User not found!=============";
    String ERR_CARD_NOT_FOUND = "=============Card not found!=============";
    String ERR_BASKET_IS_EMPTY = "=============Basket not found!=============";
    String WELCOME_SUPER_ADMIN = """
            Success
            Welcome super admin
            """;
    String SUCCESS = "Success";
    String ERROR ="Wrong input";
    String USER_LOGIN_MENU = """
            1->Sign in
            2->Sign up
            0.Exit
            """;
    String USER_MENU = """
            1->Login with email
            2->Login with phone number
            """;
    String ENTER_EMAIL = "Enter your email: ";
    String ENTER_EMAIL_PASS = "Enter your email password: ";
    String NO_SUCH_FOOD_IN_BASKET = "No such food in basket";
    String ENTER_PHONE_NUMBER = "Enter your phone number: ";
    String ENTER_PHONE_PASSWORD = " Enter your password: ";
    String ENTER_SMS_CODE = "Enter sms code: ";
    String FOOD_ALREADY_EXIST = "Food is already exist";
    String USER_MAIN_MENU = """
            1.Menu
            2.Basket
            0.Exit
            """;
    String FOOD_NOT_FOUND = "Food not found";
    String ERR_FOOD_ALREADY_EXISTS = "Food already exists!";
    String ENTER_NEW_FOOD_NAME = "Enter new food name: ";
    String ENTER_FOOD_NAME = "Enter food name: ";
    String ENTER_FOOD_PRICE = "Enter food price: ";
    String ENTER_FOOD_DESCRIPTION = "Enter food description: ";
    String FOOD_CATEGORY_ALREADY_EXIST = "Food category is already exist";
    String FOOD_CATEGORY_NOT_FOUND = "Food category not found";
    String ENTER_FOOD_CATEGORY_NAME = "Enter food category name: ";
    String ENTER_FOOD_NEW_CATEGORY_NAME = "Enter new food category name: ";
    String PAY_TYPE_MENU = "1.By Plastic Card\t2.By Cash \t0.Back";
    String CHOOSE_PAYMENT_SERVICE= "1.CLICK\t2.PAYME \t0.Back";
    String ADD_CARD_NUMBER = "Add your card number :";
    String ADD_CARD_PASSWORD = "Enter your card password : ";
    String BASKET_MENU ="1.Checkout\t2.Remove food\t0.Back";
    String BY_CARD_RESPONSE = "You chose payment by card\n" +
            "Our courier will connect with you soon\n" +
            "Be patient\n" +
            "Wait us\n";
    String BY_CASH_RESPONSE ="You chose payment by cash\n" +
            "Our courier will connect with you soon\n" +
            "Be patient\n" +
            "Wait us\n";
    String SUPER_ADMIN_MAIN_MENU = """
            1->CRUD FOOD CATEGORY
            2->CRUD FOOD
            3->USER lIST
            4->ORDER HISTORY
            5->BALANCE
            0->EXIT
            """;
    String S_A_CRUD_FOOD = """
            1->CREATE NEW FOOD
            2->SHOW FOOD LIST
            3->EDIT FOOD
            4->DELETE FOOD
            0->EXIT
            """;
    String S_A_CRUD_FOOD_TYPE = """
            1->CREATE NEW FOOD TYPE
            2->SHOW FOOD TYPE LIST
            3->EDIT FOOD TYPE
            4->DELETE FOOD TYPE
            0->EXIT
            """;
//    String FOOD_NOT_FOUND = "Food not found";
//    String ENTER_NEW_FOOD_NAME = "Enter new food name: ";
//    String ENTER_FOOD_NAME = "Enter food name: ";
//    String ENTER_FOOD_PRICE = "Enter food name: ";
//    String ENTER_FOOD_DESCRIPTION = "Enter food description: ";
//    String FOOD_CATEGORY_ALREADY_EXIST = "Food category is already exist";
//    String FOOD_CATEGORY_NOT_FOUND = "Food category not found";
//    String ENTER_FOOD_CATEGORY_NAME = "Enter new food category name: ";
//    String ENTER_FOOD_NEW_CATEGORY_NAME = "Enter new food category name: ";
//    String REGISTRATION_MENU = "";
//    String SUPER_ADMIN_MAIN_MENU = """
//            1->CRUD FOOD CATEGORY
//            2->CRUD FOOD
//            3->USER lIST
//            4->ORDER HISTORY
//            5->BALANCE
//            0->EXIT""";
//    String S_A_CRUD_CAT_FOOD = """
//            1->CREATE NEW FOOD
//            2->SHOW FOOD LIST
//            3->EDIT FOOD
//            4->DELETE FOOD
//            0->EXIT""";
}

