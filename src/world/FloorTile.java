package world;

import java.awt.image.BufferedImage;

import desenvolvimento.Game;

public class FloorTile extends Tile{

	
	public FloorTile(int x, int y, BufferedImage sprite ,int mixFloor) {
		super(x, y, sprite);
		
		TILE_FLOOR = Game.spritesheet.getSprite(0,0+(16*(mixFloor)),16,16);
		
	}

}
