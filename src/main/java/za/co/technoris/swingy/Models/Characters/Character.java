/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:57:54 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:57:54 
 */
package za.co.technoris.swingy.Models.Characters;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public abstract class Character {

	@NotNull
	@Size(min = 2, max = 20)
	protected String name;

	protected String type;
	protected int attack;
	protected int defense;
	protected int hp;
	protected int level;
	protected int x;
	protected int y;

	abstract public void attack(Character character);

	abstract public void defend(Character character, int damage);
}
