package Game.Organisms.Plants;

import Game.Organisms.Organism;

import java.util.Random;
import Game.World;

public interface Plant extends Organism {

    default boolean sow()  //losowanie czy zasiac rosline
    {
        Random generator = new Random();
        int random = generator.nextInt(100);
        if (random % 7 == 0) { return true; } //co 7 raz uda sie rozmnozyc
        return false;
    }

    default int draw_place(World world)
    {
        int available_directions = 0;
        int map_width = world.get_map_width();
        int map_height = world.get_map_height();
        int directions[] = new int[4];
        //losowanie z miejsc ktore sa wolne
        if (get_position() % map_width != 0 && world.is_free(get_position()-1) == -1)
        {
            directions[available_directions] = get_position() - 1;
            available_directions++;
        }
        if (get_position() % map_width != map_width - 1 && world.is_free(get_position() + 1) == -1)
        {
            directions[available_directions] = get_position() + 1;
            available_directions++;
        }
        if (get_position() >= map_width && world.is_free(get_position() - map_width) == -1)
        {
            directions[available_directions] = get_position() - map_width;
            available_directions++;
        }
        if (get_position() < (map_width * (map_height - 1)) && world.is_free(get_position() + map_width) == -1)
        {
            directions[available_directions] = get_position() + map_width;
            available_directions++;
        }
        if (available_directions == 0) { return -1; }
        Random generator = new Random();
        return directions[generator.nextInt(available_directions)];
    }

    default void action(World world)
    {
        if (!sow()) { return; }
        int wanted_position = draw_place(world);
        if (wanted_position == -1) { return; }
        world.add_organism(this.make_baby(world.get_day(), wanted_position), wanted_position);
    }

    default int collision(Organism attacker, World world)  //roslina zawsze umiera poniewaz jest jedzona
    {
        world.add_comment(attacker.get_name() +" ate " + this.get_name());
        return 1; //organizm atakujacy zjada rosline
    }

}
