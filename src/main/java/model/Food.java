package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.FoodType;

import java.math.BigDecimal;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Food extends BaseModel {
    private UUID foodId;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isActiveFood;

    {
        this.isActiveFood=true;
        this.foodId = UUID.randomUUID();
    }
}
