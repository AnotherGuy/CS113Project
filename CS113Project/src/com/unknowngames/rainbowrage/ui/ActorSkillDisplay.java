package com.unknowngames.rainbowrage.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.unknowngames.rainbowrage.BaseClass;
import com.unknowngames.rainbowrage.EverythingHolder;
import com.unknowngames.rainbowrage.entity.Actor;
import com.unknowngames.rainbowrage.parser.ActorStructure;
import com.unknowngames.rainbowrage.parser.HeroStructure;
import com.unknowngames.rainbowrage.parser.SkillContainerStructure;

public class ActorSkillDisplay extends BaseClass
{
	ActorStructure shownActor;
	SpriteBatch batch;
	int x, y;
	
//	TextureRegion[] skillIcons = new TextureRegion[4];
	RectangularButton[] buttons = new RectangularButton[7];
	TextureRegion chibi;
	String description = "", price = "";
	BitmapFont font = everything.getFont(1);
	int selectedSkill = -1;
	int width = Gdx.graphics.getWidth();
	int height = Gdx.graphics.getHeight();
	float scale; // = height / 480; //width / 800;
	
	public ActorSkillDisplay(int x, int y)
	{
		this.x = x;
		this.y = y;
		scale = everything.getScreenScale();
	}
	
	public void setActor(ActorStructure actor)
	{
		shownActor = actor;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				if (!actor.getSkill(i, j).equals("empty"))
					buttons[i * 2 + j] = new RectangularButton(x + (300 - (j * 60)) * scale, y + (200 - (i * 60)) * scale, 50 * scale, 50 * scale, getIcon(actor, i, j));
				else
					buttons[i * 2 + j] = null;
			}
			if (shownActor instanceof HeroStructure)
				buttons[6] = new RectangularButton(x + 300 * scale, y - 40 * scale, 50 * scale, 50 * scale, getIcon(actor, 3, 0));
			else
				buttons[6] = null;
		}
		
		description = actor.description();
		price = "";
		chibi = getActorImage(shownActor.name());
	}
	
	public int hit(float x, float y)
	{
		for (int i = 0; i < buttons.length; i++)
			if (buttons[i] != null && buttons[i].hit(x, y))
			{
				buttonHit(i);
				return i + 30;
			}
		return -1;
	}
	
	public void buttonHit(int h)
	{
		if (h >= 0 && h < 7)
			setSelected(h);
	}
	
	public void setSelected(int s)
	{
		selectedSkill = s;
		description = everything.getSkillStructure(shownActor, s / 2, s % 2).description;
		int c = everything.getSkillStructure(shownActor, s / 2, s % 2).cost;
		price = "Cost: " + (c == 0 ? "Free" : everything.getSkillStructure(shownActor, s / 2, s % 2).cost);
	}
	
	public void update(float delta)
	{
//		if (Gdx.input.justTouched()) {
//			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0f));
////			touchPoint.set(Gdx.input.getX(), Gdx.input.getY());
//			System.out.println(touchPoint.x + " " + touchPoint.y);
//			for (int i = 0; i < buttons.length; i++)
//				if (buttons[i].hit(touchPoint.x, touchPoint.y))
//				{
//					buttonHit(i);
//					return;
//				}
//		}
	}
	
	public void render(SpriteBatch batch)
	{
		if (chibi != null)
		{
			float resize = 325 / chibi.getRegionHeight();
			if (resize * chibi.getRegionWidth() > 390)
				resize = 350f / chibi.getRegionWidth();
			batch.draw(chibi, x, y - 50 * scale, chibi.getRegionWidth() * resize * scale, chibi.getRegionHeight() * resize * scale);//, 350, 40);
		}
		for (Button b : buttons)
		{
			if (b != null)
				b.draw(batch, 0);
		}
		
		font.draw(batch, description, x, y - 50 * scale);
		font.draw(batch, price, x, y - 75 * scale);
	}
	
//	private SkillContainerStructure getSkillStructure(ActorStructure a, int skill, int level)
//	{
//		if (skill == 0)
//			return everything.getSkillContainer(a.firstSkill(level));
//		else if (skill == 1)
//			return everything.getSkillContainer(a.secondSkill(level));
//		else if (skill == 2)
//			return everything.getSkillContainer(a.thirdSkill(level));
//		else if (skill == 3)
//			return everything.getSkillContainer(((HeroStructure)a).activeSkill(level));
//		return null;
//	}
	
	private TextureRegion getIcon(ActorStructure a, int skill, int level)
	{
		SkillContainerStructure s = everything.getSkillStructure(a, skill, level);
		
//		if (skill == 0)
//			s = everything.getSkillContainer(a.firstSkill(level));
//		else if (skill == 1)
//			s = everything.getSkillContainer(a.secondSkill(level));
//		else if (skill == 2)
//			s = everything.getSkillContainer(a.thirdSkill(level));
//		else if (skill == 3)
//			s = everything.getSkillContainer(((HeroStructure)a).activeSkill(level));
		if (s != null)
			return getSkillIcon(s.icon);
		return getSkillIcon("defaulticon");
	}
	
	private TextureRegion getActorImage(String name)
	{
		TextureRegion t = EverythingHolder.getObjectTexture(name + "image");
		if (t == null)
			t = EverythingHolder.getObjectTexture("defaultimage");
		return t;
	}
	
	private TextureRegion getSkillIcon(String s)
	{
		TextureRegion t = EverythingHolder.getObjectTexture(s);
		if (t == null)
			t = EverythingHolder.getObjectTexture("defaulticon");
		return t;
	}
}