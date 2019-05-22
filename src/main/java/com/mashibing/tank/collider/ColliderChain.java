package com.mashibing.tank.collider;

import com.mashibing.tank.GameObject;

import java.util.LinkedList;
import java.util.List;

public class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>();

    public void add(Collider c) {
        this.colliders.add(c);
    }

    @Override
    public boolean collide(GameObject gameObject1, GameObject gameObject2) {
        for (int i = 0; i < colliders.size(); i++) {
            if (!colliders.get(i).collide(gameObject1, gameObject2)) {
                return false;
            }
        }
        return true;
    }


}
