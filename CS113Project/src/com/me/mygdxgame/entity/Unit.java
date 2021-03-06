package com.me.mygdxgame.entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.map.Coordinate;

public abstract class Unit extends Actor
{
	float speed;
	float xSpeed, ySpeed;
	Coordinate destination;
	Iterator<Coordinate> pathIter;
	static ArrayList<ArrayList<Animation>> animations;
	TextureRegion currentFrame;
	boolean walking, changedDirection;
	float stateTime;
	
	public Unit(int x, int y, int team, Iterator<Coordinate> pathIter)
	{
		super(x, y, team);
		//this.speed = speed;
		this.pathIter = pathIter;
		destination = pathIter.next();
		stateTime = 0f;
		changedDirection = true;
	}

	@Override
	public void draw(SpriteBatch batch)
	{		
		stateTime += Gdx.graphics.getDeltaTime();
		TextureRegion current;
		int unitType;
		if (this.getClass() == Swordsman.class)
			unitType = 0;
		else if (this.getClass() == Archer.class)
			unitType = 1;
		else
			unitType = 0;
			
		if (this.xSpeed > 0.6)
			current = animations.get(unitType).get(2).getKeyFrame(stateTime, true);
		else if (this.xSpeed < -0.6)
			current = animations.get(unitType).get(1).getKeyFrame(stateTime, true);
		else if (this.ySpeed > 0.6)
			current = animations.get(unitType).get(3).getKeyFrame(stateTime, true);
		else
			current = animations.get(unitType).get(0).getKeyFrame(stateTime, true);
		
		batch.draw(current, xCoord, yCoord);
	}
	
	public static void loadAnimations()
	{
		animations = new ArrayList<ArrayList<Animation>>();
		ArrayList<Animation> unitAnimation = new ArrayList<Animation>();
		
		unitAnimation.add(loadAnimation(0, 0, 29, 47, 5, false, false));
		unitAnimation.add(loadAnimation(0, 47, 34, 43, 5, false, false));
		unitAnimation.add(loadAnimation(0, 47, 34, 43, 5, true, false));
		unitAnimation.add(loadAnimation(0, 90, 31, 43, 5, false, false));
		animations.add(unitAnimation);
		
		
		unitAnimation = new ArrayList<Animation>();
		
		unitAnimation.add(loadAnimation(0, 0, 29, 47, 5, false, true));
		unitAnimation.add(loadAnimation(0, 47, 34, 43, 5, false, true));
		unitAnimation.add(loadAnimation(0, 47, 34, 43, 5, true, true));
		unitAnimation.add(loadAnimation(0, 90, 31, 43, 5, false, true));
		animations.add(unitAnimation);
	}
	
	private static Animation loadAnimation(int x, int y, int w, int h, int count, boolean flipX, boolean flipY)
	{
		TextureRegion[] frames = new TextureRegion[count];
		
		TextureRegion temp = new TextureRegion(spriteSheet, x, y, w * count, h);
		TextureRegion[][] tmp = temp.split(w, h);
		
		for (int i = 0; i < count; i++)
		{
			frames[i] = tmp[0][i];
			if (flipX || flipY)
				frames[i].flip(flipX, flipY);
		}
		
		Animation tempAnimation = new Animation(.05f, frames);
		tempAnimation.setPlayMode(Animation.LOOP_PINGPONG);
		return tempAnimation;
	}
	
	public void advance()
	{
		targetSelector();
		
		if ((target == null || !target.isAlive()) && attackCooldown <= 0)
		{
			float distance = getDistanceSquared(destination.x(), destination.y());
			if (distance  < speed * speed)
			{
				this.xCoord(destination.x());
				this.yCoord(destination.y());
				if (pathIter.hasNext())
					destination = pathIter.next();
				else
				{
					alive = false;
					return;
				}
				distance = getDistanceSquared(destination.x(), destination.y());
				setSpeed(distance);
			}
			else
			{
				this.xCoord(this.xCoord + xSpeed);
				this.yCoord(this.yCoord + ySpeed);
			}
			if (xSpeed + ySpeed == 0)
				setSpeed(distance);
		}
	}
	
	private void setSpeed(float distance)
	{
		distance = (float)Math.sqrt(distance);
		xSpeed = speed * ((destination.x() - xCoord) / distance);
		ySpeed = speed * ((destination.y() - yCoord) / distance);
	}
	
	protected abstract void attack();
}
