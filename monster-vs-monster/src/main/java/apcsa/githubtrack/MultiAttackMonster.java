package apcsa.githubtrack;

import java.util.Random;

// Implement your MultiAttackMonster class here
public class MultiAttackMonster implements Monster 
{
    //instance variables
    private String name;
    private int health;
    private Weapon weapon;
    private int attacks;

    //constructor
    public MultiAttackMonster(String name, int health, Weapon weapon, int attacks)
    {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.attacks = attacks;
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
        int max;
        int damage;
        String result = "";
        //attacks multiple times (set) so we use a for loop
        for (int i = 0; i<this.attacks;i++)
        {
            max = this.weapon.getMaxDamage();
            damage = (int) Math.random() * (max-1+1)+1;
            other.takeDamage(damage); // hits a random amount of damage to the other monster
            result += this.name + " attacks " + other.getName() + " with " + this.weapon.getName() + " doing " + damage+" damage\n";
        }
        //looking at the JUnit tests really helped me figure out what kind of result I was supposed to return here
        return result;
        
    }

    public void takeDamage(int damage)
    {
        this.health -= damage;   
    }



}
