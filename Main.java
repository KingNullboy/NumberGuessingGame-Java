import static EasyJava.EasyJava.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		print("KingNullboy's Java Number Guessing Game");
		
		while (true) {
			int num = randint(1,100);
			int guess = -1;
			File save = new File("save.txt");
			
			if (!save.exists() || save.length() == 0) {
				try (FileWriter writer = new FileWriter("save.txt")) {
					writer.write("0,0");
				} catch (IOException e) {
					print("Something broke and you don't get a save file anymore. This is what broke:\n"+e);
					System.exit(1);
				}
			}

			String[] contents = null;
			int plays = -1;
			int wins = -1;
			try (Scanner reader = new Scanner(save)) {
				contents = reader.nextLine().split(",");
				plays = Integer.parseInt(contents[0]);
				wins = Integer.parseInt(contents[1]);
			} catch (FileNotFoundException | NumberFormatException e) {
				print("Something broke and you don't get a save file anymore. This is what broke:\n"+e);
				System.exit(1);
			}

			print("You have played "+plays+" time"+(plays == 1 ? "" : "s")+" before, and won "+wins+" of those times for a win rate of "+((double) wins/Math.max(1,plays))*100+"%.");

			plays++;

			while (guess != num) {
				try {
					guess = (int) input("What is your guess of the secret number? (1-100)\n> ");
				} catch (NumberFormatException e) {
					print("Buddy, that's not a number. Defaulting to 50.");
					guess = 50;
				}

				if (guess < 1 || guess > 100) {
					print("That's not a number between 1 and 100. Can't you read?");
				} else if (guess > num) {
					print("That number is greater than the secret number.");
				} else if (guess < num) {
					print("That number is less than the secret number.");
				} else if (guess == num) {
					print("You win!");
					wins++;
					break;
				}
			}

			try (FileWriter writer = new FileWriter("save.txt")) {
				writer.write(plays+","+wins);
			} catch (IOException e) {
				print("Something broke and you don't get a save file anymore. This is what broke:\n"+e);
			}

			String again = (String) input("Play again? (Y/n)\n> ");
			if (again.toLowerCase().startsWith("y")) {
				continue;
			} else if (again.toLowerCase().startsWith("n")) {
				break;
			} else {
				print("That doesn't start with y or n. Can't you read?");
				print("Assuming no.");
				break;
			}
		}
	}
}