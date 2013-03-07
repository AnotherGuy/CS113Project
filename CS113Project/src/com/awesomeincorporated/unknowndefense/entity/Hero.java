package com.awesomeincorporated.unknowndefense.entity;
import java.util.ListIterator;

import com.awesomeincorporated.unknowndefense.map.Coordinate;
import com.awesomeincorporated.unknowndefense.parser.HeroStructure;
import com.awesomeincorporated.unknowndefense.parser.MinionStructure;

public class Hero extends Unit 
{
	//int stance = 1, previousStance = 1;
	boolean changedDirection = false;
	
	public Hero(int x, int y, boolean ranged, int team, ListIterator<Coordinate> p) 
	{
		super(x, y, ranged, team, p, 0, 0);
		stance = 1;
		alive = false;
	}
	
	public Hero(int x, int y, int team, ListIterator<Coordinate> p, HeroStructure struct)
	{
		super(x, y, struct.ranged(0), team, p, 0, 0);
		this.level = 0;
		maxHealth = struct.maxHealth(level);
		currentHealth = maxHealth;
		damage = struct.damage(level);
		attackSpeed = struct.attackSpeed(level);
		attackRange = struct.attackRange(level);
		speed = struct.speed(level);
		animation = struct.animation(level);
		stance = 1;
		alive = false;
	}
	
	public void stance(int s)
	{
		stance = s;
	}
	
	private void hold()
	{
		targetSelector();
		
		if (attacking && attackCooldown <= 0)
		{
			attack();
			attackCooldown = attackSpeed;
		}
		xSpeed = 0;
		ySpeed = 0;
	}

	@Override
	public void update() 
	{
		attackCooldown--;
		
		if (stance == -1)
		{
			retreat();
			previousStance = -1;
		}
		else if (stance == 0)
			hold();
		else if (attacking && attackCooldown <= 0)
		{
			attack();
			attackCooldown = attackSpeed;
			previousStance = 1;
		}
		else
		{
			if (previousStance == -1 && (this.team == 1 ? pathIter.hasNext() : pathIter.hasPrevious()))
			{
				destination = (this.team == 1 ? pathIter.next() : pathIter.previous());
				xSpeed = 0;
				ySpeed = 0;
			}
			advance();
			previousStance = 1;
		}
		
		//previousStance = stance;
	}
	
	public void respawn(int x, int y, ListIterator<Coordinate> p)
	{
		xCoord(x);
		yCoord(y);
		alive = true;
		currentHealth = maxHealth;
		pathIter = p;
		destination = pathIter.next();
		target = null;
		xSpeed = 0;
		ySpeed = 0;
		stance = 1;
		previousStance = 1;
	}
	
	public int stance()
	{
		return stance;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}