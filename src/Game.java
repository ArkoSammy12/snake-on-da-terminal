import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Game {

    private static Game game;
    private final Snake SNAKE_HEAD;
    private Element apple;
    private final Random random;
    private final Screen screen;
    private int score;

    public static Game getInstance(){
        if(game == null){
            game = new Game();
        }
        return game;
    }

    public Game() {

        this.random = new Random();

        int initX = this.random.nextInt(0, Screen.MAX_X);
        int initY = this.random.nextInt(0, Screen.MAX_Y);

        this.SNAKE_HEAD = new Snake(new int[]{initX, initY}, Snake.Direction.RIGHT, null, null);
        this.screen = Screen.getInstance();

        JFrame f = new JFrame();
        f.addKeyListener(new SnakeController());
        f.setVisible(true);

        int appleX = this.random.nextInt(0, Screen.MAX_X);
        int appleY = this.random.nextInt(0, Screen.MAX_Y);

        apple = new Element(appleX, appleY, Element.Type.APPLE);

    }

    public Random getRandom(){
        return this.random;
    }

    public Screen getScreen(){
        return this.screen;
    }

    public void startLoop() throws InterruptedException {

        loop: while(true){

            this.screen.clearElements();
            this.screen.submitElement(apple);
            SNAKE_HEAD.updatePositions(this);
            SNAKE_HEAD.updateDirections();
            List<Element> snakeNodes = SNAKE_HEAD.getSnakeNodes(new ArrayList<Element>());
            this.screen.submitAllElements(snakeNodes);
            Snake.CollisionType collisionType = SNAKE_HEAD.checkCollision(this);
            switch(collisionType){
                case APPLE -> onAppleEaten();
                case WALL -> {
                    this.onGameLost();
                    break loop;}
                case NONE -> {}
            }
            this.screen.refreshDisplay();
            System.out.println("Score: " + this.score); 
            this.screen.display();
            Screen.clearConsole();
        }

    }

    public void onSnakeAttemptMove(Snake.Direction moveDirection){
        if(this.SNAKE_HEAD.getDirection().getOpposite() == moveDirection){
            return;
        }
        this.SNAKE_HEAD.setDirection(moveDirection);
    }

    private void onAppleEaten(){

        this.score++;
        this.SNAKE_HEAD.addTail(SNAKE_HEAD);

        int appleX = this.random.nextInt(0, Screen.MAX_X);
        int appleY = this.random.nextInt(0, Screen.MAX_Y);

        this.apple = new Element(appleX, appleY, Element.Type.APPLE);
    }

    private void onGameLost(){
        System.out.println("You lost!");
    }

}
