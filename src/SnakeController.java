import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeController extends KeyAdapter {
    
    @Override
    public void keyPressed(KeyEvent e){

        Snake.Direction moveDirection = switch(e.getKeyChar()){

            case 'w' -> Snake.Direction.UP;
            case 's' -> Snake.Direction.DOWN;
            case 'a' -> Snake.Direction.LEFT;
            case 'd' -> Snake.Direction.RIGHT;
            default -> null;

        };

        if(moveDirection != null){
            Game.getInstance().onSnakeAttemptMove(moveDirection);
        }

    }    

}
