/* 
* Exercise: Java 2 exercise 16.31
* Lauren Smith 
* 2/3/21 
* Description Makes a GUI Connect 4 game 
*/

package exercise1631;

import java.util.Arrays;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Exercise1631 extends Application {
    //declares 2d array for all the connect4 pieces
    ConnectCircle[][] pieces=new ConnectCircle[7][6];
    //array to keep track of winning pieces 
    ConnectCircle[] winners=new ConnectCircle[4]; 
    //string to keep track of who turns it is
    Color col=Color.RED; 
    //displays who's turn it is to the players
    Label status=new Label("Red's turn");
    //makes a gridPane for the game board 
    GridPane game=new GridPane();
    @Override
    public void start(Stage primaryStage) {
        //makes a BorderPane to hold and organize all pieces in pane 
        BorderPane main=new BorderPane(); 
        //makes an HBox to hold player status 
        HBox hbox=new HBox(); 
        //aligns hbox children to the center of the box
        hbox.setAlignment(Pos.CENTER);
        //adds status label to the box
        hbox.getChildren().add(status);
        //sets hbox to the top of the pane
        main.setTop(hbox); 
        //makes the game the center of the main pane 
        main.setCenter(game); 
        //sets padding for gridPane so it's actually in the center
        game.setPadding(new Insets(60, 60, 60, 40));
        //sets game vGap and hGap so pieces are spaced out
        game.setHgap(5); 
        game.setVgap(5);
        //loads in game pieces by row 
        for(int i=0; i<pieces.length; i++) 
        {
            for(int a=0; a<pieces[i].length; a++) 
            {
                final int rows=i; 
                final int cols=a; 
                //makes connectCircle pieces and adds it to pieces array
                ConnectCircle c=new ConnectCircle();
                pieces[i][a]=c; 
                //adds it to the grid at the same location as pieces array
                game.add(c,i,a); 
            }
        }
        //makes game pane background blue to look like the game 
        game.setStyle("-fx-background-color: blue;");
        Scene scene = new Scene(main, 400, 400);
        primaryStage.setTitle("Connect 4");
        primaryStage.setScene(scene);
        primaryStage.show();
    } 
    
   
   
    
    
    public void flashWin() 
    {
        //makes fadeTransitions and sets it to winners array for each index of the array
        FadeTransition ft0 = new FadeTransition(Duration.millis(3000), winners[0]); 
        ft0.setFromValue(1.0);
        ft0.setToValue(0.3);
        ft0.setCycleCount(4); 
        //does this for each index 
        FadeTransition ft1 = new FadeTransition(Duration.millis(3000), winners[1]); 
        ft1.setFromValue(1.0);
        ft1.setToValue(0.3);
        ft1.setCycleCount(4); 
        FadeTransition ft2 = new FadeTransition(Duration.millis(3000), winners[2]); 
        ft2.setFromValue(1.0);
        ft2.setToValue(0.3);
        ft2.setCycleCount(4);
        FadeTransition ft3 = new FadeTransition(Duration.millis(3000), winners[3]); 
        ft3.setFromValue(1.0);
        ft3.setToValue(0.3);
        ft3.setCycleCount(4);
        ft3.play(); 
        ft1.play(); 
        ft2.play(); 
        ft0.play(); 
    }
    
//method that returns a boolean showing if the board is full 
    public boolean full() 
    {
        //loops through every piece of the board by looping through pieces array
        for(int i=0; i<pieces.length; i++) 
        {
            for(int a=0; a<pieces[i].length; a++) 
            {
                //if any pieces return their boolean to show if they are full return false 
                if(!(pieces[i][a].getFilled())) 
                    //board isn't full, return false 
                {
                    return false; 
                }
            }
            //if it doesn't reach the if statement the board is full and returns true
        } 
        status.setText("Draw, board is full"); 
        return true; 
    }
    
    //checks for a horizontal win 
    public boolean HWin() 
    {
        //empties the array of winning pieces
        Arrays.fill(winners, null);
        //declares variable that counts pieces in a row 
        int count=0; 
        for(int rowNum=0; rowNum<6; rowNum++) 
        { 
            //resets count each time it starts to check a new row 
            count=0; 
            for(int colNum=0; colNum<pieces.length; colNum++) 
            {
                //loops through the row. If pieces color matches color of player 
                //turn that just played passed as a parameter
               if(pieces[colNum][rowNum].getFill()==col) 
               {
                   //add piece that matches to winners array
                   winners[count]=pieces[colNum][rowNum]; 
                   //increment count
                   count++; 
               } 
               //if it doesn't pieces aren't in a row 
               else
               {
                   //reset count to 0 and reset winning pieces array
                   count=0; 
                   Arrays.fill(winners,null); 
               }
               //if count is equal to 4 there's a connect 4 and return true 
               //because that player that just played has won. also flashes winning pieces 
               if(count==4) 
               {
                   flashWin(); 
                status.setText("Winner!"); 
                   return true; 
               }
            } 
        }
        //if it doesn't return true no one has won so return false 
        return false; 
    }
    
    //same system as above, except loops through collumns to look for a vertical win
    public boolean VWin() 
    {
        Arrays.fill(winners,null); 
        int count=0; 
        for(int colNum=0; colNum<7; colNum++) 
        { 
            Arrays.fill(winners,null); 
            count=0; 
            for(int rowNum=0; rowNum<pieces[colNum].length; rowNum++) 
            {
               if(pieces[colNum][rowNum].getFill()==col) 
               {
                   winners[count]=pieces[colNum][rowNum];
                   count++; 
               } 
               else
               {
                   count=0; 
                   Arrays.fill(winners,null);
               }
               if(count==4) 
               {
                    flashWin(); 
                    status.setText("Winner!"); 
                   return true; 
               }
            } 
        }
        
        return false; 
    }
    


//same system as others but searches for diagonal wins 
     public boolean DWin() 
    {
        Arrays.fill(winners,null); 
        int count=0; 
        //starts in top left corner of board 
        for(int i=0; i<=5; i++) 
        {
            if(pieces[i][i].getFill()==col) 
            {
                winners[count]=pieces[i][i]; 
                count++; 
            } 
            else 
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            } 
            
        }
        
        Arrays.fill(winners,null); 
        count=0;
        //moves down one row on same side 
        for(int i=0; i<=4; i++) 
        {
            if(pieces[i][i+1].getFill()==col) 
            {
                winners[count]=pieces[i][i+1]; 
                count++; 
            }
            else
            {
                Arrays.fill(winners, null);
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        }
        
        Arrays.fill(winners,null); 
        count=0; 
        //moves down two rows from that corner 
        for(int i=0; i<=3; i++) 
        {
            if(pieces[i][i+2].getFill()==col) 
            {
                winners[count]=pieces[i][i+2]; 
                count++; 
            }
            else
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        }
        //loops for win in bottom left corner 
        Arrays.fill(winners,null); 
        count=0; 
        for(int row=0, c=5; row<=5; row++, c--) 
        {
            if(pieces[row][c].getFill()==col) 
            {
                winners[count]=pieces[row][c]; 
                count++; 
            } 
            else
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        } 
        //moves up one row from that corner 
        Arrays.fill(winners,null); 
        count=0; 
        for(int row=0, c=4; row<=4; row++,c--) 
        {
            if(pieces[row][c].getFill()==col) 
            {  
                winners[count]=pieces[row][c]; 
                count++; 
            } 
            else
            { 
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                return true; 
            }
        } 
        //moves up two rows from that corner 
        Arrays.fill(winners,null); 
        count=0; 
        for(int row=0,c=3; row<=3; row++,c--) 
        {
            if(pieces[row][c].getFill()==col) 
            {
                winners[count]=pieces[row][c]; 
                count++; 
            } 
            else
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        }
        //looks at bottom right corner 
        Arrays.fill(winners,null); 
        count=0; 
        for(int row=6; row>0; row--) 
        {
            if(pieces[row][row-1].getFill()==col) 
            {
                winners[count]=pieces[row][row-1]; 
                count++; 
            } 
            else
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        }
        //moves up one row from that corner 
        count=0; 
        for(int row=6, c=4; row>1; row--,c--) 
        {
            if(pieces[row][row-1].getFill()==col) 
            {
                count++; 
            } 
            else
            {
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        } 
        //moves up two rows from that corner 
        count=0; 
        for(int row=6, c=3; row>2; row--,c--) 
        {
            if(pieces[row][row-1].getFill()==col) 
            {
                count++; 
            } 
            else
            {
                count=0; 
            } 
            if(count==4) 
            {
                return true; 
            }
        }
        //looks at top right coner 
        Arrays.fill(winners,null); 
        count=0; 
        for(int row=6,c=0; row>0; row--,c++) 
        {
            if(pieces[row][c].getFill()==col) 
            {
                winners[count]=pieces[row][c]; 
                count++; 
            } 
            else
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        } 
        //moves down one row from that corner 
        Arrays.fill(winners,null); 
        count=0; 
        for(int row=6,c=1; row>1; row--,c++) 
        {
            if(pieces[row][c].getFill()==col) 
            {
                winners[count]=pieces[row][c]; 
                count++; 
            } 
            else
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        }
        //moves down two rows from that corner 
        Arrays.fill(winners,null); 
        count=0; 
        for(int row=6,c=2; row>2; row--,c++)  
        {
            if(pieces[row][c].getFill()==col) 
            {
                winners[count]=pieces[row][c]; 
                count++; 
            } 
            else
            {
                Arrays.fill(winners,null); 
                count=0; 
            } 
            if(count==4) 
            {
                flashWin(); 
                status.setText("Winner!"); 
                return true; 
            }
        }
        //if none of these return true means there's no diagonal win so return false 
        return false; 
    } 

//method that combines all the differents ways to win method so it's easy to check
    public boolean win() 
    {
        if(HWin()||DWin()||VWin()) 
        {
            return true; 
        } 
        else 
        {
            return false; 
        }
    }     

     
   

    
    public static void main(String[] args) {
        launch(args);
    } 
   
    
    
    
    
    
    //inner class used for connect4 pieces 
    class ConnectCircle extends Circle {
     //boolean to see if circle has been filled with a piece yet 
    private boolean isFilled=false; 
    public ConnectCircle() 
    {
        //makes circle with 20 radius
        super(20); 
        //sets filled to false as it's not filled by a piece 
        isFilled=false; 
        //sets stroke to black and fill to white as it's an empty game part 
        setStroke(javafx.scene.paint.Color.BLACK); 
        setFill(javafx.scene.paint.Color.WHITE); 
        //sets the event handler when clicked to handleMouseClick
        this.setOnMouseClicked(e->handleMouseClick()); 
    }
    
    //getters and setters for filled 
    public void setFilled(boolean filled) 
    {
        this.isFilled=filled; 
    } 
    
    
    
    public boolean getFilled() 
    {
        return this.isFilled; 
    }
    
    //handler for mouse click
    private void handleMouseClick() 
    {
        //sets column and row to circle that has been clicked 
       int column=game.getRowIndex(this); 
       int row=game.getColumnIndex(this);   
       //checks to see if piece can go there if it last row, or can be stacked on a piece
       if(column==5||pieces[row][column+1].getFilled()) 
       {
           //if it can, sets circles fill to that players color
         pieces[row][column].setFill(col);
         //sets circles filled variable for true 
         pieces[row][column].setFilled(true); 
         //checks if board is full or if a player has won
         if(!full()&&!win()) 
         {
             //if they haven't it's the next player's turn if red just played
            if(col==Color.RED) 
            {
                //set fill color to yellow for next turn
                col=Color.YELLOW; 
                //update status bar on top of screen to Yellow's turn
                status.setText("Yellow's turn"); 
            } 
            //if it wasn't just red's turn, yellow just went 
            else
            {
                //set fill color to red for next turn
                col=Color.RED; 
                //set status bar to say red's turn 
                status.setText("Red's turn"); 
            }  
         }
         
       }
       
       
        
    }
      

    
    
}

}
