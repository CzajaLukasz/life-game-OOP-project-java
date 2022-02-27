package Game.UI;

import javax.swing.*;
import java.awt.*;
import Game.World;
import Game.Organisms.Organism;

public class Board extends JPanel {
    private int map_width;
    private int map_height;
    private final JPanel board;
    private JButton[] fields;

    Board(World world)
    {
        this.map_width = world.get_map_width();
        this.map_height = world.get_map_height();
        board = make_board(world);
    }

    private JPanel make_board(World world)
    {
        JPanel board = new JPanel();
        GridLayout gridLayout = new GridLayout(this.map_height, this.map_width);
        board.setLayout(gridLayout);
        fields = new JButton[this.map_height*this.map_width];
        for (int i = 0; i < this.map_height*this.map_width; i++)
        {
                fields[i] = new JButton();
                fields[i].setBackground(Color.WHITE);
                fields[i].setFocusable(false);
                fields[i].setPreferredSize(new Dimension(60, 60));
                board.add(fields[i]);
        }
        return board;
    }

    void draw_board(World world) {
        for (int i = 0; i < this.map_height*this.map_width; i++)
        {
            Organism organism = world.get_organism(i);
                if (organism == null)
                {
                    fields[i].setText("");
                    fields[i].setBackground(Color.white);
                }
                else
                {
                    fields[i].setText(organism.get_character());
                    fields[i].setBackground(organism.get_color());
                }
        }
    }

    JPanel get_board() {
        return board;
    }

    JButton[] get_buttons() {
        return fields;
    }

}
