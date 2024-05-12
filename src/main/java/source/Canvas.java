package source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {

    private static final int POINT_SIZE = 5;

    private CLine cur;
    private CLine move;
    private final PaintEvent paintEvent;
    private final PaintEvent moveEvent;

    public Canvas(){
        cur = null;
        paintEvent = new PaintEvent((x, y, isStart)->{
           if(isStart) cur = new CLine(x, y);
           else{ cur.setX2(x); cur.setY2(y);}
           repaint();
        });
        moveEvent = new PaintEvent((x, y, isStart)->{
            if(cur == null) return;
            if(isStart) move = new CLine(x, y);
            else{ move.setX2(x); move.setY2(y);}
            x = move.getX2() - move.getX1();
            y = move.getY2() - move.getY1();
            move.setX1(move.getX2());
            move.setY1(move.getY2());

            cur.setX1(cur.getX1() + x);
            cur.setX2(cur.getX2() + x);
            cur.setY1(cur.getY1() + y);
            cur.setY2(cur.getY2() + y);

            repaint();
        });

        setBackground(Color.white);
        setPaint();
    }

    public void clear(){
        cur = null;
        repaint();
    }

    public void setMove(){;
        removeMouseListener(moveEvent);
        removeMouseMotionListener(moveEvent);
        removeMouseListener(paintEvent);
        removeMouseMotionListener(paintEvent);
        addMouseMotionListener(moveEvent);
        addMouseListener(moveEvent);
    }

    public void setPaint(){;
        removeMouseListener(moveEvent);
        removeMouseMotionListener(moveEvent);
        removeMouseListener(paintEvent);
        removeMouseMotionListener(paintEvent);
        addMouseListener(paintEvent);
        addMouseMotionListener(paintEvent);
    }

    private void midpointLineDrawing(Graphics graphics){
        int incrE, incrNE, d, x1, y1, x2, y2, px, py;
        boolean t;

        x1 = 0; y1 = 0;
        x2 = cur.x2 - cur.x1; y2 = cur.y2 - cur.y1;

        px = py = 1;
        if(x2 < 0) {px = -px; x2 = -x2;}
        if(y2 < 0) {py = -py; y2 = -y2; }

        t = y2 > x2;
        if(t){
            int temp = x2; x2 = y2; y2 = temp;
        }

        d = y2*2 - x2;
        incrE = y2*2;
        incrNE = (y2-x2)*2;
        while(true){
            if(t) graphics.fillOval(cur.x1 + y1*px, cur.y1 + x1*py, POINT_SIZE, POINT_SIZE );
            else graphics.fillOval(cur.x1 + x1*px, cur.y1 + y1*py, POINT_SIZE, POINT_SIZE);
            if(x1 >= x2) break;
            if (d <= 0){
                d += incrE;
                x1++;
            }else{
                d += incrNE;
                x1++;
                y1++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cur != null){
            midpointLineDrawing(g);
        }
    }

    private static class CLine{
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        CLine(int x, int y){
            x1 = x2 = x;
            y1 = y2 = y;
        }

        public void setX1(int x1) {
            this.x1 = x1;
        }

        public void setX2(int x2) {
            this.x2 = x2;
        }

        public void setY1(int y1) {
            this.y1 = y1;
        }

        public void setY2(int y2) {
            this.y2 = y2;
        }

        public int getX1() {
            return x1;
        }

        public int getX2() {
            return x2;
        }

        public int getY1() {
            return y1;
        }

        public int getY2() {
            return y2;
        }
    }

    private static class PaintEvent extends MouseAdapter{
        private interface Callback{
            void func(int x, int y, boolean isStart);
        }
        private final Callback callback;
        private PaintEvent(Callback callback){this.callback = callback;}
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            callback.func(e.getX(), e.getY(), true);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            callback.func(e.getX(), e.getY(), false);
        }

    }
}
