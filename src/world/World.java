package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import desenvolvimento.Game;
import entidades.*;
import graficos.SpriteSheet;


public class World {
	
	public static Tile[] tiles;
	public static int largura,altura;
	public static final int TILE_SIZE = 16;
	

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			largura = map.getWidth();
			altura = map.getHeight();
			tiles = new Tile[largura * altura];
			map.getRGB(0, 0, largura, altura, pixels, 0, largura);
			for(int xx = 0; xx < largura; xx++) {
				for(int yy = 0; yy < altura; yy++) {
					//A opção Default coloca o chão padrão
					tiles[xx + (yy * largura)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR , Game.rand.nextInt(6));
					if(pixels[xx + (yy * largura)] == 0xFF000000) {
						//chão
						tiles[xx + (yy * largura)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_FLOOR,Game.rand.nextInt(6));
					}else if(pixels[xx + (yy * largura)] == 0xFFFFFFFF) {
						//parede
						tiles[xx + (yy * largura)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE, Tile.TILE_WALL);
					}else if(pixels[xx + (yy * largura)] == 0xff0000ff) {
						//player
						Game.player.setX(xx * TILE_SIZE);
						Game.player.setY(yy * TILE_SIZE);
					}else if(pixels[xx + (yy * largura)] == 0xFFFF0000) {
						//inimigo
						Enemy en = new Enemy(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}else if(pixels[xx + (yy * largura)] == 0xFF00cbff) {
						//borrifador
						Game.entities.add(new Borrifador(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.BORRIFADOR_EN));
					}else if(pixels[xx + (yy * largura)] == 0xFF00FF00){
						//mascara
						Game.entities.add(new Mascara(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.MASCARA_EN));
					}else if(pixels[xx + (yy * largura)] == 0xFFFF0099){
						//monoculo
						Game.entities.add(new Monoculo(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.MONOCULO_EN));
					}else if(pixels[xx + (yy * largura)] == 0xFF707070) {
						//Lixo
						Lixo lx = new Lixo(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Entity.LIXO_EN);
						Game.entities.add(lx);
						Game.lixos.add(lx);
					}
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xProximo, int yProximo) {
		int x1 = xProximo / TILE_SIZE;
		int y1 = yProximo / TILE_SIZE;
		
		int x2 = (xProximo + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yProximo / TILE_SIZE;
		
		int x3 = xProximo / TILE_SIZE;
		int y3 = (yProximo + TILE_SIZE - 1) / TILE_SIZE;
		
		int x4 = (xProximo + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yProximo + TILE_SIZE - 1) / TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.largura)] instanceof WallTile ||
				 tiles[x2 + (y2*World.largura)] instanceof WallTile ||
				 tiles[x3 + (y3*World.largura)] instanceof WallTile ||
				 tiles[x4 + (y4*World.largura)] instanceof WallTile );
	}
	
	public static void restartGame(String level) {
		Game.entities.clear();
		Game.enemies.clear();
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.lixos = new ArrayList<Lixo>();
		Game.spritesheet = new SpriteSheet("/spritesheet.png");
		Game.menuBackGround = new SpriteSheet("/menuBackgroud.png");
		Game.menuSobre = new SpriteSheet("/menuSobre.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/"+ level);
		return;
	}
	
	public void render(Graphics gra) {
		for(int xx = 0; xx < largura; xx++) {
			for(int yy = 0; yy < altura; yy++) {
				Tile tile = tiles[xx + (yy * largura)];
				tile.render(gra);
			}
		}
		
	}
	
	
}
