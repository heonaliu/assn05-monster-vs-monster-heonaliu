package apcsa.githubtrack;

//import java.util.Random;

// Implement your CrazyMonster class here
public class CrazyMonster implements Monster 
{
    //instance variables
    private int health;
    private String name;
    private int attackStrength;
    private int damageCount; // at first looking at the jUnit tests I wasn't sure whether this should've been part of the values passed in the constructor

    //constructor
    public CrazyMonster(String name, int health, int attackStrength)
    // looking at the UML Class Diagram, at first I was a little confused as to what values should be passed in this constructor since it only says health
    // but the JUnit Tests require String, int, and int
    {
        this.name = name;
        this.health = health;
        this. attackStrength = attackStrength;
        this.damageCount = 0;
    }

    //getters
    public String getName()
    {
        return this.name;
    }

    public int getHealth()
    {
        return this.health;   
    }
    
    public String attack(Monster other)
    {
        //random amount of damage based on attack strength
        int damage = (int) Math.random() * (this.attackStrength-1+1)+1;
        other.takeDamage(damage);
        return this.name + " attacks " + other.getName() + " doing " + damage+" damage\n";
    }

    public void takeDamage(int damage)
    {
        this.health -= damage;
        this.damageCount++;
        //if damage count is even, attack strength increases by damage
        if (this.damageCount%2==0)
        {
            this.attackStrength += damage;
        }
    }

}
