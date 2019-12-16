package pl.coderslab.Workshop_2.parts;


import pl.coderslab.Workshop_2.daos.ExerciseDao;
import pl.coderslab.Workshop_2.daos.SolutionDao;
import pl.coderslab.Workshop_2.daos.UserDao;
import pl.coderslab.Workshop_2.models.Exercise;
import pl.coderslab.Workshop_2.models.Solution;
import pl.coderslab.Workshop_2.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SolutionPart {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        SolutionDao solutionDao = new SolutionDao();
        Exercise exercise = new Exercise();
        ExerciseDao exerciseDao = new ExerciseDao();
        User user = new User();

        String answer = "";

        while (!answer.equals("quit")) {
            solutionDao.findAll();
            System.out.println("What do you want to do?");
            System.out.println("-> add - add new solution;");
            System.out.println("-> edit - edit solution");
            System.out.println("-> delete - remove solution from database");
            System.out.println("-> quit - exit");
            System.out.println();


            answer = answer();
            answer = checkAnswer(answer);
            if (answer.equals("quit")) {
                break;
            } else if (answer.equals("add")) {
                exerciseDao.findAll();
                int exerciseId = 0;
                while (exerciseId < 1) {
                    try {
                        System.out.println("Please write exercise ID's solution: ");
                        exerciseId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("You wrote wrong exercise ID!");
                    }
                }
                exercise.setId(exerciseId);

                userDao.findAll();
                int userId = 0;
                while (userId < 1) {
                    try {
                        System.out.println("Please write user's ID, who created this solution: ");
                        userId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("You wrote wrong user ID!");
                    }
                }
                user.setId(userId);

                System.out.println("Describe this solution: ");
                String describe = answer();


                Solution solution = new Solution();
                solution.setDescription(describe);
                solution.setExerciseId(exercise);
                solution.setUserId(user);
                solution.setCreated(currentDate());
                solutionDao.create(solution);


            } else if (answer.equals("edit")) {
                int solutionId = 0;
                while (solutionId < 1) {
                    try {
                        System.out.println("Write Solution ID: ");
                        solutionId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Write new description for this solution: ");
                String description = answer();



                Solution solution = new Solution();
                solution.setId(solutionId);
                solution.setUpdated(currentDate());
                solution.setDescription(description);
                solutionDao.update(solution);


            } else if (answer.equals("delete")) {

                int solutionId = 0;
                while (solutionId < 1) {
                    try {
                        System.out.println("Write solution ID: ");
                        solutionId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Are You sure to remove this solution? T/N :");
                String yesOrNo = answer();
                if (yesOrNo.equalsIgnoreCase("t")) {
                    solutionDao.delete(solutionId);
                }
            }

        }

    }

    public static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("edit") || answer.equals("delete") || answer.equals("quit"))) {
            System.out.println("Incorrect choice! Please write one of this - add, edit, delete lub quit: ");
            answer = answer();
        }
        return answer;
    }

    public static String answer() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    private static String currentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
