package Game;

import Game.Organisms.Animals.Cyber_sheep;
import Game.Organisms.Plants.Pine_borscht;
import Game.UI.Game;

import java.awt.*;
import Game.Organisms.Animals.*;
import Game.Organisms.Plants.*;

public class Main {

    public static void main(String[] args) {
        World world = new World(20, 20);
        int height = world.get_map_height();
        int width = world.get_map_width();
        world.add_organism(new Wolf(0, 0), 0);
        world.add_organism(new Wolf(0, (int) (height / 10 * 0.3 * width)), (int) (height / 10 * 0.3 * width));
        world.add_organism(new Wolf(0,  (height * width - 1)), height * width - 1);
        world.add_organism(new Sheep(0, (int) (height / 10 * 0.9 * width)), (int) (height / 10 * 0.9 * width));
        world.add_organism(new Sheep( 0, (int) (height / 10 * 2.5 * width)), (int) (height / 10 * 2.5 * width));
        world.add_organism(new Sheep(0,  (height / 10 * 2 * width - 1)), height / 10 * 2 * width - 1);
        world.add_organism(new Grass(0, (int) (height / 10 * 3.2 * width)), (int) (height / 10 * 3.2 * width));
        world.add_organism(new Grass( 0, (int) (height / 10 * 5.6 * width)), (int) (height / 10 * 5.6 * width));
        world.add_organism(new Grass( 0, (int) (height / 10 * 8.4 * width)), (int) (height / 10 * 8.4 * width));
        world.add_organism(new Fox( 0, (int) (height / 10 * 3.4 * width)), (int) (height / 10 * 3.4 * width));
        world.add_organism(new Fox( 0, (int) (height / 10 * 7.2 * width)), (int) (height / 10 * 7.2 * width));
        world.add_organism(new Fox( 0, (int) (height / 10 * 5.8 * width)), (int) (height / 10 * 5.8 * width));
        world.add_organism(new Turtle( 0, (int) (height / 10 * 4.6 * width)), (int) (height / 10 * 4.6 * width));
        world.add_organism(new Turtle(0, (int) (height / 10 * 8.6 * width)), (int) (height / 10 * 8.6 * width));
        world.add_organism(new Turtle( 0, (int) (height / 10 * 9.2 * width)), (int) (height / 10 * 9.2 * width));
        world.add_organism(new Pine_borscht(world, 0, (int) (height / 10 * 1.4 * width)), (int) (height / 10 * 1.4 * width));
        world.add_organism(new Pine_borscht(world, 0, (height / 10 * 9 * width)), height / 10 * 9 * width);
        world.add_organism(new Pine_borscht(world, 0, (int) (height / 10 * 2.8 * width)), (int) (height / 10 * 2.8 * width));
        world.add_organism(new Guarana(0, (int) (height / 10 * 1.2 * width)), (int) (height / 10 * 1.2 * width));
        world.add_organism(new Guarana( 0,  (height / 10 * 3 * width)), height / 10 * 3 * width);
        world.add_organism(new Guarana( 0, (int) (height / 10 * 4.2 * width)), (int) (height / 10 * 4.2 * width));
        world.add_organism(new Guarana( 0, (int) (height / 10 * 6.6 * width)), (int) (height / 10 * 6.6 * width));
        world.add_organism(new Dandelion( 0,  (height / 10 * 7 * width)),  (height / 10 * 7 * width));
        world.add_organism(new Dandelion( 0, (int) (height / 10 * 8.8 * width)), (int) (height / 10 * 8.8 * width));
        world.add_organism(new Berries( 0, (int) (height / 10 * 6.8 * width)), (int) (height / 10 * 6.8 * width));
        world.add_organism(new Berries(0, (int) (height / 10 * 7.6 * width)), (int) (height / 10 * 7.6 * width));
        world.add_organism(new Berries( 0, (int) (height / 10 * 9.8 * width)), (int) (height / 10 * 9.8 * width));
        world.add_organism(new Antelope( 0, (int) (height / 10 * 9.4 * width)),  (int) (height / 10 * 9.4 * width));
        world.add_organism(new Antelope(0, (int) (height / 10 * 0.6 * width)), (int) (height / 10 * 0.6 * width));
        world.add_organism(new Antelope( 0, (int) (height / 10 * 6.2 * width)), (int) (height / 10 * 6.2 * width));
        world.add_organism(new Cyber_sheep( 0, (int) (height / 10 * 4.4 * width)), (int) (height / 10 * 4.4 * width));
        world.add_organism(new Cyber_sheep( 0, (int) (height / 10 * 7.4 * width)), (int) (height / 10 * 7.4 * width));
        world.add_organism(new Human( 0, (int) (height / 10 * 5.5 * width)), (int) (height / 10 * 5.5 * width));
        EventQueue.invokeLater(() -> new Game(world));
    }

}
