package pl.coderslab.Workshop_2;

import pl.coderslab.Workshop_2.daos.GroupDao;
import pl.coderslab.Workshop_2.daos.UserDao;
import pl.coderslab.Workshop_2.models.Group;
import pl.coderslab.Workshop_2.models.User;

import java.util.Scanner;

public class UserPart {

    public static void main(String[] args) {
        GroupDao groupDao = new GroupDao();
        Group group = new Group();
        UserDao userDao = new UserDao();

        String answer = "";

        while (!answer.equals("quit")) {
            userDao.findAll();
            System.out.println("What do you want to do?");
            System.out.println("add");
            System.out.println("edit");
            System.out.println("delete");
            System.out.println("quit");
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
                System.out.println("Podaj numer grupy, do której jesteś zapisany: ");
                groupDao.findAll();
                int groupId = 0;
                while (groupId < 1) {
                    try {
                        groupId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano zły numer grupy! Podaj jeszcze raz: ");
                    }
                }
                group.setId(groupId);

                User user = new User(username, email, pass);
                user.setUserGroupId(group);
                userDao.create(user);
            }

        }


    }

    public static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("edit") || answer.equals("delete") || answer.equals("quit"))) {
            System.out.println("Podaj poprawną literę - add, edit, delete lub quit: ");
            answer = answer();
        }
        return answer;
    }

//    public static void yesOrNo(String answer) {
//        while (!(answer.equals("T") || answer.equals("N"))) {
//            System.out.println("Podaj poprawną literę - T lub N:");
//            answer = answer();
//        }
//    }

    public static String answer() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}
