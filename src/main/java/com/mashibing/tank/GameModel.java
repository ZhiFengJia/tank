package com.mashibing.tank;

import com.mashibing.tank.collider.Collider;
import com.mashibing.tank.collider.ColliderChain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameModel {
    public static final ExecutorService threadPool;

    private Tank myTank = new Tank(550, 800, Dir.DOWN, Group.GOOD, this);
    private List<GameObject> gameObjects = new ArrayList<>();
    private ColliderChain chain = new ColliderChain();

    static {
        threadPool = new ThreadPoolExecutor(1, 500,
                5L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    public GameModel() {
        this.myTank.setMoving(false);
        initTank();

        String[] colliders = ((String) PropertyMgr.get("colliders")).split(",");
        try {
            for (String collider : colliders) {
                this.chain.add((Collider) Class.forName(collider).getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Tank.speed = Integer.parseInt((String) PropertyMgr.get("tankSpeed"));
        Bullet.speed = Integer.parseInt((String) PropertyMgr.get("bulletSpeed"));

        threadPool.execute(() -> new Audio("audio/war1.wav").loop());
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, TankFrame.GAME_WIDTH, TankFrame.GAME_HEIGHT);

        g.setColor(Color.WHITE);
        g.drawString(String.format("坐标:(%d,%d)", myTank.getX(), myTank.getY()), 10, 45);

        g.drawString("子弹数量:" + gameObjects.stream().filter(val -> val instanceof Bullet).count(), 10, 65);
        g.drawString("爆炸数量:" + gameObjects.stream().filter(val -> val instanceof Explode).count(), 10, 85);
        g.drawString("坦克数量:" + gameObjects.stream().filter(val -> val instanceof Tank).count(), 10, 105);
        g.drawString("坦克速度:" + Tank.speed, 10, 125);
        g.drawString("子弹速度:" + Bullet.speed, 10, 145);
        g.drawString("F1:增加敌方坦克", 1100, 60);
        g.drawString("F2:增加我方坦克", 1100, 80);
        g.drawString("F3:增加坦克速度", 1100, 100);
        g.drawString("F4:减少坦克速度", 1100, 120);
        g.drawString("F5:增加子弹速度", 1100, 140);
        g.drawString("F6:减少子弹速度", 1100, 160);
        g.drawString("space:发射子弹", 1100, 180);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }

        //互相碰撞
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject o1 = gameObjects.get(i);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject o2 = gameObjects.get(j);
                chain.collide(o1, o2);
            }
        }
    }

    /**
     * 按每行20个坦克布局
     */
    private void initTank() {
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        //一行20个坦克
        int countByRow = 20;
        int rows = initTankCount / countByRow;
        if (rows == 0) {
            for (int i = 0; i < initTankCount; i++) {
                add(new Tank(10 + i * 60, 120, Dir.DOWN, Group.BAD, this));
            }
        } else {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < countByRow; j++) {
                    add(new Tank(10 + j * 60, 120 + i * 60, Dir.DOWN, Group.BAD, this));
                }
                //最后一行
                if (i == (rows - 1)) {
                    for (int m = 0; m < (initTankCount % countByRow); m++) {
                        add(new Tank(10 + m * 60, 120 + (i + 1) * 60, Dir.DOWN, Group.BAD, this));
                    }
                }
            }
        }
    }

    public Tank getMyTank() {
        return myTank;
    }

    public void add(GameObject go) {
        this.gameObjects.add(go);
    }

    public void remove(GameObject go) {
        this.gameObjects.remove(go);
    }

}
