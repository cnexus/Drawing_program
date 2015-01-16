import java.io.*;
import java.util.*;

public class Input {
	private String q;
	
	public String getQ() {
		return q;
	}

	public void setQ(String quest) {
		q = quest;
	}

	public int getIntData (){
		PrintStream c = System.out;
		Scanner input = new Scanner(System.in);
		c.print(q);
		int answer = input.nextInt();
		input.nextLine(); //Clears the buffer
		return answer;
	}
	
	public String getStringData(){
		PrintStream c = System.out;
		Scanner input = new Scanner(System.in);
		c.print(q);
		String answer = input.nextLine();
		input.nextLine();
		return answer;
	}

}
