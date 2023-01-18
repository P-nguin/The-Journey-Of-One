package com.thejourneyofone.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.*;
import com.thejourneyofone.game.EnemyManager;
import com.thejourneyofone.game.Main;
import com.thejourneyofone.game.characters.Player;
import com.thejourneyofone.game.characters.StormOfSwordsEnemy;

public class GameScreen implements Screen{
    public static final float PPM = 32;

    private Main game;
    private InputHandler inputHandler;
    private OrthographicCamera gameCamera;
    private ScreenViewport gamePort;

    private Player player;

    private EnemyManager enemyManager;

    private StormOfSwordsEnemy enemy;

    Texture texture;

    public GameScreen(Main game) {
        this.game = game;

        gameCamera = new OrthographicCamera();
        gamePort = new ScreenViewport(gameCamera);
        gamePort.setUnitsPerPixel(1/PPM);
        gamePort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        player = new Player(5,12);
        inputHandler = new InputHandler(player);

        enemy = new StormOfSwordsEnemy(5,6);
        enemy.move(3,0);

        enemyManager = new EnemyManager(player);

        texture = new Texture("libgdx.png");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.update(delta);
        gameCamera.position.set(player.getPosX(), player.getPosY(), 0);
        gameCamera.update();

        enemy.update(delta);

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0);
        player.draw(game.batch);
        //enemy.draw(game.batch);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
