package com.dinossauroProductions.main;

import java.awt.Color;

public class Sector {
	
	private int ws, we;							//wall number start and end
	private int z1, z2;							//height of bottom and top
	private int d;								//add y distances to sort drawing order
	private Color c1, c2;						//bottom and top color
	private int surf[] = new int[Game.WIDTH];	//to hold points for surface
	private int surface;						//is there a surfaces to draw
	
	public Sector(int ws, int we, int z1, int z2, int d, int c1, int c2) {
		this.ws = ws;
		this.we = we;
		this.z1 = z1;
		this.z2 = z2;
		this.d = d;
		this.c1 = new Color(c1);
		this.c2 = new Color(c2);
	}
	
	public int[] getSurf() {
		return surf;
	}

	public void setSurf(int surf, int index) {
		this.surf[index] = surf;
	}

	public int getSurface() {
		return surface;
	}

	public void setSurface(int surface) {
		this.surface = surface;
	}
	
	public Color getC1() {
		return c1;
	}

	public void setC1(Color c1) {
		this.c1 = c1;
	}

	public Color getC2() {
		return c2;
	}

	public void setC2(Color c2) {
		this.c2 = c2;
	}

	public int getWs() {
		return ws;
	}
	public void setWs(int ws) {
		this.ws = ws;
	}
	public int getWe() {
		return we;
	}
	public void setWe(int we) {
		this.we = we;
	}
	public int getZ1() {
		return z1;
	}
	public void setZ1(int z1) {
		this.z1 = z1;
	}
	public int getZ2() {
		return z2;
	}
	public void setZ2(int z2) {
		this.z2 = z2;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	
	

}
