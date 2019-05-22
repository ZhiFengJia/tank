package com.mashibing.tank.collider;

import com.mashibing.tank.Bullet;
import com.mashibing.tank.Explode;
import com.mashibing.tank.GameObject;
import com.mashibing.tank.Tank;

public class TankBulletCollider implements Collider {

    @Override
    public boolean collide(GameObject gameObject1, GameObject gameObject2) {
        if (gameObject1 instanceof Tank && gameObject2 instanceof Bullet) {
            Tank tank = (Tank) gameObject1;
            Bullet bullet = (Bullet) gameObject2;
            if (collideWith(tank, bullet)) {
                return false;
            }
        } else if (gameObject2 instanceof Tank && gameObject1 instanceof Bullet) {
            return collide(gameObject2, gameObject1);
        }
        return true;
    }

    private boolean collideWith(Tank tank, Bullet bullet) {
        if (bullet.getGroup() == tank.getGroup()) return false;

        if (bullet.getRect().intersects(tank.getRect())) {
            tank.die();
            bullet.die();
            int eX = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int eY = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            tank.getGm().add(new Explode(eX, eY, tank.getGm()));
            return true;
        }
        return false;
    }

}
