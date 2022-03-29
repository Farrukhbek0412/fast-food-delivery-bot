package front;

import model.Food;
import model.FoodType;
import model.History;
import model.user.User;
import model.user.UserRole;
import services.FoodService;
import services.FoodTypeService;
import services.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static front.UserFront.historyService;
import static response.BaseResponse.*;


public class SuperAdminFront {
    static UserService userService = new UserService();
    static FoodTypeService foodTypeService = new FoodTypeService();
    static FoodService foodService = new FoodService();
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);
    static User user = new User();
    static History history = new History();

    public static void superAdminFront() {
        System.out.print(SUPER_ADMIN_MAIN_MENU);
        switch (scannerInt.nextInt()) {
            case 1 -> {
                crudFoodCategory();
            }
            case 2 -> {
                crudFood();
            }
            case 3 -> {
                List<User> temp = userService.getAll();
                for (User user : temp) {
                    if (user.getUserRole() == UserRole.USER)
                        System.out.println(user.toString());
                }
                superAdminFront();
            }
            case 4 -> {
                System.out.println("Order history : ");
                String text = "";
                for (History history : historyService.getAll()) {
                    text=text+"\n\nDate : "+history.getDate().toString()
                            + "\n Phone Number : "+history.getCustomer().getPhoneNumber()
                            +"\n Orders :" + history.getProduct().getFoodName()
                            +"\n Amount :"+history.getProduct().getAmount()
                            +"\t Price : "+history.getProduct().getPrice()
                            +"\n\t ***\t***\t***\t***\t";
                }
                text+="\n\n Overall :"+historyService.getBalance();
                System.out.println(text);
                superAdminFront();
            }
            case 5 -> {
                System.out.println("Balance = "+historyService.getBalance());

                    superAdminFront();

            }
        }
    }

    public static void crudFoodCategory() {
        System.out.println(S_A_CRUD_FOOD_TYPE);
        int stepCode = 0;
        try {
            stepCode = scannerInt.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (stepCode) {
            case 1 -> {
                createFoodCategory();
                break;
            }
            case 2 -> {
                showFoodCategories();
                break;
            }
            case 3 -> {
                editFoodCategory();
                break;
            }
            case 4 -> {
                deleteFoodCategory();
                break;
            }
            default -> {
                superAdminFront();
                break;
            }
        }
    }

    public static void createFoodCategory() {
        System.out.print(ENTER_FOOD_CATEGORY_NAME);
        String tempName = scannerStr.nextLine();
        FoodType tempFoodType = foodTypeService.checkFood(tempName);
        if (tempFoodType != null && tempFoodType.isActiveFoodType()) {
            System.out.println(FOOD_CATEGORY_ALREADY_EXIST);
        } else if (tempFoodType != null && !tempFoodType.isActiveFoodType()) {
            tempFoodType.setActiveFoodType(true);
            System.out.println(SUCCESS);
            System.out.println();
        } else {
            FoodType foodType = new FoodType();
            foodType.setCategoryName(tempName);
            foodType.setActiveFoodType(true);
            System.out.println(foodTypeService.add(foodType));
        }
        superAdminFront();
    }

    public static void showFoodCategories() {
        List<FoodType> list = foodTypeService.getAll();
        int index = 1;
        for (FoodType f : list) {
            if (f.isActiveFoodType()) {
                System.out.println((index + ")   Food category name: " + f.getCategoryName()));
                index++;
            }

        }
        superAdminFront();
    }

    public static void showFoodCategoriesForFood() {
        List<FoodType> list = foodTypeService.getAll();
        int index = 1;
        for (FoodType f : list) {
            if (f.isActiveFoodType()) {
                System.out.println((index + ")   Food category name: " + f.getCategoryName()));
                index++;
            }

        }
    }


    public static void editFoodCategory() {
        System.out.println(ENTER_FOOD_CATEGORY_NAME);
        String tempCategoryName = scannerStr.nextLine();
        System.out.println("Enter new category name: ");
        String newName = scannerStr.nextLine();
        System.out.println(foodTypeService.editByName(tempCategoryName, newName));
        superAdminFront();

    }

    public static void deleteFoodCategory() {
        System.out.println(ENTER_FOOD_CATEGORY_NAME);
        String tempCategoryName = scannerStr.nextLine();
        System.out.println(foodTypeService.deleteByName(tempCategoryName));

        superAdminFront();
    }

    public static void crudFood() {
        System.out.println(S_A_CRUD_FOOD);
        int stepCode = scannerInt.nextInt();
        switch (stepCode) {
            case 1 -> {
                createFood();
            }
            case 2 -> {
                showFoods();
            }
            case 3 -> {
                editFood();
            }
            case 4 -> {
                deleteFood();
            }
            default -> {
                superAdminFront();
            }
        }
    }

    public static void createFood() {
        Food newFood = new Food();
        System.out.print(ENTER_NEW_FOOD_NAME);
        String tempName = scannerStr.nextLine();
        if (foodService.isExistFood(tempName) != null) {
            System.out.println(ERR_FOOD_ALREADY_EXISTS);
        } else {
            newFood.setName(tempName);
            linkFoodWithFoodType(newFood);
            setNewFoodPrice(newFood);

            System.out.println("Enter Food description: ");
            String tempDescription = scannerStr.nextLine();
            newFood.setDescription(tempDescription);
            System.out.println(foodService.add(newFood));
        }
        superAdminFront();
    }

    public static String setNewFoodPrice(Food newFood) {
        System.out.println("Enter food price: ");
        BigDecimal tempPrice = BigDecimal.valueOf(0);
        try {
            tempPrice = scannerInt.nextBigDecimal();
        } catch (Exception e) {
            e.printStackTrace();
            setNewFoodPrice(newFood);
        }
        newFood.setPrice(tempPrice);
        return SUCCESS;
    }

    public static String linkFoodWithFoodType(Food newFood) {
        showFoodCategoriesForFood();
        System.out.println("Qaysi kategoriyaga qoshmoqchisan: ");
        int stepCode = scannerInt.nextInt();
        UUID tempFoodTypeId = getFoodTypeByIndex(stepCode);
        if (tempFoodTypeId != null) {
            newFood.setFoodId(tempFoodTypeId);
        } else {
            System.out.println("Xato raqam!");
            linkFoodWithFoodType(newFood);
        }
        return SUCCESS;
    }

    public static UUID getFoodTypeByIndex(int index) {
        List<FoodType> tempList = foodTypeService.getAll();
        int count = 1;
        for (FoodType foodType : tempList) {
            if (foodType.isActiveFoodType()) {
                if (count == index) {
                    return foodType.getCategoryId();
                }

                count++;
            }
        }
        return null;
    }

    public static void showFoods() {
        int index = 1;
        for (Food f : foodService.read()) {
            for (FoodType foodType : foodTypeService.read()) {
                if (foodType.getCategoryId().equals(f.getFoodId())) {
                    System.out.println(index + ")  Category :" + foodType.getCategoryName());
                }
            }
            if (f.isActiveFood()) {
                System.out.println(" Name: " + f.getName() + "\n" + f.getDescription() + "\nPrice: " + f.getPrice());
            }
            index++;
        }
        crudFood();
    }

    public static void editFood() {
        System.out.println(ENTER_FOOD_NAME);
        String tempFoodName = scannerStr.nextLine();
        Food tempFood = foodService.checkFood(tempFoodName);
        if (tempFood == null || !tempFood.isActiveFood()) {
            System.out.println(FOOD_NOT_FOUND);
        } else if (tempFood.isActiveFood()) {
            System.out.println(tempFood);
            System.out.println(ENTER_NEW_FOOD_NAME);
            String tempNewName = scannerStr.nextLine();
            tempFood.setName(tempNewName);
            System.out.println(ENTER_FOOD_PRICE);
            BigDecimal tempPrice = scannerInt.nextBigDecimal();
            tempFood.setPrice(tempPrice);
            System.out.println(ENTER_FOOD_DESCRIPTION);
            String tempDescription = scannerStr.nextLine();
            tempFood.setDescription(tempDescription);
            System.out.println();
        }
        crudFood();
    }

    public static void deleteFood() {
        System.out.println(ENTER_FOOD_NAME);
        String tempFoodName = scannerStr.nextLine();
        Food tempFood = foodService.checkFood(tempFoodName);
        if (tempFood != null && tempFood.isActiveFood()) {
            tempFood.setActiveFood(false);
            System.out.println(SUCCESS);
        } else {
            System.out.println(FOOD_CATEGORY_NOT_FOUND);
        }
        superAdminFront();
    }

}