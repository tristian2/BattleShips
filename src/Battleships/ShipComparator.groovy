package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 *
 * this class compares two ships based on their properties that would deem them identical
 * takes two ships as parameters
 */
class ShipComparator implements Comparator {

    /**
     * compare two ships to see if they are the same based on column, row and type
     * ignore emptysea
     * @param Object object one
     * @param Object object two
     * @return int the result of the comparison for emptysea this will be zero
     */
    int compare(Object o1, Object o2) {
        Ship p1 = (Ship) o1
        Ship p2 = (Ship) o2

        if (p1.getShipType() == "EmptySea")
            return 0
        if (p1.getBowRow() != p2.getBowRow())
            return p1.getBowRow().compareTo(p2.getBowRow())
        else if (p1.getBowColumn() != p2.getBowColumn())
            return p1.getBowColumn().compareTo(p2.getBowColumn())
        return p1.getShipType().compareTo(p2.getShipType())


    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @return the result
     */
    @Override
    boolean equals(Object obj) {
        return this.equals(obj)
    }
}

