package com.unknowngames.rainbowrage.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.unknowngames.rainbowrage.EverythingHolder;
import com.unknowngames.rainbowrage.GameScreen;
import com.unknowngames.rainbowrage.RainbowRage;
import com.unknowngames.rainbowrage.ui.Button;
import com.unknowngames.rainbowrage.ui.RoundButton;

// Jason Rogers
// Mike Tang
// Matthew Ito
// Cecilia Bishton
// Michael Chen

public class MainMenuScreen implements Screen 
{
	RainbowRage game;
	private OrthographicCamera camera;
	TextureRegion texture;
	Sprite sprite;
//	Sprite titleSprite;
	Button[] buttons = new Button[4];
	private SpriteBatch batch;
	int width, height;
	Vector3 touchPoint;
	Audio tempMusic = Gdx.audio;
	Music startMusic;
	static ParticleEffect fire = new ParticleEffect();
	static ParticleEffect spark = new ParticleEffect();
	static ParticleEffect blood = new ParticleEffect();
	static ParticleEffect rainbow = new ParticleEffect();
	boolean newGameStarted;
	TextureRegion gameLogo, buttonFrame;
	
	HeroSelectScreen selectScreen;
	
	EverythingHolder everything;

	public MainMenuScreen(RainbowRage game, EverythingHolder everything)
	{
		this.game = game;
//		Settings.getInstance();
		newGameStarted = false;
//		startMusic = tempMusic.newMusic(Gdx.files.internal("audio/523938_--MB---The-Black-Wi.mp3"));
//		startMusic.setLooping(true);
//		startMusic.play();
		this.everything = everything;
		startMusic = tempMusic.newMusic(Gdx.files.internal("audio/460436_trapped_in_dreams.mp3"));
		startMusic.setLooping(true);
		startMusic.setVolume(everything.getMusicLevel());
		startMusic.play();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false);
		texture = new TextureRegion(new Texture(Gdx.files.internal("images/mainmenubackground.jpg")), 0, 0, 800, 480);
//		Texture textTexture = new Texture(Gdx.files.internal("images/textmenuscreen.png"));
//		TextureRegion textRegion = new TextureRegion(textTexture, 0, 0, 451, 49);
//		Texture icons = new Texture(Gdx.files.internal("images/buttons_sheet.png"));
//		gameLogo = new TextureRegion(icons, 0, 1514, 842, 467);
//		buttonFrame = new TextureRegion(icons, 880, 422, 361, 572);
		gameLogo = everything.getObjectTexture("gamelogo");
		buttonFrame = everything.getObjectTexture("mainbuttonframe");
		
		int stackTopX = width - 120; //680; //658;
		int stackTopY = 345; //355;
		int spaceX = 75;
		int spaceY = 54;
		int buttonRadius = 38; //(int) (width * .95f / 20);
//		buttons[0] = new RoundButton(stackTopX, stackTopY - spaceY * 2 + 4, buttonRadius, 			
//				new TextureRegion(icons, 367, 459, 152, 153));	// Sinlge-player
//		buttons[1] = new RoundButton(stackTopX + spaceX, stackTopY - spaceY * 3 + 4, buttonRadius, 			
//				new TextureRegion(icons, 367, 612, 152, 153));	// Multi-player
//		buttons[2] = new RoundButton(stackTopX, stackTopY - spaceY * 4 + 9, buttonRadius, 			
//				new TextureRegion(icons, 367, 765, 152, 153));	// Settings
//		buttons[3] = new RoundButton(stackTopX + spaceX, stackTopY - spaceY * 5 + 9, buttonRadius, 			
//				new TextureRegion(icons, 367, 153, 152, 153));	// Quit
		
		buttons[0] = new RoundButton(stackTopX, stackTopY - spaceY * 2 + 4, buttonRadius, 			
				everything.getObjectTexture("singlebutton"));	// Sinlge-player
		buttons[1] = new RoundButton(stackTopX + spaceX, stackTopY - spaceY * 3 + 4, buttonRadius, 			
				everything.getObjectTexture("multibutton"));	// Multi-player
		buttons[2] = new RoundButton(stackTopX, stackTopY - spaceY * 4 + 9, buttonRadius, 			
				everything.getObjectTexture("settingsbutton"));	// Settings
		buttons[3] = new RoundButton(stackTopX + spaceX, stackTopY - spaceY * 5 + 9, buttonRadius, 			
				everything.getObjectTexture("quitbutton"));	// Quit
		
		buttons[2].setClickable(false);
		
		touchPoint = new Vector3();
	
		fire.load(Gdx.files.internal("data/fire.p"), Gdx.files.internal("images"));
		fire.setPosition(610, 260);
		fire.start();
		
		spark.setPosition(400, 300);
		spark.start();
		
