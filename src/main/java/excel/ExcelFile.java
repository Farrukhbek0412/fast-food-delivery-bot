package excel;

import com.google.gson.Gson;
import model.History;
import telegramBot.userBot.UserInterface;
import lombok.SneakyThrows;
import model.user.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class ExcelFile implements UserInterface {
    @SneakyThrows
    public static void parseUserListToExcel(String userListURL) {
        Gson gson = new Gson();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(userListURL));
        File source = new File("\\Users\\msi\\Desktop\\e-commerse1\\userList.json");
        BufferedReader reader = new BufferedReader(new FileReader(source));
        User[] users = gson.fromJson(reader,User[].class);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("user list");
        HSSFRow rowHeadline = sheet.createRow(0);
        rowHeadline.createCell(2).setCellValue("All users in our system");
        HSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("#");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("created date");
        row.createCell(3).setCellValue("updated date");
        //row.createCell(4).setCellValue("location");
        row.createCell(4).setCellValue("phone number");
        row.createCell(5).setCellValue("chat id");

        int rowIndex = 2;
        for (User user : users) {
            HSSFRow row1 = sheet.createRow(rowIndex ++);

            HSSFCell cell0 = row1.createCell(0);
            cell0.setCellValue(rowIndex - 2);
//            cell0.setCellStyle(getStyle(workbook));

            HSSFCell cell1 = row1.createCell(1);
            cell1.setCellValue(user.getFullName());

            HSSFCell cell2 = row1.createCell(2);
            cell2.setCellValue(user.getCreatedDate());

            HSSFCell cell3 = row1.createCell(3);
            cell3.setCellValue(user.getUpdatedDate());

//            HSSFCell cell4 = row1.createCell(4);
//            cell4.setCellValue("Latitude: "+user.getLocation().getLatitude().toString()
//            +"Longitude : "+user.getLocation().getLongitude().toString());

            HSSFCell cell4 = row1.createCell(4);
            cell4.setCellValue(user.getPhoneNumber());

            HSSFCell cell5 = row1.createCell(5);
            cell5.setCellValue(user.getChatId());
        }

        workbook.write(fileOutputStream);
        workbook.close();
    }
    @SneakyThrows
    public static void parseHistoryListToExcel(String historyListURL) {
        Gson gson = new Gson();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(historyListURL));
        File source = new File("\\Users\\msi\\Desktop\\e-commerse1\\historyList.json");
        BufferedReader reader = new BufferedReader(new FileReader(source));
        History[] histories = gson.fromJson(reader,History[].class);

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet("history list");
        HSSFRow rowHeadline = sheet.createRow(0);
        rowHeadline.createCell(2).setCellValue("All histories in our system");
        HSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("#");
        row.createCell(1).setCellValue("Date");
        row.createCell(2).setCellValue("Phone Number");
        row.createCell(3).setCellValue("Food name");
        row.createCell(4).setCellValue("Amount");
        row.createCell(5).setCellValue("Price");
//        row.createCell(6).setCellValue("Overall");

        int rowIndex = 2;
        for (int i =histories.length-1; i>-1 ;i--) {

            HSSFRow row1 = sheet.createRow(rowIndex ++);

            HSSFCell cell0 = row1.createCell(0);
            cell0.setCellValue(rowIndex - 2);
//            cell0.setCellStyle(getStyle(workbook));

            HSSFCell cell1 = row1.createCell(1);
            cell1.setCellValue(histories[i].getDate());

            HSSFCell cell2 = row1.createCell(2);
            cell2.setCellValue(histories[i].getCustomer().getFullName());

            HSSFCell cell3 = row1.createCell(3);
            cell3.setCellValue(histories[i].getProduct().getFoodName());


            HSSFCell cell4 = row1.createCell(4);
            cell4.setCellValue(histories[i].getProduct().getAmount());

            HSSFCell cell5 = row1.createCell(5);
            cell5.setCellValue(histories[i].getProduct().getPrice().toString());

//            HSSFCell cell6 = row1.createCell(6);
//            cell6.setCellValue(history.getOverall().toString());
        }

        workbook.write(fileOutputStream);
        workbook.close();
    }
}
