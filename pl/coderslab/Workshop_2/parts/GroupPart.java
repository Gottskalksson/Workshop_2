package pl.coderslab.Workshop_2.parts;

import pl.coderslab.Workshop_2.daos.GroupDao;
import pl.coderslab.Workshop_2.models.Group;


import java.util.Scanner;

public class GroupPart {
    public static void main(String[] args) {
        GroupDao groupDao = new GroupDao();
        String answer = "";

        while (!answer.equals("quit")) {
            groupDao.findAll();
            System.out.println("What do you want to do?");
            System.out.println("-> add - add new group;");
            System.out.println("-> edit - edit group");
            System.out.println("-> delete - remove group from database");
            System.out.println("-> quit - exit");
            System.out.println();


            answer = answer();
            answer = checkAnswer(answer);
            if (answer.equals("quit")) {
                break;
            } else if (answer.equals("add")) {

                System.out.println("Please write group's name : ");
                String name = answer();


                Group group = new Group(name);
                groupDao.create(group);


            } else if (answer.equals("edit")) {
                int groupId = 0;
                while (groupId < 1) {
                    try {
                        System.out.println("Write Group ID: ");
                        groupId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Write new name for this group: ");
                String name = answer();


                Group group = new Group(name);
                group.setId(groupId);
                groupDao.update(group);


            } else if (answer.equals("delete")) {

                int groupId = 0;
                while (groupId < 1) {
                    try {
                        System.out.println("Write group ID: ");
                        groupId = Integer.parseInt(answer());
                    } catch (NumberFormatException e) {
                        System.out.println("Podano niewłaściwy numer!");
                    }
                }

                System.out.println("Are You sure to remove this group? T/N :");
                String yesOrNo = answer();
                if (yesOrNo.equalsIgnoreCase("t")) {
                    groupDao.delete(groupId);
                }
            }

        }

    }

    private static String checkAnswer(String answer) {
        while (!(answer.equals("add") || answer.equals("edit") || answer.equals("delete") || answer.equals("quit"))) {
            System.out.println("Incorrect choice! Please write one of this - add, edit, delete lub quit: ");
            answer = answer();
        }
        return answer;
    }

    private static String answer() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }
}

