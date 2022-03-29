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
public class MyCards extends  BaseModel{
    private UUID CardNumber;
    private String expiryDate;
    private String password;
}
