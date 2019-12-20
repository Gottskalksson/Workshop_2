package pl.coderslab.workshop2.parts;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.workshop2.DBUtil;
import pl.coderslab.workshop2.daos.GroupDao;
import pl.coderslab.workshop2.daos.UserDao;
import pl.coderslab.workshop2.models.Group;
import pl.coderslab.workshop2.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserPart {

    public static void main(String[] args) {
        GroupDao groupDao = new GroupDao();
        Group group = new Group();
        UserDao userDao = new UserDao();

        String answer = "";

        while (!answer.equals("quit")) {
            userDao.findAll();
            System.out.println("Wybierz jedną z opcji:");
            System.out.println("-> add - dodanie użytkownika");
            System.out.println("-> edit - edycja użytkownika");
            System.out.println("-> delete - usunięcie użytkownika");
            System.out.println("-> quit - zakończenie programu");
            System.out.println();

            answer = answer();
            answer = checkAnswer(answer);
            if (answer.equals("quit")) {
                break;
            } else if (answer.equals("add")) {
                System.out.println("Podaj nową nazwę użytkownika: ");
                String username = answer();
                System.out.println("Podaj e-mail: ");
                String email = answer();
                System.out.println("Ustaw hasło: ");
                String pass = answer();
                groupDao.findAll();
                int groupId = 0;
                while (groupId < 1) {
                    try {
                        System.out.println("Podaj numer grupy, do której jesteś zapisany: ");
                        groupId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano zły numer grupy!");
                    }
                }
                group.setId(groupId);

                User user = new User(username, email, pass);
                user.setUserGroupId(group);
                userDao.create(user);
            } else if (answer.equals("edit")) {
                int userId = 0;
                while (userId < 1) {
                    try {
                        System.out.println("Podaj ID użytkownika: ");
                        userId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Podaj hasło: ");
                String passToCheck = answer();
//                passToCheck = BCrypt.hashpw(passToCheck, BCrypt.gensalt());
                try (Connection conn = DBUtil.getConnection())  {
                    PreparedStatement statement = conn.prepareStatement("SELECT password FROM users WHERE id = ?");
                    statement.setInt(1, userId);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        String pass = rs.getString("password");
                        if (!BCrypt.checkpw(passToCheck, pass)) {
                            System.out.println("Niepoprawne hasło!");
                        } else {
                            System.out.println("Podaj nową nazwę użytkownika: ");
                            String username = answer();
                            System.out.println("Podaj nowe hasło: ");
                            String newPass = answer();
                            System.out.println("Podaj nowy adres email: ");
                            String email = answer();
                            System.out.println("Podaj nowy numer grupy, lista poniżej: ");
                            groupDao.findAll();
                            int groupId = 0;
                            while (groupId < 1) {
                                try {
                                    groupId = Integer.parseInt(answer());
                                } catch (NumberFormatException e) {
                                    System.out.println("Podano zły numer grupy! Wpisz poprawne ID grupy poniżej: ");
                                }
                            }
                            User user = new User(username, email, newPass);

                            user.setUserGroupId(groupId);
                            user.setId(userId);
                            userDao.update(user);
                        }
                    } else {
                        System.out.println("Zły numer ID!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (answer.equals("delete")) {

                int userId = 0;
                while (userId < 1) {
                    try {
                        System.out.println("Podaj ID użytkownika: ");
                        userId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Podaj hasło: ");
                String passToCheck = answer();
                try (Connection conn = DBUtil.getConnection())  {
                    PreparedStatement statement = conn.prepareStatement("SELECT password FROM users WHERE id = ?");
                    statement.setInt(1, userId);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        String pass = rs.getString("password");
                        if (!BCrypt.checkpw(passToCheck, pass)) {
                            System.out.println("Niepoprawne hasło!");
                        } else {
                            System.out.println("Jeśli jesteś pewien, że chcesz usunąć podanego użytkownika, wpis poniżej literę 't' :");
                            String yesOrNo = answer();
                            if (yesOrNo.equalsIgnoreCase("t")) {
                                userDao.delete(userId);
                            }
                        }
                    } else {
                        System.out.println("Zły numer ID!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("edit") || answer.equals("delete") || answer.equals("quit"))) {
            System.out.println("Podano niepoprawną opcję! Wpisz jedną z podanych - add, edit, delete lub quit: ");
            answer = answer();
        }
        return answer;
    }

    public static String answer() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
