package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.FoodType;
import repository.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FoodTypeService implements BaseService<FoodType, List<FoodType>,String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void write(List<FoodType> foodTypeList) {
        try {
            File file = new File("foodTypeList.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, foodTypeList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Writing error " + e.toString());
        }

    }

    @Override
    public List<FoodType> read() {
        List<FoodType> list = new ArrayList<>();
        try {
            list = objectMapper.readValue(new File("foodTypeList.json"),
                    new TypeReference<ArrayList<FoodType>>() {
                    });
        }catch (Exception | NoClassDefFoundError e){
            e.printStackTrace();
        }
        return  list;
    }

    @Override
    public String add(FoodType foodType) {
        List<FoodType> categoryList = read();
        categoryList.add(foodType);
        write(categoryList);
        return SUCCESS;
    }

    @Override
    public FoodType getById(UUID id) {
        return null;
    }

    @Override
    public List<FoodType> getAll() {
        List<FoodType> categoryList = read();
        return categoryList;
    }

    @Override
    public String check(String t) {
        List<FoodType> categoryList = read();
        for(FoodType f:categoryList) {
            if(f.getCategoryName().equals(t)) {
                return FOOD_CATEGORY_ALREADY_EXIST;
            }
        }
        return SUCCESS;
    }

    public boolean checkIsActive(String t) {
        List<FoodType> categoryList = read();
        for(FoodType f:categoryList) {
            if(f.getCategoryName().equals(t)) {
                return f.isActiveFoodType();
            }
        }
        return false;
    }

    public FoodType checkFood(String t) {
        List<FoodType> categoryList = read();
        for(FoodType f:categoryList) {
            if(f.getCategoryName().equals(t)) {
                return f;
            }
        }
        return null;
    }
    public String deleteByName(String name) {
        List<FoodType> list = read();
        boolean b=false;
        for(FoodType foodType:list) {
            if(foodType.getCategoryName().equals(name)) {
                foodType.setActiveFoodType(false);
                b=true;
                break;
            }
        }
        if(b) {
            write(list);
            return SUCCESS;
        }
        return "ERROR";
    }

    public String editByName(String oldName, String newName) {
        List<FoodType> list = read();
        for(FoodType foodType:list) {
            if(foodType.getCategoryName().equals(oldName)) {
                foodType.setCategoryName(newName);
                write(list);
                return SUCCESS;
            }
        }
        return "ERROR";
    }

}