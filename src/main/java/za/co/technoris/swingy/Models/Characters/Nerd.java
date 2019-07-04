/*
 * @Author: mmbatha 
 * @Date: 2019-07-04 11:05:25 
 * @Last Modified by:   mmbatha 
 * @Last Modified time: 2019-07-04 11:05:25 
 */
package za.co.technoris.swingy.Models.Characters;

public class Nerd extends Hero {

	public Nerd() {
		super();
	}

	public Nerd(String name) {
		super(name);
		this.type = "Nerd";
		this.attack += 10;
		this.defense += 1;
		this.hp += 25;
	}
}
