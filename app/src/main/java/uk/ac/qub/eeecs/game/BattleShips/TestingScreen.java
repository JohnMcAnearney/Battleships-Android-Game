package uk.ac.qub.eeecs.game.BattleShips;


import uk.ac.qub.eeecs.gage.Game;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;

import uk.ac.qub.eeecs.gage.world.GameScreen;
//Created by AT 40207942
//Basic screen for testing purposes only

public class TestingScreen extends GameScreen {

        public TestingScreen(Game game) {
            super("TestGameScreen", game);
        }

        @Override
        public void update(ElapsedTime elapsedTime) {}

        @Override
        public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {}
}
