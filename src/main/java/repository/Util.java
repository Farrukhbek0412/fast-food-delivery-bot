package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Util<T>{

    static ObjectMapper objectMapper = new ObjectMapper();

    public List<T> read(String s){
        List<T> list = new ArrayList<>();
        try {
            list = objectMapper.readValue(new File("C:\\Users\\Arslonboy\\IdeaProjects\\e-commerse\\src\\main\\java\\users.json"),
                    new TypeReference<ArrayList<T>>() {
            });
        }catch (Exception | NoClassDefFoundError e){
            e.printStackTrace();
        }
        return  list;
    }

    public void write(String s,T t){
        List<T> list = read(s);

        list.add(t);
        try {
            objectMapper.writeValue(new File("C:\\Users\\Arslonboy\\IdeaProjects\\e-commerse\\src\\main\\java\\users.json"),
                    list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
