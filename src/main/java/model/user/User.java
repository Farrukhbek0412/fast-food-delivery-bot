package model.user;

import telegramBot.userBot.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.BaseModel;
import org.telegram.telegrambots.meta.api.objects.Location;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User{
    //BaseModel dan extends olmadim,chunki excel ga
    // yozishda baseModel dagi atributlar userga korinmay qoldi
    private UUID id;
    private String updatedDate;
    private String fullName;
    private String createdDate;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
    private UserRole userRole;
    private int smsCode;
    private String chatId="";
    private UserState userState;
    private Location location;

    public void setSmsCode() {
        this.smsCode = (int) (Math.random() * 10_000d + 10_000);
    }
    {
        this.id=UUID.randomUUID();
        createdDate = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }
}
