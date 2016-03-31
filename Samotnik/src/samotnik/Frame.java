/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samotnik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Marcin
 */
public class Frame extends JFrame{
    
    final int HIGHT = 500;
    final int WIDTH = 500;
    Table table;
    static JPanel panel;
    JPanel statePanel;
    static JLabel stateGame;
    
    int wysokosc = HIGHT;
    int szerokosc = WIDTH;
    
    
    
    static JMenu optionsMenu;
    
    
    String aboutGameString = "Samotnik (również Solitaire, Peg solitaire, zakonniczka, Kapucyn) — gra logiczna rozgrywana przez jedną osobę na planszy mającej 33 lub 37 pól. " + "\n" + "\n" +
                    "Rzekomo powstała na rozkaz Ludwika XIV. Zyskała popularność we Francji w XVII wieku, później w innych krajach (w tym także w Polsce). Bardziej uproszczone gry były znane wcześniej w starożytnym Rzymie.\n" + "\n" +
                    "Celem gry jest zostawienie na planszy jednego piona. Idealnym rozwiązaniem jest jeden pion, pozostawiony na środku. Piony bije się przeskakując je. Nie wolno ich przeskakiwać na ukos.";
    
    String aboutAppString = "Aplikacja została utworzona przez studenta UWr - Marcin Barańskiego - w celu zaliczenia zadania z programowania w języku JAVA." + "\n" + "\n" +
                            "Jest to pierwsza moja wersja tej aplikacji i zapewne ostatnia." + "\n" + "\n" +
                            "Aplikacja powstała 30.11.2014";
    
    public Frame(){
        this.setTitle("Samotnik");
        this.setSize(WIDTH, HIGHT);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension tmp  = new Dimension(500,500);
        this.setMinimumSize(tmp);
        addJMenuBar();
        addJPanel();
        addJLabel();
        this.setVisible(true);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                wysokosc = Samotnik.ex.getHeight();
                szerokosc = Samotnik.ex.getWidth();
                table.setLocation(szerokosc <= 500 ? 0 : szerokosc/2 - 500/2, 
                                  wysokosc <= 400 ? 0 : (wysokosc - 85)/2 - 400/2
                                  );
                
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
    }
    
    private void addJPanel(){
        panel = new JPanel();
        table = new Table();
        panel.add(table);
        this.add(panel, BorderLayout.CENTER);
    }
    
    private void addJLabel(){
        JPanel statePanel = new JPanel();
        statePanel.setBackground(Color.GRAY);
        stateGame = new JLabel("Gra gotowa! Powodzenia!!");
        statePanel.add(stateGame);
        this.add(statePanel, BorderLayout.SOUTH);
    }
    
    
    private void addJMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem = null;
        
        addGameMenu(menuBar, menuItem);
        addMovesMenu(menuBar, menuItem);
        addOptionsMenu(menuBar, menuItem);
        addHelpMenu(menuBar, menuItem);

