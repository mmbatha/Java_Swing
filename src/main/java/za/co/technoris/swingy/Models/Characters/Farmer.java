/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 10:58:44 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 10:58:44 
 */
package za.co.technoris.swingy.Models.Characters;

public class Farmer extends Hero {

	public Farmer() {
		super();
	}

	public Farmer(String name) {
		super(name);
		this.type = "Farmer";
		this.attack += 7;
		this.defense += 2;
		this.hp += 50;
	}
}
