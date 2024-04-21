import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */
	
	public class Race
	{
		// File name to store leader board history
		
	    private static final String LEADERBOARD_FILE = "leaderboard.txt";
	    /////////////////////////////////////////////////
		public static void main(String[] a) {
			// Create a Scanner object to read input from the console
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Welcome to the HORSE RACE GAME! What's your name? ");
	        String name = scanner.nextLine();
	        // Greet the user
	        System.out.println("Hi " + name + "! Let's take you to the menu page.");
	        int optionSelected;
	        boolean playGame=true;
	        while (playGame) {
	        	optionSelected= welcome(scanner);
				menu(optionSelected);//after an option has been selected user needs to PRESS X TO RETURN TO MAIN MENu
				 System.out.println("Press X to return to the main menu or anything else to exit game");
				 String input = scanner.nextLine();
				
				 if (!(input.equalsIgnoreCase("X"))){
					 playGame=false;
				}
				 }
				 System.out.println("GOODBYE ");
				 scanner.close();
			}

    private int raceLength;
    private Horse[] horses;

    public Race(int distance)
    {
        // initialise instance variables
        raceLength = distance;
        horses = new Horse[0];
    }
    
    
    public void addHorse(Horse theHorse) {
        Horse[] newHorses = new Horse[horses.length + 1];
        for (int i = 0; i < horses.length; i++) {
            newHorses[i] = horses[i];
        }
        newHorses[horses.length] = theHorse;
        horses = newHorses;
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
  
    	public void startRace() {
    	    System.out.println("Starting a new race");
    	    // Create a random number generator for horse confidence levels
    	    Random random = new Random();
    	    
    	    // Create Horse objects and add them to the race based on user input
    	    Scanner scanner = new Scanner(System.in);
    	    System.out.println("How many horses do you want in the race? >0/1");
    	    int totalHorses = scanner.nextInt();
    	    
    	    // Create array to hold all horses in the race
    	    Horse[] horsesArray = new Horse[totalHorses];

    	    for (int i = 1; i <= totalHorses; i++) {
    	        System.out.println("Enter details for Horse " + i);
    	        System.out.println("Enter Unicode character for the horse: ");
    	        char symbol = scanner.next().charAt(0);
    	        System.out.println("Enter the name of the horse: ");
    	        String name = scanner.next();
    	        double confidence = 2.0;
    	        // Generate a random confidence level if the entered value is out of range
    	        confidence = Math.max(0.0, Math.min(1.0,confidence));
    	        if (confidence < 0.0 || confidence > 1.0) {
    	            confidence = random.nextDouble(); // Random value between 0.0 and 1.0
    	        }
    	        Horse horse = new Horse(symbol, name, confidence);
    	        horsesArray[i-1] = horse; // Add horse to the array
    	        addHorse(horse); //lane starts from 1
    	    }
    	    // Reset all the lanes (all horses not fallen and back to 0).
    	    for (int i = 0; i < horsesArray.length; i++) {
                horsesArray[i].goBackToStart();
            }

    	    // Start the race
    	    boolean finished = false;
    	    Horse winningHorse = null;

    	    while (!finished) {
    	    	// Iterate over all horses and move them
    	        for (int i = 0; i < totalHorses; i++) {
    	            moveHorse(horsesArray[i]);
    	        }
    	        printRace();
    	        for (int i = 0; i < totalHorses; i++) {
    	            if (raceWonBy(horsesArray[i])) {
    	                finished = true;
    	                winningHorse = horsesArray[i];
    	                break; // No need to continue checking if a winner is found
    	            }
    	        }

    	        try {
    	            TimeUnit.MILLISECONDS.sleep(100);
    	        } catch (Exception e) {
    	        }
    	    }

    	    if (winningHorse != null) {
    	    	String winner =winningHorse.getName();
    	    	System.out.println("And the winner is " + winner);
    	        // Update leaderboard after each race
    	        writeLeaderboard(winner, 5);
    	        // Display leaderboard after updating
    	        readLeaderboard();
    	    } else {
    	        System.out.println("No winner detected. Race ended unexpectedly.");
    	    }
    	}
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
    	 return theHorse.getDistanceTravelled() == raceLength;
    }
    
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
       
        for (int i = 0; i < horses.length; i++) {
            printLane(horses[i]);
            System.out.println();
        }
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('\u2322');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
    	public static int welcome(Scanner scanner) {
    		int option = 0;
    	    boolean validOption = false;
    	    
    	    System.out.println("**********************************************");
    	    System.out.println("*       WELCOME TO THE HORSE RACE GAME!      *");
    	    System.out.println("*                                            *");
    	    System.out.println("*                  __|__                     *");
    	    System.out.println("*            /\\   /     \\   /\\               *");
    	    System.out.println("*           /  \\ /       \\ /  \\              *");
    	    System.out.println("*          /    /         \\    \\             *");
    	    System.out.println("*         /    /           \\    \\            *");
    	    System.out.println("*        /    /             \\    \\           *");
    	    System.out.println("*       /    /               \\    \\          *");
    	    System.out.println("*      /    /_________________\\    \\         *");
    	    System.out.println("*      \\   /|                 |\\   /         *");
    	    System.out.println("*       \\_/ |                 | \\_/          *");
    	    System.out.println("*          /                   \\         	   *");
    	    System.out.println("*         /                     \\         	   *");
    	    System.out.println("*        /                       \\        	   *");
    	    System.out.println("*       /                         \\           *");
    	    System.out.println("*      /                           \\          *");
    	    System.out.println("*     /_____________________________\\         *");
    	    System.out.println("*                                              *");
    	    System.out.println("* Get ready for an exciting race!              *");
    	    System.out.println("* Please select an option from the menu        *");
    	    System.out.println("*                                              *");
    	    System.out.println("* MENU:                                        *");
    	    System.out.println("* 1. Read the game manual                      *");
    	    System.out.println("* 2. View leaderboard history                  *");
    	    System.out.println("* 3. Start a new race                          *");
    	    System.out.println("*                                              *");
    	    System.out.println("************************************************");
    	   while (!validOption) { 
    		   String input = scanner.nextLine();
    	    
    	    try {
	    	    option =  Integer.parseInt(input);//input validation to repeat question if user fails to enter a number between 1-3 
	    	    if (option >= 1 && option <= 3) {
	    	    	validOption = true;
	    	    }else{
	    	    	System.out.println("Invalid option. Please enter a value between 1 and 3.");
	            }
    	    }
	    	    catch (NumberFormatException e) {
	            System.out.println("Invalid input. Please enter a valid option or 'X' to return to the main menu.");
    	    	}
    	    }
    	    return option;
    	}
    	//MENUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU
        	public static void menu(int option) {
    		switch(option) {
    		case 1:
    			//displaying instructions for the game
    			displayGameManual();
    			 // Provide option to go back to main menu
           
    		break;
    		
    		case 2:

    	        // Read and display leader board history from the external file
    	        readLeaderboard();
              
                
    	    break;
    	        
    		case 3:
    			    // Start the race
    			    Race race1 = new Race(5);//user needs to input the distance
    			    race1.startRace();
    			    
    		break;
    		default:
        	    System.out.println("Enter a value between 1-3");//GIVE THEM ANOTHER CHANCE TO ENTER
        	    break;
    		}
    		}
        	
        	
    	public static void displayGameManual() {
    		System.out.println("Horse Race Game Instruction Manual");
			System.out.println();
			System.out.println("Objective:");
			System.out.println("Compete in thrilling horse races to determine the fastest horse and emerge as the ultimate champion.");
			System.out.println();
			System.out.println("Gameplay:");
			System.out.println("1. Start a New Race:");
			System.out.println("   - Choose the 'Start a new race' option from the main menu to begin.");
			System.out.println("   - You'll witness an exciting race with three horses competing against each other in separate lanes.");
			System.out.println();
			System.out.println("2. Race Progression:");
			System.out.println("   - Each horse moves forward randomly based on its confidence level.");
			System.out.println("   - The probability of a horse moving forward or falling depends on its confidence rating.");
			System.out.println("   - A fallen horse cannot move until the end of the race.");
			System.out.println();
			System.out.println("3. Race Completion:");
			System.out.println("   - The race continues until one of the horses crosses the finish line.");
			System.out.println("   - The first horse to complete the race is declared the winner.");
			System.out.println();
			System.out.println("4. View Leaderboard:");
			System.out.println("   - Check the leaderboard history to see past race results and track the performance of different horses.");
			System.out.println();
			System.out.println("5. Game Manual:");
			System.out.println("   - Refer to the game manual for detailed instructions and tips on how to maximize your chances of winning.");
			System.out.println();
			System.out.println("Controls:");
			System.out.println("- Use the menu options to navigate through the game.");
			System.out.println("- No direct control over the horses' movements; their progress is simulated based on predefined probabilities.");
			System.out.println();
			System.out.println("Tips for Success:");
			System.out.println("- Choose horses with higher confidence ratings for better chances of winning.");
			System.out.println("- Keep an eye on the race progress to anticipate the outcome.");
			System.out.println();
			System.out.println("Have Fun and Good Luck!");
			//ADDDDDDDDDDDDDDD option to go back to menu

    	}
    	
    	  // Method to read and display leader board history from the external file
        private static void readLeaderboard() {
            List<String> leaderboardEntries = new ArrayList<>();

            try (Scanner scanner = new Scanner(new File("C:\\Users\\olola\\eclipse-workspace\\horseRaceGame\\src\\leaderboard.txt"))) {
                System.out.println("Leaderboard History:");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String playerName = parts[0];
                        String rawScore = parts[1].trim(); // Remove leading/trailing spaces
                        int score = Integer.parseInt(rawScore);
                        System.out.println("Player: " + playerName + ", Score: " + score);
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.err.println("Leaderboard file not found: " + e.getMessage());
                e.printStackTrace(); // Add this line
            }
            // Append the new race results
            for (String entry : leaderboardEntries) {
                writeLeaderboardEntry(entry);
            }
        }// Method to write leaderboard history to the external file
        private static void writeLeaderboard(String playerName, int score) {
            String entry = playerName + "," + score;
            writeLeaderboardEntry(entry);
        }
     // Method to write leaderboard entry to the external file
        private static void writeLeaderboardEntry(String entry) {
            try (FileWriter fileWriter = new FileWriter("C:\\Users\\olola\\eclipse-workspace\\horseRaceGame\\src\\leaderboard.txt", true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
                printWriter.println(entry); // Format: playerName,score
            } catch (IOException e) {
                System.err.println("Error writing to the leaderboard file: " + e.getMessage());
            }
        }
        
     
       
    }

