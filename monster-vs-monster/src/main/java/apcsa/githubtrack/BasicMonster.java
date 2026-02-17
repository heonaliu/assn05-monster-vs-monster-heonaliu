package apcsa.githubtrack;

import java.util.Random;

// Implement your BasicMonster class here
public class BasicMonster implements Monster {
    private String name;
    private int health;
    private Weapon weapon;

    public BasicMonster(String name, int health, Weapon weapon)
    {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
    }

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
        int max = this.weapon.getMaxDamage();
        int damage = (int) Math.random() * (max-1+1)+1;
        other.takeDamage(damage);

        return this.name + " attacks " + other.getName() + " with " + this.weapon.getName() + " doing " + damage+" damage\n";
    }


    public void takeDamage(int damage)
    {
        //subtract amount of damage from health
        this.health -= damage;

    }

}
