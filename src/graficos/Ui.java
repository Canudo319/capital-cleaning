package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import desenvolvimento.Game;
import entidades.Player;

public class Ui {
	
	public void render(Graphics gra) {
		gra.setColor(Color.black);
		gra.fillRect(20 - 2, 10 - 2, 50 + 4, 8 + 4);
		gra.setColor(Color.red);
		gra.fillRect(20, 10, 50, 8);
		gra.setColor(Color.green);
		gra.fillRect(20, 10, (int)((Game.player.saude/100)*50), 8);
		
		gra.setColor(Color.black);
		gra.fillRect(80 - 2, 10 - 2, 50 + 4, 8 + 4);
		gra.setColor(new Color(000070));
		gra.fillRect(80, 10, 50, 8);
		gra.setColor(Color.cyan);
		gra.fillRect(80, 10, (int)((Game.player.alcool/100)*50), 8);
		
		gra.setColor(Color.black);
		gra.fillRect(140 - 2, 10 - 2, 50 + 4, 8 + 4);
		gra.setColor(new Color(2621517));
		gra.fillRect(140, 10, 50, 8);
		gra.setColor(new Color(8388855));
		gra.fillRect(140, 10, (int)((Game.player.avistando/500)*50), 8);
		
		gra.setColor(Color.black);
		gra.fillRect(200, 8, 50 + 4, 8 + 4);
		gra.setColor(Color.WHITE);
		gra.setFont(new Font("arial",Font.BOLD,9));
		gra.drawString("Pontos: "+(Player.pontos), 203, 18);
	}
}
