package contacts.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

	/** Buffered reader for user input */
	private BufferedReader user;
	private boolean finished; 
	
	public Client() {
		user = new BufferedReader(new InputStreamReader(System.in));	
		finished = false;
	}
	
	
	private void interact() throws IOException {
		while(!finished) {
			// Read command in and break in words
			String command = user.readLine();
			
			switch(command) {
				case "remove" : 
					//prompts for name of contact to be deleted
					//contact should be deleted and references from friends deleted
					
					System.out.print("name:");
					command = user.readLine();

					break;
				case "group" :
					//prompts user for group name
					System.out.print("group name:");

					//prints out all members of that group
					break;
				case "pull" :
					//retrieves the latest version of the addressbook from the server
					//converts that XML version into an addressbook
					//sets addressbook equal to that newly parsed one
					break;
				case "push" :
					//writes Client version of addressbook to XML
					//sends that XML version to the server
					break;
				case "query path" :
					//do stuff
					break;
				case "query mutual" :
					//do stuff
					break;
				case "quit" :
					//saves addressbook and exits the program
					finished = true;
					break;
				default :
					System.out.println("Please input one of the following: remove, "
							+ "group, pull, push, query path, query mutual, quit");
					break;
			}
			
		}
	}
	
	
	public static void main (String[] args) {
		Client c = new Client();
		try {
			c.interact();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
