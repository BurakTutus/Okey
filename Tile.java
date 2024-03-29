public class Tile {
    
    int value;

    /*
     * creates a tile using the given value. False jokers are not included in this game.
     */
    public Tile(int value) {
        this.value = value;
    }

    /* 
     * return true if they are matching, false otherwise
     */
    public boolean matchingTiles(Tile t) {
        return value == t.value;
    }

    /*
     * return 1 if given tile has smaller in value
     * return 0 if they have the same value
     * return -1 if the given tile has higher value
     */
    public int compareTo(Tile t) {
       if( t.value < this.value ){
           return 1;
       }
       else if(this.value == t.value){
           return 0;
       }
       else{
           return -1;

       }
    }

    /*
     * 
     * this method should check the difference in values of the two tiles
     * should return true if the absoulute value of the difference is 1 (they can form a chain)
     * otherwise, it should return false (they cannot form a chain)
     */
    public boolean canFormChainWith(Tile t) {
        int difference = 0;
        difference = this.value - t.value;
        if(difference == 1 || difference == -1)
        {
            return true;
        }
       return false;
    }

    public String toString() {
        return "" + value;
    }

    public int getValue() {
        return value;
    }

}
