import java.awt.*;
import java.util.*;
import javax.swing.*;


public class State {
    private String[][] state;
    private int utility;
    private int height;
    private State parent;
    private String turn;
    private ArrayList<State> children;

    public State() {
        this.state = new String[3][3];
        this.parent = null;
        this.children = null;
        this.height = 0;

        for (int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                this.state[i][j] = "";
            }
        }
    }

    public State(String[][] state) {
        this.state = new String[3][3];
        this.parent = null;
        this.height = 0;
        this.children = null;

        for (int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                this.state[i][j] = new String(state[i][j]);
            }
        }
    }

    /*
        Public constructor
        @Params:
            State parent: Preceding state
            int x, y: Coordinates of new move
            String value: value of the new move -> Also add to turn.
    */
    public State(State parent, int x, int y, String value) {
        this.state = new String[3][3];
        this.children = null;
        this.parent = parent;
        this.height = parent.getHeight() + 1;

        for (int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                this.state[i][j] = new String(parent.getState()[i][j]);
            }
        }

        // [x][y] should have nothing in it
        if(this.state[x][y].equals("")) {
            this.state[x][y] = new String(value);
            this.turn = new String(value);
        }
        else {
            // [x][y] already has a value
        }
    }

    /*
        Public Method printMe():
        - Prints the details to console for better debug
    */
    public void printMe() {
        System.out.println("");
        System.out.println("");
        System.out.println("Turn: " + this.turn);
        System.out.println("Utility: " + this.utility);
        System.out.println("height: " + this.height);

        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                if(!this.state[i][j].equals("")) {
                    System.out.print(this.state[i][j]);
                }
                else {
                    System.out.print("-");
                }
            }
            System.out.println("");
        }
    }

    public ArrayList<State> getPossibleStates() {
        ArrayList<State> tempStates = new ArrayList<State>();

        // State constructor
        // Get blank indeces
        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                if(this.state[i][j].equals("")) {
                    String nextTurn = (this.turn.equals("X")) ? "O": "X";
                    tempStates.add(new State(this, i, j, nextTurn));
                }
            }
        }

        this.children = tempStates;

        return tempStates;
    }

    public State clone() {
        return new State(this.state);
    }

    /*
        returns true if there is no more empty buttons on the field
            or
        There is already a winner
    */
    public boolean isLeafNode() {
        int score = 0;

        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                if(!this.state[i][j].equals("")) {
                    score++;
                }
            }
        }

        return (score == 9) || (!this.getWinner().equals(""));
    }

    public int getHeight() {
        return this.height;
    }

    public int getUtility() {
        return this.utility;
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    public String getTurn() {
        return new String(this.turn);
    }

    public String[][] getState() {
        return this.state;
    }

    public State getParent() {
        return this.parent;
    }

    public ArrayList<State> getChildren() {
        return this.children;
    }

    /*
        public String getWinner -> Checks the board
    */
    public String getWinner() {
        String winner = "";
        String value;

        // Check rows
        for (int i = 0 ; i < 3 ; i++) {
            winner = "";
            value = "";

            for(int j = 0 ; j < 3 ; j++) {
                value += this.state[i][j];
            }

            winner = checkWinner(value);
            if(!winner.equals("")) {
                return winner;
            }
        }

        // Check Columns
        for (int i = 0 ; i < 3 ; i++) {
            winner = "";
            value = "";

            for(int j = 0 ; j < 3 ; j++) {
                value += this.state[j][i];
            }

            winner = checkWinner(value);
            if(!winner.equals("")) {
                return winner;
            }
        }

        // Check backslash
        value = this.state[0][0] + this.state[1][1] + this.state[2][2];
        winner = checkWinner(value);
        if(!winner.equals("")) {
            return winner;
        }

        // Check slash
        value = this.state[0][2] + this.state[1][1] + this.state[2][0];
        winner = checkWinner(value);
        if(!winner.equals("")) {
            return winner;
        }

        return "";
    }


    // Returns checks value and returns X if XXX, O if OOO
    private String checkWinner(String value) {
        if(value.equals("XXX")) {
            return "X";
        }
        else if(value.equals("OOO")) {
            return "O";
        }
        else {
            return "";
        }
    }

    public boolean equals(State s) {
        int score = 0;

        for(int i = 0 ; i < 3 ; i++) {
            for(int j = 0 ; j < 3 ; j++) {
                if(this.state[i][j].equals(s.getState()[i][j])) {
                    score++;
                }
            }
        }

        return score == 9;
    }
}
