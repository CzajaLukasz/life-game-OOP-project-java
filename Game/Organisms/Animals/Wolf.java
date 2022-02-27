package Game.Organisms.Animals;

import Game.Organisms.Organism;
import Game.World;

import java.awt.*;

public class Wolf implements Animal {

    final private int born_day;
    private int power;
    private int position;
    final private String character;
    private int initiative;
    final private String name;
    final private Color color;

    public Wolf(int born_day, int position)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Wo";
        this.initiative = 5;
        this.power = 9;
        this.name = "Wolf";
        this.color=Color.gray;
    }

    public Wolf(int born_day, int position, int power)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Wo";
        this.initiative = 5;
        this.power = power;
        this.name = "Wolf";
        this.color=Color.gray;
    }
    @Override
    public Organism make_baby(int born_day, int position)
    {
        Organism new_organism = new Wolf (born_day, position);
        return new_organism;
    }

    @Override
    public int get_initiative() {return this.initiative;}
    @Override
    public int get_power() {return this.power;}
    @Override
    public int get_position() {return this.position;}
    @Override
    public String get_character() {return this.character;}
    @Override
    public int get_born_day() {return this.born_day;}
    @Override
    public String get_name() {return this.name;}
    @Override
    public void set_position(int position) {this.position=position;}
    @Override
    public void set_power(int power) {this.power=power;}
    @Override
    public void set_initiative(int initiative) {this.initiative=initiative;}
    @Override
    public Color get_color() { return this.color; }
}
