/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:30:51 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 10:43:36
 */
package za.co.technoris.swingy.Controllers;

import za.co.technoris.swingy.Helpers.FoeTypes;
import za.co.technoris.swingy.Helpers.HeroTypes;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Models.Characters.*;
import za.co.technoris.swingy.Models.Characters.Character;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static za.co.technoris.swingy.Helpers.GlobalHelper.factory;

public abstract class CharacterFactory {

	private static Character character;

	public static Character newHero(String heroName, HeroTypes heroType) {
		switch (heroType) {
		case VILLAIN:
			character = new Villain(heroName);
			break;
		case FARMER:
			character = new Farmer(heroName);
			break;
		case NERD:
			character = new Nerd(heroName);
			break;
		}
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Character>> constraintViolations = validator.validate(character);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Character> constraints : constraintViolations) {
				LoggerHelper.print(constraints.getRootBeanClass().getSimpleName() + "." + constraints.getPropertyPath()
						+ " " + constraints.getMessage());
			}
			return (null);
		} else {
			return (character);
		}
	}

	static Character newFoe(FoeTypes foeType, Character hero) {
		switch (foeType) {
		case ZOMBIE:
			character = new Zombie(hero.getLevel());
			break;
		case WOLF:
			character = new Wolf(hero.getLevel());
			break;
		}
		return (character);
	}
}
