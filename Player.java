public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /**
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     * 
     * @author Eren Özilgili
     * @return true or false according to the length of the chain
     * 
     */
    public boolean checkWinning() {
        //Takes the longest chain length and checks the winning situation
        if(findLongestChain() >= 14){ return true; }
        else{ return false; }
        
    }

    /**
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     * 
     * @author Eren Özilgili
     * @return longest chain length
     * 
     * DONE:Looks at the players tile and first of all assigns the first index as the comparisonTile.
     * Then we will compare the comparisonTile
     * with the next tile to determine if we will increment (next tile has a bigger value) the longestChain,
     * let it stay the same (next Tile has the same value),
     * or set it to 1 (smallerTile has come up).
     * 
     */
    public int findLongestChain() {
        Tile comparisonTile = this.playerTiles[0];//Assigning the first tile to compare in the below loop;
        int lenOfChain = 1;//As of start, we have only 1 tile so the length of chain is 1 for now;
        int longestChain = 1;//Holds the longest chain length;

        for(int i = 1; i < this.getNumberOfTiles(); i++){
            if(this.playerTiles[i].canFormChainWith(comparisonTile)){//Need to increment the length of the chain
                lenOfChain++;//Length of the chain is increased;
            }
            else if(this.playerTiles[i].getValue() != comparisonTile.getValue())
            {//The chain is no longer valid, next tile is smaller then the current tile;
                if(lenOfChain >= longestChain){//Record the chain length if it is the biggest one before resetting chain length;
                    longestChain = lenOfChain;
                }
                lenOfChain = 1;//Chain is broken, reset the chain length, search will start all over
            }
            else{
                //Length of the chain needs to stay the same. So no operations; 
            }

            comparisonTile = this.playerTiles[i];//comparisonTile for the next index will be this;

            //This will keep track of the longest chain;
            if(lenOfChain >= longestChain){
                longestChain = lenOfChain;
            }

        }

        return longestChain;
    }

    //returns the beginning index of longest chain (added by Ahmet Eren to use in Simplified class. Implemented by Eren and Ahmet)
    public int getIndexOfLongestChain()
    {
        int lengthOfLongest = this.findLongestChain();
        int trackNumber = 1;//Used to check if our chain is same as length of the longest chain;
        int index = 0;//Holds the index;

        for(int i = 0; i < this.numberOfTiles - 1; i++){
            //If we achieve the length of the longest chain, then we find our index;
            if(trackNumber == lengthOfLongest){
                break;
            }

            if(this.playerTiles[i].canFormChainWith(this.playerTiles[i + 1])){//trackNUmber is incremented in case chain is continuing;
                trackNumber++;
            }
            else if(this.playerTiles[i].compareTo(this.playerTiles[i + 1]) < 0){//Chain is broken
                trackNumber = 1;
                //Starting index of the longest chain should be the following index;
                index = i + 1;
            }
            else{
                //Consecutive tiles are equal, so trackNUmber is not incremented.
            }

        }

        //Returning the starting position of the index;
        return index;
    }

    /**
     * removes and returns the tile in given index position
     * @author Hikmet 
     */
    public Tile getAndRemoveTile(int index) {
        if(index < 0 || index >= playerTiles.length) {
            throw new IllegalArgumentException("Index out of bounds for the player's hand");
        }

        Tile tempTile = playerTiles[index];

        //Shifts all other remaining tiles to the left
        for(int i = index; i < playerTiles.length-1; i++){
            playerTiles[i] = playerTiles[i+1];
        }

        this.numberOfTiles--;

        playerTiles[numberOfTiles] = null;

        return tempTile;
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
        
        //This loop searches the correct position for the tile to be added
        if(numberOfTiles!=0){
            //Define a boolean flag
            boolean done = false;
            for(int i=0; i<getNumberOfTiles(); i++)
            {
                //using compareTo method 
                //If the index is correct, puts the given tile to the index
                if(playerTiles[i].compareTo(t)>0 && !done)
                {
                    Tile tempTile = new Tile(playerTiles[i].getValue());
                    playerTiles[i] = new Tile(t.getValue());
                    
                    //Shifts all other remaining tiles to the right
                    for(int j=i; j<getNumberOfTiles(); j++){
                        if(this.playerTiles[j+1] != null)
                        {
                            Tile tempTile2 = new Tile(playerTiles[j+1].getValue());
                            playerTiles[j+1] = new Tile(tempTile.getValue());
                            tempTile = new Tile(tempTile2.getValue());
                        }
                        else
                        {
                            this.playerTiles[j+1] = new Tile(tempTile.getValue());
                        }
                        
                    }
                    //When shifting is done, reverses the flag and makes sure that it will not happen again
                    done = true;
                }
            }
            if(this.playerTiles[this.getNumberOfTiles()] == null)
            {
                this.playerTiles[this.getNumberOfTiles()] = new Tile(t.getValue());
            }
        }
        else
        {
            this.playerTiles[0] = new Tile(t.getValue());
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
