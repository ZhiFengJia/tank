package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Explode extends GameObject {
	public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();
	
	private int x, y;
	private GameModel gm;

	private int step = 0;
	
	public Explode(int x, int y, GameModel gm) {
		this.x = x;
		this.y = y;
		this.gm = gm;

		GameModel.threadPool.execute(() -> new Audio("audio/explode.wav").play());
	}

	public void paint(Graphics g) {
		g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		if(step >= ResourceMgr.explodes.length) 
			gm.remove(this);
	}
}
