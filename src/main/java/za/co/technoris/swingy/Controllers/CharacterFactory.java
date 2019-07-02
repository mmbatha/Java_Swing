package za.co.technoris.swingy.Controllers;

import za.co.technoris.swingy.Helpers.CharacterTypes;
import za.co.technoris.swingy.Helpers.LoggerHelper;
import za.co.technoris.swingy.Models.characters.*;
import za.co.technoris.swingy.Models.characters.Character;

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
            case WARRIOR:
                character = new Warrior(heroName);
                break;
            case THIEF:
                character = new Thief(heroName);
                break;
            case WIZARD:
                character = new Wizard(heroName);
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
            case RAT:
                character = new Rat(hero.getLevel());
                break;
            case BAT:
                character = new Bat(hero.getLevel());
                break;
        }
        return (character);
    }
}
