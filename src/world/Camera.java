package world;

public class Camera {

	public static int x,y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static int clamp(int atual,int min, int max) {
		if(atual < min) {
			atual = min;
		}
		if(atual > max) {
			atual = max;
		}
		
		return atual;
	}
	
}
