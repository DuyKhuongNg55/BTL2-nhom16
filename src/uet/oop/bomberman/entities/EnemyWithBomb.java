package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Oneal.HorizontalComparator;
import uet.oop.bomberman.entities.Oneal.VerticalComparator;
import uet.oop.bomberman.graphics.Sprite;


//Minvo
//public static Sprite minvo_left1 = new Sprite(DEFAULT_SIZE, 8, 5, SpriteSheet.tiles, 16, 16);
//public static Sprite minvo_left2 = new Sprite(DEFAULT_SIZE, 8, 6, SpriteSheet.tiles, 16, 16);
//public static Sprite minvo_left3 = new Sprite(DEFAULT_SIZE, 8, 7, SpriteSheet.tiles, 16, 16);
//public static Sprite minvo_right1 = new Sprite(DEFAULT_SIZE, 9, 5, SpriteSheet.tiles, 16, 16);
//public static Sprite minvo_right2 = new Sprite(DEFAULT_SIZE, 9, 6, SpriteSheet.tiles, 16, 16);
//public static Sprite minvo_right3 = new Sprite(DEFAULT_SIZE, 9, 7, SpriteSheet.tiles, 16, 16);
//public static Sprite minvo_dead = new Sprite(DEFAULT_SIZE, 8, 8, SpriteSheet.tiles, 16, 16);


public class EnemyWithBomb extends Entity {
    private int IndexXOfBomb = 0;
    private int IndexYOfBomb = 0;

    protected final int MAX_ANIMATE = 7500;
    protected int _animate = 0;
    protected boolean _destroyed = false;
    private int _timeToDisapear = 20;
    private String preString = "";
    private int xTarget = 0;
    private int yTarget = 0;
    private static boolean[][] matrix = new boolean[13][31];
    private static List<Vert> vertList = new ArrayList<>();
    private boolean again = true;
    private boolean notLeft = false;
    private boolean notRight = false;
    private boolean notUp = false;
    private boolean notDown = false;
//    private  int BombCount = 1;

    public static boolean[][] getMatrix() {
        return matrix;
    }

    public static void setMatrix(boolean[][] matrix) {
        EnemyWithBomb.matrix = matrix;
    }

    public void PlaceBomb() {
        if (BombermanGame.getBombCountOfEnemy() > 0) {
            BombermanGame.getBombListOfEnemy().add(
                    new Bomb(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE,
                            Sprite.bomb.getFxImage(), BombermanGame.getBombRadius()));
            BombermanGame.BombCountOfEnemy--;
        }

    }




    public boolean is_destroyed() {
        return _destroyed;
    }

    private boolean ExposeToBom = false;

    public void set_destroyed(boolean _destroyed) {
        this._destroyed = _destroyed;
    }


    private int speed = 2;

    public int get_timeToDisapear() {
        return _timeToDisapear;
    }


    public EnemyWithBomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    protected void animate() {
        if (_animate < MAX_ANIMATE) _animate++;
        else _animate = 0;
    }

