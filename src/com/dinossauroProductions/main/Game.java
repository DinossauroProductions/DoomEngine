package com.dinossauroProductions.main;

	
	import java.awt.Canvas;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
	import java.awt.image.BufferStrategy;
	import java.awt.image.BufferedImage;

	import javax.swing.JFrame;
	
	import com.dinossauroProductions.main.Input;


	public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
		
		private static final long serialVersionUID = 1L;
		
		public static JFrame frame;
		private Thread thread;
		public boolean isRunning = true;
		public static final int res = 1;
		public static final int WIDTH = 160*res;
		public static final int HEIGHT = 120*res;
		public static final double SCALE = 4;
		public int maxFPS = 20;
		private BufferedImage image;
		public static int FPS = 0;
		
		public static Input[] inputs = Input.setUpInputs();
		
		public static Player player;
		
		

		public Game() {
			addKeyListener(this);
			addMouseListener(this);
			setPreferredSize(new Dimension((int)(WIDTH*SCALE),(int)(HEIGHT*SCALE)));
			initFrame();
			
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			
		}
		
		public static void main(String args[]) {
			Game game = new Game();
			game.start();
			Utility.loadUtility();
			player = new Player();
		}
		
		public void initFrame() {
			frame = new JFrame("Game");
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
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void run() {
			long lastTime = System.nanoTime();
			double amountOfTicks = 60.0;
			double ns = 1000000000 / amountOfTicks;
			double delta = 0;
			int frames = 0;
			double timer = System.currentTimeMillis();
			requestFocus();
			while(isRunning){
				long now = System.nanoTime();
				delta+= (now - lastTime) / ns;
				lastTime = now;
				if(delta >= 1) {
					tick();
					render();
					frames++;
					delta--;
				}
				
				if(System.currentTimeMillis() - timer >= 1000){
					
					FPS = frames;
					frames = 0;
					timer+=1000;
					
				}
				
			}
			
			stop();
		}
		public void tick() {
			
			//aplicar lógica
			
			for(int i = 0; i < inputs.length; i++) {
				if(inputs[i].getState() == true) {
					System.out.println(inputs[i].getName());
				}
			}
			
			player.tick();
			
			
			
			
		}
		public void render() {
			BufferStrategy bs = this.getBufferStrategy();
			if (bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = image.getGraphics();
			
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			
			//render stuff
			
			
			g.setColor(Color.black);
			g.fillRect(WIDTH/2, HEIGHT/2, 40, 40);
			
				
			//stop render
			
			
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, (int)(WIDTH*SCALE), (int)(HEIGHT*SCALE), null);
			
			bs.show();
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//receber os inputs

		public void keyPressed(KeyEvent e) {
			
			
			for(int i = 0; i < inputs.length; i++) {
				if(inputs[i].getKeyCode(0) == e.getKeyCode() || inputs[i].getKeyCode(1) == e.getKeyCode()) {
					//o input de número 'i' foi ativado.
					inputs[i].setState(true);
				}
			}
			
			
		}

		public void keyReleased(KeyEvent e) {
			
			/*
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				
			}
			*/
			
			for(int i = 0; i < inputs.length; i++) {
				if(inputs[i].getKeyCode(0) == e.getKeyCode() || inputs[i].getKeyCode(1) == e.getKeyCode()) {
					//o input de número 'i' foi desativado.
					inputs[i].setState(false);
				}
			}
					
		}

		public void keyTyped(KeyEvent e) {
			
			
		}

		public void mouseClicked(MouseEvent e) {
			
			
		}

		public void mouseEntered(MouseEvent arg0) {
			
			
		}

		public void mouseExited(MouseEvent arg0) {
		
			
		}
		
		public void mousePressed(MouseEvent e) {
			
			
		}

		public void mouseReleased(MouseEvent arg0) {
			
			
		}

	}
