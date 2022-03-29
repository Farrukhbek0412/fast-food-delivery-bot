package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.user.User;
import model.user.UserRole;
import repository.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class UserService implements BaseService<User, List<User>, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void write(List<User> userList) {
        try {
            File file = new File("userList.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, userList);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Writing error " + e.toString());
        }
    }

    @Override
    public List<User> read() {
        List<User> list = new ArrayList<>();
        try {
            File file = new File("userList.json");
            list = objectMapper.readValue(file, new TypeReference<ArrayList<User>>() {
            });
            return list;
        } catch (Exception | NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return null;
    }

    {
        User user = new User();
        user.setUserRole(UserRole.SUPER_ADMIN);
        user.setEmail("street77@gmail.com");
        user.setPhoneNumber("332020024");
        user.setPassword("Farrux");
        user.setChatId("1000000");
        add(user);
    }

    @Override
    public String add(User user) {
        List<User> userList = read();
        if (userList.size()==0)
            userList = new ArrayList<>();
        else if (user.getUserRole().equals(UserRole.SUPER_ADMIN)) {
            return SUCCESS;
        }
        userList.add(user);
        write(userList);
        return SUCCESS;
    }

    @Override
    public User getById(UUID id) {
        List<User> userList = read();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(id)) {
                return userList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = read();
        return userList;
    }

    @Override
    public String check(String phoneNumber) {
        List<User> userList = read();
        for (User user : userList) {
            if (user.getPhoneNumber().equals(phoneNumber))
                return ERR_USER_ALREADY_EXISTS;
        }
        return ERR_USER_NOT_FOUND;
    }

    public String checkByEmail(String email) {
        List<User> userList = read();
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getEmail().equals(email))
                return ERR_USER_ALREADY_EXISTS;
        }
        return ERR_USER_NOT_FOUND;
    }

    public User loginWithEmail(String email, String password) {
        List<User> userList = read();
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getEmail().equals(email) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public boolean checkEmail(String email) {
        List<User> userList = read();
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public boolean checkPhoneNumber(String number) {
        List<User> userList = read();
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getPhoneNumber().equals(number))
                return true;
        }
        return false;
    }

    public boolean checkUserByNumber(String phoneNumber) {
        List<User> userList = read();
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getPhoneNumber().equals(phoneNumber))
                return true;
        }
        return false;
    }

    public User loginByChatId(String chatId) {
        List<User> userList = read();
        if (userList.size() != 0) {
            for (User user : userList) {
                if (user.getChatId().equals(chatId))
                    return user;
            }
        }
        return null;
    }

    public String editByChatId(String userChatId, User editedUser) {
        List<User> userList = read();
        int index = 0;
        for (User user : userList) {
            if (user.getChatId().equals(userChatId)) {
                if (editedUser.getFullName() != null)
                    user.setFullName(editedUser.getFullName());
                if (editedUser.getUserRole() != null)
                    user.setUserRole(editedUser.getUserRole());
                if (editedUser.getUserState() != null)
                    user.setUserState(editedUser.getUserState());
                if (editedUser.getPhoneNumber() != null)
                    user.setPhoneNumber(editedUser.getPhoneNumber());
                if (editedUser.getLocation() != null)
                    user.setLocation(editedUser.getLocation());
                user.setUpdatedDate(editedUser.getCreatedDate());
                userList.set(index, user);
                write(userList);
                return SUCCESS;
            }
            index++;
        }
        return ERR_USER_NOT_FOUND;
    }

    public User loginWithNumber(String phoneNumber, String password) {
        List<User> userList = read();
        for (User user : userList) {
            if (user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public boolean isAdminCheckByNumber(String phoneNumber, String password) {
        List<User> userList = read();
        if (userList.get(0).getPhoneNumber().equals(phoneNumber) && userList.get(0).getPassword().equals(password))
            return true;
        return false;
    }
    public boolean isAdminCheckByNumber(String phoneNumber) {
        List<User> userList = read();
        if (userList.get(0).getPhoneNumber().equals(phoneNumber))
            return true;
        return false;
    }

    public boolean isAdminCheckByEmail(String email, String password) {
        List<User> userList = read();
        if (userList.get(0).getEmail().equals(email) && userList.get(0).getPassword().equals(password))
            return true;
        return false;
    }
}