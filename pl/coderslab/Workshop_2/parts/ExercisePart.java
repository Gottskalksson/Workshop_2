package pl.coderslab.Workshop_2.parts;


import pl.coderslab.Workshop_2.daos.ExerciseDao;
import pl.coderslab.Workshop_2.daos.SolutionDao;
import pl.coderslab.Workshop_2.daos.UserDao;
import pl.coderslab.Workshop_2.models.Exercise;
import pl.coderslab.Workshop_2.models.User;

import java.util.Scanner;

public class ExercisePart {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        SolutionDao solutionDao = new SolutionDao();
        ExerciseDao exerciseDao = new ExerciseDao();
        User user = new User();

        String answer = "";

        while (!answer.equals("quit")) {
            exerciseDao.findAll();
            System.out.println("What do you want to do?");
            System.out.println("-> add - add new exercise;");
            System.out.println("-> edit - edit exercise");
            System.out.println("-> delete - remove exercise from database");
            System.out.println("-> quit - exit");
            System.out.println();


            answer = answer();
            answer = checkAnswer(answer);
            if (answer.equals("quit")) {
                break;
            } else if (answer.equals("add")) {

                System.out.println("Title of exercise: ");
                String title = answer();
                System.out.println("Describe this exercise: ");
                String describe = answer();


                Exercise exercise = new Exercise();
                exercise.setDescription(describe);
                exercise.setTitle(title);
                exerciseDao.create(exercise);


            } else if (answer.equals("edit")) {
                int exerciseId = 0;
                while (exerciseId < 1) {
                    try {
                        System.out.println("Please write exercise ID's solution: ");
                        exerciseId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("You wrote wrong exercise ID!");
                    }
                }

                System.out.println("Write new title for this exercise: ");
                String title = answer();
                System.out.println("Write new description for this solution: ");
                String description = answer();



                Exercise exercise = new Exercise();
                exercise.setId(exerciseId);
                exercise.setTitle(title);
                exercise.setDescription(description);
                exerciseDao.update(exercise);


            } else if (answer.equals("delete")) {

                int exerciseId = 0;
                while (exerciseId < 1) {
                    try {
                        System.out.println("Please write exercise ID's solution: ");
                        exerciseId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("You wrote wrong exercise ID!");
                    }
                }

                System.out.println("Are You sure to remove this exercise? T/N :");
                String yesOrNo = answer();
                if (yesOrNo.equalsIgnoreCase("t")) {
                    exerciseDao.delete(exerciseId);
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
}
