package pl.coderslab.Workshop_2;


import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.Workshop_2.daos.SolutionDao;
import pl.coderslab.Workshop_2.models.Solution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Nie podano maila!");
        } else {
            String email = args[0];
            System.out.println("Podany e-mail to: " + email);
            String answer = "";

            try (Connection conn = DBUtil.getConnection()) {
                PreparedStatement findUserId = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
                findUserId.setString(1, email);
                ResultSet setUserId = findUserId.executeQuery();
                int userId = 0;
                if (setUserId.next()) {
                    userId = setUserId.getInt("id");
                }
                PreparedStatement statement = conn.prepareStatement("SELECT password FROM users WHERE email = ?");
                statement.setString(1, email);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    String pass = rs.getString("password");
                    System.out.println("Podaj hasło:");
                    if (BCrypt.checkpw(answer(), pass)) {
                        System.out.println("Zalogowano do systemu!");
                        while (!answer.equals("quit")) {
                            System.out.println("Wybierz jedną z opcji: ");
                            System.out.println("-> add - dodawanie rozwiązania");
                            System.out.println("-> view - przeglądanie swoich rozwiązań");
                            System.out.println("-> quit - wyjście z programu");
                            answer = checkAnswer(answer());
                            if (answer.equals("add")) {
                                PreparedStatement statement1 = conn.prepareStatement("SELECT e.* FROM exercises e " +
                                        "LEFT JOIN solutions s on  e.id = s.exercise_id AND user_id = ? WHERE s.created IS NULL");
                                statement1.setInt(1, userId);
                                ResultSet resultSet = statement1.executeQuery();
                                if (!resultSet.next()) {
                                    System.out.println("Nie masz więcej zadań do rozwiązania!");
                                } else {
                                    resultSet.beforeFirst();
                                    System.out.println("ID / TITLE / DESCRIPTION");
                                    while (resultSet.next()) {
                                        int id = resultSet.getInt("id");
                                        String title = resultSet.getString("title");
                                        String description = resultSet.getString("description");
                                        System.out.println(id + " / " + title + " / " + description);
                                    }
                                    System.out.println();
                                    int exerciseId = 0;
                                    while (exerciseId < 1) {
                                        try {
                                            System.out.println("Wpisz numer ID zadania, do którego chcesz wpisać rozwiązanie: ");
                                            exerciseId = Integer.parseInt(answer());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Podano niewłaściwy numer!");
                                        }
                                    }

                                    PreparedStatement isExerciseIdNull = conn.prepareStatement("SELECT * FROM exercises e " +
                                            "LEFT JOIN solutions s on  e.id = s.exercise_id AND user_id = ? WHERE s.created IS NULL" +
                                            " AND e.id = ?");
                                    isExerciseIdNull.setInt(1, userId);
                                    isExerciseIdNull.setInt(2, exerciseId);

                                    ResultSet isRsNull = isExerciseIdNull.executeQuery();

                                    if (!isRsNull.next()) {
                                        System.out.println("Ćwiczenie nie istnieje lub rozwiązanie do niego zostało napisane!");
                                    } else {
                                        System.out.println("Podaj rozwiązanie do swojego zadania: ");
                                        String solution = answer();


                                        Solution sol = new Solution();
                                        sol.setCreated(currentDate());
                                        sol.setExerciseId(exerciseId);
                                        sol.setUserId(userId);
                                        sol.setDescription(solution);
                                        SolutionDao solutionDao = new SolutionDao();
                                        solutionDao.create(sol);

                                    }
                                }
                            } else if (answer.equals("view")) {
                                PreparedStatement statement1 = conn.prepareStatement("SELECT * FROM solutions WHERE user_id = ?;");
                                statement1.setInt(1, userId);
                                ResultSet resultSet = statement1.executeQuery();
                                if (!resultSet.next()) {
                                    System.out.println("Nie masz żadnych rozwiązań w systemie!");
                                } else {
                                    resultSet.beforeFirst();
                                    System.out.println("ID / CREATED / DESCRIPTION / EXERCISE ID");
                                    while (resultSet.next()) {
                                        int id = resultSet.getInt("id");
                                        String created = resultSet.getString("created");
                                        String description = resultSet.getString("description");
                                        int exerciseId = resultSet.getInt("exercise_id");
                                        System.out.println(id + " / " + created + " / " + description + " / " + exerciseId);
                                    }
                                }
                            }

                        }
                    } else {
                        System.out.println("Błędne hasło!");
                    }
                } else {
                    System.out.println("Podany mail jest nieprawidłowy!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("view") || answer.equals("quit"))) {
            System.out.println("Nieprawidłowy wpis! Wpisz jedną z podanych opcji - add, view or quit: ");
            answer = answer();
        }
        return answer;
    }

    private static String answer() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    private static String currentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
