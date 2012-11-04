package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
class Submarine extends Ship {

    Submarine() {
        length = 1
        hit = [false]
    }

    /**
     * override the toString method
     * @return String indicating the ship
     */
    @Override
    public String toString() {
        return 'S'
    }

    /**
     * override the getShipType method
     * @return String indicating the ship type
     */
    @Override
    String getShipType() {
        return 'Submarine'
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

