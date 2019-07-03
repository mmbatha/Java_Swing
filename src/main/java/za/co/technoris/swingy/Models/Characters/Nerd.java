package za.co.technoris.swingy.Models.Characters;

/**
 * Created by mmbatha on 4/20/17.
 */
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
