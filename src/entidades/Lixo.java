package entidades;

import java.awt.image.BufferedImage;

import desenvolvimento.Game;

public class Lixo extends Entity{
	
	
	public Lixo(int x, int y, int altura, int largura, BufferedImage sprite) {
		super(x, y, altura, largura, sprite);
		LIXO_EN = Game.spritesheet.getSprite(80, 0 + (16*(Game.rand.nextInt(5))) , 16, 16);
	}

	
}
