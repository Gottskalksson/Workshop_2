package pl.coderslab.Workshop_2.parts;

import pl.coderslab.Workshop_2.daos.ExerciseDao;
import pl.coderslab.Workshop_2.daos.SolutionDao;
import pl.coderslab.Workshop_2.daos.UserDao;
import pl.coderslab.Workshop_2.models.Solution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SolutionPart {
    public static void main(String[] args) {
        ExerciseDao exerciseDao = new ExerciseDao();
        SolutionDao solutionDao = new SolutionDao();
        UserDao userDao = new UserDao();
        String answer = "";

        while (!answer.equals("quit")) {
            System.out.println("What do you want to do?");
            System.out.println("-> add - add solution to user;");
            System.out.println("-> view - show user's solutions");
            System.out.println("-> quit - exit");
            System.out.println();


            answer = answer();
            answer = checkAnswer(answer);
            if (answer.equals("quit")) {
                break;
            } else if (answer.equals("add")) {
                userDao.findAll();
                int userId = 0;
                while (userId < 1) {
                    try {
                        System.out.println("Podaj ID użytkownika: ");
                        userId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                exerciseDao.findAll();
                int exerciseId = 0;
                while (exerciseId < 1) {
                    try {
                        System.out.println("Podaj ID ćwiczenia: ");
                        exerciseId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                Solution solution = new Solution();
                solution.setUserId(userId);
                solution.setExerciseId(exerciseId);
                solution.setCreated(currentDate());
                solution.setDescription("-");
                solutionDao.create(solution);


            } else if (answer.equals("view")) {
                int userId = 0;
                while (userId < 1) {
                    try {
                        System.out.println("Write User ID: ");
                        userId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                solutionDao.read(userId);

            }

        }

    }

    private static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("view") ||  answer.equals("quit"))) {
            System.out.println("Incorrect choice! Please write one of this - add, edit, delete lub quit: ");
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
