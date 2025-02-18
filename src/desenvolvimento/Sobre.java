package desenvolvimento;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sobre {

	private BufferedImage sobreBackground = Game.menuSobre.getSprite(0, 0, Game.WIDTH, Game.HEIGHT);
	
	public boolean enter;
	
	public void tick() {
		if(enter) {
			enter = false;
			Game.estadoGame = "MENU";
			Sound.selectFx.play();
		}
	}
	
	public void render(Graphics gra) {
		gra.drawImage(sobreBackground, 0, 0,Game.WIDTH * Game.SCALE,Game.HEIGHT * Game.SCALE, null);
	}
}
