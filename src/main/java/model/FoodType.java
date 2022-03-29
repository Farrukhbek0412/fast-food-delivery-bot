package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class FoodType extends BaseModel{
     private String categoryName;
     private UUID categoryId;
     private boolean isActiveFoodType;

    {
        this.isActiveFoodType=true;
        this.categoryId= UUID.randomUUID();
    }

}
