package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameUI 
{
	static SpriteBatch batch;
	private TextureRegion sideUI;
	private TextureRegion swordIcon;
	private TextureRegion bowIcon;
	private TextureRegion serfIcon;
	private TextureRegion magicIcon;
	private TextureRegion petIcon;
	private TextureRegion spiralIcon;
	private TextureRegion attackIcon;
	private TextureRegion defendIcon;
	private TextureRegion retreatIcon;
	BitmapFont font;
	static EverythingHolder everything;
	
	public GameUI()
	{
		sideUI = new TextureRegion(new Texture(Gdx.files.internal("images/sideui.png")), 200, 480);
		swordIcon = new TextureRegion(new Texture(Gdx.files.internal("images/swordicon.png")), 69, 80);
		bowIcon = new TextureRegion(new Texture(Gdx.files.internal("images/bowicon.png")), 69, 80);
		serfIcon = new TextureRegion(new Texture(Gdx.files.internal("images/serficon.png")), 69, 80);
		magicIcon = new TextureRegion(new Texture(Gdx.files.internal("images/magiciconbnw.png")), 69, 80);
		petIcon = new TextureRegion(new Texture(Gdx.files.internal("images/peticonbnw.png")), 69, 80);
		spiralIcon = new TextureRegion(new Texture(Gdx.files.internal("images/spiraliconbnw.png")), 69, 80);
		attackIcon = new TextureRegion(new Texture(Gdx.files.internal("images/attackicon.png")), 40, 40);
		defendIcon = new TextureRegion(new Texture(Gdx.files.internal("images/holdicon.png")), 40, 40);
		retreatIcon = new TextureRegion(new Texture(Gdx.files.internal("images/retreaticon.png")), 40, 40);
		font = new BitmapFont();
	}
	
	static public void load(SpriteBatch b, EverythingHolder things)
	{
		batch = b;
		everything = things;
	}
	
	public void render()
	{
		batch.draw(sideUI, Gdx.graphics.getWidth() / 2 - sideUI.getRegionWidth(), Gdx.graphics.getHeight() / 2 - sideUI.getRegionHeight());
//		batch.draw(swordIcon, Gdx.graphics.getWidth() / 2 - sideUI.getRegionWidth() + 10 + 5, Gdx.graphics.getHeight() / 2 - swordIcon.getRegionHeight() * 2 - 10);
//		batch.draw(bowIcon, Gdx.graphics.getWidth() / 2 - sideUI.getRegionWidth() + 10 * 2 + swordIcon.getRegionWidth() + 5, Gdx.graphics.getHeight() / 2 - swordIcon.getRegionHeight() * 2 - 10);
//		batch.draw(serfIcon, Gdx.graphics.getWidth() / 2 - sideUI.getRegionWidth() + 10 * 3 + swordIcon.getRegionWidth() * 2 + 5, Gdx.graphics.getHeight() / 2 - swordIcon.getRegionHeight() * 2 - 10);
		batch.draw(swordIcon, 221, -29);
		batch.draw(bowIcon, 311, -29);
		batch.draw(serfIcon, 221, -127);
		batch.draw(magicIcon, 311, -127);
		batch.draw(petIcon, 221, -227);
		batch.draw(spiralIcon, 311, -227);
		batch.draw(attackIcon, -50, -200);
		batch.draw(defendIcon, -100, -200);
		batch.draw(retreatIcon, -150, -200);
		font.draw(batch, "Total Units: " + (everything.team(1).size() + everything.team(2).size()), Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 20);
		font.draw(batch, "Funds: " + everything.funds(), Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 70);
	}
	
	public int width()
	{
		return sideUI.getRegionWidth();
	}
}
