package apcsa.githubtrack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Implement your main class and main method here

public class Main
{
    public static void main(String[] args) throws IOException
    {
        //Initialize arrays of data using Scanner class
        ArrayList<String> monsterNameData = new ArrayList<String>();
        ArrayList<Integer> healthData = new ArrayList<Integer>();
        File myFile = new File("monster-vs-monster/src/main/resources/MonsterData.txt");
        Scanner scan = new Scanner(myFile);

        //Here in this while loop, I check if a new line exists in the file
        while (scan.hasNext())
        {
            String line = scan.nextLine();
            //find the index of the space " " character by checking each character using substring
            int space = 0;
            for (int i = 0; i<line.length()-1; i++)
            {
                String character = line.substring(i,i+1);
                if (character.equals(" "))
                {
                    space = i;
                    break;
                }
            }
            //using string slicing and the index of space, I get the name of the monster and the health
            //this works because the space can be located anywhere and is not hard coded
            String monster = line.substring(4,space);
            int health = Integer.parseInt(line.substring(space+1));
            monsterNameData.add(monster);
            healthData.add(health);
        }
        scan.close();

        // System.out.println(monsterNameData);
        // System.out.println(healthData);

        //initialize an ArrayList of type Monster and add them
        ArrayList<Monster> activeParticipants = new ArrayList<Monster>();
        //creates a test weapon
        Weapon test = new Weapon("TestBlade", 10);
        // initialize 3 monsters
        Monster m1 = new BasicMonster(monsterNameData.get(0), healthData.get(0), test);
        Monster m2 = new MultiAttackMonster(monsterNameData.get(1),healthData.get(1), test, 2);
        Monster m3 = new CrazyMonster(monsterNameData.get(2), healthData.get(2), 1);

        activeParticipants.add(m1);
        activeParticipants.add(m2);
        activeParticipants.add(m3);

        //battle loop that makes sure there must be at least participants in the list to run
        while (activeParticipants.size()>=2)
        {
            //this loop is to create a list of opponents (that doesn't include the current Monster)
            for (int i = 0; i<activeParticipants.size(); i++)
            {
                ArrayList<Integer> opponent = new ArrayList<Integer>();
                for (int j = 0; j<activeParticipants.size();j++)
                {
                    if (!activeParticipants.get(j).equals(activeParticipants.get(i)))
                    {
                        opponent.add(j);
                    }
                }
                //Once we have a list of random opponents, we can use the Random class to generate a random index in the opponents list 
                // (which stores the index of opponents)
                Random random = new Random();
                int randomIndex = random.nextInt(opponent.size());

                //once we have opponent monster and current monster, we can fight
                Monster theOpponent = activeParticipants.get(randomIndex);
                Monster current = activeParticipants.get(i);
                current.attack(theOpponent);

                //checks if opponent's health is greater than 0, if yes, then we break and go back out the while loop
                //once we go back to while loop, it will check if the size is still >= 2
                if (theOpponent.getHealth()<=0)
                {
                    activeParticipants.remove(theOpponent);
                    break; //exits the loop back to the while loop
                }

            }
        }
        System.out.println(activeParticipants.get(0).getName());

        

    }

}