package org.example;

import org.example.Exploration;

import java.util.List;

/**
 * Clasa ce defineste fiecare robot, in care ruleaza threadul fiecarui robot, permite oprirea robotilor ,pornirea lor,setarea
 * unui flag de resetare in caz de pauza si obinerea de informatii/setarea lor pentru robot.
 */
public class Robot implements Runnable {
    private String name;
    private boolean running;

    private int remainedRow;
    private int remainedCol;
    Exploration explore;

    private int restartFlag = 0;

    private int numberOfTokenExtracted;
    int waitingTime;

    public Robot(String name, int dimMap) {
        this.name = name;
        explore = new Exploration(dimMap);
        waitingTime = 8000;
        remainedRow = -9;
        remainedCol = -9;
        numberOfTokenExtracted = 0;
    }

    public int getRestartFlag() {

        return restartFlag;
    }

    public void setWaitingTime(int newTime) {

        this.waitingTime = newTime;
    }

    public void addNumberOfTokensExtracted(int nr) {

        numberOfTokenExtracted += nr;
    }

    public int getNumberOfTokensExtracted() {

        return numberOfTokenExtracted;
    }

    public void killRobot() {
        this.running = false;
    }

    public boolean getStatus() {
        return this.running;
    }

    public void killRobotWhenFinished() {
        List[][] list = new List[explore.getMap().getSize()][explore.getMap().getSize()];
        list = explore.getMap().getMatrix();
        for (int i = 0; i < explore.getMap().getSize(); i++) {
            for (int j = 0; j < explore.getMap().getSize(); j++) {
                if (list[i][j].isEmpty()) {
                    return;
                }
            }
        }
        killRobot();
    }

    public void setRemainedRowAndCol(int row, int col) {
        remainedRow = row;
        remainedCol = col;
    }

    public int getRemainedRow() {
        return remainedRow;
    }

    public void setRestartFlag(int nr) {
        restartFlag = 1;

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        running = true;
        int row = 0;
        row = explore.getMap().getRowForRobot(this);
        int col = -1;
        while (running) {


            if (col < explore.getMap().getSize() - 1) {

                col++;

            } else {

                row = explore.getMap().getRowForRobot(this);
                col = 0;

            }
            if (running == false) {
                break;
            }
            if (restartFlag == 1) {


                row = remainedRow;
                col = remainedCol;
                remainedCol = -9;
                remainedRow = -9;
                restartFlag = 0;

            }

            this.setRemainedRowAndCol(row, col);
            explore.getMap().visit(row, col, this);
            System.out.print(this.name);
            System.out.println();

            try {
                Thread.sleep(this.waitingTime);
            } catch (InterruptedException e) {
                killRobot();
            }
            killRobotWhenFinished();
        }

    }
}
