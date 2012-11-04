package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * Battleship class inherits Ship class
 * *
 */
class Battleship extends Ship {

    /**
     * battleship constructor
     */

    Battleship() {
        length = 4
        hit = [false, false, false, false]
    }

    /**
     * override the toString method
     * @return String indicating the ship
     */
    @Override
    public String toString() {
        return 'B'
    }

    /**
     * override the getShipType method
     * @return String indicating the ship type
     */
    @Override
    String getShipType() {
        return 'BattleShip'
    }

    /**
     * override the getLength method
     * @return the super class length
     */
    @Override
    int getLength() {
        return super.length
    }
}