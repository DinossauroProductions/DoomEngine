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
		public static final int W2 = WIDTH/2, H2 = HEIGHT/2;
		
		
		public int maxFPS = 20;
		public static int FPS = 0;
		
		private BufferedImage image;
		
		
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
			
			
			
			player.tick();
			
			
			
			
		}
		public void render() {
			BufferStrategy bs = this.getBufferStrategy();
			if (bs == null) {
				this.createBufferStrategy(3);
				return;
			}
			Graphics g = image.getGraphics();
			
			g.setColor(new Color(0, 0, 8));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			
			
			//render stuff
			
			draw3D(g);
			
			
			
			
				
			//stop render
			
			
			g.dispose();
			g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, (int)(WIDTH*SCALE), (int)(HEIGHT*SCALE), null);
			
			bs.show();
			
		}
		
		public int[] clipBehindPlayer(int x1, int y1, int z1, int x2, int y2, int z2) {
			
			float da = y1;
			float db = y2;
			float d = da-db; if(d == 0) {d=1;}
			float s = da/(da-db);
			
			int[] result = new int[3];
			
			x1 = (int) (x1 + s*(x2-(x1)));
			result[0] = x1;
			y1 = (int) (y1 + s*(y2-(y1))); if(y1==0) {y1=1;}
			result[1] = y1;
			z1 = (int) (z1 + s*(z2-(z1)));
			result[2] = z1;
			
			return result;
			
			
		}
		
		public void drawWall(Graphics g, int x1, int x2, int b1, int b2, int t1, int t2) {
			int x, y;
			//hold difference in distance
			int dyb 	= b2-b1;		//y distance of bottom line
			int dyt 	= t2-t1;		//y distance of top    line
			int dx 		= x2-x1;		//x distance
			if(dx == 0) {
				dx = 1;
			}
			int xs = x1;			//hold initial x1 starting position
			
			//clip X
			
			if(x1 < 		1) { x1 =         1;} //clip left
			if(x2 < 		1) { x2 = 		  1;} //clip left
			if(x1 > WIDTH - 1) { x1 = WIDTH - 1;} //clip right
			if(x2 > WIDTH - 1) { x2 = WIDTH - 1;} //clip right
			
			
			//draw x verticle lines
			for(x = x1; x < x2; x++) {
				
				//the y start and end point
				int y1 = (int) (dyb * (x-xs+0.5)/dx+b1);	//y bottom point
				int y2 = (int) (dyt * (x-xs+0.5)/dx+t1);	//y top	   point
				
				//clip Y
				
				if(y1 < 	 	 1) { y1 =          1;} //clip y
				if(y2 <  		 1) { y2 = 		    1;} //clip y
				if(y1 > HEIGHT - 1) { y1 = HEIGHT - 1;} //clip y
				if(y2 > HEIGHT - 1) { y2 = HEIGHT - 1;} //clip y
				
				for(y = y1;y<y2;y++) {
					g.drawLine(x, y, x, y);
				}
			}
			
		}
		
		
		public void draw3D(Graphics g) {
			
			int wx[] = new int[4], wy[]= new int[4], wz[]= new int[4];
			double cs = Utility.cos[player.getA()], sn = Utility.sin[player.getA()];
			
			//offset bottom 2 points by player
			
			int x1 = 40  - player.getX();
			int y1 = 10  - player.getY();
			int x2 = 40  - player.getX();
			int y2 = 290 - player.getY();
			
			//world X position
			wx[0] = (int) (x1*cs - y1*sn);
			wx[1] = (int) (x2*cs - y2*sn);
			wx[2] = wx[0];
			wx[3] = wx[1];
			
			//world y position
			wy[0] = (int) (y1*cs + x1*sn);
			wy[1] = (int) (y2*cs + x2*sn);
			wy[2] = wy[0];
			wy[3] = wy[1];
			
			//world z height
			wz[0] = (int) (0-player.getZ() + ((player.getL()*wy[0])/32.0));
			wz[1] = (int) (0-player.getZ() + ((player.getL()*wy[0])/32.0));
			wz[2] = wz[0] + 40;
			wz[3] = wz[1] + 40;
			
			//dont draw if behind player
			if(wy[0] < 1 && wy[1] < 1) { return; }
			
			
			//point 1 behind player, clip
			if(wy[0] < 1) {
				
				int[] newPos = clipBehindPlayer(wx[0], wy[0], wz[0], wx[1], wy[1], wz[1]);	//bottom line
				wx[0] = newPos[0];
				wy[0] = newPos[1];
				wz[0] = newPos[2];
				newPos = clipBehindPlayer(wx[2], wy[2], wz[2], wx[3], wy[3], wz[3]); 		//top    line
				wx[2] = newPos[0];
				wy[2] = newPos[1];
				wz[2] = newPos[2];
			}
			
			//point 2 behind player, clip
			if(wy[1] < 1) {
				
				int[] newPos = clipBehindPlayer(wx[1], wy[1], wz[1], wx[0], wy[0], wz[0]); //bottom line
				wx[1] = newPos[0];
				wy[1] = newPos[1];
				wz[1] = newPos[2];
				newPos = clipBehindPlayer(wx[3], wy[3], wz[3], wx[2], wy[2], wz[2]); //top    line
				wx[3] = newPos[0];
				wy[3] = newPos[1];
				wz[3] = newPos[2];
			}
			
			//screen x, screen y position
			wx[0] = wx[0] * 200/wy[0]+W2;
			wy[0] = wz[0] * 200/wy[0]+H2;
			
			wx[1] = wx[1] * 200/wy[1]+W2;
			wy[1] = wz[1] * 200/wy[1]+H2;
			
			wx[2] = wx[2] * 200/wy[2]+W2;
			wy[2] = wz[2] * 200/wy[2]+H2;
			
			wx[3] = wx[3] * 200/wy[3]+W2;
			wy[3] = wz[3] * 200/wy[3]+H2;
			
			//draw points
			
			g.setColor(new Color(255, 255, 0));
			
			drawWall(g, wx[0], wx[1], wy[0], wy[1], wy[2], wy[3]);
			
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
