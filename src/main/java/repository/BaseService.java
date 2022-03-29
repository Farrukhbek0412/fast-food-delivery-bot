package repository;

import model.Basket;
import model.Food;
import model.FoodType;
import response.BaseResponse;

import java.util.UUID;

public interface BaseService<T,L,R> extends BaseResponse {


    void write(L l);
    L read();



    R add(T t);
    T getById(UUID id);
    L getAll();
    R check(R t);
}
