package com.company;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel{

    private static final int WIDTH = 50;
    private static final int LENGTH = 10;
    private Deque<SnakePart> snake = new ArrayDeque<>();
    private Point apple = new Point(0,0);
    private Random rand = new Random();

    private boolean isGrowing = false;
    private boolean gameLost = false;

    private int offset = 0;
    private int newDirection = 39;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        Main panel = new Main();
        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                panel.onKeyPressed(e.getKeyCode());
            }
        });
        frame.setContentPane(panel);
        frame.setSize(LENGTH*WIDTH, LENGTH*WIDTH);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Main() {
        createApple();
        snake.add(new SnakePart(0, 0, 39));
        setBackground(Color.WHITE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    repaint();
                    try {
                        Thread.sleep(1000/60l);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void createApple() {
        boolean positionAvailable;
        do {
            apple.x = rand.nextInt(LENGTH-1);
            apple.y = rand.nextInt(LENGTH-1);
            positionAvailable = true;
            for(SnakePart p : snake) {
                if(p.x == apple.x && p.y == apple.y) {
                    positionAvailable = false;
                    break;
                }
            }
        } while(!positionAvailable);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(gameLost) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", 90, 90));
            g.drawString("Partie terminée", LENGTH*WIDTH/2 - g.getFontMetrics().stringWidth("Partie terminée")/2, LENGTH*WIDTH/2);
            return;
        }

        offset += 5;
        SnakePart head = null;
        if(offset == WIDTH) {
            offset = 0;
            try {
                head = (SnakePart) snake.getFirst().clone();
                head.move();
                head.direction = newDirection;
                snake.addFirst(head);
                if(head.x == apple.x && head.y == apple.y) {
                    isGrowing = true;
                    createApple();
                }
                if(!isGrowing)
                    snake.pollLast();
                else
                    isGrowing = false;
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        g.setColor(Color.red);
        g.fillOval(apple.x*WIDTH + WIDTH/4, apple.y*WIDTH + WIDTH/4, WIDTH/2, WIDTH/2);

        g.setColor(Color.DARK_GRAY);
        for(SnakePart p : snake) {
            if(offset == 0) {
                if(p != head) {
                    if(p.x == head.x && p.y == head.y) {
                        gameLost = true;
                    }
                }
            }
            if(p.direction == 37 || p.direction == 39) {
                g.fillRect(p.x * WIDTH + ((p.direction == 37) ? -offset : offset), p.y*WIDTH, WIDTH, WIDTH);
            } else {
                g.fillRect(p.x * WIDTH, p.y*WIDTH + ((p.direction == 38) ? -offset : offset), WIDTH, WIDTH);
            }
        }

        g.setColor(Color.BLUE);
        g.drawString("Score : "+(snake.size() -1), 10, 20);

    }

    public void onKeyPressed(int keyCode) {
        if(keyCode >= 37 && keyCode <= 40) {
            if(Math.abs(keyCode - newDirection) != 2) {
                newDirection = keyCode;
            }
        }
    }

    class SnakePart {

        public int x, y, direction;

        public SnakePart(int x, int y, int direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public void move() {
            if(direction == 37 || direction == 39) {
                x += (direction == 37) ? -1 : 1;
                if(x > LENGTH)
                    x = -1;
                else if(x < -1)
                    x = LENGTH;
            }else {
                y += (direction == 38) ? -1 : 1;
                if(y > LENGTH)
                    y = -1;
                else if(y < -1)
                    y = LENGTH;
            }
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return new SnakePart(x, y, direction);
        }
    }

}