package entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import desenvolvimento.Game;
import desenvolvimento.Sound;
import world.Camera;
import world.World;

public class Player extends Entity{

	public static int pontos = 0;
	
	public boolean right,left,up,down;
	public double velocidade = 2;
	public double saude = 100;
	public double alcool = 0;
	public double avistando = 0;
	private int dx,dy;
	private int shootDelay = 0;
	
	private int frames = 0, maxFrames = 12, index = 0, maxIndex = 1, damegedFrames = 0;
	private BufferedImage idlePlayer;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage damagedPlayer;
	private boolean armado = false;
	public boolean atirando = false;
	private boolean moved = false;
	public boolean isDameged = false;
	
	
	public Player(int x, int y, int altura, int largura, BufferedImage sprite) {
		super(x, y, altura, largura, sprite);
		idlePlayer = Game.spritesheet.getSprite(32, 0, 16, 16);
		rightPlayer = new BufferedImage[2];
		leftPlayer = new BufferedImage[2];
		upPlayer = new BufferedImage[2];
		downPlayer = new BufferedImage[2];
		damagedPlayer = Game.spritesheet.getSprite(32, 16, 16, 16);
		for(int i = 0; i < (maxIndex + 1); i++){
			rightPlayer[i] = Game.spritesheet.getSprite(48 + (16*i), 0, 16, 16);
		}
		for(int i = 0; i < (maxIndex + 1); i++){
			leftPlayer[i] = Game.spritesheet.getSprite(48 + (16*i), 16, 16, 16);
		}
		for(int i = 0; i < (maxIndex + 1); i++){
			downPlayer[i] = Game.spritesheet.getSprite(48 + (16*i), 32, 16, 16);
		}
		for(int i = 0; i < (maxIndex + 1); i++){
			upPlayer[i] = Game.spritesheet.getSprite(48 + (16*i), 48, 16, 16);
		}
		
	}

	
	public void tick() {
		//Movimentação
		moved = false;
		if(right && World.isFree((int)(x + velocidade), this.getY())) {
			moved = true;
			x+=velocidade;
			dx = 1;
			dy = 0;
		}else if(left && World.isFree((int)(x - velocidade), this.getY())) {
			moved = true;
			x-=velocidade;
			dx = -1;
			dy = 0;
		}
		if(up && World.isFree(this.getX(),(int)(y - velocidade))) {
			moved = true;
			y-=velocidade;
			dx = 0;
			dy = -1;
		}else if(down && World.isFree(this.getX(),(int)(y + velocidade))) {
			moved = true;
			y+=velocidade;	
			dx = 0;
			dy = 1;
		}
		//Animações do Player
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
		if(isDameged) {
			damegedFrames ++;
			if(this.damegedFrames == 5) {
				this.damegedFrames = 0;
				isDameged = false;
			}
		}
		
		//Verificações de Saude e  alcool
		if(atirando && armado && shootDelay==30) {
			atirando = false;
			Alcool alc = new Alcool(this.getX(),this.getY(),16,16,Game.spritesheet.getSprite(96, 80, 16, 16),dx,dy);
			Game.alcool.add(alc);
			alcool -=20;
			shootDelay = 0;
		}
		
		if(saude <= 0) {
			//Game over
			Game.estadoGame = "GAME_OVER";
		}
		
		if(alcool <= 0) {
			alcool = 0;
			armado = false;
			atirando = false;
		}
		
		Camera.x = Camera.clamp(this.getX() - Game.WIDTH/2,0, World.largura*16 - Game.WIDTH) ;
		Camera.y = Camera.clamp(this.getY() - Game.HEIGHT/2,0, World.altura*16 - Game.HEIGHT) ;
		
		if(avistando > 0) {
			avistando--;
		}
		
		if(shootDelay < 30) {
			shootDelay++;
		}
		
		checkCollision();
		
	}
	
	public void checkCollision() {
		for(int i = 0;i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Mascara) {
				if(Entity.isColiding(this, atual)) {
					saude = 100;
					Game.entities.remove(atual);
					Sound.colectFx.play();
				}
			}else if(atual instanceof Borrifador) {
				if(Entity.isColiding(this, atual)) {
					alcool = 100;
					Game.entities.remove(atual);
					armado = true;
					Sound.colectFx.play();
				}
			}else if(atual instanceof Lixo) {
				if(Entity.isColiding(this, atual)) {
					pontos+=1;
					Game.entities.remove(atual);
					Game.lixos.remove(atual);
					Sound.colectFx.play();
				}
			}else if(atual instanceof Monoculo) {
				if(Entity.isColiding(this, atual)) {
					avistando = 500;
					Game.entities.remove(atual);
					Sound.colectFx.play();
				}
			}
		}
	}
	
	public void render(Graphics gra) {
		if(!isDameged) {
			if(right) {
				gra.drawImage(rightPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(left) {
				gra.drawImage(leftPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(down){
				gra.drawImage(downPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(up){
				gra.drawImage(upPlayer[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else {
				gra.drawImage(idlePlayer,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}else {
			gra.drawImage(damagedPlayer,this.getX() - Camera.x,this.getY() - Camera.y,null);
		}

	}
	
}