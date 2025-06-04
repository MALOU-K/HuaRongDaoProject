package controller;

import model.Direction;
import view.game.BoxComponent;

import java.util.Timer;
import java.util.TimerTask;

public class Move {


    public static void pieceMove(BoxComponent box, int size, Direction direction) {
        int initialX = box.getCol() * size + 2;
        int initialY = box.getRow() * size + 2;
        final int[] x = {initialX};
        final int[] y = {initialY};
        final int[] step = {0};
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                x[0] += direction.getCol();
                y[0] += direction.getRow();
                box.setLocation(x[0], y[0]);
                step[0] +=1;
                box.repaint();
                if (step[0] == size){
                    timer.cancel();
                }
            }
        };
        long delay = 1;
        long period = 2;
        timer.schedule(task,delay,period);

    }
}
