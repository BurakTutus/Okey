public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() {
        return false;
    }

    /*
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() {
        int longestChain = 0;

        return longestChain;
    }

    //returns the beginning index of longest chain (added by Ahmet Eren to use in Simplified class. May be implemented later.)
    public int getIndexOfLongestChain()
    {
        int index = 0;

        return index;
    }

    /*
     * TODO: removes and returns the tile in given index position
     */
    public Tile getAndRemoveTile(int index) {
        return null;
    }

    /*
     * TODO: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    /**
     * @author Mehmet Efe Mutlu
     * Done
     */
    public void addTile(Tile t) {
        //Define a boolean flag
        boolean done = false;
        //This loop searches the correct position for the tile to be added
        for(int i=0; i<playerTiles.length; i++){
            //using compareTo method 
            //If the index is correct, puts the given tile to the index
            if(playerTiles[i].compareTo(t)<=0 && !done){
                Tile tempTile = playerTiles[i];
                playerTiles[i] = t;
                
                //Shifts all other remaining tiles to the right
                for(int j=i; j<playerTiles.length-1; j++){
                    Tile tempTile2 = playerTiles[j+1];
                    playerTiles[j+1] = tempTile;
                    tempTile = tempTile2;
                }
                //When shifting is done, reverses the flag and makes sure that it will not happen again
                done = true;
            }
        }
        //Increases the numberOfTiles by one
        this.numberOfTiles++;
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }

    public int getNumberOfTiles()
    {
        return this.numberOfTiles;
    }
}
