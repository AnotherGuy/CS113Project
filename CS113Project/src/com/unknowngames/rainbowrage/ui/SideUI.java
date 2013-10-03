package com.unknowngames.rainbowrage.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.unknowngames.rainbowrage.BaseClass;
import com.unknowngames.rainbowrage.EverythingHolder;

public class SideUI extends BaseClass
{
	Button[] buttons = new Button[6];
	
	int width = Gdx.graphics.getWidth();
	float scale = everything.getScreenScale();
	
	public SideUI()
	{
		buttons[0] = new RectangularButton(0, 290 * scale, 96 * .55f * scale,
				95 * .55f * scale,
				EverythingHolder.getObjectTexture("upgradebutton")); // Upgrades
		buttons[1] = new RectangularButton(0, 230 * scale, 96 * .55f * scale,
				95 * .55f * scale,
				EverythingHolder.getObjectTexture("chatbutton")); // Chat
		buttons[2] = new RectangularButton(60 * scale, 350 * scale, 96 * .55f * scale,
				95 * .55f * scale,
				EverythingHolder.getObjectTexture("chatbutton")); // Happy
		buttons[3] = new RectangularButton(60 * scale, 290 * scale, 96 * .55f * scale,
				95 * .55f * scale,
				EverythingHolder.getObjectTexture("chatbutton")); // Angry
		buttons[4] = new RectangularButton(60 * scale, 230 * scale, 96 * .55f * scale,
				95 * .55f * scale,
				EverythingHolder.getObjectTexture("chatbutton")); // GG
		buttons[5] = new RectangularButton(0, 350 * scale, 96 * .55f * scale,
				95 * .55f * scale,
				EverythingHolder.getObjectTexture("storebutton")); // Item Shop
		buttons[1].setClickable(false);
		buttons[5].setClickable(false);
		buttons[1].setVisible(false);
		buttons[2].setVisible(false);
		buttons[3].setVisible(false);
		buttons[4].setVisible(false);
	}
	
	public int hit(float x, float y)
	{
		for (int i = 0; i < 5; i++)
			if (buttons[i].hit(x, y))
				return i + 12;
		if (buttons[5].hit(x, y))
			return 10;
		return -1;
	}
	
	public void render(SpriteBatch batch, float delta)
	{
		for (int i = 0; i < 6; i++)
		{
			buttons[i].draw(batch, delta);
		}
	}
}