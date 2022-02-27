package Game.Organisms.Animals;

import Game.Organisms.Organism;

import java.awt.*;
import java.util.Random;
import Game.World;

public class Fox implements Animal{

    private World world;
    final private int born_day;
    private int power;
    private int position;
    final private String character;
    private int initiative;
    final private String name;
    final private Color color;

    public Fox(int born_day, int position)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Fo";
        this.initiative = 7;
        this.power = 3;
        this.name = "Fox";
        this.color=Color.PINK;
    }

    public Fox(int born_day, int position, int power)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Fo";
        this.initiative = 7;
        this.power = power;
        this.name = "Fox";
        this.color=Color.PINK;
    }
    @Override
    public Organism make_baby(int born_day, int position)
    {
        Organism new_organism = new Fox (born_day, position);
        return new_organism;
    }

    @Override
    public int draw_place(World world) //lis losuje miejsce z tych ktore sa wolne, lub zajmowane przez slabsze organizmy. Jezeli nie ma takiego miejsca to sie nie rusza
    {
        int available_directions = 0;
        int map_width = world.get_map_width();
        int map_height = world.get_map_height();
        int directions[] = new int[4];

        if (position % map_width != 0 && world.is_free(position - 1) <= this.power)
        {
            directions[available_directions] = position - 1;
            available_directions++;
        }
        if (position % map_width != map_width - 1 && world.is_free(position + 1) <= this.power)
        {
            directions[available_directions] = position + 1;
            available_directions++;
        }
        if (position >= map_width && world.is_free(position - map_width) <= this.power)
        {
            directions[available_directions] = position - map_width;
            available_directions++;
        }
        if (position < (map_width * (map_height - 1)) && world.is_free(position + map_width) <= this.power)
        {
            directions[available_directions] = position + map_width;
            available_directions++;
        }
        if (available_directions == 0) { return -1; } //nie rusza sie
        Random generator = new Random();
        return directions[generator.nextInt(available_directions)];
    }

    @Override
    public void action(World world)
    {
        int wanted_position = draw_place(world);
        if (wanted_position == -1) { return; } //nie rusza sie
        world.wanted_move(this.get_position(), wanted_position, this.get_character());
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
