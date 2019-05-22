package com.mashibing.tank;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        while (true) {
            Thread.sleep(10);
            tf.repaint();
        }

    }

}
