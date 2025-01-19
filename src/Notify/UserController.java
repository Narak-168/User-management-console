package Notify;

import model.User;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;
import display.UserDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final List<User> users = new ArrayList<>();
    private final UserDisplay view;
    private int userIdCounter = 0;
    private final Scanner scanner = new Scanner(System.in);

    public UserController(UserDisplay view) {
        this.view = view;
    }

    public void start(){
        while(true){
            view.displayMenu();
            int choice =  Integer.parseInt(view.getInput(""));
            switch(choice){
                case 1 -> createUser();
                case 2 -> searchUser();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> displayAllUsers();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");

            }
        }
    }
    private void createUser(){
        String name = view.getInput("Enter Name: ");
        String email = view.getInput("Enter Email: ");
        User user = new User(userIdCounter++, name, email);
        users.add(user);
        TelegramNotification notification = new TelegramNotification();
        notification.sendNotification("User created successfully!");
        System.out.println("User created successfully! User ID: " + user.getId());
    }
    private void searchUser(){
        String uuid = view.getInput("Enter UUID: ");
        User user = users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst().orElse(null);
        if (user != null) {
            view.displayUsers(List.of(user));
        } else {
            view.showMessage("User not found.");
        }
    }

    private void updateUser() {
        String uuid = view.getInput("Enter UUID: ");
        User user = users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst().orElse(null);
        if (user != null) {
            String newName = view.getInput("Enter new name: ");
            String newEmail = view.getInput("Enter new email: ");
            user.setName(newName);
            user.setEmail(newEmail);
            view.showMessage("User updated successfully!");
        } else {
            view.showMessage("User not found.");
        }
    }

    private void deleteUser() {
        String uuid = view.getInput("Enter UUID: ");
        User user = users.stream().filter(u -> u.getUuid().equals(uuid)).findFirst().orElse(null);
        if (user != null) {
            user.setDeleted(true);
            view.showMessage("User deleted successfully!");
        } else {
            view.showMessage("User not found.");
        }
    }

    private void displayAllUsers() {
        List<User> activeUsers = users.stream().filter(u -> !u.isDeleted()).toList();

        if (activeUsers.isEmpty()) {
            System.out.println("No active users found.");
            return;
        }

        int pageSize = 5; // Number of users per page
        int totalUsers = activeUsers.size();
        int totalPages = (totalUsers + pageSize - 1) / pageSize;

        Scanner scanner = new Scanner(System.in);
        int currentPage = 1;

        while (true) {
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(currentPage * pageSize, totalUsers);

            Table table = new Table(4, BorderStyle.UNICODE_BOX);

            CellStyle headerStyle = new CellStyle(CellStyle.HorizontalAlign.center);

            table.addCell("UUID", headerStyle);
            table.addCell("Name", headerStyle);
            table.addCell("Email", headerStyle);
            table.addCell("IsDeleted", headerStyle);

            for (int i = startIndex; i < endIndex; i++) {
                User user = activeUsers.get(i);
                table.addCell(user.getUuid().toString());
                table.addCell(user.getName());
                table.addCell(user.getEmail());
                table.addCell(String.valueOf(user.isDeleted()));
            }

            System.out.println(table.render());

            System.out.println("\nPage " + currentPage + " of " + totalPages);
            System.out.print("Enter 'n' for next page, 'p' for previous page, or 'q' to quit:");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("n") && currentPage < totalPages) {
                currentPage++; // Go to the next page
            } else if (input.equals("p") && currentPage > 1) {
                currentPage--; // Go to the previous page
            } else if (input.equals("q")) {
                break; // Exit the loop if the user quits
            } else {
                System.out.println("Invalid input. Please enter 'n', 'p', or 'q'.");
            }
        }

    }

}