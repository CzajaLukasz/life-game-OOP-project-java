package Game.Organisms.Animals;

import Game.Organisms.Organism;

import java.awt.*;
import java.util.Random;
import Game.World;

public class Antelope implements Animal{

    final private int born_day;
    private int power;
    private int position;
    final private String character;
    private int initiative;
    final private String name;
    final private Color color;

    public Antelope(int born_day, int position)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "An";
        this.initiative = 4;
        this.power = 4;
        this.name = "Antelope";
        this.color=Color.orange;
    }

    public Antelope( int born_day, int position, int power)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "An";
        this.initiative = 4;
        this.power = power;
        this.name = "Antelope";
        this.color=Color.orange;
    }

    @Override
    public Organism make_baby(int born_day, int position)
    {
        Organism new_organism = new Antelope (born_day, position);
        return new_organism;
    }

    @Override
    public int draw_place(World world)
    {
        int first_draw = Animal.super.draw_place(world); // losowanie dowolnego sasiedniego miejsca
        int directions[] = new int[4];
        directions[0] = first_draw;
        int available_directions = 1;
        int map_width = world.get_map_width();
        int map_height = world.get_map_height();

        //losowanie z miejsc sasiadujacych z wylosowanym wlacznie z wylosowanym, wylaczajac miejsce na ktorym jestesmy
        if (first_draw % map_width != 0 && first_draw-1 !=position)
        {
            directions[available_directions] = first_draw - 1;
            available_directions++;
        }
        if (first_draw % map_width != map_width - 1 && first_draw + 1 != position)
        {
            directions[available_directions] = first_draw + 1;
            available_directions++;
        }
        if (first_draw >= map_width && first_draw - map_width != position)
        {
            directions[available_directions] = first_draw - map_width;
            available_directions++;
        }
        if (first_draw < (map_width * (map_height - 1)) && first_draw + map_width != position)
        {
            directions[available_directions] = first_draw + map_width;
            available_directions++;
        }
        Random generator = new Random();
        return directions[generator.nextInt(available_directions)];
    }

    @Override
    public void action(World world)
    {
        int wanted_position = draw_place(world);
        world.wanted_move(this.get_position(), wanted_position, this.get_character());
    }

    @Override
    public int collision(Organism attacker, World world)
    {
        Random generator = new Random();
        if (generator.nextInt(2) == 0) { return Animal.super.collision(attacker, world); }
        int free_field = world.free_field(position);
        if (free_field == -1) //jezeli antylopa nie ma miejsca do ucieczki to wchodzi na miejsce atakujacego
        {
            world.add_comment(attacker.get_name() +" changed place with " + this.get_name());
            return 4; //zamiana miejsc
        }
        world.move_organism(position, free_field);
        return 1; //organizm wchodzi na miejsce antylopy
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
