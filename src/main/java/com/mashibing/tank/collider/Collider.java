package com.mashibing.tank.collider;

import com.mashibing.tank.GameObject;

public interface Collider {

	boolean collide(GameObject o1, GameObject o2);
}
