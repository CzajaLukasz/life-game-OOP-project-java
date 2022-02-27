package Game.Organisms.Animals;

import Game.Organisms.Organism;
import Game.World;

import java.awt.*;

public class Human implements Animal {


    final  private int born_day;
    private int power;
    private int position;
    final private String character;
    private int initiative;
    final private String name;
    final private Color color;
    private int after_superpower;

    public Human(int born_day, int position)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Hu";
        this.initiative = 4;
        this.power = 5;
        this.after_superpower = 10;
        this.name = "Human";
        this.color = new Color(75,55,50);
    }

    public Human(int born_day, int position, int power, int after_superpower)
    {
        this.position = position;
        this.born_day = born_day;
        this.character = "Hu";
        this.initiative = 4;
        this.power = power;
        this.after_superpower = after_superpower;
        this.name = "Human";
        this.color = new Color(75,55,50);
    }

    void kill_neighbors(World world)
    {
        int map_width = world.get_map_width();
        int map_height = world.get_map_height();
        if (position % map_width != 0 && world.is_free(position - 1) != -1)
        {
            world.add_comment(this.get_name() + " burned " + world.get_pos_organism_name(position - 1));
            world.delete_organism(position - 1);
        }
        if (position % map_width != map_width - 1 && world.is_free(position + 1) != -1)
        {
            world.add_comment(this.get_name() + " burned " + world.get_pos_organism_name(position + 1));
            world.delete_organism(position + 1);
        }
        if (position >= map_width && world.is_free(position - map_width) != -1)
        {
            world.add_comment(this.get_name() + " burned " + world.get_pos_organism_name(position - map_width));
            world.delete_organism(position - map_width);
        }
        if (position < (map_width * (map_height - 1)) && world.is_free(position + map_width) != -1)
        {
            world.add_comment(this.get_name() + " burned " + world.get_pos_organism_name(position + map_width));
            world.delete_organism(position + map_width);
        }
    }
    @Override
    public int draw_place(World world)
    {
        int choosen_position=-1;
        int map_width = world.get_map_width();
        int map_height = world.get_map_height();



            if (world.get_turn_superpower() && after_superpower == 10)
            {
                after_superpower = 0;
                world.set_after_superpower(0);
                world.add_comment("Superpower turned on");
            }
                switch (world.get_human_direction()) //jezeli gracz wybierze kierunek ktory nie jest dostepny (koniec mapy) to czlowiek poruszy sie w strone przeciwna
                {
                    case "UP":
                        if (position > map_width) { choosen_position = position - map_width; }
                        else { choosen_position = position + map_width; }
                        break;
                    case "DOWN":
                        if (position < (map_width* (map_height - 1))) { choosen_position = position + map_width; }
                        else { choosen_position = position - map_width; }
                        break;
                    case "RIGHT":
                        if (position % map_width != map_width-1) { choosen_position = position + 1; }
                        else { choosen_position = position - 1; }
                        break;
                    case "LEFT":
                        if (position % map_width != 0) { choosen_position = position - 1; }
                        else { choosen_position = position + 1; }
                        break;
                }
        if (this.after_superpower < 5) //spalanie wszystkich sasiednich organizmow przez 5 rund ( od rundy 0 do rundy 4)
        {
            kill_neighbors(world);
        }
        else if (after_superpower < 10)
        {
            after_superpower++;
            if (after_superpower == 10) { world.add_comment("Superpower available again"); }
        }
        return choosen_position;
    }

    @Override
    public void action(World world)
    {
        int wanted_position = draw_place(world);
        world.wanted_move(this.position, wanted_position, this.character);
        if (after_superpower < 5) //ponowne spalanie organizmow na nowej pozycji
        {
            kill_neighbors(world);
            after_superpower++;
            if (after_superpower == 5) { world.add_comment("Superpower turned off"); }
        }
    }

    @Override
    public Organism make_baby(int born_day, int position)
    {
        Organism new_organism = new Human(born_day, position);
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
