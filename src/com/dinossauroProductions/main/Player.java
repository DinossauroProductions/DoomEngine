package com.dinossauroProductions.main;

public class Player {
	
	private int x, y, z;
	private int a;
	private int l;
	
	public Player() {
		x = 70;
		y = -110;
		z = 20;
		a = 0;
		l = 0;
	}
	
	public void tick() {
		
		processInputs();
		
		
	}
	
	
	private void processInputs() {
		
		//olhando para os lados
		
		if(Game.inputs[Input.M].getState() == false) {
			if(Game.inputs[Input.LEFT].getState() == true) {
				//look left
				a+=4;
				if(a < 0) {
					a+=360;
				}
			}
			
			if(Game.inputs[Input.RIGHT].getState() == true) {
				//look right
				a-=4;
				if(a > 359) {
					a-=360;
				}
			}
		}
		
		//calculando a movimentação com base no ângulo
		
		int dx = (int) (Utility.sin[a] * 10);
		int dy = (int) (Utility.cos[a] * 10);
		
		//movendo a posição
		
		if(Game.inputs[Input.M].getState() == false) {
			if(Game.inputs[Input.UP].getState() == true) {
				//move up
				x+= dx;
				y+= dy;
			}
			
			if(Game.inputs[Input.DOWN].getState() == true) {
				//move down
				x-= dx;
				y-= dy;
			}
			
		}
		
		//movendo para os lados
		
		if(Game.inputs[Input.STRIGHT].getState() == true) {
			//strafe right
			x+= dy;
			y-= dx;
		}
		
		if(Game.inputs[Input.STLEFT].getState() == true) {
			//strafe left
			x-= dy;
			y+= dx;
		}
		
		//olhar para cima e para baixo,
		//mover para cima e para baixo
		
		if(Game.inputs[Input.M].getState() == true) {
			if(Game.inputs[Input.LEFT].getState() == true) {
				//look down
				l-=1;
			}
			
			if(Game.inputs[Input.RIGHT].getState() == true) {
				//look up
				l+=1;
			}
			
			if(Game.inputs[Input.UP].getState() == true) {
				//move up
				z+=4;
			}
			
			if(Game.inputs[Input.DOWN].getState() == true) {
				//move down
				z-=4;
			}
		}
		
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}

}
