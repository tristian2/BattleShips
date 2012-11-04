package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * various tests to test public methods and properties of ship class
 * uses concrete extended classes to infer values of abstract ship class
 */
abstract class Ship {

    int bowRow
    int bowColumn
    int length
    boolean horizontal
    def symbol
    boolean[] hit = new boolean[4]
    boolean sunk = false

    String shipType

    /**
     * Returns the type of this ship. This method exists only to be overridden, so it doesn't much matter what it returns.
     * @return String shipType
     */
    String getShipType() {
        return this.shipType
    }
    /**
     * horizontal status -
     * @return boolean whether the ship is horizontal or not
     */
    protected boolean isHorizontal() {
        return this.horizontal
    }
    /**
     * set horizontal status
     * @param boolean whether horizontal or not
     */
    void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal
    }

    /**
     * get the bow row
     * @return the bow row of the ship
     */
    int getBowRow() {
        return this.bowRow
    }
    /**
     * set the bow row
     * @param int the bow row of the ship
     */
    void setBowRow(int row) {
        this.bowRow = row
    }
    /**
     * set the bow column
     * @param the bow column of the ship
     */
    void setBowColumn(int column) {
        this.bowColumn = column
    }
    /**
     * get the bow column
     * @return the bow column of the ship
     */
    int getBowColumn() {
        return this.bowColumn
    }

    /**
     * get the length
     * @return int length
     */
    int getLength() {
        return this.length
    }

    /**
     * is it sunk?
     * @return boolean the ship being sunk or not
     */
    boolean isSunk() {
        return this.sunk
    }

    /**
     * sink the ship
     * @param boolean set if the ship is sunk or not
     */
    boolean setSunk(boolean sunk) {
        this.sunk = sunk
    }

    /**
     * get the array of hits
     * @return boolean[] array of the hits made on the ship
     */
    boolean[] getHit() {
        return this.hit
    }

    /**
     * is the ship sunk based on the array of hits?
     * @return boolean find out of the ship is sunk
     */
    boolean determineIfShipIsSunk() {
        int length = this.getLength() - 1
        sunk = this.hit[0..length].every()
        return sunk
    }

    /**
     * place the show at given row, column and horizontal status
     * @param int the row of the ships bow
     * @param int the column of the ships bow
     * @param boolean horizontal?
     * @param Ocean the ocean object
     */
    protected placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {

        this.setHorizontal(horizontal)

        def placed = false;
        while (!placed) {
            def valid = okToPlaceShipAt(row, column, horizontal, ocean)
            if (valid) {
                placeOnBoard(row, column, horizontal, ocean)
                placed = true;
            } else {
                row = ocean.aRandomRow(horizontal, this.getLength())
                column = ocean.aRandomColumn(horizontal, this.getLength())
            }
        }

    }

    /**
     * is it okay to place the ship based on the constraints at a given location?
     * @param int the row of the ships bow
     * @param int the column of the ships bow
     * @param boolean horizontal?
     * @param Ocean the ocean object
     */
    protected boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        def valid = true

        if (ocean.getFirstShip()) {
            ocean.setFirstShip(false)
            return valid
        }

        if (!isEmpty(row, column, horizontal, ocean)) {
            valid = false
            return valid
        }

        Ship[][] ships = ocean.getShipArray()

        for (def i = 0; i < this.getLength(); i++) {
            if (horizontal) {
                if (ocean.ships[row][column + i].getShipType() != 'EmptySea') {
                    valid = false
                    return valid
                }
            } else {
                if (ocean.ships[row + i][column].getShipType() != 'EmptySea') {
                    valid = false
                    return valid
                }
            }
        }
        return valid
    }

    /**
     * check the adjacent squares
     * @param int the row of the ships bow
     * @param int the column of the ships bow
     * @param boolean horizontal?
     * @param Ocean the ocean object
     */
    protected isEmpty(int row, int column, boolean horizontal, Ocean ocean) {
        Ship[][] ships = ocean.getShipArray()
        int length = this.getLength()
        def empty = true;
        def peekRow = row - 1
        def peekColumn = column - 1

        if (horizontal) {
            while (peekRow < row + 2) {
                try {
                    while (peekColumn < column + length + 2) {
                        try {
                            if (ships[peekRow][peekColumn].getShipType() != 'EmptySea') {
                                empty = false
                            }
                            peekColumn++
                        }
                        catch (ArrayIndexOutOfBoundsException aoobe) {
                            //might be at the edge, increment anyway
                            peekColumn++
                        }
                    }
                    peekColumn = column - 1
                    peekRow++
                }
                catch (ArrayIndexOutOfBoundsException aoobe) {
                    //might be at the edge, increment anyway
                    peekRow++
                }
            }
        }
        else {
            //vertical
            while (peekRow < row + length + 1) {
                try {
                    while (peekColumn < column + 2) {
                        try {

                            if (ships[peekRow][peekColumn].getShipType() != 'EmptySea') {
                                empty = false
                            }
                            peekColumn++
                        }
                        catch (ArrayIndexOutOfBoundsException aoobe) {
                            //might be at the edge, increment anyway
                            peekColumn++
                        }
                    }
                    peekColumn = column - 1
                    peekRow++
                }
                catch (Exception ex) {
                    //might be at the edge, increment anyway
                    peekRow++
                }
            }
        }

        return empty

    }

    /**
     * place the ship on the board
     * @param int the row of the ships bow
     * @param int the column of the ships bow
     * @param boolean horizontal?
     * @param Ocean the ocean object
     */
    protected placeOnBoard(int row, int column, boolean horizontal, Ocean ocean) {
        Ship[][] ships = ocean.getShipArray()

        this.setBowRow(row)
        this.setBowColumn(column)

        for (def i = 0; i < this.getLength(); i++) {
            if (horizontal) {
                ships[row][column + i] = this
            } else {
                ships[row + i][column] = this
            }
        }
    }

    /**
     * shoot at the ship
     * @param int the row of the shot
     * @param int the column of the shot
     */
    boolean shootAt(int row, int column) {
        //if row and column part of ship
        boolean directHit = false

        if (this.horizontal) {
            for (int i = 0; i < this.getLength(); i++) {
                if ((this.bowRow == row) && (this.bowColumn + i == column)) {
                    directHit = true
                    this.hit[i] = true
                }
            }
        } else {
            for (int i = 0; i < this.getLength(); i++) {
                if ((this.bowRow + i == row) && (this.bowColumn == column)) {
                    directHit = true
                    this.hit[i] = true
                }
            }
        }
        this.setSunk(determineIfShipIsSunk())

        return directHit
    }
}


