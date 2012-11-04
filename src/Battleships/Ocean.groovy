package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
class Ocean {

    def upperBoardBound = 10
    Ship[][] ships = new Ship[10][10] // Used to quickly determine which ship is in any given location.

    Ocean ocean
    boolean firstShip  //is it the first ship
    int shotsFired  //-- The total number of shots fired by the user.
    int hitCount //-- The number of times a shot hit a ship. If the user shoots the same part of a ship more than once, every hit is counted, even though the additional "hits" don't do the user any good.
    int shipsSunk //-- The number of ships sunk (10 ships in all).

    //the fleet - declared in this scope so they are available
    def battleship = new Battleship()
    def cruiser1 = new Cruiser()
    def cruiser2 = new Cruiser()
    def destroyer1 = new Destroyer()
    def destroyer2 = new Destroyer()
    def destroyer3 = new Destroyer()
    def submarine1 = new Submarine()
    def submarine2 = new Submarine()
    def submarine3 = new Submarine()
    def submarine4 = new Submarine()

    /**
     * The constructor. Creates an "empty" ocean (fills the ships array with EmptySeas).
     * Also initializes any game variables, such as how many shots have been fired.
     */
    public Ocean() {
        firstShip = true
        shotsFired = 0
        shipsSunk = 0
        hitCount = 0
        this.assignEmptySeaIndicesToShipArray()
    }

    /**
     *  the place on the board occupied
     * @param the row
     * @param the column
     * @return String the ship type
     */
    boolean isOccupied(int row, int column) {
        return ships[row][column].getShipType() != 'EmptySea'
    }

    /**
     * Returns the number of shots fired (in this game).
     * @return int the total number of shots fired in a given game
     */
    int getShotsFired() {
        return this.shotsFired
    }
    /**
     * increments the shots fired in the game count
     */
    void setShotsFired() {
        this.shotsFired++
    }
    /**
     * Returns the number of hits recorded (in this game). All hits are counted, not just the first time a given square is hit.
     * @return int the number of hits made in a game
     */
    int getHitCount() {
        return this.hitCount
    }
    /**
     * Returns the number of ships sunk (in this game).
     * return int the number of ships that have been sunk
     */
    int getShipsSunk() {
        ShipComparator shipComparator = new ShipComparator()
        def uniqueShips = ships.flatten().toList()
        int shipsSunk = 0

        uniqueShips.unique(shipComparator).each {
            it.each {
                if (it.isSunk()) {
                    shipsSunk++
                }
            }
        }

        return shipsSunk
    }
    /**
     * Returns true if all ships have been sunk, otherwise false.
     * return whether the game is over or not
     */
    boolean isGameOver() {
        def gameOver = false
        if (this.getShipsSunk() == 10)
            gameOver = true
        return gameOver
    }
    /**
     * Returns the 10x10 array of ships. The methods in the Ship class that take an Ocean parameter really need to be able
     * to look at the contents of this array; the placeShipAt method even needs to modify it. While it is undesirable to
     * allow methods in one class to directly access instance variables in another class, sometimes there is just no good alternative.
     * @return Ship[][] the ships array
     */
    Ship[][] getShipArray() {
        return this.ships
    }

    /**
     * override the tioString() method
     * Returns a String for printing the ocean. To aid the user, row numbers should be displayed along the left edge of the array,
     * and column numbers should be displayed along the top. Numbers should be 0 to 9, not 1 to 10. The top left corner square
     * should be 0, 0. Use 'S' to indicate a location that you have fired upon and hit a (real) ship, '-' to indicate a location
     * that you have fired upon and found nothing there, 'x' to indication location containing a sunken ship, and '.' to indicate
     * a location that you have never fired upon.  This is the only method in the Ocean class that does any input/output, and it
     * is never called from within the Ocean class (except possibly during debugging), only from the BattleshipGame class.
     * @return the formatted board
     */
    @Override
    public String toString() {
        return this.formatBoardForConsole()
    }

