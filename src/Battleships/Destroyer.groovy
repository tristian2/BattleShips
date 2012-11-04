package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * Destroyer class inherits Ship class
 */
class Destroyer extends Ship {

    /**
     * Destroyer constructor
     */
    Destroyer() {
        length = 2
        hit = [false, false]
    }

    /**
     * override the toString method
     * @return String indicating the ship
     */
    @Override
    public String toString() {
        return 'D'
    }

    /**
     * override the getShipType method
     * @return String indicating the ship type
     */
    @Override
    String getShipType() {
        return 'Destroyer'
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
