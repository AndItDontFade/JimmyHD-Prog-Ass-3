import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

public class Problem1 extends Thread{
	/*
	 * Could not get this Problem to work no matter what I tried, it does an infinite loop
	 * I tried (to no avail) to implement the hands-over locking method seen in lec 9
	 * Basically Anytime a node was to be added or removed it would require two locks
	 * My approach was to decide which node to remove randomly in terms of number of movements
	 * what this meant is, I would fetch a random numer from 1 to size of the list (measured by static listSize)
	 * However large this number is, is how many steps we would move over from the head to get to the node to delete
	 * 
	 */
	public class Node{
		int tag;
		Node next = null;
	}
	static Node head = null;
	Node curr, pred;
	static int presents = 500000;
	static int presentCounter = 0;
	static int listSize = 0;
	static int thankYouCards = 0;
	int servantId;
	static ArrayList<Node> locks = new ArrayList<Node>();
	
	public Problem1(int id){
		servantId = id;
	}
	
	public void run(){
		Random r = new Random();
		int pos;
		while(presentCounter < presents && thankYouCards < presents){
			add(++presentCounter);
			pos = r.nextInt((listSize - 1) + 1) + 1;
			if(listSize > 0  && thankYouCards < presents){
				remove(pos);
			}
		}
	}
	
	public boolean add(int tag){
		if(head == null && listSize == 0){
			while(lock(head)){}
			listSize++;
			head = new Node();
			head.tag = tag;
			unlock(head);
			return true;
		}
		if(listSize < 2){
			//while(head == null){}
			listSize++;
			head.next = new Node();
			head.tag = tag;
			return true;
		}
		pred = head; curr = pred.next;
		while(!lock(pred)) { }
		while(!lock(curr)) { }
		while(curr.tag < tag && curr != null){
			unlock(pred);
			pred = curr;
			curr = curr.next;
			while(!lock(pred)) { }
			while(!lock(curr)) { }
		}
		Node node = new Node();
		node.tag = tag;
		if(curr != null){
			node.next = curr;
			pred.next = node;
		}else{
			pred.next = node;
		}
		synchronized (this){
			listSize++;
		}
		unlock(pred);
		unlock(curr);
		return true;
	}
	
	public boolean remove(int pos){
		int counter = 1;
		if(thankYouCards >= presents){
			return false;
		}
		if(head == null){
			return false;
		}
		if( pos == 1 && head.next != null){
			while(lock(head)){}
			head = head.next;
			unlock(head);
			return true;
		}
		if(listSize >= 2){
			pred = head;
			curr = head.next;
			while(!lock(pred)){ }
			while(!lock(curr)){ }
		}
		while(counter < pos && curr != null){
			counter++;
			unlock(pred); 
			pred = curr;
			curr = curr.next;
			while(!lock(pred)) {}
			while(!lock(curr)){
				
			}
		}
		if(counter == pos){
			pred.next = curr.next;
			synchronized(this){
				listSize--;
				thankYouCards++;
			}
			unlock(curr);
			return true;
		}
		return false;
	}
	
	public boolean lock(Node node){
		if(!locks.contains(node)){
			locks.add(node);
			return true;
		}
		return false;
	}
	
	public boolean unlock(Node node){
		if(locks.remove(node)){
			return true;
		}
		return false;
	}
	
}
