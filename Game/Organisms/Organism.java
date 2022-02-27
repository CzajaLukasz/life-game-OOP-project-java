package Game.Organisms;
import Game.World;
import java.awt.*;
public interface Organism {


    void action (World world);

    int collision(Organism organism, World world);

    Organism make_baby(int born_day, int position);

    int get_initiative();

    int get_power();

    int get_position();

    String get_character();

    int get_born_day();

    String get_name();

    void set_position(int position);

    void set_power(int power);

    void set_initiative(int initiative);

    Color get_color();
}
