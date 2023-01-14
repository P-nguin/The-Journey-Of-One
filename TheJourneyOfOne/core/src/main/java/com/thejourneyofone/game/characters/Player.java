package com.thejourneyofone.game.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thejourneyofone.game.Resources;
import com.thejourneyofone.game.Resources.AnimationOptions;
import com.thejourneyofone.game.screens.GameScreen;

public class Player extends Character {

    public static final int SIZEX = 134;
    public static final int SIZEY= 47;
    private static final int OFFSETLEFT = 99;
    private static final int OFFSETRIGHT = 35;
    private static final int BODYWIDTH = 11;

    private float timeCnt = 0;
    private AnimationOptions curAnimation = AnimationOptions.SwordOfStormsKneel;

    private boolean leftMove, rightMove, upMove, downMove;
    private boolean prevLeftMove, prevRightMove, prevUpMove, prevDownMove;

    private final float idleBattleMax = 5.0f;
    private float idleBattleTime = 0;

    public Player(float health, float speed) {
        super(health, speed, SIZEX / GameScreen.PPM*2.5f, SIZEY / GameScreen.PPM*2.5f, OFFSETLEFT, OFFSETRIGHT, BODYWIDTH);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        timeCnt += dt;

        boolean isMoving = updateMove(dt);
        updateDirection();

        Animation curAni = Resources.getAnimation(curAnimation);
        if(curAni.isAnimationFinished(timeCnt) && curAni.getPlayMode() != Animation.PlayMode.LOOP || curAnimation == AnimationOptions.SwordOfStormsRun && !isMoving) {
            if(curAnimation == AnimationOptions.SwordOfStormsAttack1) {
                checkKeys();
            }

            setAnimation(AnimationOptions.SwordOfStormsIdle);
        }

        if(curAnimation == AnimationOptions.SwordOfStormsIdle) idleBattleTime += dt;
        if(idleBattleTime >= idleBattleMax) {
            setAnimation(AnimationOptions.SwordOfStormsKneel);
            idleBattleTime = 0;
        }

        //Flip direction of player
        TextureRegion keyFrame = Resources.getAnimation(curAnimation).getKeyFrame(timeCnt);
        if(shouldFlip() != keyFrame.isFlipX()) {
            keyFrame.flip(true, false);

        }
        setTexture(Resources.getAnimation(curAnimation).getKeyFrame(timeCnt));
    }

    private void checkKeys() {
        downMove = prevDownMove;
        upMove = prevUpMove;
        rightMove = prevRightMove;
        leftMove = prevLeftMove;
    }

    public void setRightMove(boolean t) {
        if(leftMove & t) leftMove = false;
        rightMove = t;
    }

    public void setLeftMove(boolean t) {
        if(rightMove & t) rightMove = false;
        leftMove = t;
    }

    public void setUpMove(boolean t) {
        if(downMove & t) downMove = false;
        upMove = t;
    }

    public void setDownMove(boolean t) {
        if(upMove & t) upMove = false;
        downMove = t;
    }

    public void attack() {
        prevLeftMove = leftMove; prevRightMove = rightMove; prevUpMove = upMove; prevDownMove = downMove;
        leftMove = false; rightMove = false; upMove = false; downMove = false;
        setAnimation(AnimationOptions.SwordOfStormsAttack1);
    }

    public boolean updateMove(float dt) {
        boolean ret = false;
        float x = 0, y = 0;
        if(rightMove) {
            x += getSpeed() * dt;
        }
        if(leftMove) {
            x -= getSpeed() * dt;
        }
        if(upMove) {
            y += getSpeed() * dt;
        }
        if(downMove) {
            y -= getSpeed() * dt;
        }

        if(x != 0 || y != 0) {
            setAnimation(AnimationOptions.SwordOfStormsRun);
            ret = true;
        }
        move(x,y);

        return ret;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void setAnimation(AnimationOptions newAnimation) {
        if(curAnimation != newAnimation) {
            curAnimation = newAnimation;
            timeCnt = 0;
            idleBattleTime = 0;
        }
    }
}
