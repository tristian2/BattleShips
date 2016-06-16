package Battleships
/**
 * Created by IntelliJ IDEA.
 * User: tristian o'brien
 * Date: 29/01/12
 * Time: 19:19
 * main games class with gui
 */

import javax.swing.WindowConstants as WC
import java.awt.FlowLayout as FL
import javax.swing.BoxLayout as BXL

import groovy.swing.SwingBuilder
import java.awt.Font
import javax.swing.JOptionPane

class BattleshipGame {

    def ocean
    def swing = new SwingBuilder()
    def frame

    def gameHistory = []

    /**
     * static method for entering the application
     * @param def args - default parameter collection for application entry point
     */
    static main(def args) {
        BattleshipGame battleshipGame = new BattleshipGame()
        battleshipGame.showWindow()
    }
    /**
     * show the swing GUI
     */
    protected showWindow() {

        play()

        frame = swing.frame(id: 'mainFrame',
                title: 'Battleships V1.0',
                pack: true,
                visible: true,
                defaultCloseOperation: WC.DISPOSE_ON_CLOSE,
                resizable: false,
                preferredSize: [185, 595],
                size: [170, 595],
                background: java.awt.Color.LIGHT_GRAY) {

            boxLayout(axis: BXL.Y_AXIS)
            panel(alignmentX: 0f, id: 'mainPanel') {
                flowLayout(alignment: FL.LEFT)
                textPane(
                        editable: false,
                        name: 'outputArea',
                        id: 'contents',
                        contentType: 'text',
                        //text: contents,
                        background: java.awt.Color.BLUE,
                        foreground: java.awt.Color.BLACK,
                        font: new Font("Monospaced", Font.BOLD, 12)
                )
            }
            panel(id: 'statusPanel', alignmentX: 0f) {
                flowLayout(alignment: FL.CENTER)
                label('Status',
                        id: 'status'
                )
            }

            panel(id: 'fireStatusPanel', alignmentX: 0f) {
                flowLayout(alignment: FL.CENTER)
                label('Status',
                        id: 'fireStatus'
                )
            }
            panel(id: 'scorePanel', alignmentX: 0f) {
                flowLayout(alignment: FL.CENTER)
                label(id: 'score')
            }
            panel(id: 'selectorPanel', alignmentX: 0f) {
                flowLayout(alignment: FL.RIGHT)
                label('Row')
                comboBox(id: 'row',
                        items: 0..9
                )
                flowLayout(alignment: FL.RIGHT)
                label('Column')
                comboBox(id: 'column',
                        items: 0..9
                )
            }

            panel(id: 'debugStatusPanel', alignmentX: 0f) {
                flowLayout(alignment: FL.CENTER)
                label(id: 'debugStatus')
            }


            panel(id: 'controlPanel', alignmentX: 0f) {

                button('Fire', actionPerformed: {
                    swing.controlPanel.background = java.awt.Color.RED
                    fire(swing.row.selectedItem, swing.column.selectedItem)
                    swing.fireStatus.text = 'Fired at Row:' + swing.row.selectedItem + ' Column:' + swing.column.selectedItem
                    swing.controlPanel.background = java.awt.Color.LIGHT_GRAY
                })
                button('Quit', actionPerformed: {
                    swing.dispose()
                    exit()
                })
            }
        }

        initialiseControls()
    }

    /**
     * carry out a fire operation, update the various controls depending on the game status
     * @param row of the shot
     * @param column of the shot
     */
    protected fire(def row, def column) {

        ocean.shootAt(row, column)
        updateShips()

        if (hasTurnAlreadyBeenPlayed(row, column)) {
            swing.status.text = "What? You already fired there!"
            return
        }

        if (ocean.isGameOver()) {
            swing.status.text = "You Won!"
            swing.score.text = "Ultimate Victory! Fleet sunk!"

            if (JOptionPane.showConfirmDialog(swing.mainPanel,
                    "Well done. Wanna another go?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION)) {
                exit()
            } else {
                playAgain()
            }

            return
        }

        def classType = ocean.ships[row][column].class.toString()

        if (classType != "class Battleships.EmptySea") {
            //update window to show the hit!
            swing.status.text = "You hit a ship!"
            swing.score.text = ocean.getShipsSunk() + " ships sunk"
        }
        else {
            swing.status.text = "Loser! You missed"
        }
    }

    /**
     *  update the main game panel
     */
    protected updateShips() {
        println ocean.toString()
        swing.contents.text = ocean.toString()
    }

    /**
     * locally scoped history of players moves
     * @param row of the shot
     * @param column of the shot
     */
    protected boolean hasTurnAlreadyBeenPlayed(def row, def column) {
        if (gameHistory.count(row.toString() + column.toString()) > 0) {
            return true
        }
        else {
            gameHistory.add(row.toString() + column.toString())
            return false
        }
    }

    /**
     * start playing - instantiate a new ocean
     */
    protected play() {
        ocean = new Ocean()
        ocean.placeAllShipsRandomly()
        //useful for tracing or cheating!
        print ocean.ships
    }

    /**
     *   exit the application
     */
    protected exit() {
        System.exit(0)
    }

    /**
     * reset crucial things such as game history, reset controls
     */
    protected playAgain() {
        ocean = null
        play()
        initialiseControls()
        gameHistory = []
    }

    /**
     * method to reset all the necessary controls
     */
    protected initialiseControls() {
        def contents = ocean.showBlankBoard()
        swing.contents.text = ocean.toString()
        swing.status.text = 'Aye Aye Captain!'
        swing.fireStatus.text = 'Ready for orders!'
        swing.score.text = '0 ships sunk'
        swing.row.selectedIndex = 0
        swing.column.selectedIndex = 0
    }

}
