package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import desenvolvimento.Game;
import desenvolvimento.Sound;
import world.Camera;
import world.World;

public class Enemy extends Entity{

	private double velocidade = 0.6;
	
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 1 ,damegedFrames = 0;
	private BufferedImage invisibleEnemy;
	private BufferedImage idleEnemy;
	private BufferedImage[] leftEnemy;
	private BufferedImage[] rightEnemy;
	private BufferedImage[] upEnemy;
	private BufferedImage[] downEnemy;
	private BufferedImage[] damegedEnemy;
	private boolean moved = false;
	private int direction;
	private String estado = "VIVO";
	
	public Enemy(int x, int y, int altura, int largura, BufferedImage sprite) {
		super(x, y, altura, largura, null);
		idleEnemy = Game.spritesheet.getSprite(112, 0, 16, 16);
		invisibleEnemy = Game.spritesheet.getSprite(112, 64, 16, 16);
		leftEnemy = new BufferedImage[2];
		rightEnemy = new BufferedImage[2];
		upEnemy = new BufferedImage[2];
		downEnemy = new BufferedImage[2];
		damegedEnemy = new BufferedImage[4];
		for(int i = 0; i < (maxIndex + 1); i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(128 + (16*i), 0 , 16, 16);
		}
		for(int i = 0; i < (maxIndex + 1); i++) {
			leftEnemy[i] = Game.spritesheet.getSprite(128 + (16*i), 16 , 16, 16);
		}
		for(int i = 0; i < (maxIndex + 1); i++) {
			downEnemy[i] = Game.spritesheet.getSprite(128 + (16*i), 32 , 16, 16);
		}
		for(int i = 0; i < (maxIndex + 1); i++) {
			upEnemy[i] = Game.spritesheet.getSprite(128 + (16*i), 48 , 16, 16);
		}
		for(int i = 0; i < (4); i++) {
			damegedEnemy[i] = Game.spritesheet.getSprite(112 , 0 + (16*i) , 16, 16);
		}
	}

	public void tick() {
		//Inteligencia do inimigo que faz ele seguir o jogador
		if (isColidingWithPlayer() == false) {
			if((int)x < Game.player.getX() && World.isFree((int)(x + velocidade),this.getY()) && !isColiding((int)(x + velocidade),this.getY())) {
				x+=velocidade;
				moved = true;
				direction = 1;
			}else if ((int)x > Game.player.getX() && World.isFree((int)(x - velocidade),this.getY()) && !isColiding((int)(x - velocidade),this.getY())) {
				x-=velocidade;
				moved = true;
				direction = 2;
			}
			if((int)y < Game.player.getY() && World.isFree(this.getX(),(int)(y + velocidade)) && !isColiding(this.getX(),(int)(y + velocidade))) {
				y+=velocidade;
				moved = true;
				direction = 3;
			}else if ((int)y > Game.player.getY() && World.isFree(this.getX(),(int)(y - velocidade)) && !isColiding(this.getX(),(int)(y - velocidade))) {
				y-=velocidade;
				moved = true;
				direction = 4;
			}
		}else {
			//Inimigo atinge o jogador
			Game.player.isDameged = true;
			Game.player.saude -= 25;
			Game.entities.remove(this);
			Game.enemies.remove(this);
			Sound.hitFx.play();
			return;
		}
		//Animações
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		if(estado == "MORENDO") {
			damegedFrames++;
			if (this.damegedFrames == 12) {
				estado = "MORTO";
			}
		}
		
		alcoolColision();
		checkMorte();
		
	}
	
	public boolean isColidingWithPlayer() {
		Rectangle currentEnemy = new Rectangle(getX(),getY(),World.TILE_SIZE,World.TILE_SIZE);
		Rectangle player = new Rectangle(Game.player.getX(),Game.player.getY(),16,16);
		return currentEnemy.intersects(player);
	}
	
	public boolean isColiding(int xProximo, int yProximo) {
		Rectangle currentEnemy = new Rectangle(xProximo,yProximo,World.TILE_SIZE,World.TILE_SIZE);
		for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX(),e.getY(),World.TILE_SIZE,World.TILE_SIZE);
			if(currentEnemy.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void alcoolColision() {
		for(int i = 0; i < Game.alcool.size(); i++) {
			Entity e = Game.alcool.get(i);
			if(e instanceof Alcool) {
				if(isColiding(this,e)) {
					Sound.hitFx.play();
					estado = "MORENDO";
					Game.alcool.remove(i);
					return;
				}
			}
		}
	}
	
	public void checkMorte() {
		if(estado == "MORTO") {
			Game.entities.remove(this);
			Game.enemies.remove(this);
			return;
		}
	}
	
	public void render(Graphics gra) {
		if(Game.player.avistando > 0) {
			if(estado == "VIVO") {
			switch (direction) {
			case 1:
				gra.drawImage(rightEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
				break;
			case 2:
				gra.drawImage(leftEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
				break;
			case 3:
				gra.drawImage(downEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
				break;
			case 4:
				gra.drawImage(upEnemy[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
				break;
				default:
					gra.drawImage(idleEnemy,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
			}else if(estado == "MORENDO"){
				gra.drawImage(damegedEnemy[(damegedFrames)/3],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}else {
			gra.drawImage(invisibleEnemy,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
}
