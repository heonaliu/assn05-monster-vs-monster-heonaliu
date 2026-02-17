package apcsa.githubtrack;

import java.util.Random;

// Implement your CrazyMonster class here
public class CrazyMonster implements Monster 
{
    private int health;
    private String name;
    private int attackStrength;
    private int damageCount;

    public CrazyMonster(String name, int health, int attackStrength)
    {
        this.name = name;
        this.health = health;
        this. attackStrength = attackStrength;
        this.damageCount = 0;
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
        int damage = (int) Math.random() * (this.attackStrength-1+1)+1;
        other.takeDamage(damage);
        return this.name + " attacks " + other.getName() + " doing " + damage+" damage\n";
    }

    public void takeDamage(int damage)
    {
        this.health -= damage;
        this.damageCount++;
        if (this.damageCount%2==0)
        {
            this.attackStrength += damage;
        }
    }

}
