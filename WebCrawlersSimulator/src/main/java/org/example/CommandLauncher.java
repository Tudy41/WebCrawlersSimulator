package org.example;

import java.util.Scanner;

/**
 * Clasa ce se ocupa de lansarea comenzilor de la tastatura pentru un obiect de tip Exploration. Se utilizeaza un Scanner
 * pentru citirea comenzilor. Pentru comenzile pause1,pauseall sidisplaytokens se apeleaza explicit functiile ce asigura
 * functionalitatea corecta a threadurilor din Roboti.
 */
public class CommandLauncher {

    private Exploration explore;
    private Scanner scanner;

    private String commandName;

    private String robotName;

    private int secondsPause;

    public CommandLauncher(Exploration explore2) {

        explore = explore2;
    }

    public void run() {

        while (true) {

            scanner = new Scanner(System.in);
            commandName = scanner.next();
            if (commandName.equals("pause1")) {
                robotName = scanner.next();
                secondsPause = scanner.nextInt();

            }
            if (commandName.equals("start1")) {
                robotName = scanner.next();
            }
            if (commandName.equals("exit")) {

                break;
            }
            if (commandName.equals("pauseall")) {
                explore.setFromPauseSignal(true);
                for (Robot robot : explore.getRobots()) {
                    robot.setRestartFlag(1);
                    robot.killRobot();

                }

            }
            if (commandName.equals("startall")) {
                Thread r;
                explore.setFromPauseSignal(false);
                for (Robot robot : explore.getRobots()) {

                    r = new Thread(robot);
                    r.start();
                }

            }
            if (commandName.equals("start1") && !robotName.equals(null)) {
                Thread r;
                for (Robot robot : explore.getRobots()) {
                    if (robot.getName().equals(robotName)) {
                        robot.setWaitingTime(10000);
                        r = new Thread(robot);

                        r.start();
                    }
                }

            }

            if (commandName.equals("pause1") && !robotName.equals(null) && secondsPause == -1) {

                for (Robot robot : explore.getRobots()) {
                    if (robot.getName().equals(robotName)) {
                        robot.setRestartFlag(1);
                        robot.killRobot();

                    }
                }

            }

            if (commandName.equals("pause1") && !robotName.equals(null) && secondsPause >= 0) {

                for (Robot robot : explore.getRobots()) {
                    if (robot.getName().equals(robotName)) {
                        robot.setWaitingTime(secondsPause);
                        robot.setRestartFlag(1);
                    }
                }

            }
            if (commandName.equals("displaytokens")) {

                explore.displayNrOfTokensExtractedForEachRobot();
            }
        }


    }


}
