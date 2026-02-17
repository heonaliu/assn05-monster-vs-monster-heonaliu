package apcsa.githubtrack;

//import java.util.Random;

// Implement your BasicMonster class here
public class BasicMonster implements Monster {
    //instance variables
    private String name;
    private int health;
    private Weapon weapon;
    
    //constructor
    public BasicMonster(String name, int health, Weapon weapon)
    {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
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
        //find maxDamage of our weapon
        //at the beginning I assumed it was the maxDamage of the other monster's weapon and not ours
        //then I was confused as to how to access that. I thought I was also supposed add method like getWeapon
        //then I figured it wasn't the "other" weapon, it was ours.
        int max = this.weapon.getMaxDamage();
        // use the Random class to randomize damage
        int damage = (int) Math.random() * (max-1+1)+1;
        //call takeDamage at other Monster
        other.takeDamage(damage);

        return this.name + " attacks " + other.getName() + " with " + this.weapon.getName() + " doing " + damage+" damage\n";
    }


    public void takeDamage(int damage)
    {
        //subtract amount of damage from health
        this.health -= damage;

    }

}
