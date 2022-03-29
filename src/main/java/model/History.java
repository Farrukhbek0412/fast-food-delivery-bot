package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.user.User;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class History extends BaseModel{
    private User customer;
    private Basket product;
    private String date;
    private BigDecimal overall;
    {
       date =new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }


}
