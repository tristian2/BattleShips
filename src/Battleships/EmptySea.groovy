package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * Emptysea class inherits Ship class
 */
class EmptySea extends Ship {

    def firedAt  //holds state of being fired at, used to assist depiction of state in the UI

    /**
     * emptysea constructor
     */
    public EmptySea() {
        firedAt = false
    }

    /**
     * set fired at status, used by the ocean board to denote symbol shown
     */
    boolean setFiredAt() {
        this.firedAt = true
    }

    /**
     * override the getLength method
     * @return def fired at?
     */
    boolean getFiredAt() {
        return this.firedAt
    }

    /**
     * override the toString method
     * @return String indicating the ship
     */
    @Override
    public String toString() {
        return 'E'
    }

    /**
     * override the getShipType method
     * @return String indicating the ship type
     */
    @Override
    String getShipType() {
        return 'EmptySea'
    }

    /**
     * override shootat the ship method so that it always returns false
     * @param row of the shot
     * @param column of the shot
     * @return false
     */
    @Override
    boolean shootAt(int row, int column) {
        return false
    }

    /**
     * override isSunk method so that it always returns false
     * @return false
     */
    @Override
    boolean isSunk() {
        return false
    }


}
