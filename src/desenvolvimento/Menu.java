package desenvolvimento;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Menu {

	private BufferedImage menuBackground = Game.menuBackGround.getSprite(0, 0, Game.WIDTH, Game.HEIGHT);
	private BufferedImage selecter = Game.spritesheet.getSprite(32, 32, 16, 16);
	private BufferedImage enterSprite = Game.spritesheet.getSprite(32, 48, 16, 16);
	
	public String[] opitions = {"Jogar","Sobre","Sair"};
	
	public int currentopition = 0;
	public int maxOpition = opitions.length - 1;
	
	public boolean up,down,enter;
	
	public void tick() {
		if(up) {
			up = false;
			currentopition--;
			if(currentopition < 0) {
				currentopition = maxOpition;
			}
		}if(down) {
			down = false;
			currentopition++;
			if(currentopition > maxOpition) {
				currentopition = 0;
			}
		}
		if(enter) {
			enter = false;
			if(opitions[currentopition] == "Jogar") {
				Game.estadoGame = "NORMAL";
				Sound.selectFx.play();
			}else if(opitions[currentopition] == "Sobre"){
				Game.estadoGame = "SOBRE";
				Sound.selectFx.play();
			}else if(opitions[currentopition] == "Sair"){
				Sound.selectFx.play();
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics gra) {
		gra.setColor(Color.BLACK);
		gra.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		gra.drawImage(menuBackground, 0, 0,Game.WIDTH * Game.SCALE,Game.HEIGHT * Game.SCALE, null);
		gra.setColor(Color.WHITE);
		gra.setFont(new Font("arial",Font.BOLD,40));
		gra.drawString("Jogar", ((Game.WIDTH*Game.SCALE)/2)-50, ((Game.WIDTH*Game.SCALE)/2)-100);
		gra.drawString("Sobre", ((Game.WIDTH*Game.SCALE)/2)-50, ((Game.WIDTH*Game.SCALE)/2)-30);
		gra.drawString("Sair", ((Game.WIDTH*Game.SCALE)/2)-35, ((Game.WIDTH*Game.SCALE)/2)+40);
		
		if(opitions[currentopition] == "Jogar") {
			gra.drawImage(selecter, ((Game.WIDTH*Game.SCALE)/2)-125, ((Game.WIDTH*Game.SCALE)/2)-130,16*Game.SCALE,16*Game.SCALE, null);
			gra.drawImage(enterSprite, ((Game.WIDTH*Game.SCALE)/2)+90, ((Game.WIDTH*Game.SCALE)/2)-130,16*Game.SCALE,16*Game.SCALE, null);
		}else if(opitions[currentopition] == "Sobre") {
			gra.drawImage(selecter, ((Game.WIDTH*Game.SCALE)/2)-125, ((Game.WIDTH*Game.SCALE)/2)-70,16*Game.SCALE,16*Game.SCALE, null);
			gra.drawImage(enterSprite, ((Game.WIDTH*Game.SCALE)/2)+90, ((Game.WIDTH*Game.SCALE)/2)-70,16*Game.SCALE,16*Game.SCALE, null);
		}else if(opitions[currentopition] == "Sair") {
			gra.drawImage(selecter, ((Game.WIDTH*Game.SCALE)/2)-125, ((Game.WIDTH*Game.SCALE)/2),16*Game.SCALE,16*Game.SCALE, null);
			gra.drawImage(enterSprite, ((Game.WIDTH*Game.SCALE)/2)+90, ((Game.WIDTH*Game.SCALE)/2),16*Game.SCALE,16*Game.SCALE, null);
		}
	}
}
