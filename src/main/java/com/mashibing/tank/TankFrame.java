package com.mashibing.tank;

import com.mashibing.tank.util.RandomUtils;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
    public static final int GAME_WIDTH = 1220, GAME_HEIGHT = 960;
    private static final GameModel gm = new GameModel();

    public TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setTitle("坦克大战-->无敌加强版");
        setResizable(false);
        setVisible(true);

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    //double buffer
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    private class MyKeyListener extends KeyAdapter {
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            setMyTankDir();

            //TODO 按键按住不放时,音效会发生重叠.
            GameModel.threadPool.execute(() -> new Audio("audio/tank_move.wav").play());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_SPACE:
                    gm.getMyTank().fire();
                    break;
                case KeyEvent.VK_F1:
                    gm.add(new Tank(RandomUtils.nextInt(TankFrame.GAME_WIDTH),
                            RandomUtils.nextInt(TankFrame.GAME_HEIGHT),
                            Dir.values()[RandomUtils.nextInt(4)],
                            Group.BAD, gm));
                    break;
                case KeyEvent.VK_F2:
                    gm.add(new Tank(RandomUtils.nextInt(TankFrame.GAME_WIDTH),
                            RandomUtils.nextInt(TankFrame.GAME_HEIGHT),
                            Dir.values()[RandomUtils.nextInt(4)],
                            Group.GOOD, gm));
                    break;
                case KeyEvent.VK_F3:
                    Tank.speed = Tank.speed + 1;
                    break;
                case KeyEvent.VK_F4:
                    Tank.speed = Tank.speed - 1;
                    break;
                case KeyEvent.VK_F5:
                    Bullet.speed = Bullet.speed + 1;
                    break;
                case KeyEvent.VK_F6:
                    Bullet.speed = Bullet.speed - 1;
                    break;
                default:
                    break;
            }
            setMyTankDir();
        }

        private void setMyTankDir() {
            if (!bL && !bU && !bR && !bD)
                gm.getMyTank().setMoving(false);
            else {
                gm.getMyTank().setMoving(true);

                if (bL)
                    gm.getMyTank().setDir(Dir.LEFT);
                if (bU)
                    gm.getMyTank().setDir(Dir.UP);
                if (bR)
                    gm.getMyTank().setDir(Dir.RIGHT);
                if (bD)
                    gm.getMyTank().setDir(Dir.DOWN);
            }
        }
    }
}
