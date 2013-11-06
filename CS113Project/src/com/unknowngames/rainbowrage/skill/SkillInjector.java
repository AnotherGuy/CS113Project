package com.unknowngames.rainbowrage.skill;

import java.util.ArrayList;

import com.unknowngames.rainbowrage.entity.Actor;
import com.unknowngames.rainbowrage.parser.SkillStructure;

public class SkillInjector extends Skill
{
	ArrayList<Skill> skills = new ArrayList<Skill>();
	
	public SkillInjector(SkillStructure s, SkillContainer sc)
	{
		super(s, sc);
	}
	
	public ArrayList<Skill> skills()
	{
		return skills;
	}
	
	@Override
	public void applyToTargets()
	{
		ArrayList<Actor> targetActors = inRange();
		if (targetActors == null)
			return;
		
		for (Actor a : targetActors)
			if (a.isAlive())
				a.takeSkillEffect(new SkillEffectInjector(this, a, targetActors.size()));
		
//		for (int i = 0; i < targetActors.size(); i++)
//			if (targetActors.get(i).isAlive())
//				targetActors.get(i).takeSkillEffect(new SkillEffect(this, targetActors.get(i), targetActors.size()));
	}
}
