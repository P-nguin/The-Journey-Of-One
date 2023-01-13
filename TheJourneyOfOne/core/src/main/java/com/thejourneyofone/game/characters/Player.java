package com.thejourneyofone.game.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thejourneyofone.game.Resources;
import com.thejourneyofone.game.Resources.AnimationOptions;

public class Player extends Character {

    public static final int SIZEX = 134;
    public static final int SIZEY= 47;

    private float timeCnt = 0;
    private AnimationOptions curAnimation = AnimationOptions.SwordOfStormsKneel;

    private boolean leftMove, rightMove;

    private final float idleBattleMax = 5.0f;
    private float idleBattleTime = 0;

    public Player(float health, float speed) {
        super(health, speed, SIZEX, SIZEY);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        timeCnt += dt;

        updateMove(dt);
        updateDirection();

        Animation curAni = Resources.getAnimation(curAnimation);
        if(curAni.isAnimationFinished(timeCnt) && curAni.getPlayMode() != Animation.PlayMode.LOOP) {
            setAnimation(AnimationOptions.SwordOfStormsIdle);
            idleBattleTime += dt; timeCnt = 0;
        }
        else idleBattleTime = 0;

        if(idleBattleTime >= idleBattleMax) {
            setAnimation(AnimationOptions.SwordOfStormsKneel);
        }

        //Flip direction of player
        TextureRegion keyFrame = Resources.getAnimation(curAnimation).getKeyFrame(timeCnt);
        if(shouldFlip() != keyFrame.isFlipX()) {
            keyFrame.flip(true, false);
        }
        setTexture(Resources.getAnimation(curAnimation).getKeyFrame(timeCnt));
    }

    public void setRightMove(boolean t) {
        if(leftMove & t) leftMove = false;
        rightMove = t;
    }

    public void setLeftMove(boolean t) {
        if(rightMove & t) rightMove = false;
        leftMove = t;
    }

    public void attack() {
        leftMove = false; rightMove = false;
        setAnimation(AnimationOptions.SwordOfStormsAttack1);
    }

    public void updateMove(float dt) {
        boolean ret = false;
        float x = 0, y = 0;
        if(rightMove) {
            x += getSpeed() * dt;
        }
        if(leftMove) {
            x -= getSpeed() * dt;
        }

        if(x != 0 || y != 0) {
            setAnimation(AnimationOptions.SwordOfStormsRun);
        }
        move(x,y);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void setAnimation(AnimationOptions newAnimation) {
        if(curAnimation != newAnimation) {
            curAnimation = newAnimation;
            timeCnt = 0;
        }
    }
}
