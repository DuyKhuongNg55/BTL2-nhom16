package uet.oop.bomberman.entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.OldCode.graphics.Sprite;

public class Flame extends Entity{
    protected int _direction;
    private int _radius;
    protected int xOrigin, yOrigin;

    protected FlameSegment[] _flameSegments = new FlameSegment[0];
    //private static List<Entity> BrickEntity = new ArrayList<>();
    public int get_direction() {
        return _direction;
    }

    public void set_direction(int _direction) {
        this._direction = _direction;
    }

    public int get_radius() {
        return _radius;
    }

    public void set_radius(int _radius) {
        this._radius = _radius;
    }

    public int getxOrigin() {
        return xOrigin;
    }

    public void setxOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public int getyOrigin() {
        return yOrigin;
    }

    public void setyOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    public FlameSegment[] get_flameSegments() {
        return _flameSegments;
    }

    public void set_flameSegments(FlameSegment[] _flameSegments) {
        this._flameSegments = _flameSegments;
    }

    /**
     *
     * @param x hoành độ bắt đầu của Flame
     * @param y tung độ bắt đầu của Flame
     * @param direction là hướng của Flame
     * @param radius độ dài cực đại của Flame
     */
    public Flame(int x, int y, int direction, int radius, Image img) {
        super(x,y,img);
        _direction = direction;
        _radius = radius;
        createFlameSegments();
    }

    /**
     * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
     */
    private void createFlameSegments() {
        /**
         * tính toán độ dài Flame, tương ứng với số lượng segment
         */
        _flameSegments = new FlameSegment[calculatePermitedDistance()];

        /**
         * biến last dùng để đánh dấu cho segment cuối cùng
         */
        boolean last;

        // TODO: tạo các segment dưới đây
        int x = this.x /Sprite.SCALED_SIZE;
        int y = this.y / Sprite.SCALED_SIZE;
        for (int i =0; i < _flameSegments.length;i++)
        {
            if (i == _flameSegments.length-1) last = true;
            else last = false;

            switch (_direction)
            {
                case 0: y--; break;
                case 1: x++; break;
                case 2: y++; break;
                case 3: x--; break;
            }

            _flameSegments[i]= new FlameSegment(x,y, Sprite.explosion_horizontal.getFxImage(), _direction,last);
        }
    }

    /**
     * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
     * @return
     */
    private int calculatePermitedDistance() {
        // TODO: thực hiện tính toán độ dài của Flame
        int radius = 0;
        int x = this.x;
        int y = this.y;

        while (radius< _radius)
        {
            if (_direction==0) y-= Sprite.SCALED_SIZE;
            if (_direction==1) x+= Sprite.SCALED_SIZE;
            if (_direction==2) y+= Sprite.SCALED_SIZE;
            if (_direction==3) x-= Sprite.SCALED_SIZE;

            Entity a = null;
            for (int i = 0; i < BombermanGame.getEntities().size(); i++){
                if(BombermanGame.getEntities().get(i).getX()/ Sprite.SCALED_SIZE == x / Sprite.SCALED_SIZE &&
                        BombermanGame.getEntities().get(i).getY()/ Sprite.SCALED_SIZE == y / Sprite.SCALED_SIZE){
                    a = BombermanGame.getEntities().get(i);
                }
            }
            for (int i = 0; i < BombermanGame.getStillObjects().size(); i++){
                if(BombermanGame.getStillObjects().get(i).getX()/ Sprite.SCALED_SIZE == x / Sprite.SCALED_SIZE &&
                        BombermanGame.getStillObjects().get(i).getY()/ Sprite.SCALED_SIZE == y / Sprite.SCALED_SIZE){
                    a = BombermanGame.getStillObjects().get(i);
                }
            }
            if (a instanceof Bomber || a instanceof Grass || a instanceof Oneal || a instanceof Balloom || a instanceof Brick) radius++;
            if(a instanceof Brick) break;
            if(a instanceof Wall ) break;
        }
//       System.out.println("radius nay" + radius + "Bricksize" + BrickEntity.size());
        // không cần đến cái
//        if(BrickEntity.size() > 1) radius = radius - (BrickEntity.size()-1);
//        BrickEntity.clear();
        //System.out.println("radius nay" + radius);
        return radius;
    }

    public FlameSegment flameSegmentAt(int x, int y) {
        for (int i = 0; i < _flameSegments.length; i++) {
            if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
                return _flameSegments[i];
        }
        return null;
    }

//    @Override
//    public boolean isREMOVEFIXPROTECTTED() {
//        return false;
//    }
//
//    @Override
//    public void setREMOVEFIXPROTECTTED(boolean REMOVEFIXPROTECTTED) {
//
//    }

    @Override
    public void kill() {

    }

    @Override
    public void update() {
        for(int i = 0 ; i< _flameSegments.length;i++)
        {
            _flameSegments[i].update();
        }
    }
//
//    @Override
//    public void render(Screen screen) {
//        for (int i = 0; i < _flameSegments.length; i++) {
//            _flameSegments[i].render(screen);
//        }
//    }
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
        return true;
    }
}
