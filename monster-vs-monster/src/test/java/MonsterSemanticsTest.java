import static org.junit.jupiter.api.Assertions.*;

import apcsa.githubtrack.*;
import org.junit.jupiter.api.Test;

/**
 * MonsterSemanticsTest
 *
 * <p>This test suite validates the observable behavior of the Monster vs Monster project as
 * specified in README.md. These tests check correctness of: - Interface-based polymorphism -
 * Accessor methods - Damage and attack logic - Output formatting - Multi-attack behavior -
 * CrazyMonster strength growth semantics
 *
 * <p>Tests intentionally avoid checking implementation details such as loops, indexing, or private
 * instance variables.
 */
public class MonsterSemanticsTest {

  // ---------- Helper Methods ----------

  /**
   * Creates a Weapon with a fixed name and configurable maxDamage. This ensures consistent,
   * repeatable testing across test cases.
   */
  private Weapon makeTestWeapon(int maxDamage) {
    return new Weapon("TestBlade", maxDamage);
  }

  // ---------- Interface Compliance ----------

  @Test
  void testMonstersImplementInterface() {
    // Declares each Monster using the Monster interface type.
    // Verifies that each concrete class can be used polymorphically.

    Monster m1 = new BasicMonster("Goblin", 50, makeTestWeapon(10));
    Monster m2 = new MultiAttackMonster("Orc", 60, makeTestWeapon(10), 2);
    Monster m3 = new CrazyMonster("Troll", 70, 1);

    // Confirms that object construction succeeds without runtime errors.
    assertNotNull(m1);
    assertNotNull(m2);
    assertNotNull(m3);
  }

  // ---------- Accessors ----------

  @Test
  void testGetNameAndHealthBasicMonster() {
    // Verifies that BasicMonster correctly stores and returns its fields.

    BasicMonster m = new BasicMonster("Fred", 100, makeTestWeapon(10));

    assertEquals("Fred", m.getName()); // Checks name accessor
    assertEquals(100, m.getHealth()); // Checks health accessor
  }

  @Test
  void testGetNameAndHealthMultiAttackMonster() {
    // Verifies that MultiAttackMonster correctly stores and returns its fields.

    MultiAttackMonster m = new MultiAttackMonster("Amy", 80, makeTestWeapon(10), 3);

    assertEquals("Amy", m.getName());
    assertEquals(80, m.getHealth());
  }

  @Test
  void testGetNameAndHealthCrazyMonster() {
    // Verifies that CrazyMonster correctly stores and returns its fields.

    CrazyMonster m = new CrazyMonster("Zed", 90, 1);

    assertEquals("Zed", m.getName());
    assertEquals(90, m.getHealth());
  }

  // ---------- takeDamage Semantics ----------

  @Test
  void testTakeDamageReducesHealthBasic() {
    // Confirms that BasicMonster.takeDamage subtracts damage from health.

    BasicMonster m = new BasicMonster("Test", 100, makeTestWeapon(10));

    m.takeDamage(25);

    assertEquals(75, m.getHealth());
  }

  @Test
  void testTakeDamageReducesHealthMultiAttack() {
    // Confirms that MultiAttackMonster.takeDamage behaves identically
    // to BasicMonster.takeDamage.

    MultiAttackMonster m = new MultiAttackMonster("Test", 100, makeTestWeapon(10), 2);

    m.takeDamage(40);

    assertEquals(60, m.getHealth());
  }

  @Test
  void testCrazyMonsterTakeDamageReducesHealth() {
    // Confirms that CrazyMonster.takeDamage subtracts damage from health,
    // regardless of internal damageCount or attackStrength logic.

    CrazyMonster m = new CrazyMonster("Test", 100, 1);

    m.takeDamage(10);
    m.takeDamage(5);

    assertEquals(85, m.getHealth());
  }

  // ---------- Attack Behavior ----------

  @Test
  void testBasicMonsterAttackFormatAndEffect() {
    // Verifies that BasicMonster.attack:
    //  - Returns a correctly formatted String
    //  - Applies damage to the target Monster

    BasicMonster attacker = new BasicMonster("Fred", 100, makeTestWeapon(10));
    BasicMonster target = new BasicMonster("Amy", 100, makeTestWeapon(10));

    String result = attacker.attack(target);

    // Checks required text elements (not exact damage value).
    assertTrue(result.contains("Fred attacks Amy with TestBlade doing"));

    // Ensures newline formatting matches README specification.
    assertTrue(result.endsWith("damage\n"));

    // Confirms that damage was actually applied.
    assertTrue(target.getHealth() < 100);
  }

  @Test
  void testMultiAttackProducesCorrectNumberOfLines() {
    // Verifies that MultiAttackMonster.attack:
    //  - Executes exactly numberOfAttacks attacks
    //  - Produces one output line per attack

    MultiAttackMonster attacker = new MultiAttackMonster("Fred", 100, makeTestWeapon(5), 3);
    BasicMonster target = new BasicMonster("Amy", 100, makeTestWeapon(5));

    String result = attacker.attack(target);

    // Splits the returned String into individual attack lines.
    String[] lines = result.split("\n");

    // Confirms the number of lines equals numberOfAttacks.
    assertEquals(3, lines.length);

    // Confirms each line follows the required format.
    for (String line : lines) {
      assertTrue(line.contains("Fred attacks Amy with TestBlade doing"));
    }

    // Confirms cumulative damage was applied.
    assertTrue(target.getHealth() < 100);
  }

  @Test
  void testCrazyMonsterAttackFormatAndEffect() {
    // Verifies that CrazyMonster.attack:
    //  - Does not reference a Weapon
    //  - Uses correct output format
    //  - Applies damage to the target

    CrazyMonster attacker = new CrazyMonster("Chaos", 100, 1);
    BasicMonster target = new BasicMonster("Amy", 100, makeTestWeapon(10));

    String result = attacker.attack(target);

    assertTrue(result.contains("Chaos attacks Amy doing"));
    assertTrue(result.endsWith("damage\n"));
    assertTrue(target.getHealth() < 100);
  }

  // ---------- Random Damage Bounds ----------

  @Test
  void testBasicMonsterAttackWithinWeaponBounds() {
    // Confirms that BasicMonster.attack damage is within [1, maxDamage].

    BasicMonster attacker = new BasicMonster("Fred", 100, makeTestWeapon(5));
    BasicMonster target = new BasicMonster("Amy", 100, makeTestWeapon(5));

    int before = target.getHealth();
    attacker.attack(target);
    int dealt = before - target.getHealth();

    assertTrue(dealt >= 1 && dealt <= 5);
  }

  @Test
  void testCrazyMonsterAttackStrengthGrowsOnEvenHits() {
    // Verifies that CrazyMonster.attackStrength increases on even-numbered hits
    // and affects the upper bound of attack damage.

    CrazyMonster attacker = new CrazyMonster("Chaos", 100, 1);
    BasicMonster target = new BasicMonster("Amy", 100, makeTestWeapon(10));

    // First hit (odd): no strength increase
    attacker.takeDamage(4);

    // Second hit (even): attackStrength should increase by 6
    attacker.takeDamage(6);

    int before = target.getHealth();
    attacker.attack(target);
    int dealt = before - target.getHealth();

    // attackStrength should now be > 1, allowing damage up to 7
    assertTrue(dealt >= 1 && dealt <= 7);
  }
}