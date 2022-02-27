package Game.Organisms.Animals;

import Game.Organisms.Organism;

import java.util.Random;
import Game.World;

public interface Animal extends Organism {

    default int draw_place(World world) //losowanie miejsca do ruchu z dostepnych miejsc
    {
        int available_directions = 0;
        int map_width = world.get_map_width();
        int map_height = world.get_map_height();
        int directions[] = new int[4];

        if (get_position() % map_width != 0)  //jezeli nie lewa krawedz
        {
            directions[available_directions] = get_position() - 1;
            available_directions++;
        }
        if (get_position() % map_width != map_width - 1) // jezeli nie prawa krawedz
        {
            directions[available_directions] = get_position() + 1;
            available_directions++;
        }
        if (get_position() >= map_width) // jezeli nie gorna krawedz
        {
            directions[available_directions] = get_position() - map_width;
            available_directions++;
        }
        if (get_position() < (map_width * (map_height - 1))) // jezeli nie dolna krawedz
        {
            directions[available_directions] = get_position() + map_width;
            available_directions++;
        }
        Random generator = new Random();
        return directions[generator.nextInt(available_directions)];
    }

    default void action(World world)
    {
        int wanted_position = draw_place(world);
        world.wanted_move(this.get_position(), wanted_position, this.get_character());
    }

    default int collision(Organism attacker, World world)
    {
        if (attacker.get_power() > this.get_power())
        {
            world.add_comment(attacker.get_name() +" killed " + this.get_name());
            return 1;
        }
        if (attacker.get_power() < this.get_power())
        {
            world.add_comment(this.get_name() +" killed " + attacker.get_name());
            return 0;
        }
        if (attacker.get_born_day() > this.get_born_day())
        {
            world.add_comment(this.get_name() +" killed " + attacker.get_name());
            return 0;
        }
        world.add_comment(attacker.get_name() +" killed " + this.get_name());
        return 1;
    }

}
