import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class SearchforLight {

	static Finch myFinch = new Finch(); // initialise finch
	// two arraylist
	static ArrayList<Integer> Light_Values = new ArrayList<Integer>();
	static ArrayList<Long> initial_time = new ArrayList<Long>();
	static int Light_count;
	// take the first two initial values
	static int initial_valueL = myFinch.getLeftLightSensor();
	static int initial_valueR = myFinch.getRightLightSensor();

	public static void main(String args[]) {

		System.out.println("Welcome To Search For Light. Make sure Finch is level");
		initial_time.add(System.currentTimeMillis());
		levelup();

	}
	/*
	 * Checks if the light is detected or not within 5 seconds, if no it will
	 * change direction
	 */

	public static void MainProgram() {

		long sec = System.currentTimeMillis();
		while (System.currentTimeMillis() - sec < 5000) {

			if (myFinch.isBeakUp() == false) {

				myFinch.setLED(0, 255, 0);
				myFinch.setWheelVelocities(100, 100);

				int left_light = myFinch.getLeftLightSensor();
				int right_light = myFinch.getRightLightSensor();
				Light_Values.add(left_light);
				Light_Values.add(right_light);

				if (left_light > 100 || right_light > 100) {
					Light_count++;
					PerformFinch();

				}

			} else {

				myFinch.quit();
				System.out.println("Finch Quit!");
				log();
			}

		}
		myFinch.stopWheels();
		myFinch.sleep(1000);
		myFinch.setWheelVelocities(100, 0, 1500);
		MainProgram();

	}
	/*
	 * This method will see which side the light source is coming from
	 */

	public static void PerformFinch() {

		while (true) {
			int left_light = myFinch.getLeftLightSensor();
			int right_light = myFinch.getRightLightSensor();
			Light_Values.add(left_light);
			Light_Values.add(right_light);

			if (left_light > 100 || right_light > 100) {
				FollowLight();

				if (left_light > right_light) {

					myFinch.setWheelVelocities(0, 100);

				} else {
					myFinch.setWheelVelocities(100, 0);

				}

			} else {
				MainProgram();
			}

		}

	}

	/*
	 * this method get the average intensity of the red light
	 */
	public static void FollowLight() {

		int left_light = myFinch.getLeftLightSensor();
		int right_light = myFinch.getRightLightSensor();
		int average_List = left_light + right_light / 2;
		myFinch.setLED(average_List, 0, 0);

	}
	/*
	 * This checks if the finch is level
	 */
	public static void levelup() {

		if (myFinch.isFinchLevel() == true) {
			MainProgram();
		} else {
			System.out.println("Finch is not leveled, Finch will go to log");
			log();
		}

	}

	/*
	 * this is the log
	 */
	public static void log() {
		long end_time = System.currentTimeMillis();
		long start_time = initial_time.get(0);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Do you want to see your result? (Enter \"y\"for Yes and \"n\" for No)");
		String user = scanner.nextLine();
		// print the log if the user enter "Y"
		if (user.equalsIgnoreCase("Y")) {
			System.out.println("The left and right sensor values at the beginning of the execution(L R): "
					+ initial_valueL + " " + initial_valueR);
			// ----------------------------------------------------------------------------------------------
			Collections.sort(Light_Values);
			int Lowest = Light_Values.get(0);
			int Highest = Light_Values.get(Light_Values.size() - 1);

			System.out.println("The Lowest Light Value:" + Lowest);
			System.out.println("The Highest Light Value:" + Highest);
			// ----------------------------------------------------------------------------------------------
			long duration = (end_time - start_time) / 1000;
			System.out.println("The duration of the execution: " + duration + " Seconds");
			// ----------------------------------------------------------------------------------------------
			System.out.println("The number of times the finch detected light: " + Light_count);
			// ----------------------------------------------------------------------------------------------

			// Prints "Thank you" only if the user enter "N"

		} else if (user.equalsIgnoreCase("N")) {
			System.out.println("Thank You For Playing!");
			System.exit(0);

			// any other value will make the user enter the value again.
		} else {
			System.out.println("Please Enter The Correct Value!");
			log();
		}
		// exits the system
		System.exit(0);

	}
}
