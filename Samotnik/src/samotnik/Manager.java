/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samotnik;

import java.awt.Color;

/**
 *
 * @author Marcin
 */
public class Manager {
    
    int cardsOnTable;
    int version;
    Color colorBoard;
    Color colorCards;
    Color colorPlaces;
    Color colorActiveCard;
    
    boolean working;
    
    static Cards[][] cards = new Cards[7][7];
    
    public Manager(){
        version = 1;
        cardsOnTable = 33;
        colorBoard = Color.LIGHT_GRAY;
        colorCards = Color.BLACK;
        colorPlaces = Color.GRAY;
        colorActiveCard = Color.WHITE;
        working = true;
        prepareVersion();
    }
    
    public void setVersion(int version){
        this.version = version;
    }
    public void setColorBoard(Color color){
        this.colorBoard = color;
    }
    public void setColorCards(Color color){
        this.colorCards = color;
    }
    public void setColorPlaces(Color color){
        this.colorPlaces = color;
    }
    public void setColorActiveCard(Color color){
        this.colorActiveCard = color;
    }
    public void setWorking(boolean tmp){
        this.working = tmp;
    }
    public void setCardsOnTable(int x){
        this.cardsOnTable = x;
    }
    
    private void prepareVersion(){
        if(version == 1){
            createBritishVersion();
        }else if(version == 2){
            createEuropeVersion();
        }
    }
    
    public Cards findActiveCard(){
        for(int x = 0; x < cards.length; x++){
            for(int y = 0; y < cards.length; y++){
                if(cards[x][y] != null){
                    if(cards[x][y].isActive() == true){
                        return cards[x][y];
                    }
                }
            }
        }
        return null;
    }
    
