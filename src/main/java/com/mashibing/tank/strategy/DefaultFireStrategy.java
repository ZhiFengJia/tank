package com.mashibing.tank.strategy;

import com.mashibing.tank.Audio;
import com.mashibing.tank.Bullet;
import com.mashibing.tank.GameModel;
import com.mashibing.tank.Tank;

public class DefaultFireStrategy implements FireStrategy {

    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int bY = tank.getY() + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        tank.getGm().add(new Bullet(bX, bY, tank.getDir(), tank.getGroup(), tank.getGm()));

        if (tank == tank.getGm().getMyTank())
            GameModel.threadPool.execute(() -> new Audio("audio/tank_fire.wav").play());
    }

}
