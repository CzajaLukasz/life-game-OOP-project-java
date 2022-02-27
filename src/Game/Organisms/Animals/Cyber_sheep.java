package Game.Organisms.Animals;

import Game.Organisms.Organism;
import Game.World;

import java.awt.*;
import java.util.Random;
import java.util.Vector;


public class Cyber_sheep implements Animal{

    final private int born_day;
    private int power;
    private int position;
    final private String character;
    private int initiative;
    final private String name;
    final private Color color;

    public Cyber_sheep(int born_day, int position)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "CS";
        this.initiative = 4;
        this.power = 11;
        this.name = "Cyber sheep";
        this.color=Color.black;
    }

    public Cyber_sheep(int born_day, int position, int power)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Sh";
        this.initiative = 4;
        this.power = power;
        this.name = "Cyber sheep";
        this.color=Color.black;
    }
    @Override
    public Organism make_baby(int born_day, int position)
    {
        Organism new_organism = new Cyber_sheep (born_day, position);
        return new_organism;
    }

    @Override
    public int draw_place(World world)
    {
        Vector<Integer> pine_borschts = world.getPine_borschts();
        int nearest=-1, nearest_position=0;
        int tmp;
        int map_width=world.get_map_width();
        int direction;
        for (int i=0;i<pine_borschts.size(); i++) //szukanie najblizszego barszczu
        {
            int position = pine_borschts.get(i);
            tmp = java.lang.Math.abs(this.position % map_width - position % map_width);
            tmp += java.lang.Math.abs(this.position / map_width - position / map_width);
            if ( tmp < nearest || nearest == -1 )
            {
                nearest = tmp;
                nearest_position = position;
            }
        }
        if (nearest != -1) //jezeli na mapie jest barszcz
        {
            if (nearest_position % map_width > this.position % map_width)
            {
                direction = this.position + 1;
            }
            else if (nearest_position % map_width < this.position % map_width)
            {
                direction = this.position - 1;
            }
            else if ( nearest_position > this.position)
            {
                direction = this.position + map_width;
            }
            else
            {
                direction = this.position - map_width;
            }
            return direction;
        }
        int available_directions = 0;
        int map_height = world.get_map_height();
        int []directions = new int[4];

        if (position % map_width != 0)
        {
            directions[available_directions] = position - 1;
            available_directions++;
        }
        if (position % map_width != map_width - 1 )
        {
            directions[available_directions] = position + 1;
            available_directions++;
        }
        if (position >= map_width )
        {
            directions[available_directions] = position - map_width;
            available_directions++;
        }
        if (position < (map_width * (map_height - 1)) )
        {
            directions[available_directions] = position + map_width;
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
