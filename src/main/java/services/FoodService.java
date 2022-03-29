package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Basket;
import model.Food;
import repository.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoodService implements BaseService<Food, List<Food>, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void write(List<Food> foodList) {
        try {
            File file = new File("foodList.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, foodList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Writing error " + e.toString());
        }
    }

    @Override
    public List<Food> read() {
        List<Food> list = new ArrayList<>();
        try {
            list = objectMapper.readValue(new File("foodList.json"),
                    new TypeReference<ArrayList<Food>>() {
                    });
        }catch (Exception | NoClassDefFoundError e){
            e.printStackTrace();
        }
        return  list;
    }

    @Override
    public String add(Food food) {
        List<Food> foodList = read();
        foodList.add(food);
        write(foodList);
        return SUCCESS;
    }

    @Override
    public Food getById(UUID id) {
        List<Food> foodList = read();
        for (int i = 0; i < foodList.size(); i++) {
            if(foodList.get(i).getId().equals(id)){
                return foodList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Food> getAll() {
        return read();
    }

    @Override
    public String check(String t) {
        return null;
    }

    public Food get(String name) {
        for (Food product : read()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }

        return null;
    }

    public Food checkByName(String foodName) {
        List<Food> foodList = read();
        for (Food food:foodList) {
            if(food.getName().equals(foodName))
                return food;
        }
        return null;
    }

    public Food checkFood(String foodName) {
        List<Food> foodList = read();
        for (Food food:foodList) {
            if(food.getName().equals(foodName))
                return food;
        }
        return null;
    }
    public String isExistFood(String foodName){
        List<Food> foodList = read();
        for (Food food:foodList) {
            if(food.getName().equals(foodName))
                return foodName;
        }
        return null;
    }

    public List<Food> getByCategoryId(UUID id) {
        List<Food> foodList = read();
        List<Food> response = new ArrayList<>();
        for(Food food:foodList) {
            if(food.getFoodId().equals(id)) {
                response.add(food);
            }
        }
        return response;
    }

}