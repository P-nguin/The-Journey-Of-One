package com.thejourneyofone.game.characters;

import com.thejourneyofone.game.Resources.CharacterAnimations;
import com.thejourneyofone.game.Resources.CharacterOptions;
import com.thejourneyofone.game.screens.GameScreen;

public class StormOfSwordsEnemy extends Enemy {

    public static final int SIZEX = 189;
    public static final int SIZEY= 47;

    public StormOfSwordsEnemy(float health, float speed) {
        super(health, speed, SIZEX / GameScreen.PPM*2.5f, SIZEY / GameScreen.PPM*2.5f, 12, 22, CharacterOptions.SwordOfStorms, CharacterAnimations.IdleKneel, 12.f, 8.f, 2.f);
    }
}
