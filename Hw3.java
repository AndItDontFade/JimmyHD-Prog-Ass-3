import java.util.Arrays;
import java.util.Scanner;

public class Hw3 {
	public static void main(String[] args){
		
		/* PROBLEM 1 CODE (NOT RUNNING, infinite loop)
		int numThreads = 4;
		Problem1[] probOne = new Problem1[numThreads];
		for(int i = 0; i < numThreads; i++){
			probOne[i] = new Problem1(i + 1);
			probOne[i].start();
		}
		
		for(int i = 0; i < numThreads; i++){
			try{
				probOne[i].join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		System.out.println("Number of thank you cards " + Problem1.thankYouCards);
		
		*/
		//PROBLEM 2 CODE
		int numThreads = 8;
		Scanner scan = new Scanner(System.in);
		ProblemTwo[] probTwo = new ProblemTwo[numThreads];
		System.out.println("This is the problem TWO solution, could not get problem one to run" + "\n" + "(code and comments for problem1 in .Java)");
		System.out.println("How many hours do you want the program to run for? (type)");
		int hours = scan.nextInt();
		for(int i = 0; i < numThreads; i++){
			probTwo[i] = new ProblemTwo(i + 1, hours);
			probTwo[i].start();
		}
		for(int i = 0; i < numThreads; i++){
			try{
				probTwo[i].join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
		System.out.print("Top 5 highest temps were: ");
		System.out.println(Arrays.toString(ProblemTwo.highest));
		System.out.print("Top 5 lowest temps were: ");
		System.out.println(Arrays.toString(ProblemTwo.lowest));
		System.out.println("Highest interval after " + hours + " hours was " + ProblemTwo.highestInterval);
	}
}
