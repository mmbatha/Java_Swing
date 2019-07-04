/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:05:40 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 11:05:40 
 */
package za.co.technoris.swingy.Models.Characters;

public class Villain extends Hero {

	public Villain() {
		super();
	}

	public Villain(String name) {
		super(name);
		this.type = "Villain";
		this.attack += 5;
		this.defense += 3;
		this.hp += 75;
	}
}
