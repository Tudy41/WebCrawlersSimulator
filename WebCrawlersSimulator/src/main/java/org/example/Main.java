package org.example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Exploration explore = new Exploration(7);
        explore.addRobot(new Robot("Wall-E", 7));
        explore.addRobot(new Robot("R2D2", 7));
        explore.addRobot(new Robot("Optimus-Prime", 7));

        Thread t = new Thread(new TimeKeeper(explore, 300000));
        t.setDaemon(true);
        t.start();


        CommandLauncher launcher = new CommandLauncher(explore);
        launcher.run();

    }
}