		blood.load(Gdx.files.internal((Gdx.app.getType() == ApplicationType.Android ? "data/BloodEffectAndroid.p" : "data/BloodEffect.p")), Gdx.files.internal("images"));
		blood.setPosition(400, 300);
		for (ParticleEmitter pe : blood.getEmitters())
			pe.setContinuous(true);
		blood.start();
		
		
		rainbow = everything.getEffect("rainbowtrailsparkle");
		System.out.println("Trying");
		rainbow.setPosition(332, 423);
		rainbow.start();
		
		Gdx.graphics.setVSync(true);
		//Gdx.input.setCursorCatched(false);
	}

	@Override
	public void render(float delta) 
	{
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		/*if (x < 0 || x > 800 || y < 0 || y > 480)
			Gdx.input.setCursorCatched(false);
		else
			Gdx.input.setCursorCatched(true);*/
		update(delta);
		GL10 gl = Gdx.graphics.getGL10();
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(texture, 0, 0, width, height);
		batch.draw(gameLogo, 155, 262, 382, 212);
		batch.draw(buttonFrame, width - 166, -10, buttonFrame.getRegionWidth() * .95f * 80 / 146, buttonFrame.getRegionHeight() * .95f * 80 / 146);
//		
//		newGameSprite.draw(batch);
//		settingsSprite.draw(batch);
		
		for (Button button : buttons)
			button.draw(batch, delta);
		
//		if (newGameStarted)
//			continueSprite.draw(batch);
//		else
//			continueFadedSprite.draw(batch);
		
		if (fire.isComplete())
			fire.start();
		if (spark.isComplete())
			spark.start();
		if (blood.isComplete())
			blood.start();
		if (rainbow.isComplete())
			rainbow.start();
		
		fire.draw(batch, delta * .5f);//, delta);
		blood.setPosition(x, height - y);
		blood.draw(batch, delta * .5f);
		rainbow.draw(batch, delta * .5f);
		
		batch.end();
	}

	public void update (float deltaTime) 
	{
		if (Gdx.input.justTouched()) {
//			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
//			touchPoint.set(Gdx.input.getX(), 480 - Gdx.input.getY(), 0);
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));		
			System.out.println(touchPoint.x + " " + touchPoint.y);
			for (int i = 0; i < buttons.length; i++)
				if (buttons[i].hit(touchPoint.x, touchPoint.y))
				{
					buttonHit(i);
					return;
				}
//			if (OverlapTester.pointInRectangle(newGameRectangle, touchPoint.x, touchPoint.y)) {
//				//System.out.println(true);
////				startMusic.stop();
//				game.gameScreen = new GameScreen(game);
//				newGameStarted = true;
//				game.setScreen(game.gameScreen);
//				return;
//			}
//			else if (OverlapTester.pointInRectangle(continueRectangle, touchPoint.x, touchPoint.y)) {
//				if (newGameStarted) {
////				startMusic.stop();
//				game.setScreen(game.gameScreen);
//				return;
//				}
//			}
//			else if (OverlapTester.pointInRectangle(settingsRectangle, touchPoint.x, touchPoint.y)) {
////				startMusic.stop();
//				game.setScreen(game.settingsScreen);
//				return;
//			}
		}
	}
	
	public void buttonHit(int h)
	{
		if (h == 0) // Single-player
		{
			selectScreen = new HeroSelectScreen(everything, game);
			game.setScreen(selectScreen);
			newGameStarted = true;
//			startMusic.stop();
//			everything.loadTeams("red", "blue", "mrwizard", "swordface");
//			game.gameScreen = new GameScreen(game, false);
//			newGameStarted = true;
//			game.setScreen(game.gameScreen);
			return;
		}
		else if (h == 1) // Multi-player
		{
//			startMusic.stop();
			everything.loadTeams("blue", "red", "mrwizard", "swordface");
			game.gameScreen = new GameScreen(game, true);
			newGameStarted = true;
			game.setScreen(game.gameScreen);
			return;
		}
		else if (h == 2) // Settings
		{
//			startMusic.stop();
//			game.setScreen(game.settingsScreen);
			return;
		}
		else if (h == 3) // Quit
		{
//			selectScreen = new HeroSelectScreen(everything, game);
//			game.setScreen(selectScreen);
//			startMusic.stop();
			System.exit(0);
		}
			
	}

	@Override
	public void resize(int width, int height) 
	{
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void show() 
	{
		startMusic.play();
		// TODO Auto-generated method stub
	//	if (Settings.getInstance().getSound() == SoundEnum.ON)
	//		startMusic.play();
	}
	
	@Override
	public void hide() 
	{
		startMusic.stop();
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void pause() 
	{
		// TODO Auto-generated method stub
	
	}
	
	@Override
	public void resume() 
	{
		// TODO Auto-generated method stub
	
	
	}
	
	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
	
	}
	
	public void gameWon() 
	{
		newGameStarted = false;
	}
}
