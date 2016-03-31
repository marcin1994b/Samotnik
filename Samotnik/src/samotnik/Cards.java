/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samotnik;

/**
 *
 * @author Marcin
 */
public class Cards {
    
    boolean active;
    boolean inGame;
    
    int x;
    int y;
    
    public Cards(int y, int x, boolean active, boolean inGame){
        this.x = x;
        this.y = y;
        this.active = active;
        this.inGame = inGame;
    }
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public boolean isActive(){
        return this.active;
    }
    public boolean isInGame(){
        return this.inGame;
    }
    public void setActive(boolean tmp){
        this.active = tmp;
    }
    public void setInGame(boolean tmp){
        this.inGame = tmp;
    }
    
}
