package com.mashibing.tank.strategy;

import com.mashibing.tank.*;

public class FourDirFireStrategy implements FireStrategy {

    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            tank.getGm().add(new Bullet(bX, bY, dir, tank.getGroup(), tank.getGm()));
        }

        if (tank == tank.getGm().getMyTank())
            GameModel.threadPool.execute(() -> new Audio("audio/tank_fire.wav").play());
    }

}
