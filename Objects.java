package GameData;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Objects extends Game{
	public static ArrayList<Objects> objectlist = new ArrayList<Objects>();
	private double x;
	private double y;
	private double MoveSpeed;
	private Image face;
	private int sizeX;
	private int sizeY;
	private boolean Touching = false;
	private String name = "";
	private double DownRandom = 0;
	private double RightRandom = 0;
	private Random random = new Random();
	private double Xvelocity;
	private double Yvelocity;

	//right end of object
	public double Right(){
		double farx = x + sizeX - 1;
		return (farx);
	}
	//bottom end of object
	
	public double Bottom(){
		double fary = y + sizeY - 1;
		return (fary);
	}
	//current x of object
	public double Left(){
		return (x);
	}
	//current y of object
	public double Top(){
		return (y);
	}
	public double CenterX(){
		return(x+sizeX/2);
	}
	public double CenterY(){
		return(y+sizeY/2);
	}
	//changes the y of object
	public void NewY(double y){
		this.y = y;
	}
	//changes the x of object
	public void NewX(double x){
		this.x = x;
	}
	//returns the image of the object
	public Image sprite(){
		return (face);
	}
	public double MoveSpeed(){
		return (MoveSpeed);
	}
	public void Sprite(Objects object, String picture){
		object.face = new Image(picture);
	}
	public boolean Touching(){
		return (Touching);
	}
	public void isTouching(boolean touch){
		this.Touching = touch;
	}
	
	public String Name(){
		return (name);
	}
	public double Xspeed(){
		return (Xvelocity);
	}
	public double Yspeed(){
		return (Yvelocity);
	}
	
	//creates the object
	//square moving objects
	
	public Objects(double startx, double starty, double speed, String image, int xsize, int ysize, String name, double Xvelocity, double Yvelocity){
		this.face = new Image(image);
		this.x = startx;
		this.y = starty;
		this.MoveSpeed = speed;
		this.sizeX = xsize;
		this.sizeY = ysize;
		this.name = name;
		this.Xvelocity = Xvelocity;
		this.Yvelocity = Yvelocity;
		objectlist.add(this);
	}
	//square objects
	
	//health bar
	
	public Objects(String image){
		this.face = new Image(image);
	}

	
	public double[] Move(Objects moving, double moveX, double moveY) {
		double[] movement = new double[2];
		// MAKE SURE ONLY X OR Y IS INPUTED. NOT BOTH AT ONCE
		//   ^ need to fix this eventually
		//System.out.println(moveX + " " + moveY);
		for (Objects item : objectlist) {
			if (item == moving) // don't compare against self
				continue;
			if (moving.Right() + moveX < item.Left()){
				continue;
			}
			if (moving.Left() + moveX > item.Right()){
				continue;
			}
			if (moving.Bottom() + moveY < item.Top()){
				continue;
			}
			if (moving.Top() + moveY > item.Bottom()){
				continue;
			}
			// calculate amount that can be moved, return to caller
			// also calculate if touching other objects
			moving.Touching = false;
			
			if (moveX > 0){
				moveX = Math.min(moveX, item.Left() - moving.Right()-1);
				if (moveX == 0)
					if (item.name == "bullet" && moving.name != "bullet"){
						item.Touching = true;
						moving.Touching = true;
					} else if (item.name == "" && moving.name != "" && moving.name != "player"){
						item.Touching = true;
						moving.Touching = true;
						
					}
			}
			else if (moveX < 0){
				moveX = -Math.min(-moveX, moving.Left() - item.Right()-1);
				if (moveX == 0)
					if (item.name == "bullet" && moving.name != "bullet"){
						item.Touching = true;
						moving.Touching = true;
					} else if (item.name == "" && moving.name != "" && moving.name != "player"){
						item.Touching = true;
						moving.Touching = true;
						
					}
			}
			
			if (moveY > 0) {
				moveY = Math.min(moveY, item.Top() - moving.Bottom()-1);
				if (moveY == 0)
					if (item.name == "bullet" && moving.name != "bullet"){
						item.Touching = true;
						moving.Touching = true;
					} else if (item.name == "" && moving.name != "" && moving.name != "player"){
						item.Touching = true;
						moving.Touching = true;
						
					}
			}
			else if (moveY < 0){
				moveY = -Math.min(-moveY, moving.Top() - item.Bottom()-1);
				if (moveY == 0)
					if (item.name == "bullet" && moving.name != "bullet"){
						item.Touching = true;
						moving.Touching = true;
					} else if (item.name == "" && moving.name != "" && moving.name != "player"){
						item.Touching = true;
						moving.Touching = true;
						
					}
			}
		}
		
		
		//System.out.println("moving " + moveX + ", " + moveY);
		//System.out.println(moveY);
		moving.x += moveX;
		moving.y += moveY;
		movement[0] = moveX;
		movement[1] = moveY;
		return (movement);

	}
	
	public void Follow(Objects follower, Objects leader){
		double moveX = 0;
		double moveY = 0;
		double moved[] = { 0, 0 };

		if (follower.RightRandom != 0 || follower.DownRandom != 0) {			
			//System.out.println(follower.counter);
			moveX = Math.copySign(Math.min(Math.abs(follower.RightRandom), follower.MoveSpeed),
								  follower.RightRandom);
			follower.RightRandom -= moveX;
		
			moveY = Math.copySign(Math.min(Math.abs(follower.DownRandom), follower.MoveSpeed),
								  follower.DownRandom);
			
			follower.DownRandom -= moveY;
		    
		} else {
			if (follower.CenterX() < leader.CenterX()) {
				moveX = follower.MoveSpeed;
			} else if (follower.CenterX() > leader.CenterX()) {
				moveX = -follower.MoveSpeed;
			} else {
				moveX = 0;
			}		
			if (follower.CenterY() < leader.CenterY()) {
				moveY = follower.MoveSpeed;
			} else if (follower.CenterY() > leader.CenterY()) {
				moveY = -follower.MoveSpeed;
			} else {
				moveY = 0;
			}
		}
	    //System.out.println("move " + moveX + " " + moveY);

		if (moveX != 0)
			moved = follower.Move(follower, moveX, 0);
		if (moveY != 0)
			moved = follower.Move(follower, 0, moveY);
		 
		if (moved[0] == 0 && moved[1] == 0) {
			
			//System.out.println("stuck");
			if (follower.DownRandom == 0 && follower.RightRandom == 0) {
				follower.RightRandom = 6 * (follower.random.nextDouble() - 0.5) * follower.sizeX;
				follower.DownRandom = 6 * (follower.random.nextDouble() - 0.5) * follower.sizeY;
			    //System.out.println("select " + follower.RightRandom + " " + follower.DownRandom);

			}
		}
	}	
	
	public void Follownew(Objects follower, Objects leader){

		double MoveX = 0;
		double MoveY = 0;
		
		if (follower.CenterX() < leader.CenterX())
			MoveX = follower.MoveSpeed();
		else if (follower.CenterX() > leader.CenterX())
			MoveX = -follower.MoveSpeed();
		else
			MoveY = follower.MoveSpeed() / 2;
		
		if (follower.CenterY() < leader.CenterY())
			MoveY += follower.MoveSpeed();
		else if (follower.CenterY() > leader.CenterY())
			MoveY += -follower.MoveSpeed();
		else
			MoveX += follower.MoveSpeed() / 2;
		
		follower.Move(follower, MoveX, MoveY);
	}
	
	public void Shoot(Objects shooter, double Xdirection, double Ydirection, double speed){
		double Xmove;
		double Ymove;
		double DistanceToTravel;
		double x = 0;
		double y = 0;
		int BulletSize = 16;
		
		// also need to make the bullet appear on the side where its being shot from
		Xmove = Xdirection - shooter.x;
		Ymove = Ydirection - shooter.y;
		//System.out.println(Xmove);
		//System.out.println(Ymove);
		if (Xmove > 0)
			x = shooter.Right() + 1;
		else
			x = shooter.Left() - BulletSize;
		if (Ymove > 0)
			y = shooter.Bottom() + 1;
		else
			y = shooter.Top() - 1;
		
		DistanceToTravel = Math.sqrt((Xmove*Xmove)+(Ymove*Ymove));
		double change = speed/DistanceToTravel;
		double xvelocity = (Xmove*change*speed);
		double yvelocity = (Ymove*change*speed);
		AiList.add(new Objects(x, y, speed, "Bullet16x16.png", BulletSize, BulletSize, "bullet", xvelocity, yvelocity));
		
		
	}
	
}
