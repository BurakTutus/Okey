public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*
     * 
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        for(int i = 0; i < 15; i++)
        {
            players[0].playerTiles[i] = tiles[i];
        }
        players[0].numberOfTiles = 15;
        for(int i = 1; i < 4; i++)
        {
            for(int j = 0; j < 14; j++)
            {
                players[i].playerTiles[j] = tiles[15 + j];
            }
            players[i].numberOfTiles = 14;
        }

    }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        this.players[getCurrentPlayerIndex()].addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Tile lastile = tiles[tiles.length - 1];
        Tile [] temp = new Tile[tiles.length - 1];
        for(int i = 0; i < temp.length; i++)
        {
            temp[i] = tiles[i];
        }
        tiles = new Tile[temp.length];
        for(int i = 0; i < temp.length; i++)
        {
            tiles[i] = temp[i];
        }
        this.players[getCurrentPlayerIndex()].addTile(lastile);
        return lastile.toString();
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        for(int i = 0 ; i<200;i++)
        {
            int tempIndex1 = (int)(Math.random()*tileCount);
            int tempIndex2 = (int)(Math.random()*tileCount);
            Tile tempTile = this.tiles[tempIndex1];
            this.tiles[tempIndex1] = this.tiles[tempIndex2];
            this.tiles[tempIndex2] = tempTile;
        }
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     */
    public boolean didGameFinish() {
        return players[getCurrentPlayerIndex()].checkWinning();
    }

    /* TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    public Player[] getPlayerWithHighestLongestChain() {
        Player[] winners = new Player[1];

        winners[0] = players[0];
        for(int i=1; i<players.length; i++){
            if(winners[0].findLongestChain()<players[i].findLongestChain()){
                winners[0] = players[i];
            }

        }
        return winners;
    }
    
    /*
     * checks if there are more tiles on the stack to continue the game
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    /*
     * TODO: pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     */
    public void pickTileForComputer() {
        int tempChain = players[currentPlayerIndex].findLongestChain();
        this.getLastDiscardedTile();
        if(tempChain == players[currentPlayerIndex].findLongestChain())
        {
            players[currentPlayerIndex].getAndRemoveTile(players[currentPlayerIndex].findPositionOfTile(lastDiscardedTile));
            this.getTopTile();
        }
    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     */
    public void discardTileForComputer() {
        boolean isDiscarded = false;

        Tile[] currTiles = this.players[currentPlayerIndex].getTiles();
        
        for(int i = 0 ; i < currTiles.length-1 && !isDiscarded ; i++)
        {
            if(currTiles[i].matchingTiles(currTiles[i+1]))
            {
                this.discardTile(i);
                isDiscarded = true;
            }
        }

        if(!isDiscarded)
        {
            int longestIndex = players[currentPlayerIndex].getIndexOfLongestChain();
            int longestLength = players[currentPlayerIndex].findLongestChain();
            if(longestIndex == 0)
            {
                this.discardTile(players[currentPlayerIndex].getNumberOfTiles()-1);
            }
            else if(longestIndex + longestLength == players[currentPlayerIndex].getNumberOfTiles())
            {
                this.discardTile(0);
            }
            else
            {
                int firstChainEnding = 0;
                while(currTiles[firstChainEnding].canFormChainWith(currTiles[firstChainEnding+1]))
                {
                    firstChainEnding++;
                }
                int lastChainBeginning = players[currentPlayerIndex].getNumberOfTiles()-1;
                while(currTiles[lastChainBeginning].canFormChainWith(currTiles[lastChainBeginning-1]))
                {
                    lastChainBeginning--;
                }
                if(currTiles[longestIndex].getValue()-currTiles[firstChainEnding].getValue() < currTiles[lastChainBeginning].getValue() - currTiles[longestIndex+longestLength-1].getValue())
                {
                    this.discardTile(players[currentPlayerIndex].getNumberOfTiles()-1);
                }
                else
                {
                    this.discardTile(0);
                }
            }
        }
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = players[getCurrentPlayerIndex()].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
