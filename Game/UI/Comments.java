package Game.UI;

import javax.swing.*;
import javax.swing.text.*;
import Game.World;

import java.awt.*;

public class Comments extends JPanel {

    private final JPanel comments;

    public Comments(World world)
    {
        comments = new JPanel();
        comments.setLayout(new BoxLayout(comments, BoxLayout.Y_AXIS));
        comments.setPreferredSize(new Dimension(250, 800));
        JScrollPane scrollPane = write_comments(world);
        comments.add(scrollPane);
    }

    private JScrollPane write_comments(World world) {
        StyledDocument document = new DefaultStyledDocument(new StyleContext());
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        if (!world.get_comments().isEmpty()) {
            for (String comment : world.get_comments()) {
                try
                {
                    document.insertString(document.getLength(), comment + "\n", attributes);
                }
                catch (BadLocationException e)
                {
                    e.printStackTrace();
                }
            }
        }
        JTextPane text_pane = new JTextPane(document);
        text_pane.setEditable(false);
        return new JScrollPane(text_pane);
    }

    JPanel get_comments() {
        return comments;
    }

}
