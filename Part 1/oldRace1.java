
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane for a given distance
 * 
 * @author McFarewell
 * @version 1.0
 */

public class oldRace1 {
	private static int raceLength;
	private Horse lane1Horse;
	private Horse lane2Horse;
	private Horse lane3Horse;

	public oldRace1(int distance) {
		raceLength = distance;
		lane1Horse = null;
		lane2Horse = null;
		lane3Horse = null;
	}

	public void addHorse(Horse theHorse, int laneNumber) {
		if (laneNumber == 1) {
			lane1Horse = theHorse;
		} else if (laneNumber == 2) {
			lane2Horse = theHorse;
		} else if (laneNumber == 3) {
			lane3Horse = theHorse;
		} else {
			System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
		}
	}

	public void startRace() {
		boolean finished = false;

		lane1Horse.goBackToStart();
		lane2Horse.goBackToStart();
		lane3Horse.goBackToStart();

		while (!finished) {
			moveHorse(lane1Horse);
			moveHorse(lane2Horse);
			moveHorse(lane3Horse);

			printRace();

			if (raceWonBy(lane1Horse) || raceWonBy(lane2Horse) || raceWonBy(lane3Horse)) {
				finished = true;
			}

			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Randomly make a horse move forward or fall depending on its confidence rating
	 * A fallen horse cannot move
	 * 
	 * @param theHorse the horse to be moved
	 */
	private void moveHorse(Horse theHorse) {
		// if the horse has fallen it cannot move,
		// so only run if it has not fallen

		if (!theHorse.hasFallen()) {
			// the probability that the horse will move forward depends on the confidence;
			if (Math.random() < theHorse.getConfidence()) {
				theHorse.moveForward();
			}

			// the probability that the horse will fall is very small (max is 0.1)
			// but will also will depends exponentially on confidence
			// so if you double the confidence, the probability that it will fall is *2
			if (Math.random() < (0.1 * theHorse.getConfidence() * theHorse.getConfidence())) {
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
	private static boolean raceWonBy(Horse theHorse) {
		if (theHorse.getDistanceTravelled() == raceLength) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * Print the race on the terminal
	 */
	private void printRace() {
		System.out.print('\u000C'); // clear the terminal window

		multiplePrint('=', raceLength + 3); // top edge of track
		System.out.println();

		printLane(lane1Horse);
		System.out.println();

		printLane(lane2Horse);
		System.out.println();

		printLane(lane3Horse);
		System.out.println();

		multiplePrint('=', raceLength + 3); // bottom edge of track
		System.out.println();
	}

	/**
	 * print a horse's lane during the race for example | X | to show how far the
	 * horse has run
	 */
	private void printLane(Horse theHorse) {
		// calculate how many spaces are needed before
		// and after the horse
		int spacesBefore = theHorse.getDistanceTravelled();
		int spacesAfter = raceLength - theHorse.getDistanceTravelled();

		// print a | for the beginning of the lane
		System.out.print('|');

		// print the spaces before the horse
		multiplePrint(' ', spacesBefore);

		// if the horse has fallen then print dead
		// else print the horse's symbol
		if (theHorse.hasFallen()) {
			System.out.print('\u2322');
		} else {
			System.out.print(theHorse.getSymbol());
		}

		// print the spaces after the horse
		multiplePrint(' ', spacesAfter);

		// print the | for the end of the track
		System.out.print('|');
	}

	/***
	 * print a character a given number of times. e.g. printmany('x',5) will print:
	 * xxxxx
	 * 
	 * @param aChar the character to Print
	 */
	private void multiplePrint(char aChar, int times) {
		int i = 0;
		while (i < times) {
			System.out.print(aChar);
			i = i + 1;
		}
	}

	public static void main(String[] args) {
		// Create a Race object with a race length of 100 units
		oldRace1 race = new oldRace1(10);

		// Create three Horse objects
		Horse horse1 = new Horse('♘', "PIPPI LONGSTOCKING", 0.6);
		Horse horse2 = new Horse('♞', "KOKOMO", 0.6);
		Horse horse3 = new Horse('❌', "EL JEFE", 0.4);

		// Add the horses to the race in lanes 1, 2, and 3 respectively
		race.addHorse(horse1, 1);
		race.addHorse(horse2, 2);
		race.addHorse(horse3, 3);

		// Start the race
		race.startRace();

		// Display race results
		System.out.println("===================================================================================");
		System.out.println("| " + horse1.getSymbol() + " | " + horse1.getName() + " (Current confidence "
				+ horse1.getConfidence() + ")");
		System.out.println("| " + horse2.getSymbol() + " | " + horse2.getName() + " (Current confidence "
				+ horse2.getConfidence() + ")");
		System.out.println("| " + horse3.getSymbol() + "2 | " + horse3.getName() + " (Current confidence "
				+ horse3.getConfidence() + ")");
		System.out.println("=================================================================================");

		// Determine the winner and display
		Horse winner = determineWinner(horse1, horse2, horse3);
		System.out.println("And the winner is " + winner.getName());
	}

	private static Horse determineWinner(Horse horse1, Horse horse2, Horse horse3) {
		// Check if any horse has finished the race
		if (raceWonBy(horse1)) {
			return horse1;
		} else if (raceWonBy(horse2)) {
			return horse2;
		} else if (raceWonBy(horse3)) {
			return horse3;
		}

		// If no horse has finished, compare distances to determine the winner
		if (horse1.getDistanceTravelled() >= horse2.getDistanceTravelled()
				&& horse1.getDistanceTravelled() >= horse3.getDistanceTravelled()) {
			return horse1;
		} else if (horse2.getDistanceTravelled() >= horse1.getDistanceTravelled()
				&& horse2.getDistanceTravelled() >= horse3.getDistanceTravelled()) {
			return horse2;
		} else {
			return horse3;
		}
	}
}

	
