package source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {

    private static final int X = 100;
    private static final int Y = 100;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private final JButton clear;
    private final JButton paint;
    private final JButton move;
    private final Canvas canvas;

    public Main(){
        clear = new JButton("clear");
        paint = new JButton("paint");
        move = new JButton("move");
        canvas = new Canvas();
        init();
        setEvent();
    }

    private void setEvent(){
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.clear();
            }
        });
        paint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setPaint();
            }
        });
        move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setMove();
            }
        });
    }

    private void init(){
        setTitle("Midpoint Line Drawing & Translation");

        setLocation(X, Y);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonGroup.add(paint);
        buttonGroup.add(clear);
        buttonGroup.add(move);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.BOTH;

        add(buttonGroup, constraints);

        constraints.gridy = 1;
        constraints.weighty = 1;
        add(canvas, constraints);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}