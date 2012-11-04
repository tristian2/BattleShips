package Battleships

/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
class ShipTest extends GroovyTestCase {
    Submarine submarine
    Cruiser cruiser
    Destroyer destroyer
    Battleship battleship
    EmptySea emptySea

    Ocean ocean

    void setUp() {
        ocean = new Ocean()
        submarine = new Submarine(hit: [true], horizontal: true)
        cruiser = new Cruiser(hit: [true, true, true], horizontal: true)
        destroyer = new Destroyer(hit: [true, true], horizontal: true)
        battleship = new Battleship(hit: [true, true, true, true], horizontal: false)
        emptySea = new EmptySea(horizontal: false)

        submarine.placeOnBoard(1, 1, true, ocean)
        cruiser.placeOnBoard(3, 3, true, ocean)
        destroyer.placeOnBoard(5, 5, true, ocean)
        battleship.placeOnBoard(1, 9, false, ocean)
        emptySea.placeOnBoard(9, 9, false, ocean)
    }

    void tearDown() {
        ocean = null
        submarine = null
        cruiser = null
        destroyer = null
        battleship = null
        emptySea = null
    }

    void testGetBowRow() {
        //various ships set earlier, test values
        assertEquals(submarine.getBowRow(), 1)
        assertEquals(cruiser.getBowRow(), 3)
        assertEquals(destroyer.getBowRow(), 5)
        assertEquals(battleship.getBowRow(), 1)
        assertEquals(emptySea.getBowRow(), 9)
    }

    void testGetBowColumn() {
        //various ships set earlier, test values
        assertEquals(submarine.getBowColumn(), 1)
        assertEquals(cruiser.getBowColumn(), 3)
        assertEquals(destroyer.getBowColumn(), 5)
        assertEquals(battleship.getBowColumn(), 9)
        assertEquals(emptySea.getBowColumn(), 9)
    }

    void testGetShipType() {
        //various ships set earlier, test values
        assertEquals(submarine.getShipType(), 'Submarine')
        assertEquals(cruiser.getShipType(), 'Cruiser')
        assertEquals(destroyer.getShipType(), 'Destroyer')
        assertEquals(battleship.getShipType(), 'BattleShip')
        assertEquals(emptySea.getShipType(), 'EmptySea')
    }

    void testIsHorizontal() {
        //various ships set earlier, test values
        assertTrue(submarine.isHorizontal())
        assertTrue(cruiser.isHorizontal())
        assertTrue(destroyer.isHorizontal())
        assertFalse(battleship.isHorizontal())
        assertFalse(emptySea.isHorizontal())
    }

    void testIsSunk() {
        //sink the ships
        submarine.setSunk(true)
        cruiser.setSunk(true)
        destroyer.setSunk(true)
        battleship.setSunk(true)
        emptySea.setSunk(false)
        assertEquals(submarine.isSunk(), true)
        assertEquals(cruiser.isSunk(), true)
        assertEquals(destroyer.isSunk(), true)
        assertEquals(battleship.isSunk(), true)
        assertEquals(emptySea.isSunk(), false)
    }

    void testDetermineIfShipIsSunk() {
        assertTrue(submarine.determineIfShipIsSunk())
        assertTrue(cruiser.determineIfShipIsSunk())
        assertTrue(destroyer.determineIfShipIsSunk())
        assertTrue(battleship.determineIfShipIsSunk())
        assertFalse(emptySea.determineIfShipIsSunk())
    }

    void testShootAt() {
        //we know there will be hits for the ships and a miss for emptysea
        assertTrue(submarine.shootAt(1, 1))
        assertTrue(cruiser.shootAt(3, 3))
        assertTrue(destroyer.shootAt(5, 5))
        assertTrue(battleship.shootAt(1, 9))
        assertFalse(emptySea.shootAt(9, 9))
    }


}
