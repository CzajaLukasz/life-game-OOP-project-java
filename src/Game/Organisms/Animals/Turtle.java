package Game.Organisms.Animals;

import Game.Organisms.Organism;

import java.awt.*;
import java.util.Random;
import Game.World;

public class Turtle implements Animal {

    final private int born_day;
    private int power;
    private int position;
    final private String character;
    private int initiative;
    final private String name;
    final private Color color;

    public Turtle(int born_day, int position)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Tu";
        this.initiative = 1;
        this.power = 2;
        this.name = "Turtle";
        this.color=Color.RED;
    }

    public Turtle(int born_day, int position, int power)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Tu";
        this.initiative = 1;
        this.power = power;
        this.name = "Turtle";
        this.color=Color.RED;
    }

    @Override
    public Organism make_baby(int born_day, int position)
    {
        Organism new_organism = new Turtle (born_day, position);
        return new_organism;
    }

    @Override
    public int collision(Organism attacker, World world)
    {
        if (attacker.get_power() < 5)
        {
            world.add_comment(this.get_name() +" countered attack from " + attacker.get_name());
            return 3; //odbicie ataku
        }
        return Animal.super.collision(attacker, world);
    }

    @Override
    public void action(World world)
    {
        Random generator = new Random();
        if (generator.nextInt(4) == 0) {
            Animal.super.action(world);
        }
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
