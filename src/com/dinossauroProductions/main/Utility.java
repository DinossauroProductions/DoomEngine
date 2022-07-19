package com.dinossauroProductions.main;

public class Utility {
	
	public static double[] sin;
	public static double[] cos;
	
	public static void loadUtility() {
		sin = new double[360];
		cos = new double[360];
		
		for(int i = 0; i < 360; i++) {
			sin[i] = Math.sin(Math.toRadians(i));
			cos[i] = Math.cos(Math.toRadians(i));
		}
	}

}
