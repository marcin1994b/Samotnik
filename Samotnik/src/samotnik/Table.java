/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samotnik;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Marcin
 */
public class Table extends JPanel  {
    
    ArrayList<Shape> ovalsList = new ArrayList<>();
    static boolean first = true;
    
    
    public Table(){
        //createShapes();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if(Samotnik.manager.working == true){
                    for(Shape s : ovalsList){
                        if(s.contains(e.getPoint())){

                            if(Samotnik.manager.isEnd() == true){

                                Samotnik.manager.setWorking(false);
                                Frame.stateGame.setText("Koniec gry! Rozpocznij nową grę lub zamknij!");
                                Frame.optionsMenu.setEnabled(true);
                                first = true;
                            }
                            Samotnik.manager.makeGoodMoveMouse(e.getY(), e.getX());


                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void paint(Graphics g){
        //if(Samotnik.manager.working == true){
            if(first == true){
                this.setLocation(Samotnik.ex.getWidth() <= 500 ? 0 : Samotnik.ex.getWidth()/2 - 500/2, 
                                  Samotnik.ex.getHeight() <= 400 ? 0 : (Samotnik.ex.getHeight() - 85)/2 - 400/2
                                  );
                first = false;
            }
            this.setSize(500, 400);
            Frame.panel.setBackground(Samotnik.manager.colorBoard);
            this.setBackground(Samotnik.manager.colorBoard);
            super.paint(g);
            drawBoard(g);
            this.repaint();
            System.out.println("repaint");
            //System.gc();
        //}
    }
    
    
    
    private void drawBoard(Graphics g){
        createShapes();
        for(int x = 0; x < Manager.cards.length; x++){
            for(int y = 0; y < Manager.cards.length; y++){
                if(Manager.cards[x][y] != null){
                    if(Manager.cards[x][y].isInGame() == true){
                        g.setColor(Samotnik.manager.colorCards);
                        g.fillOval(Manager.cards[x][y].getX(), Manager.cards[x][y].getY(), 40, 40);
                        if(Manager.cards[x][y].isActive() == true && Manager.cards[x][y].isInGame() == true){
                            g.setColor(Samotnik.manager.colorActiveCard);
                            g.fillOval(Manager.cards[x][y].getX(), Manager.cards[x][y].getY(), 40, 40);
                        }
                    }else{
                        g.setColor(Samotnik.manager.colorPlaces);
                        g.fillOval(Manager.cards[x][y].getX(), Manager.cards[x][y].getY(), 40, 40);
                        
                    }
                }
            }
        }
    }
    
    public void createShapes(){
        for(int y = 0; y < Manager.cards.length; y++){
            for(int x = 0; x< Manager.cards.length; x++){
                if(Manager.cards[y][x] != null){
                    ovalsList.add(new Ellipse2D.Double(50*x + 70, 50*y + 35, 40, 40));
                }
            }
        }
    }
    
    
    
    
    
}
