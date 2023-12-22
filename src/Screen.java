import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Screen {

    private static Screen instance = null;
    public static final int MAX_X = 140;
    public static final int MAX_Y = 30;
    public static final int FRAME_DELAY = 0;
    private final String[][] matrix = new String[MAX_Y][MAX_X];
    private List<Element> elements = new ArrayList<>();

    private Screen(){
        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                matrix[j][i] = " ";
            }
        }
    }

    public static Screen getInstance(){

        if (instance == null){
            instance = new Screen();
        }

        return instance;

    }

    public Element getElementAtPosition(int x, int y){
        for(Element element : this.elements){
            if(element.x() == x && element.y() == y && element.type() != Element.Type.SNAKE_HEAD){
                return element;
            }
        }
        return null;
    }

    public void clearElements(){ 
        this.elements.clear();
    }

    public void submitElement(Element e){
        if(!elements.contains(e)){
            elements.add(e);
        }
    }

    public void submitAllElements(Collection<Element> points){
        for(Element e : points){
            if(!elements.contains(e)){
                elements.add(e);
            }
        }
    }

    public void refreshDisplay() {
        for (int i = 0; i < MAX_X; i++) {
            for (int j = 0; j < MAX_Y; j++) {
                matrix[j][i] = " ";
            }
        }
        for(Element e : this.elements){
            matrix[e.y()][e.x()] = e.type().getGraphic();
        }
    }

    public void display() throws InterruptedException {
        for(int i = MAX_Y - 1; i >= 0; i--){
            for(int j = 0; j < MAX_X; j++){
                String s = matrix[i][j];
                System.out.print(s);
            }
            System.out.println();
        }
        Thread.sleep(FRAME_DELAY);
    }

    public static void clearConsole(){

        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}