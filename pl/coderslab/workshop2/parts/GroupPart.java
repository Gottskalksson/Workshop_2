package pl.coderslab.workshop2.parts;

import pl.coderslab.workshop2.daos.GroupDao;
import pl.coderslab.workshop2.models.Group;


import java.util.Scanner;

public class GroupPart {
    public static void main(String[] args) {
        GroupDao groupDao = new GroupDao();
        String answer = "";

        while (!answer.equals("quit")) {
            groupDao.findAll();
            System.out.println("Wybierz jedną z opcji");
            System.out.println("-> add - dodaj grupę;");
            System.out.println("-> edit - edytuj grupę");
            System.out.println("-> delete - usuń grupę");
            System.out.println("-> quit - zakończ program");
            System.out.println();


            answer = answer();
            answer = checkAnswer(answer);
            if (answer.equals("quit")) {
                break;
            } else if (answer.equals("add")) {

                System.out.println("Podaj nazwę grupy : ");
                String name = answer();


                Group group = new Group(name);
                groupDao.create(group);


            } else if (answer.equals("edit")) {
                int groupId = 0;
                while (groupId < 1) {
                    try {
                        System.out.println("Podaj ID grupy: ");
                        groupId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Podaj nową nazwę dla grupy: ");
                String name = answer();


                Group group = new Group(name);
                group.setId(groupId);
                groupDao.update(group);


            } else if (answer.equals("delete")) {

                int groupId = 0;
                while (groupId < 1) {
                    try {
                        System.out.println("Podaj ID grupy: ");
                        groupId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Jeśli na pewno chcesz usunąć grupę, potwierdź poniżej, wpisując literę 't' :");
                String yesOrNo = answer();
                if (yesOrNo.equalsIgnoreCase("t")) {
                    groupDao.delete(groupId);
                }
            }

        }

    }

    private static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("edit") || answer.equals("delete") || answer.equals("quit"))) {
            System.out.println("Podano niepoprawną opcję! Wpisz jedną z podanych - add, edit, delete lub quit: ");
            answer = answer();
        }
        return answer;
    }

    private static String answer() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}

