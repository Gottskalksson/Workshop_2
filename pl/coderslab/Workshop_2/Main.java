package pl.coderslab.Workshop_2;


import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Podaj e-mail:");
        String email = answer();
        String answer = "";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement findUserId = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
            findUserId.setString(1, email);
            ResultSet setUserId = findUserId.executeQuery();
            if (setUserId.next()) {
                int userId = setUserId.getInt("id");
            }
            PreparedStatement statement = conn.prepareStatement("SELECT password FROM users WHERE email = ?");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String pass = rs.getString("password");
                System.out.println("Podaj hasło:");
                if (BCrypt.checkpw(answer(), pass)) {
                    System.out.println("Password correct!");
                    while (!answer.equals("quit")) {
                        System.out.println("Wybierz jedną z opcji: ");
                        System.out.println("-> add - dodawanie rozwiązania");
                        System.out.println("-> view - przeglądanie swoich rozwiązań");
                        System.out.println("-> quit - wyjście z programu");
                        answer = checkAnswer(answer());
                        if (answer.equals("add")) {
                            PreparedStatement statement1 = conn.prepareStatement("SELECT e.* FROM exercises e LEFT JOIN solutions s " +
                                    "on  e.id = s.exercise_id AND user_id = ? WHERE s.created IS NULL;");
                            statement1.setString(1, email);
                            ResultSet resultSet = statement1.executeQuery();
                            System.out.println("ID / CREATED / UPDATED / DESCRIPTION / EXERCISE ID");
                            while (resultSet.next()) {
                                resultSet.getInt("id");
                                resultSet.getString("created");
                                resultSet.getString("updated");
                                resultSet.getString("description");
                                resultSet.getInt("exercise_id");
                            }
                        }

                    }
                } else {
                    System.out.println("Wrong password!");
                }
            } else {
                System.out.println("Incorrect e-mail!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("view") ||  answer.equals("quit"))) {
            System.out.println("Incorrect choice! Please write one of this - add, view or quit: ");
            answer = answer();
        }
        return answer;
    }
    private static String answer() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
