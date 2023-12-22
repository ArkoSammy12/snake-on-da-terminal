import java.lang.annotation.ElementType;
import java.util.List;

public class Snake {

    private int[] pos = new int[2];
    private Direction direction;
    private Snake next;
    private Snake prev;

    public Snake(int[] pos, Direction direction, Snake next, Snake prev){
        this.pos = pos;
        this.direction = direction;
        this.next = next;
        this.prev = prev;
    }

    public void addTail(Snake snakePart){
        if(snakePart.next == null){
            snakePart.next = new Snake(snakePart.direction.getOpposite().addPosition(snakePart.pos), snakePart.direction, null, snakePart);
            return;

        }
        addTail(snakePart.next);
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public int[] getPosition(){
        return this.pos;
    }

    public List<Element> getSnakeNodes(List<Element> snakeNodes){
        snakeNodes.add(new Element(this.pos[0], this.pos[1], Element.Type.SNAKE_HEAD));
        if(this.next != null){
            return this.next.snakeNodesAfterHead(snakeNodes);
        }
        return snakeNodes;

    }

    private List<Element> snakeNodesAfterHead(List<Element> snakeNodes){
        snakeNodes.add(new Element(this.pos[0], this.pos[1], Element.Type.SNAKE));
        if(this.next != null){
            this.next.snakeNodesAfterHead(snakeNodes);
        }
        return snakeNodes;
    }

    public void updatePositions(Game game) {
        int[] newPos = this.direction.addPosition(pos);
        this.pos = newPos;
        this.wrapPositionIfNeeded();
        if (this.next != null) {
            this.next.updatePositions(game);
        }
    }

    private void wrapPositionIfNeeded(){
        int x = this.pos[0];
        int y = this.pos[1];
        if(x < 0){
            x = Screen.MAX_X - 1;
        } else if (x >= Screen.MAX_X){
            x = 0;
        }
        if(y < 0){
            y = Screen.MAX_Y - 1;
        } else if (y >= Screen.MAX_Y){
            y = 0;
        }
        this.pos = new int[]{x, y};
    }
    
    public void updateDirections() {
        Snake tail = this.getTail();
        tail.updateDirection();
    }

    private void updateDirection(){
        Snake prev = this.prev;
        if(prev == null){
            return;
        }
        this.setDirection(prev.direction);
        prev.updateDirection();
    }

    public CollisionType checkCollision(Game game){
        int[] pos = this.pos;
        Element elementAtPos = game.getScreen().getElementAtIgnoringSnakeHead(pos[0], pos[1]);
        if(elementAtPos == null){
            return CollisionType.NONE;
        }
        return switch(elementAtPos.type()){
            case APPLE -> CollisionType.APPLE;
            case SNAKE -> CollisionType.WALL;
            case WALL -> CollisionType.WALL;
            case SNAKE_HEAD -> CollisionType.NONE;
        };
    }
    
    public Snake getTail() {
        if (this.next == null) {
            return this;
        } else {
            return this.next.getTail();
        }
    }

    enum Direction {

        UP(new int[]{0, 1}),
        RIGHT(new int[]{1, 0}),
        DOWN(new int[]{0, -1}),
        LEFT(new int[]{-1, 0});

        int[] vec = new int[2];

        Direction(int[] vec){

            this.vec = vec;

        }

        public int[] addPosition(int[] pos){
            return new int[]{pos[0] + this.vec[0], pos[1] + this.vec[1]};
        }

        public Direction getOpposite(){
            return switch(this){

                case UP -> DOWN;
                case DOWN -> UP;
                case RIGHT -> LEFT;
                case LEFT -> RIGHT;

            };
        }

    }

    enum CollisionType {
        NONE,
        APPLE,
        WALL;
    }

}
