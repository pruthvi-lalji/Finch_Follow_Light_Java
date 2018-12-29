import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;


//-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Things to DO -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-//

// 1 - Add array list for storing values of light
// 2 - Add array list for speed
// 3 - Changing the Colour/ intensity of  beak based on light intensity
// 4 - 




// Method 
// 1 - Need to store values for "log"
// 2 - Need to ask user's input



//Left from 
// - Duration Timing









//-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-//
	
public class LightSensor {
		static Finch myf = new Finch();
		// Get the light intensity for the room
		static public int ambient_light_left = myf.getLeftLightSensor();
		static public int ambient_light_right = myf.getRightLightSensor();
		
		
		static ArrayList<Integer> light_Data = new ArrayList<Integer>();
		//Right Light Sensor Reading Data 
		static ArrayList<Integer> right_light = new ArrayList<Integer>();
		//Left Light Sensor Reading Data 
		static ArrayList<Integer> left_light = new ArrayList<Integer>();
		//Time Array List
		static ArrayList<Long> total_time = new ArrayList<Long>();
		
	
		
		
	   public static void main( String[] args)    {
		  start();     /// ----------========== Main Start Enable this  ==========---------////////
		   
	   }
	   
	   public static void start()
	   {
		   JOptionPane.showMessageDialog(null, "Place the Finch on Level!");
		// Make the finchButton buzz to indicate the user which one is the button
			if (myf.isFinchLevel() == true)// condition
			{
				//Main part of the program
				   long time_start2 = System.currentTimeMillis();
				   System.out.println("Current Time: " + time_start2);
				   total_time.add(time_start2);
				checkLight();
			}
			else{
				myf.setLED(Color.red);
				JOptionPane.showMessageDialog(null, "Place the Finch on Level!");
				if (myf.isFinchLevel() == true)// condition
				{
					//run the main(game) method
					   long time_start2 = System.currentTimeMillis();
					   System.out.println("Current Time: " + time_start2);
					   total_time.add(time_start2);
					checkLight();
				}
				else
				{
					JOptionPane.showMessageDialog(null,"You didn't Placed the Finch on Level! Exiting...");
					//Make a sound of dying ///------============---------//
					System.exit(0);
					
				}
			}
			
	   }
	   
