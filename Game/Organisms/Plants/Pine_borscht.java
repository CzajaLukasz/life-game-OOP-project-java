package Game.Organisms.Plants;

import Game.Organisms.Organism;
import Game.World;

import java.awt.*;

public class Pine_borscht implements Plant{

    private World world;
    private int born_day;
    private int power;
    private int position;
    private String character;
    private int initiative;
    private String name;
    private Color color;

    public Pine_borscht(World world, int born_day, int position)
    {
        this.world = world;
        this.position = position;
        this.born_day = born_day;
        this.character = "PB";
        this.initiative = 0;
        this.power = 10;
        this.name = "Pine Borscht";
        this.color= new Color(72, 34, 124);
        world.add_pineborscht(position);
    }
    @Override
    public Organism make_baby(int born_day, int position)
    {
        Organism new_organism = new Pine_borscht(world, born_day, position);
        return new_organism;
    }

    @Override
    public void action(World world)
    {
        int map_width = world.get_map_width();
        int map_height = world.get_map_height();

        if (position % map_width != 0 && world.is_free(position-1) > 0)
        {
            String name = world.get_pos_organism_name(position - 1);
            if (!name.equals("Pine Borscht") && !name.equals("Wolf Berries") && !name.equals("Cyber sheep") )
            {
                world.add_comment(this.get_name() +" killed " + world.get_pos_organism_name(position - 1));
                world.delete_organism(position - 1);
            }
        }
        if (position % map_width != map_width - 1 && world.is_free(position + 1) > 0)
        {
            String name = world.get_pos_organism_name(position + 1);
            if (!name.equals("Pine Borscht") && !name.equals("Wolf Berries") && !name.equals("Cyber sheep") )
            {
                world.add_comment(this.get_name() +" killed " + world.get_pos_organism_name(position + 1));
                world.delete_organism(position + 1);
            }
        }
        if (position >= map_width && world.is_free(position - map_width) > 0)
        {
            String name = world.get_pos_organism_name(position - map_width);
            if (!name.equals("Pine Borscht") && !name.equals("Wolf Berries") && !name.equals("Cyber sheep") )
            {
                world.add_comment(this.get_name() +" killed " + world.get_pos_organism_name(position - map_width));
                world.delete_organism(position - map_width);
            }
        }
        if (position < (map_width * (map_height - 1)) && world.is_free(position + map_width) > 0)
        {
            String name = world.get_pos_organism_name(position + map_width);
            if (!name.equals("Pine Borscht") && !name.equals("Wolf Berries") && !name.equals("Cyber sheep") )
            {
                world.add_comment(this.get_name() +" killed " + world.get_pos_organism_name(position + map_width));
                world.delete_organism(position + map_width);
            }
        }
        Plant.super.action(world);
    }


    @Override
    public int collision(Organism attacker, World world) //barszcz sosnowskiego umiera poniewaz jest jedzony ale zabija organizm ktory go zjadl
    {
        world.remove_pineborscht(this.position);
        if (attacker.get_name().equals("Cyber sheep"))
        {
            world.add_comment(attacker.get_name() +" ate " + this.get_name());
            return 1;
        }
        world.add_comment(attacker.get_name() +" ate " + this.get_name() + " and died ");
        return 2;
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