    /**
     * returns a random column integer
     * @return def a random column
     */
    protected int aRandomColumn(boolean horizontal, int length) {

        def randomColumn = new Random()
        if (horizontal) {
            randomColumn = randomColumn.nextInt(upperBoardBound - length)
        } else {
            randomColumn = randomColumn.nextInt(upperBoardBound)
        }
        return randomColumn
    }

    /**
     * returns a random row integer
     * @return def a random row
     */
    protected int aRandomRow(boolean horizontal, int length) {

        def randomRow = new Random()
        if (horizontal) {
            randomRow = randomRow.nextInt(upperBoardBound)
        } else {
            randomRow = randomRow.nextInt(upperBoardBound - length)
        }
        return randomRow
    }

    /**
     * returns a random boolean
     * @return def random true or false
     */
    private boolean aRandomBoolean() {
        def rnd = new Random()
        return rnd.nextBoolean()
    }

    /**
     * Place all ten ships randomly on the (initially empty) ocean.
     * Place larger ships before smaller ones, or you may end up with no legal place to put a large ship.
     * You will want to use the Random class in the java.util package, so look that up in the Java API.
     * */
    void placeAllShipsRandomly() {

        def ships = ['battleship', 'cruiser', 'destroyer', 'submarine']
        ships.each
                {
                    boolean horizontal = this.aRandomBoolean()

                    //heuristic: position larger ships first
                    switch (it) {
                        case 'battleship':
                            int row = this.aRandomRow(horizontal, battleship.getLength())
                            int column = this.aRandomColumn(horizontal, battleship.getLength())
                            battleship.placeShipAt(row, column, horizontal, this)
                            break
                        case 'cruiser':
                            int row = this.aRandomRow(horizontal, cruiser1.getLength())
                            int column = this.aRandomColumn(horizontal, cruiser1.getLength())
                            cruiser1.placeShipAt(row, column, horizontal, this)
                            row = this.aRandomRow(horizontal, cruiser2.getLength())
                            column = this.aRandomColumn(horizontal, cruiser2.getLength())
                            cruiser2.placeShipAt(row, column, horizontal, this)
                            break
                        case 'destroyer':
                            int row = this.aRandomRow(horizontal, destroyer1.getLength())
                            int column = this.aRandomColumn(horizontal, destroyer1.getLength())
                            destroyer1.placeShipAt(row, column, horizontal, this)
                            row = this.aRandomRow(horizontal, destroyer2.getLength())
                            column = this.aRandomColumn(horizontal, destroyer2.getLength())
                            destroyer2.placeShipAt(row, column, horizontal, this)
                            row = this.aRandomRow(horizontal, destroyer3.getLength())
                            column = this.aRandomColumn(horizontal, destroyer3.getLength())
                            destroyer3.placeShipAt(row, column, horizontal, this)
                            break
                        case 'submarine':
                            horizontal = true
                            int row = this.aRandomRow(horizontal, submarine1.getLength())
                            int column = this.aRandomColumn(horizontal, submarine1.getLength())
                            submarine1.placeShipAt(row, column, horizontal, this)
                            row = this.aRandomRow(horizontal, submarine2.getLength())
                            column = this.aRandomColumn(horizontal, submarine2.getLength())
                            submarine2.placeShipAt(row, column, horizontal, this)
                            row = this.aRandomRow(horizontal, submarine3.getLength())
                            column = this.aRandomColumn(horizontal, submarine3.getLength())
                            submarine3.placeShipAt(row, column, horizontal, this)
                            row = this.aRandomRow(horizontal, submarine4.getLength())
                            column = this.aRandomColumn(horizontal, submarine4.getLength())
                            submarine4.placeShipAt(row, column, horizontal, this)
                            break
                    }

                }

    }

