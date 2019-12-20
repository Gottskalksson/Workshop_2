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
            System.out.println("Wybierz jedną z opcji:");
            System.out.println("-> add - dodaj zadanie");
            System.out.println("-> edit - edytuj zadanie");
            System.out.println("-> delete - usuń zadanie");
            System.out.println("-> quit - zakończ program");
            System.out.println();


            answer = answer();
            answer = checkAnswer(answer);
            if (answer.equals("quit")) {
                break;
            } else if (answer.equals("add")) {

                System.out.println("Nazwa zadania: ");
                String title = answer();
                System.out.println("Polecenie zadania: ");
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
                        System.out.println("Podano zły numer ID zadania!");
                    }
                }

                System.out.println("Podaj nową nazwę zadania: ");
                String title = answer();
                System.out.println("Podaj nowe polecenie do zadania: ");
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
                        System.out.println("Podaj poniżej numer ID zadania: ");
                        exerciseId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano zły numer ID zadania!");
                    }
                }

                System.out.println("Jeśli na pewno chcesz usunąć to zadanie, wpisz poniżej literę 't' :");
                String yesOrNo = answer();
                if (yesOrNo.equalsIgnoreCase("t")) {
                    exerciseDao.delete(exerciseId);
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
