public record Element(int x, int y, Type type) {

    enum Type {

        APPLE("\u001B[31mO\u001B[0m"),
        SNAKE("\u001B[32mV\u001B[0m"),
        WALL("#");

        String graphic;

        Type(String graphic){
            this.graphic = graphic;
        }

        public String getGraphic(){
            return this.graphic;
        }

        public static Type fromGraphic(String grahpic){
            for(Type type : Type.values()){
                if(type.graphic.equals(grahpic)){
                    return type;
                }
            }
            throw new IllegalArgumentException();
        }

    }
    
}