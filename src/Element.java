public record Element(int x, int y, Type type) {

    enum Type {

        APPLE("\u001B[31mO\u001B[0m"),
        SNAKE_HEAD("\u001B[32mU\u001B[0m"),
        SNAKE("\u001B[32mV\u001B[0m"),
        WALL("#");

        String graphic;

        Type(String graphic){
            this.graphic = graphic;
        }

        public String getGraphic(){
            return this.graphic;
        }

    }
    
}