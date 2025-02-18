package desenvolvimento;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import entidades.Alcool;
import entidades.Enemy;
import entidades.Entity;
import entidades.Lixo;
import entidades.Player;
import graficos.SpriteSheet;
import graficos.Ui;
import world.World;


public class Game extends Canvas implements Runnable,KeyListener{

	//V�riaveis para customiza��o da tela
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 3;
	
	//Determina a quantidade de n�veis disponiveis
	private int level_atual = 1, level_max = 5;
	
	//V�riavel que gera a imagem de fundo
	private BufferedImage image;
	
	//Listas onde as entidades s�o colocadas e depois ticadas e renderizadas
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<Alcool> alcool;
	public static List<Lixo> lixos;
	
	//V�riaveis de objetos que puxam imagens e o estado atual do jogo
	public static SpriteSheet spritesheet;
	public static SpriteSheet menuBackGround;
	public static SpriteSheet menuSobre;
	public static World world;
	public static Player player;
	public static Random rand;
	public static String estadoGame = "MENU";
	private boolean restartGame = false;
	public Ui ui;
	public Menu menu;
	public Sobre sobre;
	
	public Game() {
		Sound.musicBackGround.loop();
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		//Inicializando objetos
		ui = new Ui();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		lixos = new ArrayList<Lixo>();
		alcool = new ArrayList<Alcool>();
		
		spritesheet = new SpriteSheet("/spritesheet.png");
		menuBackGround = new SpriteSheet("/menuBackgroud.png");
		menuSobre = new SpriteSheet("/menuSobre.png");
		
		player = new Player(0,0,16,16,spritesheet.getSprite(32, 0, 16, 16));
		entities.add(player);
		world = new World("/map1.png");
		
		menu = new Menu();
		sobre = new Sobre();
	}
	
	//inicializa o JFrame onde o jogo acontece
	public void initFrame(){
		frame = new JFrame("Capital Cleaning");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(estadoGame == "NORMAL") {
			this.restartGame = false;
			for(int i = 0; i < entities.size();i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			for(int i = 0; i < alcool.size();i++) {
				alcool.get(i).tick();
			}
			//Sistema de avan�o de Niveis
			if (enemies.size() == 0 || lixos.size() == 0) {
				enemies.clear();
				lixos.clear();
				level_atual++;
				if(level_atual > level_max) {
					estadoGame = "CONCLUIDO";
					level_atual = 1;
				}
				String newWorld = "map" + level_atual + ".png";
				World.restartGame(newWorld);
			}
		}else if(estadoGame == "GAME_OVER") {
			if(restartGame) {
				this.restartGame = false;
				estadoGame = "NORMAL";
				level_atual = 1;
				String newWorld = "map" + level_atual + ".png";
				World.restartGame(newWorld);
			}
		}else if(estadoGame == "CONCLUIDO") {
			if(restartGame) {
				this.restartGame = false;
				estadoGame = "NORMAL";
				level_atual = 1;
				String newWorld = "map" + level_atual + ".png";
				World.restartGame(newWorld);
			}
		}else if(estadoGame == "MENU") {
			menu.tick();
		}else if(estadoGame == "SOBRE") {
			sobre.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics gra = image.getGraphics();
		gra.setColor(new Color(20,90,10));
		gra.fillRect(0,0,WIDTH,HEIGHT);
		
		//Renderiza��o do Mundo
		world.render(gra);
		
		/*Renderiza��o da Entidades do jogo, renderizadas a partir de Lista, 
		onde todas est�o arquivadas e o loop renderiza elas*/
		for(int i = 0; i < entities.size();i++) {
			Entity e = entities.get(i);
			e.render(gra);
		}
		for(int i = 0; i < alcool.size();i++) {
			alcool.get(i).render(gra);
		}
		ui.render(gra);
		
		gra.dispose();
		gra = bs.getDrawGraphics();
		gra.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		if (estadoGame == "GAME_OVER") {
			Graphics2D gra2 = (Graphics2D) gra;
			gra2.setColor(new Color(0,0,0,150));
			gra2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			gra.setFont(new Font("arial",Font.BOLD,80));
			gra.setColor(Color.WHITE);
			gra.drawString("GAME OVER",(WIDTH*SCALE)/4, (HEIGHT*SCALE)/2);
			gra.setFont(new Font("arial",Font.BOLD,40));
			gra.drawString(">Pressione Enter<",(WIDTH*SCALE)/4 + 80, (HEIGHT*SCALE)/2 + 50);
		}else if(estadoGame == "CONCLUIDO") {
			Graphics2D gra2 = (Graphics2D) gra;
			gra2.setColor(new Color(0,0,0,150));
			gra2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			gra.setFont(new Font("arial",Font.BOLD,80));
			gra.setColor(Color.WHITE);
			gra.drawString("PARABENS",(WIDTH*SCALE)/4 + 20, (HEIGHT*SCALE)/2);
			gra.setFont(new Font("arial",Font.BOLD,40));
			gra.drawString("Obrigado por Limpar as Ruas",(WIDTH*SCALE)/4 - 30, (HEIGHT*SCALE)/2 + 50);
			gra.setFont(new Font("arial",Font.BOLD,40));
			gra.drawString("Sua Pontuacao foi de: " + Player.pontos,(WIDTH*SCALE)/4 + 20, (HEIGHT*SCALE)/2 + 100);
		}else if(estadoGame == "MENU") {
			menu.render(gra);
		}else if(estadoGame == "SOBRE") {
			sobre.render(gra);
		}
		bs.show();
	}
	
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1 ) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if(estadoGame == "MENU") {
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if(estadoGame == "MENU") {
				menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_SPACE ) {
			player.atirando = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			Player.pontos = 0;
			if(estadoGame == "MENU") {
				menu.enter = true;
			}else if(estadoGame == "SOBRE") {
				sobre.enter = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			estadoGame = "MENU";
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
