package telegramBot.userBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import services.*;


public interface UserInterface {
    ObjectMapper objectMapper = new ObjectMapper();

    BasketService basketService = new BasketService();
    FoodService foodService = new FoodService();
    FoodTypeService foodTypeService = new FoodTypeService();
    HistoryService historyService = new HistoryService();
    UserService userService = new UserService();

    String BOT_USERNAME = "https://t.me/b1_faang_bot";
    String BOT_TOKEN = "5070771578:AAH4LvDVJzOYwoOeTG921YVGb28X09ceoKU";

    String friURL = "https://data.whicdn.com/images/316789053/original.jpg";
    String cheesePizzaURL = "https://yandex.ru/images/search?pos=4&from=tabbar&img_url=https%3A%2F%2Fsun9-58.userapi.com%2Fc849036%2Fv849036392%2F1e213d%2FKnBja9DRuw4.jpg&text=cheese+pizz&rpt=simage" ;
    String bigPizzaURL = "https://yandex.ru/images/search?p=2&text=big+pizza&pos=81&rpt=simage&img_url=https%3A%2F%2Fsun9-28.userapi.com%2Fc857220%2Fv857220911%2Fc5f2a%2FNgQDzADf574.jpg&from=tabbar";
    String cheeseBurgerURL ="https://yandex.ru/images/search?pos=1&from=tabbar&img_url=https%3A%2F%2Fassets.epicurious.com%2Fphotos%2F5c745a108918ee7ab68daf79%2Fmaster%2Fpass%2FSmashburger-recipe-120219.jpg&text=cheeseburger&rpt=simage";
    String bigBurgerURL = "https://yandex.ru/images/search?pos=14&from=tabbar&img_url=https%3A%2F%2F247wallst.com%2Fwp-content%2Fuploads%2F2018%2F01%2Fbig-mac.jpg&text=big+burger+png&rpt=simage";
    String nuggetsURL = "https://yandex.ru/images/search?pos=22&from=tabbar&img_url=https%3A%2F%2Fg1.dcdn.lt%2Fimages%2Fpix%2Fvistienos-kepsneliai-74546024.jpg&text=nuggets&rpt=simage";
    String longerURL = "https://yandex.ru/images/search?pos=4&from=tabbar&img_url=https%3A%2F%2Fsun9-21.userapi.com%2Fimpf%2Fc622925%2Fv622925791%2F27bdd%2FGe81ZgLPs1E.jpg%3Fsize%3D130x91%26quality%3D96%26sign%3D49abfd3cbbe9f8bc90d4843ed3452d8b%26type%3Dalbum&text=Longer&rpt=simage";
    String canadianURL = "https://yandex.ru/images/search?pos=0&from=tabbar&img_url=https%3A%2F%2Fsun9-65.userapi.com%2Fc857224%2Fv857224019%2F169281%2FuH3SN_GryVI.jpg&text=Canadian+hot+dog&rpt=simage";
    String pepsiURL = "https://yandex.ru/images/search?pos=5&from=tabbar&img_url=https%3A%2F%2Fmain-cdn.goods.ru%2Fhlr-system%2F17203931028%2F100024355583b0.jpg&text=Pepsi%28+1%2C5+l+%29&rpt=simage";
    String colaURL = "https://yandex.ru/images/search?pos=0&from=tabbar&img_url=https%3A%2F%2Fbonsai-sushi.ru%2Fwp-content%2Fuploads%2F2020%2F08%2FCoca-Cola_0-5.jpg&text=Cola+%28+0%2C5+l+%29&rpt=simage";

}
