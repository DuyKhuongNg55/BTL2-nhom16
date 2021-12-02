package uet.oop.bomberman.entities;

import uet.oop.bomberman.OldCode.graphics.Sprite;
import javafx.scene.image.Image;

public class FlameSegment extends Entity {

    protected boolean _last;

    /**
     *
     * @param x
     * @param y
     * @param direction
     * @param last cho biết segment này là cuối cùng của Flame hay không,
     *                segment cuối có sprite khác so với các segment còn lại
     */
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 1000;


    protected void animate() {
        if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
    }

    public FlameSegment(int x, int y, Image img, int direction, boolean last) {
        super(x,y, img);
        _last = last;
        this._direction = direction;
        //animate();
//        switch (direction) {
//            case 0:
//                if(!last) {
//                    _sprite = Sprite.explosion_vertical2;
//                    this.img = _sprite.getFxImage();
//                } else {
//                    _sprite = Sprite.explosion_vertical_top_last2;
//                    this.img = _sprite.getFxImage();
//                }
//                break;
//            case 1:
//                if(!last) {
//                    _sprite = Sprite.explosion_horizontal2;
//                    this.img = _sprite.getFxImage();
//                } else {
//                    _sprite = Sprite.explosion_horizontal_right_last2;
//                    this.img = _sprite.getFxImage();
//                }
//                break;
//            case 2:
//                if(!last) {
//                    _sprite = Sprite.explosion_vertical2;
//                    this.img = _sprite.getFxImage();
//                } else {
//                    _sprite = Sprite.explosion_vertical_down_last2;
//                    this.img = _sprite.getFxImage();
//                }
//                break;
//            case 3:
//                if(!last) {
//                    _sprite = Sprite.explosion_horizontal2;
//                    this.img = _sprite.getFxImage();
//                } else {
//                    _sprite = Sprite.explosion_horizontal_left_last2;
//                    this.img = _sprite.getFxImage();
//                }
//                break;
//        }
    }

//    @Override
//    public void render(Screen screen) {
//        int xt = (int)_x << 4;
//        int yt = (int)_y << 4;
//
//        screen.renderEntity(xt, yt , this);
//    }

    public int get_animate() {
        return _animate;
    }

    public void set_animate(int _animate) {
        this._animate = _animate;
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
        //animate();
        switch (_direction) {
            case 0:
                if(!_last) {
                    //_sprite = Sprite.explosion_vertical2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical2,Sprite.explosion_vertical1,Sprite.explosion_vertical,_animate,60);
                    this.img = _sprite.getFxImage();
                } else {
                   // _sprite = Sprite.explosion_vertical_top_last2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last2,Sprite.explosion_vertical_top_last1,Sprite.explosion_vertical_top_last,_animate,60);
                    this.img = _sprite.getFxImage();
                }
                break;
            case 1:
                if(!_last) {
                    //_sprite = Sprite.explosion_horizontal2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal2,Sprite.explosion_horizontal1,Sprite.explosion_horizontal,_animate,60);
                    this.img = _sprite.getFxImage();
                } else {
                    //_sprite = Sprite.explosion_horizontal_right_last2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last2,Sprite.explosion_horizontal_right_last1,Sprite.explosion_horizontal_right_last,_animate,60);
                    this.img = _sprite.getFxImage();
                }
                break;
            case 2:
                if(!_last) {
                    _sprite = Sprite.explosion_vertical2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical2,Sprite.explosion_vertical1,Sprite.explosion_vertical,_animate,60);
                    this.img = _sprite.getFxImage();
                } else {
                    _sprite = Sprite.explosion_vertical_down_last2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last2,Sprite.explosion_vertical_down_last1,Sprite.explosion_vertical_down_last,_animate,60);
                    this.img = _sprite.getFxImage();
                }
                break;
            case 3:
                if(!_last) {
                    _sprite = Sprite.explosion_horizontal2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal2,Sprite.explosion_horizontal1,Sprite.explosion_horizontal,_animate,60);
                    this.img = _sprite.getFxImage();
                } else {
                    _sprite = Sprite.explosion_horizontal_left_last2;
                    _sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last2,Sprite.explosion_horizontal_left_last1,Sprite.explosion_horizontal_left_last,_animate,60);
                    this.img = _sprite.getFxImage();
                }
                break;
        }
    }


    public boolean collide(Entity e) {
        // TODO: xử lý khi FlameSegment va chạm với Character

        return true;
    }


}