/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:05:50 
 * @Last Modified by: mmbatha
 * @Last Modified time: 2019-07-04 11:07:06
 */
package za.co.technoris.swingy.Models.Characters;

public class Wolf extends Foe {

	public Wolf(int level) {
		super(level);
		this.name = "Wolf";
		this.type = "Wolf";
		this.attack = 5 + this.level;
		this.defense = 1 + this.level;
		this.HP = 10 + this.level;
	}
}
