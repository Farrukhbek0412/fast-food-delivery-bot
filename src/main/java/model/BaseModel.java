package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BaseModel {
    protected UUID id;
    protected String fullName;
    protected String createdDate;
    protected String updatedDate;

    {
        this.id=UUID.randomUUID();
        createdDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }
}
