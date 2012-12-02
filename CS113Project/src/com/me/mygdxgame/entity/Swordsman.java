package com.me.mygdxgame.entity;

import java.util.Iterator;
import java.util.ListIterator;

import com.me.mygdxgame.map.Coordinate;

public class Swordsman extends Minion
{
	public Swordsman(int x, int y, int team, ListIterator<Coordinate> p)
	{
		super(x, y, team, p);
		maxHealth = 20;
		currentHealth = maxHealth;
		damage = 10;
		attackSpeed = 25;
		attackCooldown = 0;
		attackRange = 25;
		speed = 1.5f;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
