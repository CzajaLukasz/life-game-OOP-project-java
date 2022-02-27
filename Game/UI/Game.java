package Game.UI;

import Game.Organisms.Animals.*;
import Game.Organisms.Organism;
import Game.Organisms.Plants.*;
import Game.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

public class Game extends JFrame implements KeyEventDispatcher{
    private Board board;
    private World world;
    private Comments comments;

    public Game(World world)
    {
        super("Lukasz Czaja, 184249");
        this.world = world;
        this.comments = new Comments(world);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(this);
        board = new Board(world);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton round = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new_round();
            }
        });
        round.setText("Next round");
        round.setFocusable(false);
        JButton superpower = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turn_on_superpower();
            }
        });
        JButton load = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        JButton save = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        load.setText("Load");
        save.setText("Save");
        load.setFocusable(false);
        save.setFocusable(false);
        superpower.setText("SUPERPOWER");
        superpower.setFocusable(false);
        JPanel south = new JPanel();
        south.add(round);
        south.add(superpower);
        south.add(load);
        south.add(save);
        for (int i = 0; i < world.get_map_height()*world.get_map_width(); i++)
        {
                int position = i;
                board.get_buttons()[i].addActionListener(e -> {
                    if (board.get_buttons()[position].getText().equals("")) {
                        Object[] organisms_list = {"Antelope", "Cyber sheep", "Fox", "Sheep", "Turtle", "Wolf", "Wolf Berries", "Dandelion", "Grass", "Guarana", "Pine Borscht"};
                        JOptionPane pane = new JOptionPane();
                        pane.setFocusable(false);
                        Object chosen_organism = JOptionPane.showInputDialog(null, "Choose organism", "Add organism", JOptionPane.INFORMATION_MESSAGE, null, organisms_list, organisms_list[0]);
                        if (chosen_organism != null) {
                            add_organism(chosen_organism.toString(), position);
                            draw_board(world);
                        }
                    }
                });
        }
        add(board.get_board(), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        board.draw_board(world);
        add(comments.get_comments(), BorderLayout.EAST);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void new_board(World world)
    {
        remove(board.get_board());
        this.board = new Board(world);
        add(board.get_board(), BorderLayout.CENTER);
        for (int i = 0; i < world.get_map_height()*world.get_map_width(); i++)
        {
            int position = i;
            board.get_buttons()[i].addActionListener(e -> {
                if (board.get_buttons()[position].getText().equals("")) {
                    Object[] organisms_list = {"Antelope", "Cyber sheep", "Fox", "Sheep", "Turtle", "Wolf", "Wolf Berries", "Dandelion", "Grass", "Guarana", "Pine Borscht"};
                    JOptionPane pane = new JOptionPane();
                    pane.setFocusable(false);
                    Object chosen_organism = JOptionPane.showInputDialog(null, "Choose organism", "Add organism", JOptionPane.INFORMATION_MESSAGE, null, organisms_list, organisms_list[0]);
                    if (chosen_organism != null) {
                        add_organism(chosen_organism.toString(), position);
                        draw_board(world);
                    }
                }
            });
        }
        board.draw_board(world);
        revalidate();
        repaint();
    }

    public void add_organism(String name, int position)
    {
        Organism organism;
        int born_day = world.get_day();
        if (name.equals("Antelope") ) { organism = new Antelope(born_day, position); }
        else if (name.equals("Fox") ) { organism = new Fox(born_day, position); }
        else if (name.equals("Sheep") ) { organism = new Sheep(born_day, position); }
        else if (name.equals("Turtle") ) { organism = new Turtle(born_day, position); }
        else if (name.equals("Wolf") ) { organism = new Wolf(born_day, position); }
        else if (name.equals("Wolf Berries") ) { organism = new Berries(born_day, position); }
        else if (name.equals("Grass") ) { organism = new Grass(born_day, position); }
        else if (name.equals("Guarana") ) { organism = new Guarana(born_day, position); }
        else if (name.equals("Pine Borscht") ) { organism = new Pine_borscht(world, born_day, position); }
        else if (name.equals("Cyber sheep") ) { organism = new Cyber_sheep(born_day, position); }
        else  { organism = new Dandelion(born_day, position); }
        world.add_organism(organism, position);

    }


    private void draw_board(World world)
    {
        board.draw_board(world);
        revalidate();
        repaint();
    }

    private void write_comments(World world)
    {
        remove(comments.get_comments());
        this.comments = new Comments(world);
        add(comments.get_comments(), BorderLayout.EAST);
        revalidate();
        repaint();
    }

    private void new_round()
    {
        world.do_round();
        write_comments(world);
        draw_board(world);
    }

    public void save()
    {
        world.save();
    }

    public void load()
    {
        try
        {
            world = world.load();
            new_board(world);
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public void turn_on_superpower()
    {
        world.turn_on_superpower();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        int direction = e.getKeyCode();
        switch (direction)
        {
            case KeyEvent.VK_DOWN: {
                world.set_human_direction("DOWN");
                break;
            }
            case KeyEvent.VK_UP: {
                world.set_human_direction("UP");
                break;
            }
            case KeyEvent.VK_LEFT: {
                world.set_human_direction("LEFT");
                break;
            }
            case KeyEvent.VK_RIGHT: {
                world.set_human_direction("RIGHT");
                break;
            }
            default: {
                return false;
            }
        }
        return false;
    }

}
