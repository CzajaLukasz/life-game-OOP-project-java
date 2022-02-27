package Game;


import Game.Organisms.Animals.*;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Game.Organisms.Organism;
import Game.Organisms.Plants.*;

public class World {


    private final Vector<Organism> organisms = new Vector<>();
    final private int map_width;
    final private int map_height;
    private int day;
    private int after_superpower;
    private String human_direction="UP";
    final private Field []map;
    private final Vector<String> comments = new Vector<>();
    private final Vector<Integer> pine_borschts = new Vector<>();
    private boolean turn_on_superpower = false;

    World(int map_height, int map_width)
    {
        if (map_height<10) { map_height=10; }
        if (map_width<10) { map_width=10; }
        this.map_height = map_height;
        this.map_width = map_width;
        this.day = 0;
        after_superpower = 10;
        map = new Field[map_height * map_width];
        for (int i=0;i<map_height*map_width;i++)
        {
            map[i]= new Field();
        }
    }

    public void add_comment(String comment) {this.comments.add(comment);}

    public void save()
    {
        try (PrintWriter file = new PrintWriter("save.txt", "UTF-8"))
        {
            file.println(this.map_height);
            file.println(this.map_width);
            file.println(this.get_human_direction());
            file.println(this.day);
            for (Organism organism : organisms)
            {
                file.println(organism.get_name());
                file.println(organism.get_position());
                file.println(organism.get_born_day());
                file.println(organism.get_power());
                if (organism.get_name().equals("Human") ) { file.println(after_superpower); }
            }
        }
        catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    public void do_round()
    {
        comments.removeAllElements();
        day++;
        int size = organisms.size();
        for (int i=0;i<size;i++)
        {
            if (organisms.get(i).get_initiative()==-1) {continue;}
            organisms.get(i).action(this);
        }
        this.turn_on_superpower = false;
        if (after_superpower != 10) { after_superpower++; }
        sort_organisms();
    }


    public void sort_organisms()
    {
        int size = organisms.size();
        for (int i=0;i<size;i++)
        {
            for (int j=0;j<size-i-1;j++)
            {
                if (organisms.get(j).get_initiative() < organisms.get(j+1).get_initiative())
                {
                    Collections.swap(organisms, j, j+1);
                }
            }
        }
        while (organisms.lastElement().get_initiative()==-1)
        {
            organisms.remove(organisms.size()-1);
        }
    }

    public void delete_organism(int position)
    {
        map[position].empty = true;
        map[position].organism.set_initiative(-1);
        map[position].organism = null;
    }

    public void add_organism(Organism organism, int position)
    {
        map[position].empty=false;
        map[position].organism=organism;
        organisms.add(organism);
        add_comment(" there is a new " + organism.get_name() + " On the map");
    }



    public int free_field(int position)  //zwraca wolne sasiednie pole lub wartosc -1 gyd takiego pola nie ma
    {
        if (position % map_width != 0 && map[position - 1].empty) { return position - 1; }
        if (position % map_width != map_width-1 && map[position + 1].empty) { return position + 1; }
        if (position >= map_width && map[position - map_width].empty) { return position - map_width; }
        if (position < (map_width*(map_height-1)) && map[position + map_width].empty) { return position + map_width; }
        return -1;
    }

    public void wanted_move(int current_position, int wanted_position, String character) //funkcja decyduje o tym co sie wydarzy gdy organizm przesunie sie na zadane pole
    {
        if (map[wanted_position].empty)
        {
            move_organism(current_position, wanted_position);
            return;
        }
        Organism organism=map[wanted_position].organism;
        if (character.equals(organism.get_character())) //rozmnozenie
        {
            int free_field = free_field(wanted_position);
            if (free_field == -1) { free_field = free_field(current_position); }
            if (free_field == -1) { return; }
            this.add_organism(organism.make_baby(day, free_field), free_field);
            return;
        }
        Organism organism_current=map[current_position].organism;
        int verdict = organism.collision(organism_current, this);
        if (verdict == 1) //organizm atakujacy zabija organizm zajmujacy pole
        {
            if (map[wanted_position].empty)
            {
                move_organism(current_position, wanted_position);
                return;
            }
            delete_organism(wanted_position);
            move_organism(current_position, wanted_position);
            return;
        }
        else if (verdict == 0) //organizm atakujacy umiera
        {
            delete_organism(current_position);
            return;
        }
        else if (verdict == 2) //umiera i zabija (zjedzenie trujacych roslin)
        {
            delete_organism(wanted_position);
            delete_organism(current_position);
            return;
        }
        else if (verdict == 4) //zamiana miejsc (antylopa)
        {
            map[wanted_position].organism = organism_current;
            map[current_position].organism = organism;
            organism.set_position(current_position);
            organism_current.set_position(wanted_position);
        }
    }

    void make_organism(String name, int position, int power, int born_day)
    {
        Organism organism;
        if (name.equals("Antelope") ) { organism = new Antelope(born_day, position, power); }
        else if (name.equals("Fox") ) { organism = new Fox(born_day, position, power); }
        else if (name.equals("Sheep") ) { organism = new Sheep(born_day, position, power); }
        else if (name.equals("Turtle") ) { organism = new Turtle(born_day, position, power); }
        else if (name.equals("Wolf") ) { organism = new Wolf(born_day, position, power); }
        else if (name.equals("Wolf Berries") ) { organism = new Berries(born_day, position); }
        else if (name.equals("Grass") ) { organism = new Grass(born_day, position); }
        else if (name.equals("Guarana") ) { organism = new Guarana( born_day, position); }
        else if (name.equals("Pine Borscht") ) { organism = new Pine_borscht(this, born_day, position); }
        else if (name.equals("Cyber sheep") ) { organism = new Cyber_sheep(born_day, position, power); }
        else  { organism = new Dandelion(born_day, position); }
        add_organism(organism, position);
    }

    public World load () throws FileNotFoundException
    {
        Scanner file = new Scanner(new File("save.txt"));
        int map_height = file.nextInt();
        file.nextLine();
        int map_width = file.nextInt();
        World world = new World(map_height, map_width);
        file.nextLine();
        world.set_human_direction(file.nextLine());
        world.set_day(file.nextInt());
        while (file.hasNext())
        {
            file.nextLine();
            String name = file.nextLine();
            int position = file.nextInt();
            file.nextLine();
            int born_day = file.nextInt();
            file.nextLine();
            int power = file.nextInt();
            if (name.equals("Human"))
            {
                file.nextLine();
                int after_superpower = file.nextInt();
                world.set_after_superpower(after_superpower);
                world.add_organism(new Human(born_day, position, power, after_superpower), position);
                continue;
            }
            world.make_organism(name, position, power, born_day);
        }
        return world;
    }

    public void move_organism(int current_position, int wanted_position)
    {
        map[wanted_position].empty = false;
        map[wanted_position].organism = map[current_position].organism;
        map[current_position].empty = true;
        map[current_position].organism = null;
        map[wanted_position].organism.set_position(wanted_position);
    }

    public int is_free(int position)  //sprawdza czy pole jest wolne, jesli nie to zwraca siłę organizmu na polu
    {
        if (map[position].empty) { return -1; }
        return map[position].organism.get_power();
    }

    public void add_pineborscht(Integer position)
    {
        pine_borschts.add(position);
    }

    public void remove_pineborscht(Integer position)
    {
        for (int i=0;i<this.pine_borschts.size();i++)
        {
            if (pine_borschts.get(i).equals(position)/*==position*/)
            {
                pine_borschts.removeElementAt(i);
                return;
            }
        }
    }

    public String get_pos_organism_name(int position)
    {
        return map[position].organism.get_name();
    }

    public int get_map_width()
    {
        return map_width;
    }

    public int get_map_height()
    {
        return map_height;
    }

    public int get_day()
    {
        return day;
    }

    public Vector<String> get_comments() { return this.comments; }

    public void set_human_direction(String direction)
    {
        this.human_direction = direction;
    }

    public void set_day(int day)
    {
        this.day = day;
    }

    public void set_after_superpower(int after_superpower)
    {
        this.after_superpower = after_superpower;
    }

    public Organism get_organism(int position)
    {
        if (map[position].empty) { return null; }
        return map[position].organism;
    }

    public String get_human_direction()
    {
        return this.human_direction;
    }

    public void turn_on_superpower() { this.turn_on_superpower=true; }

    public boolean get_turn_superpower() { return this.turn_on_superpower; }

    public Vector<Integer> getPine_borschts() { return pine_borschts; }

}
