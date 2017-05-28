package com.zhuravchak.chocolatebracker;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Gameplay extends JPanel implements KeyListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean play = false;
	private int score = 0;
	private int totalBricks= 28;
	private Timer timer;
	private int delay=8;
	private int playerX = 20;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private MapGenerator map;


	public Gameplay() {
		map = new MapGenerator(4, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer =new Timer(delay,this);
	    timer.start();
	}
 
	 public void paint(Graphics g){
		 //background
		 g.setColor(Color.WHITE);
		 g.fillRect(1, 1, 692, 592);
		 
		 //drawing map
		 map.draw((Graphics2D)g);
		 
		 //borders
		 g.setColor(map.color);
		 g.fillRect(0, 0,3, 592);
		 
		 g.fillRect(0, 0, 692, 3);
		 g.fillRect(691, 0, 3, 592);
		 
		 //scores
		 g.setColor(map.color);
		 g.setFont(new Font("serif",Font.BOLD,25));
		 g.drawString(""+score, 590, 30);
		 
		 //paddle
		// g.setColor(Color.green);
		// g.fillRect(playerX, 550, 100, 8);
		 Image image1 = new ImageIcon("images/padlle.jpg").getImage();
		   g.drawImage(image1,playerX,550,this);
		 
		 //ball
		 //g.setColor(Color.yellow);
		 //g.fillOval(ballposX, ballposY, 20, 20);
		 Image image2 = new ImageIcon("images/head.jpg").getImage();
		  g.drawImage(image2,ballposX,ballposY,this);
		  
		  if(totalBricks==0){
			  play=false;
			  ballXdir=0;
			  ballYdir=0;
              g.setColor(Color.RED);
              g.setFont(new Font("serif",Font.BOLD,30));
     		  g.drawString("You won", 290, 340);
     		 g.drawString("Scores: "+score, 275, 385);
     		 g.setFont(new Font("serif",Font.BOLD,20));
    		  g.drawString("Press Enter to restart", 250, 430);
			  
		  }
		  if(ballposY>570){
			  play=false;
			   ballXdir=0;
			  ballYdir=0;
              g.setColor(Color.RED);
              g.setFont(new Font("serif",Font.BOLD,30));
     		  g.drawString("Game over", 270, 340);
     		 g.drawString("Scores: "+score, 275, 385);
     		 g.setFont(new Font("serif",Font.BOLD,20));
    		  g.drawString("Press Enter to restart", 250, 430);
		  }
		 g.dispose();
	 }
	 
	 @Override
		public void keyReleased(KeyEvent e) {}
	 @Override
		public void keyTyped(KeyEvent e) {}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play){
			
			if(new Rectangle(ballposX, ballposY, 30, 52).intersects(new Rectangle(playerX,550,100,8))){
				ballYdir= - ballYdir;
			}
			
		A:	for(int i =0;i<map.map.length;i++){
				for(int j=0;j<map.map[0].length;j++){
					if(map.map[i][j]>0){
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 40, 50);
						Rectangle brickRect = rect;
						if(ballRect.intersects(brickRect)){
							map.setBrickeValue(0, i, j);
							totalBricks--;
							score+= 5;
							if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width){
								ballXdir = -ballXdir;	
							}
							else ballYdir= -ballYdir;
							
							break A;
						}
						
					}
				}
		}
			//ballposX+=ballXdir;
			//ballposY+=ballYdir;
			ballposX=ballposX+(int)(ballXdir*1.5);
			ballposY=ballposY+(int)(ballYdir*1.5);
			
			if(ballposX<5){
				ballXdir= -ballXdir;
			}
			if(ballposY<5){
				ballYdir= -ballYdir;
			}
			if(ballposX>652){
				ballXdir= -ballXdir;
			}

		}
		
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(playerX>=590){
				playerX=590;
			} else{
				moveRigh();
			}
		}
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
        	if(playerX<=5){
				playerX=5;
			} else{
				moveLeft();
			}
		}
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
        	if(!play){
        		play=true;
        		ballposX = 120;
        		 ballposY = 350;
        		 ballXdir = -1;
        		 ballYdir = -2;
        		 playerX = 20;
        		 score=0;
        		 totalBricks=28;
        		 map=new MapGenerator(4,7);
        		 repaint();
        	}
        }
		
	}

	public void moveRigh(){
		play=true;
		playerX+=20;
	}
	public void moveLeft(){
		play=true;
		playerX-=20;
	}

}
