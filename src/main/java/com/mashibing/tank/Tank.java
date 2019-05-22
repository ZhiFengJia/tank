package com.mashibing.tank;

import com.mashibing.tank.strategy.FireStrategy;
import com.mashibing.tank.util.RandomUtils;

import java.awt.*;

public class Tank extends GameObject {
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();
    public static int speed = 3;

    private int x, y;
    private Dir dir;
    private Group group;
    private GameModel gm;
    private Rectangle rect = new Rectangle();
    private FireStrategy fs;

    private boolean living = true;
    private boolean moving = true;

    public Tank(int x, int y, Dir dir, Group group, GameModel gm) {
        super();
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.gm = gm;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        String goodFSName = "com.mashibing.tank.strategy.DefaultFireStrategy";
        if (group == Group.GOOD) {
            goodFSName = (String) PropertyMgr.get("goodFS");
        } else if (group == Group.BAD) {
            goodFSName = (String) PropertyMgr.get("badFS");
        }

        try {
            fs = (FireStrategy) Class.forName(goodFSName).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        if (!living) gm.remove(this);

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }
        move();
    }

    private void move() {
        if (!moving) return;

        switch (dir) {
            case LEFT:
                x -= speed;
                break;
            case UP:
                y -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case DOWN:
                y += speed;
                break;
        }

        if (this != this.getGm().getMyTank() && RandomUtils.nextInt(100) > 95)
            fire();

        if (this != this.getGm().getMyTank() && RandomUtils.nextInt(100) > 95)
            randomDir();

        boundsCheck();
        // update rect
        rect.x = this.x;
        rect.y = this.y;

    }

    public void fire() {
        fs.fire(this);
    }

    public void randomDir() {
        this.dir = Dir.values()[RandomUtils.nextInt(4)];
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

    public void die() {
        this.living = false;
    }

    //---get/set method
    public boolean isLiving() {
        return living;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Dir getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getRect() {
        return rect;
    }

    public GameModel getGm() {
        return gm;
    }
}
