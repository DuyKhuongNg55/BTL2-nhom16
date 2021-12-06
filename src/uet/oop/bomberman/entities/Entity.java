package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected boolean _removed = false;
    protected Sprite _sprite;
    protected Image img;
    protected int times;
    protected int _direction = -1;
    protected boolean _alive = true;
//    private boolean REMOVEFIXPROTECTTED = false ;
    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        times = 0;
    }

//    public abstract boolean isREMOVEFIXPROTECTTED();
//
//
//
//    public abstract void setREMOVEFIXPROTECTTED(boolean REMOVEFIXPROTECTTED);

    public abstract void kill();

    public int get_direction() {
        return _direction;
    }

    public void set_direction(int _direction) {
        this._direction = _direction;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y + Sprite.SCALED_SIZE * 2);
    }
    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public Image getImg() {
        return this.img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    public void remove() {
        _removed = true;
    }

    public boolean isRemoved() {
        return _removed;
    }

}