    @Override
    public void update() {
//        if((this.getX() / Sprite.SCALED_SIZE + this.getY() /  Sprite.SCALED_SIZE))
//        PlaceBomb();
        createVert();
        String curString = preString;
        for (int i = 0; i < vertList.size(); i++) {
            if (vertList.get(i).getX() * Sprite.SCALED_SIZE == x

                    && vertList.get(i).getY() * Sprite.SCALED_SIZE == y
                    && vertList.get(i).getName().charAt(0) != 'S') {

                again = true;
                break;
            }
        }
        if (!again) {
            switch (curString) {
                case "Up":
                    again = !moveUp();
                    break;
                case "Left":
                    again = !moveLeft();
                    break;
                case "Right":
                    again = !moveRight();
                    break;
                case "Down":
                    again = !moveDown();
                    break;
            }
        }
        while (again) {
            int ranNum = ThreadLocalRandom.current().nextInt(0, 4);
            if (ranNum == 0) {
                curString = "Up";
            } else if (ranNum == 1) {
                curString = "Left";
            } else if (ranNum == 2) {
                curString = "Right";
            } else if (ranNum == 3) {
                curString = "Down";
            }
            if (preString != null) {
                if (!preString.equals(curString)) {
                    times = 0;
                }
            }
            preString = curString;
            switch (curString) {
                case "Up":
                    again = !moveUp();
                    if (again) {
                        notUp = true;
                    } else {
                        notUp = false;
                        notLeft = false;
                        notRight = false;
                        notDown = false;
                    }
                    break;
                case "Left":
                    again = !moveLeft();
                    if (again) {
                        notLeft = true;
                    } else {
                        notUp = false;
                        notLeft = false;
                        notRight = false;
                        notDown = false;
                    }
                    break;
                case "Right":
                    again = !moveRight();
                    if (again) {
                        notRight = true;
                    } else {
                        notUp = false;
                        notLeft = false;
                        notRight = false;
                        notDown = false;
                    }
                    break;
                case "Down":
                    again = !moveDown();
                    if (again) {
                        notDown = true;
                    } else {
                        notUp = false;
                        notLeft = false;
                        notRight = false;
                        notDown = false;
                    }
                    break;
            }
            if (notDown && notLeft && notUp && notRight) {
                again = false;
            }
        }

        if (!_alive) {
            for (int i = 0; i < BombermanGame.getBombList().size(); i++) {
                if (BombermanGame.getBombList().get(i).is_exploded()) {
                    Flame[] fl = BombermanGame.getBombList().get(i).get_flames();
                    for (int j = 0; j < fl.length; j++) {
                        FlameSegment[] fls = fl[j].get_flameSegments();
                        for (int k = 0; k < fls.length; k++) {
                            //fls[k].set_animate(BombermanGame.getBombList().get(i).get_animate());
                            if (fls[k].getX() / Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE && fls[k].getY() / Sprite.SCALED_SIZE ==
                                    this.getY() / Sprite.SCALED_SIZE) {
                                ExposeToBom = true;
                            }
                        }
                    }
                }
            }
        }
        if (ExposeToBom) {
            if (_timeToDisapear != 0) {
                speed = 0;
                animate();
                _timeToDisapear--;
                this.img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60).getFxImage();
            }
            else BombermanGame.getEntitiesRemove().add(this);
        }
    }

    public boolean moveRight() {
        times++;
        if (times % 12 >= 0 && times % 12 <= 3) {
            img = Sprite.minvo_right1.getFxImage();
        } else if (times % 12 >= 4 && times % 12 <= 7) {
            img = Sprite.minvo_right2.getFxImage();
        } else {
            img = Sprite.minvo_right3.getFxImage();
        }
        if (!matrix[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 1]) {
            return false;
        }

//        for(int i = 0 ; i < BombermanGame.getBombListOfEnemy().size();i++){
//            if((this.getX() + speed ) / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getX() / Sprite.SCALED_SIZE &&
//                    this.getY()  / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getY() / Sprite.SCALED_SIZE){
//                if(!BombermanGame.getBombListOfEnemy().get(i)._allowedToPassThru) return false;
//            }
//        }

        x += speed;
        return true;
    }

    public boolean moveLeft() {
        times++;
        if (times % 12 >= 0 && times % 12 <= 3) {
            img = Sprite.minvo_left1.getFxImage();
        } else if (times % 12 >= 4 && times % 12 <= 7) {
            img = Sprite.minvo_left2.getFxImage();
        } else {
            img = Sprite.minvo_left3.getFxImage();
        }
        if (x % Sprite.SCALED_SIZE == 0 && !matrix[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1]) {
            return false;
        } else if (x % Sprite.SCALED_SIZE != 0 && !matrix[y / Sprite.SCALED_SIZE][x
                / Sprite.SCALED_SIZE]) {

            return false;
        }
//        for(int i = 0 ; i < BombermanGame.getBombListOfEnemy().size();i++){
//            if((this.getX() + speed ) / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getX() / Sprite.SCALED_SIZE &&
//                    this.getY()  / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getY() / Sprite.SCALED_SIZE){
//                if(!BombermanGame.getBombListOfEnemy().get(i)._allowedToPassThru) return false;
//            }
//        }
        x -= speed;
        return true;
    }

    public boolean moveUp() {
        if (y % Sprite.SCALED_SIZE == 0 && !matrix[y / Sprite.SCALED_SIZE - 1][x

                / Sprite.SCALED_SIZE]) {
            return false;
        } else if (y % Sprite.SCALED_SIZE != 0 && !matrix[y / Sprite.SCALED_SIZE][x
                / Sprite.SCALED_SIZE]) {

            return false;
        }
//        for(int i = 0 ; i < BombermanGame.getBombListOfEnemy().size();i++){
//            if((this.getX() + speed ) / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getX() / Sprite.SCALED_SIZE &&
//                    this.getY()  / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getY() / Sprite.SCALED_SIZE){
//                if(!BombermanGame.getBombListOfEnemy().get(i)._allowedToPassThru) return false;
//            }
//        }
        y -= speed;
        return true;
    }

    public boolean moveDown() {
        if (!matrix[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE]) {
            return false;
        }
        y += speed;
//        for(int i = 0 ; i < BombermanGame.getBombListOfEnemy().size();i++){
//            if((this.getX() + speed ) / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getX() / Sprite.SCALED_SIZE &&
//                    this.getY()  / Sprite.SCALED_SIZE == BombermanGame.getBombListOfEnemy().get(i).getY() / Sprite.SCALED_SIZE){
//                if(!BombermanGame.getBombListOfEnemy().get(i)._allowedToPassThru) return false;
//            }
//        }
        return true;
    }

    public void createMatrix() {
        for (int i = 0; i < BombermanGame.getStillObjects().size(); i++) {

            if (BombermanGame.getStillObjects().get(i) instanceof Wall || BombermanGame.getStillObjects().get(i) instanceof Brick) {
                matrix[BombermanGame.getStillObjects().get(i).getY() / Sprite.SCALED_SIZE][
                        BombermanGame.getStillObjects().get(i).getX() / Sprite.SCALED_SIZE] = false;
            } else {
                matrix[BombermanGame.getStillObjects().get(i).getY() / Sprite.SCALED_SIZE][
                        BombermanGame.getStillObjects().get(i).getX() / Sprite.SCALED_SIZE] = true;
            }
        }
        for (int h = 0; h < BombermanGame.getBombList().size(); h++) {
            matrix[BombermanGame.getBombList().get(h).getY() / Sprite.SCALED_SIZE][
                    BombermanGame.getBombList().get(h).getX() / Sprite.SCALED_SIZE] = false;
        }
        for (int h = 0; h < BombermanGame.getBombListOfEnemy().size(); h++) {
            IndexXOfBomb = BombermanGame.getBombListOfEnemy().get(h).getX() / Sprite.SCALED_SIZE;
            IndexYOfBomb = BombermanGame.getBombListOfEnemy().get(h).getY() / Sprite.SCALED_SIZE;
            //System.out.println(" index " + IndexXOfBomb + " " + IndexYOfBomb);
            if((this.getX() / Sprite.SCALED_SIZE) != IndexXOfBomb || (this.getY()/ Sprite.SCALED_SIZE) != IndexYOfBomb){
                matrix[IndexYOfBomb][IndexXOfBomb] = false;
                //System.out.println("Chay den cho false  " + IndexXOfBomb + " " + IndexYOfBomb);
            }
        }


//        if(BombermanGame.getBombListOfEnemy().isEmpty()){
//            //System.out.println("chay den day");
//            matrix[IndexYOfBomb][IndexXOfBomb ] = true;
//            System.out.println(IndexXOfBomb + " " + IndexYOfBomb + " 2");
//        }

        for (int i = 0; i < BombermanGame.getEntities().size(); i++) {
            if (BombermanGame.getEntities().get(i) instanceof Oneal ||BombermanGame.getEntities().get(i) instanceof Balloom ||
                    BombermanGame.getEntities().get(i) instanceof EnemyWithTwoLife ||
                    (BombermanGame.getEntities().get(i) instanceof EnemyWithBomb
                            && (BombermanGame.getEntities().get(i).getX() != x
                            || BombermanGame.getEntities().get(i).getY() != y))) {
                matrix[BombermanGame.getEntities().get(i).getY() / Sprite.SCALED_SIZE][
                        BombermanGame.getEntities().get(i).getX() / Sprite.SCALED_SIZE] = false;
                if (BombermanGame.getEntities().get(i).getX() % Sprite.SCALED_SIZE != 0) {
                    matrix[BombermanGame.getEntities().get(i).getY() / Sprite.SCALED_SIZE][
                            BombermanGame.getEntities().get(i).getX() / Sprite.SCALED_SIZE + 1] = false;
                }
                if (BombermanGame.getEntities().get(i).getY() % Sprite.SCALED_SIZE != 0) {
                    matrix[BombermanGame.getEntities().get(i).getY() / Sprite.SCALED_SIZE + 1][
                            BombermanGame.getEntities().get(i).getX() / Sprite.SCALED_SIZE] = false;

                }
            }
        }
    }

    public void createVert() {
        vertList.clear();
        createMatrix();
        for (int i = 1; i < 30; i++) {
            for (int j = 1; j < 12; j++) {
                if (matrix[j][i]) {
                    int timesTemp = 0;
                    if (!matrix[j - 1][i]) {
                        timesTemp++;
                    }
                    if (!matrix[j][i - 1]) {
                        timesTemp++;
                    }
                    if (!matrix[j][i + 1]) {
                        timesTemp++;
                    }
                    if (!matrix[j + 1][i]) {
                        timesTemp++;
                    }
                    boolean isBalloom = false;
                    if (x / Sprite.SCALED_SIZE == i && y / Sprite.SCALED_SIZE == j) {
                        isBalloom = true;
                    }
                    if (isBalloom || timesTemp == 1 || timesTemp == 0 || (

                            timesTemp == 2 && !(matrix[j][i - 1] && matrix[j][i + 1]) && !(matrix[j - 1][i]
                                    && matrix[j + 1][i]))) {
                        String nameVert = i + " " + j;
                        if (!(timesTemp == 1 || timesTemp == 0 || (timesTemp == 2 && !(matrix[j][i - 1]
                                && matrix[j][i + 1]) && !(matrix[j - 1][i] && matrix[j + 1][i])))) {
                            nameVert = "S" + nameVert;
                        }
                        vertList.add(new Vert(nameVert, i, j));
                    }
                }
            }
        }
    }

    static class HorizontalComparator implements Comparator<Vert> {

        @Override
        public int compare(Vert o1, Vert o2) {
            if (o1.getY() < o2.getY()) {
                return -1;
            } else if (o1.getY() > o2.getY()) {
                return 1;
            } else {
                return Integer.compare(o1.getX(), o2.getX());
            }
        }
    }

    static class VerticalComparator implements Comparator<Vert> {

        @Override
        public int compare(Vert o1, Vert o2) {
            if (o1.getX() < o2.getX()) {
                return -1;
            } else if (o1.getX() > o2.getX()) {
                return 1;
            } else {
                return Integer.compare(o1.getY(), o2.getY());
            }
        }
    }

    public static void createEdge() {
        Oneal.HorizontalComparator horizontalComparator = new Oneal.HorizontalComparator();
        vertList.sort(horizontalComparator);
        for (int i = 0; i < vertList.size() - 1; i++) {
            if (vertList.get(i).getY() == vertList.get(i + 1).getY()) {
                int length = 1;
                boolean isEdge = true;
                for (int j = vertList.get(i).getX() + 1; j < vertList.get(i + 1).getX(); j++) {
                    if (matrix[vertList.get(i).getY()][j]) {
                        length++;
                    } else {
                        isEdge = false;
                    }
                }
                if (isEdge) {
                    vertList.get(i).addNeighbour(new Edge(length, vertList.get(i), vertList.get(i + 1)));
                    vertList.get(i + 1).addNeighbour(new Edge(length, vertList.get(i + 1), vertList.get(i)));
                }
            }
        }
        //canh doc
        Oneal.VerticalComparator verticalComparator = new Oneal.VerticalComparator();
        vertList.sort(verticalComparator);
        for (int i = 0; i < vertList.size() - 1; i++) {
            if (vertList.get(i).getX() == vertList.get(i + 1).getX()) {
                int length = 1;
                boolean isEdge = true;
                for (int j = vertList.get(i).getY() + 1; j < vertList.get(i + 1).getY(); j++) {
                    if (matrix[j][vertList.get(i).getX()]) {
                        length++;
                    } else {
                        isEdge = false;
                    }
                }
                if (isEdge) {
                    vertList.get(i).addNeighbour(new Edge(length, vertList.get(i), vertList.get(i + 1)));
                    vertList.get(i + 1).addNeighbour(new Edge(length, vertList.get(i + 1), vertList.get(i)));
                }
            }
        }
    }
}
