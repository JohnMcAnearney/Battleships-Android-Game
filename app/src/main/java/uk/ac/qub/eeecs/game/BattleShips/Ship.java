package uk.ac.qub.eeecs.game.BattleShips;
//Sprint 3 User Story 13 - Creating the four different battleship
//classes via inheritance

//I will first create the main Ship class
//I will further implement each ship size at a later stage

//Sprint 4 - (40201925) User Story 3 - Placement of entity object
// Further implementation to the Ship class

import android.graphics.Bitmap;

public class Ship
{
    //Instance variables for the Ship class
    private String shipType; //data field for the type of ship e.g. Cargo Ship
    //Sprint 4 - added two additional instance variables to define the position of the ship entity object (40201925)
    private float startPositionX;
    private float startPositionY;

    //Constructor
    //Sprint 4 - Implemented additional code to the constructor of the ship class (40201925)
    public Ship(String shipType, float startPositionX, float startPositionY, Bitmap bitmap)
    {
        this.shipType = shipType;
        this.startPositionX = startPositionX;
        this.startPositionY = startPositionY;
    }

    //Getters
    //Sprint 4 - Implemented and improved on the Ship class' Getters (40201925)
    public String shipType()
    {
        return shipType;
    }

    public float getStartPositionX()
    {
        return startPositionX;
    }

    public float getStartPositionY()
    {
        return startPositionY;
    }

    //Setters
    //Sprint 4 - Implemented and improved on the Ship class' Setters (40201925)
    public void setShipType(String shipType)
    {
      this.shipType = shipType;
    }

    public void setStartPositionX(float startPositionX)
    {
        this.startPositionX = startPositionX;
    }

    public void setStartPositionY(float startPositionY)
    {
        this.startPositionY = startPositionY;
    }




}

