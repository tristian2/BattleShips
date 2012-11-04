package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * Cruiser class inherits Ship class
 */
class Cruiser extends Ship {

    /**
     * cruiser constructor
     */
    Cruiser() {
        length = 3
        hit = [false, false, false]
    }

    /**
     * override the toString method
     * @return String indicating the ship
     */
    @Override
    public String toString() {
        return 'C'
    }

    /**
     * override the getShipType method
     * @return String indicating the ship type
     */
    @Override
    String getShipType() {
        return 'Cruiser'
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
