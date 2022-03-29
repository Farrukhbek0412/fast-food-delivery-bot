package excel;

import model.user.User;
import org.apache.poi.xwpf.usermodel.*;
import telegramBot.userBot.UserInterface;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class WordFile<outputStream> implements UserInterface {
//    static String path = "C:\\Users\\msi\\Desktop\\e-commerse1\\";
//    FileOutputStream outputStream = new FileOutputStream("C:\\Users\\msi\\Desktop\\e-commerse1\\userList.docx");
//    File word = new File("C:\\Users\\msi\\Desktop\\e-commerse1\\userList.docx");
//
//    {
//
//
//        XWPFDocument document = new XWPFDocument();
//
//        XWPFParagraph paragraph = document.createParagraph();
//        paragraph.setAlignment(ParagraphAlignment.CENTER);
//
//        XWPFRun run = paragraph.createRun();
//        run.setBold(true);
//
//        run.setText("Current time : " + LocalDate.now());
//        XWPFTable table = document.createTable();
//        table.setWidth("100%");
//        XWPFTableRow row = table.getRow(0);
//
//
//        row.getCell(0).setText("T/r ");
//        row.createCell().setText("Name");
//        row.createCell().setText("Registered date");
//        row.createCell().setText("Location");
//        row.createCell().setText("PhoneNumber");
//        row.createCell().setText("Chat id");
//        int index = 0;
//        for (User user : userService.read()) {
//            // har safar yangi qator yaratib keyin qo'shib ketiladi
//
//            XWPFTableRow tableRow = table.createRow();
//
//            tableRow.getCell(0).setText("" + (index + 1));
//            tableRow.getCell(1).setText(user.getFullName());
//            tableRow.getCell(2).setText(user.getCreatedDate().toString());
//            tableRow.getCell(3).setText(user.getLocation().toString());
//            tableRow.getCell(4).setText(user.getPhoneNumber());
//            tableRow.getCell(5).setText(user.getChatId());
//
//        }
//
//
//
//        }
//            document.write(outputStream);
//
//            document.close();
//            outputStream.close();
//
//}
//
}