    public  void makeGoodMoveMouse(int y, int x){
        Cards tmp = findActiveCard();
        int activeX = (tmp.getX() - 70) / 50;
        int activeY = (tmp.getY() - 35) / 50;
        x = (x-70)/50;
        y = (y-35)/50;
        if(cards[y][x].isInGame() == false){
            if(y == activeY){
                if(activeX == x+2){
                    if(cards[activeY][activeX - 1].isInGame() == true){
                        cards[activeY][activeX-1].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }else if(activeX == x-2){
                    if(cards[activeY][activeX + 1].isInGame() == true){
                        cards[activeY][activeX+1].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }
            }else if(x == activeX){
                if(activeY == y+2){
                    if(cards[activeY - 1][activeX].isInGame() == true){
                        cards[activeY-1][activeX].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }else if(activeY == y-2){
                    if(cards[activeY + 1][activeX].isInGame() == true){
                        cards[activeY+1][activeX].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }
            } 
        }else{
            if(cards[y][x].isInGame() == true){
                cards[activeY][activeX].setActive(false);
                cards[y][x].setActive(true);
            }
            
        }
    }
    
    public boolean isGoodMove(int y, int x){
        if(x >= 0 && x <= 6 && y >= 0 && y <= 6){
            if(cards[y][x] != null){
                return true;
            }
        }
        return false;
    }
    
    public void makeGoodMoveKeyBoard(int activeY, int activeX, int y, int x){
        if(cards[y][x].isInGame() == false){
            if(y == activeY){
                if(activeX == x+2){
                    if(cards[activeY][activeX - 1].isInGame() == true){
                        cards[activeY][activeX-1].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }else if(activeX == x-2){
                    if(cards[activeY][activeX + 1].isInGame() == true){
                        cards[activeY][activeX+1].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }
            }else if(x == activeX){
                if(activeY == y+2){
                    if(cards[activeY - 1][activeX].isInGame() == true){
                        cards[activeY-1][activeX].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }else if(activeY == y-2){
                    if(cards[activeY + 1][activeX].isInGame() == true){
                        cards[activeY+1][activeX].setInGame(false);
                        changeActivityCards(activeY, activeX, y, x);
                    }
                }
            } 
        }else{
            if(cards[y][x].isInGame() == true){
                cards[activeY][activeX].setActive(false);
                cards[y][x].setActive(true);
            }
            
        }
    }
    
    public void changeActivityCards(int activeY, int activeX, int y, int x){
        cards[activeY][activeX].setActive(false);
        cards[activeY][activeX].setInGame(false);
        cards[y][x].setInGame(true);
        cards[y][x].setActive(true);
    }
    
    public void changeActiveCards(int activeY, int activeX, String kierunek){
        if(kierunek == "Wybierz prawy"){
            for(int x = activeX+1; x < cards.length; x++){
                if(isGoodMove(activeY, x)){
                    if(cards[activeY][x].isInGame()){
                       cards[activeY][activeX].setActive(false);
                       cards[activeY][x].setActive(true);
                       break;
                    }
                }
            }
        }else if(kierunek == "Wybierz lewy"){
            for(int x = activeX-1; x >= 0; x--){
                if(isGoodMove(activeY,x)){
                    if(cards[activeY][x].isInGame()){
                        cards[activeY][activeX].setActive(false);
                        cards[activeY][x].setActive(true);
                        break;
                    }
                }
            }
        }else if(kierunek == "Wybierz górny"){
            for(int y = activeY-1; y >= 0; y--){
                if(isGoodMove(y,activeX)){
                    if(cards[y][activeX].isInGame()){
                        cards[activeY][activeX].setActive(false);
                        cards[y][activeX].setActive(true);
                        break;
                    }
                }
            }
        }else if(kierunek == "Wybierz dolny"){
            for(int y = activeY+1; y < cards.length; y++){
                if(isGoodMove(y,activeX)){
                    if(cards[y][activeX].isInGame()){
                        cards[activeY][activeX].setActive(false);
                        cards[y][activeX].setActive(true);
                        break;
                    }
                }
            }
        }
    }
    
    private void createBritishVersion(){
        for(int y = 0; y < cards.length; y++){
            for(int x = 0; x < cards.length; x++){
                if( y == 0 || y == 1 || y == 5 || y == 6){
                    if(x >= 2 && x <= 4){
                        cards[y][x] = new Cards(50*y + 35, 50*x + 70, false, true);
                    }else{
                        cards[y][x] = null;
                    }
                }else{
                    cards[y][x] = new Cards(50*y + 35, 50*x + 70, false, true);
                }
            }
        }
        cards[3][3].setInGame(false);
        cards[3][3].setActive(false);
        cards[3][4].setActive(true);
    }
    
    private void createEuropeVersion(){
        for(int y = 0; y < cards.length; y++){
            for(int x = 0; x < cards.length; x++){
                if( y == 0 || y == 6){
                    if(x >= 2 && x <= 4){
                        cards[y][x] = new Cards(50*y + 35, 50*x + 70, false, true);
                    }else{
                        cards[y][x] = null;
                    }
                }else if(y == 1 || y == 5){
                    if(x > 0 && x < 6){
                        cards[y][x] = new Cards(50*y + 35, 50*x + 70, false, true);
                    }else if(x == 0 || x == 6){
                        cards[y][x] = null;
                    }
                }else{
                    cards[y][x] = new Cards(50*y + 35, 50*x + 70, false, true);
                }
            }
        }
        cards[3][3].setInGame(false);
        cards[3][3].setActive(false);
        cards[3][4].setActive(true);
    }
    
    public void startNewGame(){
        prepareVersion();
        working = true;
        Table.first = true;
    }
    
    public boolean isEnd(){
        for(int y = 0; y < cards.length; y++){
            for(int x = 0; x < cards.length; x++){
                if(cards[y][x] != null){
                    if(cards[y][x].isInGame() == true){
                        if(canBeat(y,x) == true){
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    private boolean canBeat(int y, int x){
        if(x+2 <= 6){
            if(cards[y][x+2] != null && cards[y][x+1] != null){
                if(cards[y][x+1].isInGame() == true){
                    if(cards[y][x+2].isInGame() == false){
                        return true;
                    }
                }
            }
        }
        if(x-2 >= 0){
            if(cards[y][x-2] != null && cards[y][x-1] != null){
                if(cards[y][x-1].isInGame() == true){
                    if(cards[y][x-2].isInGame() == false){
                        return true;
                    }
                }
            }
        }
        if(y-2 >= 0){
            if(cards[y-2][x] != null && cards[y-1][x] != null){
                if(cards[y-1][x].isInGame() == true){
                    if(cards[y-2][x].isInGame() == false){
                        return true;
                    }
                }
            }
        }
        if(y+2 <= 6){
            if(cards[y+2][x] != null && cards[y+1][x] != null){
                if(cards[y+1][x].isInGame() == true){
                    if(cards[y+2][x].isInGame() == false){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    
    
}