	   //This check for the light levels 
	   public static void checkLight() {
		   long before  = System.currentTimeMillis();  //Local Time
		   while(System.currentTimeMillis() - before < 5000) { // Takes the local time takes away from the "long" to remove error's/ Time delays and run the while statment for 5 seconds
			   finch_Level();
			   myf.setLED(Color.green);
			   myf.setWheelVelocities(100, 100); // Moves the Finch at specific speed
			   int leftCheck = myf.getLeftLightSensor(); // Check the light sensors 
			   int rightCheck = myf.getRightLightSensor();
			   if ((leftCheck > 100) | (rightCheck > 100)) // Check if the light levels are above certain threshold 
			   {
				   mainstart(); //Runs the main program
			   }
		    
		   }  /// time frame
		  /* ---------- Just Testing ----------- */ 
		  
		   myf.buzz(128, 2000);
		   myf.sleep(1000); //Stops the Finch for 1 Seconds
		   myf.stopWheels();
		   
		   /*---------Changing Direction ---------*/
		   dir_turn(); //Direction turn for the the Finch
		   
		   
		   
	   }
	   //main program
	   public static void mainstart()
	   {
		   long delay_time = System.currentTimeMillis(); // Allows 2 seconds for the Finch to record the light // 
		   while (true) // ======------------- Change this ------============//
		   {
			   //Takes the reading of the Actual Light Source
			   int lef2 = myf.getLeftLightSensor();
			   int rit2 = myf.getRightLightSensor();
			   while (System.currentTimeMillis() > (delay_time + 2000)) // Gives a 2 second delay_time
			   {
				   //While the light the Finch Detects is greater than the acutal room's Light, it runs the finch
				   while ( (lef2 > ((ambient_light_left) + 5)) | (rit2 > (ambient_light_right) + 5)) {
					   finch_Level();
					int lef = myf.getLeftLightSensor();
					
					   int rit = myf.getRightLightSensor();
			 		   long current_time = System.currentTimeMillis();
					   //Take an average of each light level and set the LED Light Levels for the beak (RGB)
					   
					    int level = (lef + rit)/2 ;  
					   
					   //Get an average
					   		//myf.setLED(Color.red); // --------========  Change this ====--------/////
					   		myf.setLED(level, 0, 0);
					    
					    
					   		//Have to check for speed if it is above 255
					   		int difference = ((rit - lef) * 2);	//*2 to speed up process	
					   		int left_wheel_vel = (difference + 50);		//left wheel will turn forwards to angle itself towards the light source
							int right_wheel_vel = (50 - difference);	//right wheel will turn backwards to angle towards the light source
							System.out.println(left_wheel_vel + " " + right_wheel_vel);
	/* This Maybe */		long duration = (System.currentTimeMillis() - current_time); //------------\\\
							myf.setWheelVelocities((difference + 50), (50 - difference)); //Changes the speed and direction of the finch depending on the light levels on the each sensor
					   		myf.setWheelVelocities(left_wheel_vel, right_wheel_vel);
				   
					   		 
					   		//Change direction if no light is detected
					   		if (((lef < (ambient_light_left + 5)) & (rit < (ambient_light_right + 5)) )) 
					   		{
					   			System.out.println("No Light Detected/Light Level is too Low");
					   			myf.setLED(Color.green); // Changes the colour if no light source is presented
					 
					   			long before = System.currentTimeMillis();
					   			while(System.currentTimeMillis() - before < 5000) // 5 Second time frame // this will turn for 5 seconds
					   			{
					   				System.out.println("IF stattem");
					   				
					   				int rit1 = myf.getRightLightSensor(); //allows measurement of sensors inside the loop
									int lef1 = myf.getLeftLightSensor();
									//Add the light value to the array List
									left_light.add(lef1);
									right_light.add(rit1);
									
					   				if (( (lef1 > (80)) | (rit1 > (80 )) )){  //The if statement would only work if the light source is above 80  
					   					//so it only takes the light from the light source that is presented at the time
										System.out.println("Again method");
					   					mainstart();
									}
					   				myf.setWheelVelocities(left_wheel_vel, right_wheel_vel);
					   			}
					   			
					
					   			System.out.println("No Light Dectected for 5 Seconds");
					   			//Changing Direction 
					   			finch_Level();
					   			dir_turn();
					   		}
				   }
	
			   }
			   
		   }
		   
		   
	   } // End of MainStart
	   
	   
	   public static void dir_turn() {
		   myf.sleep(1000);
		   myf.setLED(Color.blue); //change the colour when then the Finch turn 
		   myf.setWheelVelocities(50, -50, 2000);
		   checkLight();
		   finch_Level();
		  
	   }
	   
	   
	   public static void finch_Level() {
		   if (myf.isBeakUp() == true)
		   {
			   //End game Method
			   myf.stopWheels();
			   //continue;
			  time_Record();
		   }
		   
		   
	   }
	   
	   
	   public static void time_Record() {
		   
		   if(myf.isBeakUp() == true) {
			   myf.stopWheels();
			   long time = total_time.get(0);
			   System.out.println("Start Time" + time);
			   System.out.println(System.currentTimeMillis());
			   System.out.println("End Time: " + (System.currentTimeMillis() - time));
			   long Duration = ((System.currentTimeMillis() - time) / 1000);
			   System.out.println("New Time Check: " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - time));
			   
			   System.out.println("Total Time: " + Duration + " Seconds");
			  
			   //myf.quit();
			   System.exit(0);
		   }
		   
		   
	   }
	   
	   
	   
	   
	   
	}
	

