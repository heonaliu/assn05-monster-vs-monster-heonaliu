// Do NOT modify this file!

package apcsa.githubtrack;

public class Weapon {
  private String name = "";
  private int maxDamage = 0;

  public Weapon(String name, int maxDamage) {
    this.name = name;
    this.maxDamage = maxDamage;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String newName) {
    this.name = newName;
  }

  public int getMaxDamage() {
    return this.maxDamage;
  }

  @Override
  public String toString() {
    return String.format("Weapon {name: %s, maxDamage: %d}", this.name, this.maxDamage);
  }
}
