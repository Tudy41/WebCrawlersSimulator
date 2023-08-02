package org.example;

import org.example.Exploration;

/**
 * Clasa care are rolul de a rula un daemon pentru threadul pt. obiectul de tip Exploration ce ruleaza concurent cu robotii
 * activati de obiectul Exploration curent, permite afisarea timpului de explorare realizat in total de toti robotii activi atunci
 * cand toti au finalizat explorarea completa a matricei sau cand toti robotii au fost opriti.
 */
public class TimeKeeper implements Runnable {
    private Exploration exp;
    private int timeLimit;

    private int initialTime;

    private int currentTime;

    TimeKeeper(Exploration exp2, int t) {
        timeLimit = t;
        exp = exp2;

    }

    public void setCurrentTime(int time) {

        this.currentTime = time;
    }

    public int getCurrentTime() {

        return this.currentTime;
    }

    public void setInitialTime(int time) {

        this.initialTime = time;
    }

    public int getInitialTime() {

        return this.initialTime;
    }

    public int getTimePassedUntilNow() {
        return (getCurrentTime() - getInitialTime());
    }

    @Override
    public void run() {
        boolean ok = false;
        long initTime = 0;
        long timeWhenDone = 0;
        while (true) {
            if (ok == false) {
                initTime = System.currentTimeMillis();
                exp.start();


                ok = true;
            }
            timeWhenDone = System.currentTimeMillis();
            if ((timeWhenDone - initTime) > timeLimit) {
                System.out.println("The final time passed from the start of the exploration: " + (timeWhenDone - initTime));
                exp.killAll();

                break;
            }
            ;
            if (exp.checkExplorationFinished() && exp.getFromPauseSignal() == false) {


                System.out.println("The final time passed from the start of the exploration: " + (timeWhenDone - initTime));
                exp.setFromPauseSignal(false);
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

        }

    }
}
