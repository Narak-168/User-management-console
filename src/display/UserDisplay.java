package display;

import model.User;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Scanner;

public class UserDisplay {
    private final Scanner scanner = new Scanner(System.in);
    public void displayMenu(){
        System.out.println("""
                User Management Console: 
                 1. Create User
                 2. Search User
                 3. Update User
                 4. Delete User
                 5. Display All Uses
                 6. Exit
                """);
        System.out.println("Enter Your Options: ");
    }
    public String getInput(String message){
        System.out.println(message);
        return scanner.nextLine();
    }
    public void displayUsers(List<User> users){
        if (users.isEmpty()) {
            System.out.println("No users found.");
        }
        Table table = new Table(5, BorderStyle.UNICODE_BOX);

        CellStyle headerStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        table.addCell("ID", headerStyle);
        table.addCell("UUID", headerStyle);
        table.addCell("Name", headerStyle);
        table.addCell("Email", headerStyle);
        table.addCell("isDeleted", headerStyle);

        for (User user : users) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getUuid());
            table.addCell(user.getName());
            table.addCell(user.getEmail());
            table.addCell(String.valueOf(user.isDeleted()));


        }
        System.out.println(table.render());
    }

    public void showMessage(String message){
        System.out.println(message);
    }
}
