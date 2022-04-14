import java.util.ArrayList;
import java.util.Random;

public class ProblemTwo extends Thread{
	/* This program is highly efficient, running in less than a quarter second for a 1 hour report
	 * This program is also accurate because given a larger number of hours, 
	 * the highest and lowest arrays will be more sequential. (i.e. highest: 70, 69, 68, 67, 66)
	 * When a temp value is obtained, it first checks to see if it is bigger than anything in highest array
	 * if it's not bigger than anything, it checks to see if it is smaller than anything in lowest array
	 * if a number is moved out of the high array (due to shifting)
	 * and there is space in the lowest array, it will get moved
	 * if there is no space, nothing will happen, because we already know it cannot go in the lower array
	 * 
	 */
	int id;
	int limit;
	static int minutes = 0;
	static boolean finished = false;
	static int numTop = 5;
	static int TenMinInterval = 1;
	static int[] highest = new int[numTop]; //by default will be all 0s
	static int[] lowest = new int[numTop];
	static int highestInterval = 0;
	static int highestIntervalTime;
	
	public ProblemTwo(int ThreadId, int hours){
		id = ThreadId;
		limit = hours * 60;
	}
	
	public void run(){
		int i, lostVal;
		while(!finished){
			int temp = getTemp();
			for(i = 0; i < numTop; i++){
				if(temp >= highest[i]){
					break;
				}
			}
			if(i < numTop && temp > highest[i]){
				lostVal = shiftHigh(i);
				highest[i] = temp;
				if(lowest.length < 5){
					checkLowest(temp);
				}
			}
			else if(i < numTop && temp == highest[i]){
				
			}else{ //we need to see if it can go in lowest array since it cant go in highest
				checkLowest(temp);
			}
			minutes++;
			if(minutes % 10 == 0) {
				int interval = highest[0] - lowest[0];
				if(interval > highestInterval){
					highestInterval = interval;
					highestIntervalTime = minutes;
				}
				//System.out.println("Highest interval at " + minutes + "mins is " + highestInterval);
			}
			if(minutes >= limit) {
				finished = true;			
			}
		}
	}
	
	public int checkLowest(int temp){ //will see if value can go in lowest array
		int i;
		for(i = 0; i < numTop; i++){
			if(temp <= lowest[i]){
				break;
			}
		}
		if(i < numTop && temp < lowest[i]){
			shiftLow(i);
			lowest[i] = temp;
			return i;
		}
		else if(i < numTop && temp == lowest[i]) {} //do nothing
		return -1;
	}
	
	public int shiftHigh(int startIndex){ //returns last value lost in shift
		int i, lastVal = highest[numTop - 1];
		for(i = numTop - 1; i > startIndex; i--){
			highest[i] = highest[i - 1];
		}
		return lastVal;
	} //after Shift will need to check to see if there is space for lostVal in lowest array
	/*Will use:
	 * if(lowest.length() < 5){ //means there is space available in lowest array
	 * 	//put lost val at bottom of lowest array (last element)
	 * }
	 * 
	 */
	public void shiftLow(int startIndex){ //no lastVal needed, we know it cant go in high array
		int i;
		for(i = numTop - 1; i > startIndex; i--){
			lowest[i] = lowest[i - 1];
		}
	} //after Shift will need to check to see if there is space for lostVal in lowest array

	
	
	public int getTemp(){
		Random r = new Random();
		int temp = r.nextInt(70 + 100 + 1) - 100; //between -100 and 70 (inclusive)
		return temp;
	}
}
