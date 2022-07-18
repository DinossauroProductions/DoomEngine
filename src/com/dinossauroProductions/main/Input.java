package com.dinossauroProductions.main;

import java.awt.event.KeyEvent;

public class Input {
	
	public static final int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3, STLEFT = 4, STRIGHT = 5, M = 6;
	
	private int[] keyCode;
	private boolean state = false;
	private String name;
	
	public Input(int keyCode) {
		this.keyCode = new int[2];
		this.keyCode[0] = keyCode;
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public int getKeyCode(int index) {
		return this.keyCode[index];
	}
	
	public String getName() {
		return name;
	}
	
	public void setKeyCode(int index, int keyCode) {
		this.keyCode[index] = keyCode;
	}
	
	public static Input[] setUpInputs() {
		Input[] list = new Input[7];
		
		list[0] = new Input(KeyEvent.VK_W);
		list[0].setKeyCode(1, KeyEvent.VK_UP);
		list[0].name = "up";
		
		list[1] = new Input(KeyEvent.VK_A);
		list[1].setKeyCode(1, KeyEvent.VK_LEFT);
		list[1].name = "left";
		
		list[2] = new Input(KeyEvent.VK_S);
		list[2].setKeyCode(1, KeyEvent.VK_DOWN);
		list[2].name = "down";
		
		list[3] = new Input(KeyEvent.VK_D);
		list[3].setKeyCode(1, KeyEvent.VK_RIGHT);
		list[3].name = "right";
		
		list[4] = new Input(KeyEvent.VK_COMMA);
		list[4].name = "strafe left";
		
		list[5] = new Input(KeyEvent.VK_PERIOD);
		list[5].name = "strafe right";
		
		list[6] = new Input(KeyEvent.VK_M);
		list[6].name = "sei la caraio M";
		
		
		
		return list;
	}

	
	

}
