package GameData;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

//so many imports /:
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Game extends Application {
	//dimensions of the game
	private final int width = 1216;
	private final int height = 768;
	int[][] PixelArray = new int[width][height];
	private final int maxX = width - 1;
	private final int maxY = height - 1;
	private final int fps = 45; // 55 is about the fastest atm //50 is the normal amount
	double prevTime = 0;
	int prefferedTime = (1000/fps);
	int sleepTime = 0;
	int spawnCap = 40;
	int aiAmount = 0;
	
	public double mouseX = 0;
	public double mouseY = 0;
	
	Random RandInt = new Random();
	int number = 0;
	int pressTimer = 0;
	int shootDelay = 12;
	int spawnTimer = 0;
	int spawnDelay = 150;
	//int myRandomNumber = 0;
	//myRandomNumber = r.nextInt(maxValue-minValue+1)+minValue;
	String button = "";
	public static ArrayList<Objects> AiList = new ArrayList<Objects>();
	public static ArrayList<Objects> Remove = new ArrayList<Objects>();
	
	public void spawn(){
		int sides = RandInt.nextInt(6-1+1)+1;
		int amount = RandInt.nextInt(10-2+1)+2;
		
		if (sides == 1){
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(30 + 65*i, 10, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
		} else if (sides == 2){
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(800 + 65*i, 10, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
		} else if (sides == 3){
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(30 + 65*i, 700, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
		} else if (sides == 4){
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(800 + 65*i, 700, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
		} else if (sides == 5){
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(30 + 65*i, 10, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
			
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(800 + 65*i, 700, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
		} else if (sides == 6){
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(800 + 65*i, 10, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
			for (int i = 0; i <amount; i++){
				AiList.add(new Objects(30 + 65*i, 700, 2,"player32x32.png", 32, 32, "", 0, 0));
			}
		}
		
		if (sides <= 4){
			aiAmount += amount;
		} else if (sides >= 5){
			aiAmount += amount*2;
		}
	}

	
	public void start(Stage Window) {
	    Window.setTitle("Game");
	    System.out.println("test");
	    Objects player = new Objects(400, 400, 7, "PlayerRight64x64.png", 64, 64, "player", 0, 0);
	    Objects hpBar = new Objects("Hp64.png");
	    Image Background = new Image("BackGround1216x768.png");
	    //Objects object1 = new Objects(800, 700, 4, "player32x32.png", 32);
	    //Objects object2 = new Objects(64, 400, 4, "player32x32.png", 32);
	    
		for (int i = 0; i <2; i++) {
			AiList.add(new Objects(30 + 72*i, 10, 2,"player32x32.png", 32, 32, "", 0, 0));
			aiAmount ++;
		}
	    
	    Group root = new Group();
	    Scene theWindow = new Scene(root);
	    Window.setScene(theWindow);
	    Window.setResizable(false);
	    
	    Canvas canvas = new Canvas(width, height);
	    root.getChildren().add(canvas);
	    
	    GraphicsContext screen = canvas.getGraphicsContext2D();
	    
	    //array for keyboard input
	    ArrayList<String> input = new ArrayList<String>();
	    
	    
	    //keyboard input
     	    theWindow.setOnKeyPressed(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
 
                    // only add once... prevents duplicates
                    if ( !input.contains(code) ){
                        input.add( code );
                    	button = code;
                    }
                }
            });
 
      	    theWindow.setOnKeyReleased(new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    input.remove( code );
                    button = "";
                }
            });
        
    	    //mouse input
    	    theWindow.setOnMouseMoved(new EventHandler<MouseEvent>(){
        	public void handle(MouseEvent event){
        		mouseX = event.getX();
        		mouseY = event.getY();
        	}
        });
	        
	        // main game loop
	    new AnimationTimer(){
	    	public void handle(long NanoSeconds){
	    		//double seconds = NanoSeconds / 1000000000;
	    		//System.out.println(time);
	    		
	    		//making the player move while within the window
	    		//left movement and collision detection
	    		if (input.contains("LEFT") || input.contains("A") && player.Left() > 0) {
	    			double moveX = Math.min(player.Left(), player.MoveSpeed());
	    			player.Move(player, -moveX, 0);
	    			player.Sprite(player,"PlayerLeft64x64.png");
	    		}
	    		//right movement and collision detection
	    		if (input.contains("RIGHT") || input.contains("D") && player.Right() < maxX) {
	    			double moveX = Math.min(maxX - player.Right(), player.MoveSpeed());
	    			player.Move(player, moveX, 0);
	    			player.Sprite(player,"PlayerRight64x64.png");
	    		}
	    		//up movement and collision detection
	    		if (input.contains("UP") || input.contains("W") && player.Top() > 0) {
	    			double moveY = Math.min(player.Top(), player.MoveSpeed());
	    			player.Move(player,0, -moveY);
	    		}
	    		//down movement and collision detection
	    		if (input.contains("DOWN") || input.contains("S") && player.Bottom() < maxY) {
	    			double moveY = Math.min(maxY - player.Bottom(), player.MoveSpeed());
	    			player.Move(player, 0, moveY);
	    			//System.out.println("DOWN");
	    		}
	    		
	    		if (input.contains("SPACE")){
	    			if (pressTimer <= 0){
    					player.Shoot(player, mouseX, mouseY, 3.5);
    					pressTimer = shootDelay;
    				}
	    		}
	    		
	    		/*
	    		//other buttons
	    		switch (button){
	    			case "SPACE":
	    				if (pressTimer <= 0){
	    					player.Shoot(player, mouseX, mouseY, 3.5);
	    					pressTimer = delay;
	    				}
	    		}
	    		
	    		*/
	    		//redrawing the screen after player moves
	    		//screen.clearRect(0, 0, width, height);
	    		screen.drawImage(Background, 0, 0);

	    		// dealing with objects
	    		for (Objects blob : AiList){

	    			if (blob.Left() < 0 || blob.Bottom() < 0 || 
	    				blob.Right() > maxX || blob.Top() > maxY){
	    					Remove.add(blob);
	    					//System.out.println("removed");
	    			}
	    			
	    			if (blob.Touching() == true){
	    				switch (blob.Name()){
	    					case "":
	    						Remove.add(blob);
	    						break;
	    					case "bullet":
	    						Remove.add(blob);
	    						break;
	    					case "player":
	    						System.out.println("player");
	    				}
	    				
	    			} else if (blob.Name() == "") {
	    				blob.Follow(blob, player);
	    				blob.Sprite(blob, "player32x32.png");
	    			} else if (blob.Name() == "bullet"){
	    				//System.out.println("test");
	    				blob.Move(blob, 0, blob.Yspeed());
	    				blob.Move(blob, blob.Xspeed(), 0);
	    			}
	    			screen.drawImage(blob.sprite(), blob.Left(), blob.Top());
	    		}
	    		
	    		for (Objects Delete: Remove){
	    			AiList.remove(Delete);
	    			Objects.objectlist.remove(Delete);
	    			Delete = null;
					aiAmount --;
	    		}
	    		
	    		Remove.clear();
	    		
	    		/*
	    		if (AiList.isEmpty()){
	    			spawn();
	    		}
	    		
	    		*/
	    		
	    		if (spawnTimer <= 0 && aiAmount < spawnCap ){
	    			spawn();
	    			spawnTimer = spawnDelay;
	    		}
	    		
	    		hpBar.Follow(hpBar, player);
	    		hpBar.Sprite(hpBar, "Hp64.png");
	    		
	    		screen.drawImage(player.sprite(), player.Left(), player.Top());
	    		screen.drawImage(hpBar.sprite(), hpBar.Left(), hpBar.Top());
	    		//System.out.println(player.Touching());
	    		
	    		try {
	    			double time = NanoSeconds / 1000000;
	    		    double timetaken =  time - prevTime;
	    		    prevTime = time;
	    		    sleepTime = prefferedTime - (int)timetaken;
	    		    pressTimer --;
	    		    spawnTimer --;
	    	//to see the amount of time taken in each cycle
	    		    //System.out.println(sleepTime);
	    		    //System.out.println(prefferedTime);
	    		    //System.out.println(timetaken);
	    		    //System.out.println((int)timetaken+sleepTime);
	    		    if (!((int)timetaken > prefferedTime)){
	    		    	Thread.sleep(sleepTime);
	    		    	//System.out.print("true");
	    		    }
	    		    
	    		} catch(InterruptedException ex) {
	    			System.out.println("InterruptedException");
	    		}
	    	}
	    }.start();
	    
	    Window.show();
	}
	
	public static void main(String[] args) {
		 
        launch(args);
        
        System.out.print(Objects.objectlist.size());
        
	}
	
}
