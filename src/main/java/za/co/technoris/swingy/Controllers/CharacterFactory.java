package za.co.technoris.swingy.Controllers;

import za.co.technoris.swingy.Helpers.CharacterTypes;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Models.Characters.*;
import za.co.technoris.swingy.Models.Characters.Character;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static za.co.technoris.swingy.Helpers.GlobalHelper.factory;

/**
 * Created by mmbatha on 4/27/17.
 */
public abstract class CharacterFactory {

    private static Character character;

    public static Character newHero(String heroName, CharacterTypes characterType) {
        switch (characterType) {
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
        if (constraintViolations.size() > 0 ) {
            for (ConstraintViolation<Character> constraints : constraintViolations) {
                LoggerHelper.print(constraints.getRootBeanClass().getSimpleName()+
                        "." + constraints.getPropertyPath() + " " + constraints.getMessage());
            }
            return (null);
        }
        else {
            return (character);
        }
    }

    static Character newFoe(CharacterTypes characterType, Character hero) {
        switch (characterType) {
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
