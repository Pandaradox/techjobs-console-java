package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");
        actionChoices.put("exit", "Exit");

        System.out.println("Welcome to LaunchCode's TechJobs App!");
        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                    	
                        System.out.println(item);
                    }
                }

            } else if (actionChoice.equals("search")){ 

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            } else { //Choice is exit
            	System.out.println("\nGoodbye!");
            	break;
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
    	String display = "";
    	//Alphabetically Sort Jobs By Job Name:
    	int y = 0; //Char Compare Position
		for (int x = 0; x < someJobs.size()-1; x++) {
			y = 0;
			//Checks for equal Char (case-insensitive):
			while (Character.toLowerCase(someJobs.get(x).get("name").toCharArray()[y]) 
					== Character.toLowerCase(someJobs.get(x+1).get("name").toCharArray()[y])) {
				
				//Checks for name boundary
				if (y == someJobs.get(x).get("name").length()-1 
						|| y == someJobs.get(x+1).get("name").length()-1) {
					break;
				}
				y++;
			}
			
			//Performs swap based on Compare Char position and resets the whole array scan
			//Included condition where the second term ran out of chars to compare, leading to a needed swap.
			if ((Character.toLowerCase(someJobs.get(x).get("name").toCharArray()[y]) 
					> Character.toLowerCase(someJobs.get(x+1).get("name").toCharArray()[y])) 
					|| ((Character.toLowerCase(someJobs.get(x).get("name").toCharArray()[y]) 
									== Character.toLowerCase(someJobs.get(x+1).get("name").toCharArray()[y]))
										&& someJobs.get(x+1).get("name").length() < someJobs.get(x).get("name").length())){
				Collections.swap(someJobs, x, x+1);
				x = -1;
			}
		}
   	
		if (someJobs.size() == 0) {
			System.out.println("No Jobs Were Found");
		}
    	
    	for (HashMap<String,String> job : someJobs) {
//	    	String display = String.format("*****\n" + 
//	    			"position type: %1s\n" + 
//	    			"name: %2s\n" + 
//	    			"employer: %3s\n" + 
//	    			"location: %4s\n" + 
//	    			"core competency: %5s\n*****",
//	    			job.get("position type"),
//	    			job.get("name"),
//	    			job.get("employer"),
//	    			job.get("location"),
//	    			job.get("core competency"));
    		System.out.println("\n*****");
    		for (String key : job.keySet()) {
    			display = key + ": "+ job.get(key);
    			System.out.println(display);
    		}
    		System.out.println("*****");
    	}
    }
}
