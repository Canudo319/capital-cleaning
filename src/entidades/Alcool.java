package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import desenvolvimento.Game;
import world.Camera;

public class Alcool extends Entity{
	
	private int dx,dy,alLife = 0;
	private double spd = 4;
	
	public Alcool(int x, int y, int altura, int largura, BufferedImage sprite ,int dx ,int dy) {
		super(x, y, altura, largura, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x+=dx*spd;
		y+=dy*spd;
		alLife++;
		if(alLife >= 100) {
			Game.alcool.remove(this);
			return;
		}
	}
	public void render(Graphics gra) {
		gra.setColor(Color.CYAN);
		gra.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 8, 8);

	}
	
}
