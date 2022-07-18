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


	public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
		
		private static final long serialVersionUID = 1L;
		
		public static JFrame frame;
		private Thread thread;
		public boolean isRunning = true;
		public static final int WIDTH = 360*2;
		public static final int HEIGHT = 160*2;
		public static final double SCALE = 1;
		public int maxFPS = 60;
		private BufferedImage image;
		public static int FPS = 0;
		

		

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
			
			//teste 2
			
			
			
			
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
			
			
			
			
				
			//stop render
			
			
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, (int)(WIDTH*SCALE), (int)(HEIGHT*SCALE), null);
			
			bs.show();
			
		}

		public void keyPressed(KeyEvent e) {
			
			
		}

		public void keyReleased(KeyEvent e) {
			
			/*
			if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
				
			}
			*/
					
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