    /**
     * initialise the array with empty seas
     */
    private assignEmptySeaIndicesToShipArray() {
        //board the "2d array"
        ships.eachWithIndex {
            rowObject, row ->
            rowObject.eachWithIndex {
                objValue, col ->
                if (objValue == null) {
                    def emptysea = new EmptySea()
                    placeOnBoard(row, col, emptysea)

                }
            }
        }
    }

    /**
     *  format the board historically for console, but also used by swing GUI
     * @return String the formatted board
     */
    private String formatBoardForConsole() {
        //replace this with (for row 0 ) -+ and \n
        //for second row  them | then then the  symbol finally \n

        StringBuilder sb1 = new StringBuilder()

        sb1.append('  0 1 2 3 4 5 6 7 8 9 \n ')

        ships.eachWithIndex {
            rowObject, row ->
            rowObject.eachWithIndex {
                objValue, col ->
                sb1.append('+-')
            }
            sb1.append("+\n" + row)
            rowObject.eachWithIndex {
                objValue, col ->
                Ship ship = ships[row][col]
                sb1.append('+' + this.getSymbolForShipState(ship, row, col))
            }
            sb1.append("|\n ")

        }

        sb1.append('+-+-+-+-+-+-+-+-+-+-+\n')
        return sb1

    }

    /**
     * get the relevant symbol for the ship state
     * @param Ship
     * @param int the row
     * @param int the column
     * @return String the relevant symbol for the ship depending on its state
     */
    private String getSymbolForShipState(Ship ship, int row, int col) {

        String replacementSymbolFragment = '.'

        if (ship.getShipType() == "EmptySea") {    //is it emptysea? is it hit?  then show - no? then show .
            if (((EmptySea) ship).getFiredAt())
                replacementSymbolFragment = '-'
            else
                replacementSymbolFragment = '.'
        } else { //must be a ship

            if (ship.isHorizontal()) { //check row col subtract bowcol from col, get index from hit array, is it true? show x
                boolean[] hit = ship.getHit()
                if (hit[col - ship.getBowColumn()])
                    replacementSymbolFragment = 'x'
            }
            if (!ship.isHorizontal()) { //it's vertical
                boolean[] hit = ship.getHit()
                if (hit[row - ship.getBowRow()])
                    replacementSymbolFragment = 'x'
            }

            if (ship.isSunk()) {
                replacementSymbolFragment = 'S'
            }

        }

        return replacementSymbolFragment
    }

    /**
     * generate a "blank" board
     * @return String of the blank board
     */
    private String showBlankBoard() {

        StringBuilder sb1 = new StringBuilder()
        (1..10).each {
            sb1.append('+-+-+-+-+-+-+-+-+-+-+\n')
            sb1.append('| | | | | | | | | | |\n')
        }
        sb1.append('+-+-+-+-+-+-+-+-+-+-+')

        return sb1

    }

    /**
     * now place ships on the playing board
     * @param int the row
     * @param int the column
     * @param Ship a ship
     */
    private placeOnBoard(int row, int column, Ship ship) {
        ship.setBowRow(row)
        ship.setBowColumn(column)
        ships[row][column] = ship
    }

    /**
     * Returns true if the given location contains a "real" ship, still afloat, (not an EmptySea), false if it does not.
     * In addition, this method updates the number of shots that have been fired, and the number of hits.
     * Note: If a location contains a "real" ship, shootAt should return true every time the user shoots at that same location.
     * Once a ship has been "sunk", additional shots at its location should return false.
     * @param int a row
     * @param int a column
     * @return boolean whether there has been a hit
     */
    boolean shootAt(int row, int column) {

        boolean hit = false

        this.setShotsFired()
        Ship ship = ships[row][column]

        if (isOccupied(row, column)) {

            if (!ship.isSunk()) {
                hit = ship.shootAt(row, column)
                this.hitCount++
            } else {
                hit = false
            }

        } else {  //assume its an empty sea
            if (ship.getShipType() == "EmptySea") {
                ((EmptySea) ship).setFiredAt()
            }
        }

        return hit
    }
}
