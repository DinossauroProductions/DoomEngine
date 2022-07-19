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
		private static final int numSect = 4;
		private static final int numWall = 16;
		public static Wall[] walls = new Wall[numWall];
		public static Sector[] sectors = new Sector[numSect];
		
		
		//wall start, wall end, z1 height, z2 height
		private static int loadSectors[] = {
					0 , 4 , 0 , 40,	0xfee761, 0xf77622,		//sector 1
					4 , 8 , 0 , 40,	0x63c74d, 0x265c42,		//sector 2
					8 , 12, 0 , 40,	0x2ce8f5, 0x124e89,		//sector 3
					12, 16, 0 , 40,	0xe43b44, 0x3f2832		//sector 4
					};		

		private static int loadWalls[]= 
			{//x1, y1, x2, y2, color
					0 , 0 , 32, 0 , 0xfee761,
					32, 0 , 32, 32, 0xfeae34,
					32, 32, 0 , 32, 0xfee761,
					0 , 32, 0 , 0 , 0xfeae34,
					
					64, 0 , 96, 0 , 0x63c74d,
					96, 0 , 96, 32, 0x3e8948,
					96, 32, 64, 32, 0x63c74d,
					64, 32, 64, 0 , 0x3e8948,
					
					64, 64, 96, 64, 0x2ce8f5,
					96, 64, 96, 96, 0x0095e9,
					96, 96, 64, 96, 0x2ce8f5,
					64, 96, 64, 64, 0x0095e9,
					
					0 , 64, 32, 64, 0xe43b44,
					32, 64, 32, 96, 0x9e2835,
					32, 96, 0 , 96, 0xe43b44,
					0 , 96, 0 , 64, 0x9e2835
			};
		
		
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
			
			//load utility class
			Utility.loadUtility();
			
			//load player
			player = new Player();
			
			//load sectors
			int s, w, v1=0, v2=0;
			for(s=0;s<numSect;s++) {
				sectors[s] = new Sector(0, 0, 0, 0, 0, 0, 0);
				sectors[s].setWs(loadSectors[v1+0]); 
				sectors[s].setWe(loadSectors[v1+1]); 
				sectors[s].setZ1(loadSectors[v1+2]); 
				sectors[s].setZ2(loadSectors[v1+3]); 
				sectors[s].setC1(new Color(loadSectors[v1+4])); 
				sectors[s].setC2(new Color(loadSectors[v1+5])); 
				v1+=6;
				for(w=sectors[s].getWs(); w<sectors[s].getWe();w++) {
					walls[w] = new Wall(0, 0, 0, 0, null);
					walls[w].setX1(loadWalls[v2+0]);
					walls[w].setY1(loadWalls[v2+1]);
					walls[w].setX2(loadWalls[v2+2]);
					walls[w].setY2(loadWalls[v2+3]);
					walls[w].setC (new Color(loadWalls[v2+4]));
					v2+=5;
				}
			}
			
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
		
		public void drawWall(Graphics g, int x1, int x2, int b1, int b2, int t1, int t2, int s, int w) {
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
				
				if(sectors[s].getSurface() == 1 ) { sectors[s].setSurf(y1, x); continue; }  //save bottom points
				if(sectors[s].getSurface() == 2 ) { sectors[s].setSurf(y2, x); continue; }  //save top points
				if(sectors[s].getSurface() == -1) { 
					for(y = sectors[s].getSurf()[x]; y < y1; y++) {
						g.setColor(sectors[s].getC1());
						g.drawLine(x, y, x, y);		//bottom
					} 
				}
				if(sectors[s].getSurface() == -2) { 
					for(y=y2;y<sectors[s].getSurf()[x];y++) {
						g.setColor(sectors[s].getC2());
						g.drawLine(x, y, x, y);		//top
					} 
				}
				for(y = y1;y<y2;y++) {
					g.setColor(walls[w].getC());
					g.drawLine(x, y, x, y);			//normal wall
				}
			}
			
		}
		
		public int dist(int x1, int y1, int x2, int y2) {
			int distance = (int) Math.sqrt( (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) );
			return distance;
		}
		
		public void draw3D(Graphics g) {
			
			int s, w, wx[] = new int[4], wy[]= new int[4], wz[]= new int[4];
			double cs = Utility.cos[player.getA()], sn = Utility.sin[player.getA()];
			
			//order sectors by distance
			
			for(s=0;s<numSect;s++) {
				for(w=0;w<numSect-s-1;w++) {
					if(sectors[w].getD() < sectors[w+1].getD()) {
						Sector st    = sectors[w];
						sectors[w]   = sectors[w+1];
						sectors[w+1] = st;
					}
				}
			}
			
			//draw sectors
			for(s=0; s < numSect; s++) {
				
				sectors[s].setD(0);		//clear distance
				
				if(player.getZ() < sectors[s].getZ1()) {
					sectors[s].setSurface(1);			//bottom surface
				} 
				else if(player.getZ() > sectors[s].getZ2()) {
					sectors[s].setSurface(2);			//top 	 surface
				} 
				else {
					sectors[s].setSurface(0);			//no 	 surface
				}
				
				for(int loop=0;loop<2;loop++) {
					for(w = sectors[s].getWs(); w < sectors[s].getWe(); w++) {
						
						//offset bottom 2 points by player
						
						int x1 = walls[w].getX1()  - player.getX();
						int y1 = walls[w].getY1()  - player.getY();
						int x2 = walls[w].getX2()  - player.getX();
						int y2 = walls[w].getY2()  - player.getY();
						
						//swap for surface
						if(loop == 0) { int swp=x1; x1 = x2; x2 = swp; swp = y1; y1 = y2; y2 = swp;}
						
						//int swp=x1; x1 = x2; x2 = swp; swp = y1; y1 = y2; y2 = swp;  //meme
						
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
						
						sectors[s].setD(sectors[s].getD()+dist(0, 0, (wx[0]+wx[1])/2, (wy[0] + wy[1])/2));	//store this wall distance
						
						//world z height
						wz[0] = (int) (sectors[s].getZ1()-player.getZ() + ((player.getL()*wy[0])/32.0));
						wz[1] = (int) (sectors[s].getZ1()-player.getZ() + ((player.getL()*wy[0])/32.0));
						wz[2] = wz[0] + sectors[s].getZ2();
						wz[3] = wz[1] + sectors[s].getZ2();
						
						//dont draw if behind player
						if(wy[0] < 1 && wy[1] < 1) { continue; }
						
						
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
						
						
						
						drawWall(g, wx[0], wx[1], wy[0], wy[1], wy[2], wy[3], s, w);
					}
					
				
				
					sectors[s].setD(sectors[s].getD()/(sectors[s].getWe()-sectors[s].getWs()));  //find average sector distance
					sectors[s].setSurface(sectors[s].getSurface()*(-1)); 						 //flip to negative to draw surface
				}
			
			}
			
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