        this.setJMenuBar(menuBar);
    }
    
    private class GameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Nowa":
                    Samotnik.manager.setWorking(true);
                    optionsMenu.setEnabled(false);
                    Samotnik.manager.startNewGame();
                    stateGame.setText("Gra gotowa! Powodzenia!");
                    break;
                case "Koniec":
                    Samotnik.manager.setWorking(false);
                    optionsMenu.setEnabled(true);
                    stateGame.setText("Gra zakończona!");
                    Table.first = true;
                    break;
            }
        }
        
    }
    
    private class MovesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(e.getActionCommand());
            Cards activeCard = Samotnik.manager.findActiveCard();
            int x = (activeCard.getX() - 70) / 50;
            int y = (activeCard.getY() - 35) / 50;
            if(Samotnik.manager.isEnd() == true){
                Samotnik.manager.setWorking(false);
            }
            if(Samotnik.manager.working == true){
                switch (e.getActionCommand()) {

                    case "Wybierz górny":
                        if(Samotnik.manager.isGoodMove(y-1,x)){
                            Samotnik.manager.changeActiveCards(y, x, e.getActionCommand());
                        }
                        break;
                    case "Wybierz dolny":
                        if(Samotnik.manager.isGoodMove(y+1,x)){
                            Samotnik.manager.changeActiveCards(y, x,e.getActionCommand());
                        }
                        break;
                    case "Wybierz prawy":
                        if(Samotnik.manager.isGoodMove(y,x+1)){
                            Samotnik.manager.changeActiveCards(y, x, e.getActionCommand());
                        }
                        break;
                    case "Wybierz lewy":
                        if(Samotnik.manager.isGoodMove(y,x-1)){
                            Samotnik.manager.changeActiveCards(y, x, e.getActionCommand());
                        }
                        break;
                    case "skok w górę":
                        if(Samotnik.manager.isGoodMove(y-2,x)){
                            Samotnik.manager.makeGoodMoveKeyBoard(y, x, y-2, x);
                        }
                        break;
                    case "skok w dół":
                        if(Samotnik.manager.isGoodMove(y+2,x)){
                            Samotnik.manager.makeGoodMoveKeyBoard(y, x, y+2, x);
                        }
                        break;
                    case "skok w prawo":
                        if(Samotnik.manager.isGoodMove(y,x+2)){
                            Samotnik.manager.makeGoodMoveKeyBoard(y, x, y, x+2);
                        }
                        break;
                    case "skok w lewo":
                        if(Samotnik.manager.isGoodMove(y,x-2)){
                            Samotnik.manager.makeGoodMoveKeyBoard(y, x, y, x-2);
                        }
                        break;

                }
            }
        }

    }
    
    private class OptionsListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            AbstractButton aButton = (AbstractButton) e.getSource();
            String label = aButton.getText();
            
            switch (label) {
                case "Brytyjska":
                    Samotnik.manager.setVersion(1);
                    break;
                case "Europejska":
                    Samotnik.manager.setVersion(2);
                    break;
            }
        }
    }
    
    private class OptionListener2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            createColorFrame(e.getActionCommand());
        }
        
    }
    
    private class HelpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "O grze":
                    showDialogWindow(aboutGameString);
                    break;
                case "O aplikacji":
                    showDialogWindow(aboutAppString);
                    break;
            }
        }
        
    }
    
    
    
    private void addGameMenu(JMenuBar menuBar, JMenuItem menuItem){
        JMenu gameMenu = new JMenu("Gra");
        GameListener listener = new GameListener();
       
        
        menuBar.add(gameMenu);
        
        menuItem = gameMenu.add(new JMenuItem("Nowa", 'n'));
        menuItem.addActionListener(listener);
        gameMenu.add(new JSeparator());
        menuItem = gameMenu.add(new JMenuItem("Koniec", 'k'));
        menuItem.addActionListener(listener);
    }
    
    private void addMovesMenu(JMenuBar menuBar, JMenuItem menuItem){
        MovesListener listener = new MovesListener();
        
        JMenu movesMenu = new JMenu("Ruchy");
        menuBar.add(movesMenu);
        menuItem = movesMenu.add(new JMenuItem("Wybierz prawy"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('d'));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("Wybierz lewy"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('a'));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("Wybierz górny"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('w'));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("Wybierz dolny"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke('s'));
        menuItem.addActionListener(listener);
        
        movesMenu.add(new JSeparator());
        menuItem = movesMenu.add(new JMenuItem("skok w górę"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("skok w dół"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("skok w lewo"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
        menuItem.addActionListener(listener);
        
        menuItem = movesMenu.add(new JMenuItem("skok w prawo"));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
        menuItem.addActionListener(listener);
    }
    
    private void addOptionsMenu(JMenuBar menuBar, JMenuItem menuItem){
        optionsMenu = new JMenu("Opcje");
        ButtonGroup group = new ButtonGroup();
        OptionsListener listener = new OptionsListener();
        OptionListener2 listener2 = new OptionListener2();
        
        menuBar.add(optionsMenu);
        
        
        JRadioButton britishVersion = new JRadioButton("Brytyjska", true);
        group.add(britishVersion);
        britishVersion.addItemListener(listener);
        JRadioButton europeVersion = new JRadioButton("Europejska", false);
        group.add(europeVersion);
        europeVersion.addItemListener(listener);
        
        optionsMenu.add(britishVersion);
        optionsMenu.add(europeVersion);
        
        menuItem = optionsMenu.add(new JMenuItem("Kolor planszy"));
        menuItem.addActionListener(listener2);
        menuItem = optionsMenu.add(new JMenuItem("Kolor pionków"));
        menuItem.addActionListener(listener2);
        menuItem = optionsMenu.add(new JMenuItem("Kolor pól"));
        menuItem.addActionListener(listener2);
        menuItem = optionsMenu.add(new JMenuItem("Kolor aktywnego"));
        menuItem.addActionListener(listener2);

        optionsMenu.setEnabled(false);
    }
    
    private void addHelpMenu(JMenuBar menuBar, JMenuItem menuItem){
        JMenu helpMenu = new JMenu("Pomoc");
        HelpListener helpListener = new HelpListener();
        menuBar.add(helpMenu);
        
        menuItem = helpMenu.add(new JMenuItem("O grze"));
        menuItem.addActionListener(helpListener);
        menuItem = helpMenu.add(new JMenuItem("O aplikacji"));
        menuItem.addActionListener(helpListener);
    }
    
    private void showDialogWindow(String string){
        JDialog dialog = new JDialog(this, "O Grze... ", true);
        dialog.setSize(300, 300);
        
        JTextPane field = new JTextPane();
        field.setText(string);
        field.setEditable(false);
        dialog.add(field);
        dialog.show();
    }
    
    private void createColorFrame(String string){
        Color initialColor = Color.red;
        Color newColor = JColorChooser.showDialog(null, "Dialog Title", initialColor);
        switch (string) {
            case "Kolor planszy":
                Samotnik.manager.setColorBoard(newColor);
                break;
            case "Kolor pionków":
                Samotnik.manager.setColorCards(newColor);
                break;
            case "Kolor pól":
                Samotnik.manager.setColorPlaces(newColor);
                break;
            case "Kolor aktywnego":
                Samotnik.manager.setColorActiveCard(newColor);
                break;
        }
        
        
        
               
        
    }
   


}
