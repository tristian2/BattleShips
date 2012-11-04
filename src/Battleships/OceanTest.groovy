package Battleships

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 29/01/12
 * Time: 19:21
 * various tests to test public methods and properties of ocean class
 */
class OceanTest extends GroovyTestCase {
    Ocean ocean

    /**
     * setup the tests
     */
    void setUp() {
        ocean = new Ocean()
    }

    /**
     * tear 'em down
     */
    void tearDown() {
        ocean = null
    }

    /**
     * test upper board bound
     */
    void testSetUpperBoardBound() {
        assertEquals(ocean.getUpperBoardBound(), 10)
    }

    /**
     * test ocean constructor
     */
    void testGetShips() {

        def ships = ocean.getShipArray()

        //assert construntor initialised values
        assertTrue(ocean.firstShip)
        assertEquals(ocean.shotsFired, 0)
        assertEquals(ocean.shipsSunk, 0)
        assertEquals(ocean.hitCount, 0)

        //check the board is empty
        def uniqueShips = ships.flatten().toList()
        assertTrue(uniqueShips.every {it.getShipType() == 'EmptySea'})

        //place the ships!
        ocean.placeAllShipsRandomly()
        uniqueShips = ships.flatten().toList()

        //check initial assertion is now false ie not all empty sea
        assertFalse(uniqueShips.every {it.getShipType() == 'EmptySea'})

        int[] numberEmptySeas = uniqueShips.findIndexValues {it.getShipType() == 'EmptySea'}
        assertEquals(80, numberEmptySeas.length) //check if there are 80 emptyseas
        int[] numberBattleships = uniqueShips.findIndexValues {it.getShipType() == 'BattleShip'}
        assertEquals(4, numberBattleships.length) //check if there are 4 battleship squares
        int[] numberDestroyers = uniqueShips.findIndexValues {it.getShipType() == 'Destroyer'}
        assertEquals(6, numberDestroyers.length) //check if there are 6 destroyer squares
        int[] numberCruiser = uniqueShips.findIndexValues {it.getShipType() == 'Cruiser'}
        assertEquals(6, numberCruiser.length) //check if there are 6 cruiser squares
        int[] numberSubmarines = uniqueShips.findIndexValues {it.getShipType() == 'Submarine'}
        assertEquals(4, numberSubmarines.length) //check if there are 4 submarine squares


    }

    /**
     * test get ocean
     */
    void testGetOcean() {
        def ships = ocean.getShipArray()

        assertEquals(ocean.firstShip, true)
        assertEquals(ocean.shotsFired, 0)
        assertEquals(ocean.shipsSunk, 0)
        assertEquals(ocean.hitCount, 0)
        //check the board is empty
        ships.eachWithIndex {
            rowObject, row ->
            rowObject.eachWithIndex {
                objValue, col ->
                if (objValue == null) {
                    assertEquals(ocean.isOccupied(row, col), false)

                }
            }
        }
    }

    /*
    *test shots fired
     */

    void testSetShotsFired() {
        int shotsFiredBefore = ocean.getShotsFired()
        ocean.setShotsFired()
        int shotsFiredAfter = ocean.getShotsFired()
        assertEquals(shotsFiredBefore + 1, shotsFiredAfter)
    }

    /**
     * is it occupied
     */
    void testIsOccupied() {
        boolean occupied = ocean.isOccupied(0, 0)

        assertNotNull(occupied)
    }

    /**
     * get hit count test
     */
    void testGetHitCount() {
        int hitCount = ocean.getHitCount()
        assertTrue(hitCount >= 0)
    }

    /**
     * is ships sunk between 0 and 20?
     */
    void setGetShipsSunk() {
        int shipsSunk = ocean.getShipsSunk()
        assertTrue(shipsSunk >= 0 && shipsSunk <= 20)
    }

    /**
     * is game over?
     */
    void testIsGameOver() {
        ocean.placeAllShipsRandomly()
        this.playEntireGame()
        assertTrue(ocean.isGameOver())
    }

    /**
     * test place all ships randomly
     */
    void testPlaceAllShipsRandomly() {
        int emptySeaSquaresCount
        int battleShipSquaresCount
        int cruiserSquaresCount
        int destroyerSquaresCount
        int submarineSquaresCount

        ocean.placeAllShipsRandomly()
        Ship[][] ships = ocean.getShipArray()

        def allShips = ships.flatten().toList()

        allShips.each {
            switch (it.getShipType()) {
                case 'BattleShip':
                    battleShipSquaresCount++
                    break
                case 'Cruiser':
                    cruiserSquaresCount++
                    break
                case 'Destroyer':
                    destroyerSquaresCount++
                    break
                case 'Submarine':
                    submarineSquaresCount++
                    break
                case 'EmptySea':
                    emptySeaSquaresCount++
                    break

            }
        }
        assertEquals(battleShipSquaresCount, 4) //one battleship occupies four squares
        assertEquals(cruiserSquaresCount, 6)  //two crusiers occupy six squares
        assertEquals(destroyerSquaresCount, 6) //three destroyers occupy six squares
        assertEquals(submarineSquaresCount, 4) //four sub occupy four squares
        assertEquals(emptySeaSquaresCount, 80)

    }

    /**
     * test shoot at method - will either be true or false
     */
    public testShootAt() {
        boolean result = ocean.shootAt(0, 0)
        assertNotNull(result)

    }

    /**
     * used by other tests, simulate playing the entire game
     */
    protected playEntireGame() {

        Ship[][] ships = ocean.getShipArray()

        ships.eachWithIndex {
            rowObject, row ->
            rowObject.eachWithIndex {
                objValue, col -> ocean.shootAt(row, col)
            }
        }

    }


}
