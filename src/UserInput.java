import java.io.*;
class UserInput {
	public static void main(String [] args){
		String name;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//ask the user for their name
		PrintStream p = System.out;
		System.out.println("What is your name?");
		try {
			name = reader.readLine();
			p.println(name + " is your name.");
		} catch (IOException ioe) {
			p.println("An unexpected error occurred. Please abandon all hope here.");
		}
	}
}