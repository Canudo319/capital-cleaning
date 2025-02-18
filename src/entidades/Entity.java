package entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import desenvolvimento.Game;
import world.Camera;

/*Essa clase é a classe pai que fara com que o jogador, itens, e inimigos herdem caracteristicas da sua aparencia
 * ela indica em qual arquivo o jogo será referenciado para puxar imagens
 */

public class Entity {

	public static BufferedImage BORRIFADOR_EN = Game.spritesheet.getSprite(96, 0, 16, 16);
	public static BufferedImage MASCARA_EN = Game.spritesheet.getSprite(96, 16, 16, 16);
	public static BufferedImage MONOCULO_EN = Game.spritesheet.getSprite(96, 32, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(112, 0, 16, 16);
	public static BufferedImage LIXO_EN = Game.spritesheet.getSprite(80, 0 , 16, 16);
	
	//Atributos nescessarios para iniciar e puxar imagens do SpriteSheet 
	protected double x,y;
	protected int altura,largura;
	private BufferedImage sprite;
	
	private int maskx,masky,mwidth,mheight;
	
	
	//Metodo construtor que Exige ao criar uma entidade qual o Sprite que essa entidade usar dentro do jogo
	public Entity(int x,int y, int altura, int largura, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.altura = altura;
		this.largura = largura;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.maskx = 0;
		this.mwidth = largura;
		this.mheight = altura;
	}

	public void setMask(int maskx,int masky,int mwidth ,int mheight) {
		this.maskx = maskx;
		this.maskx = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void tick() {
		
	}
	
	public static boolean isColiding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics gra) {
		gra.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	
	
	//Metodos de Geters e Seters
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getAltura() {
		return this.altura;
	}
	
	public int getLargura() {
		return this.largura;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
}
