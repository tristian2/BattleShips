package Battleships

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 31/01/12
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
class ShipComparatorTest extends GroovyTestCase {
    Ocean ocean
    Battleship battleship
    Submarine submarine

    void setUp() {
        ocean = new Ocean()

        battleship = new Battleship()
        battleship.placeShipAt(1, 1, true, ocean)
        submarine = new Submarine()
        submarine.placeShipAt(5, 5, false, ocean)

    }

    void tearDown() {
        ocean = null
        battleship = null
        submarine = null
    }
    /**
     * test the custom comparator works
     */
    void testCompare() {
        ShipComparator shipComparator = new ShipComparator()
        assertEquals(-1, shipComparator.compare(battleship, submarine))
        assertEquals(0, shipComparator.compare(battleship, battleship))
    }

}
