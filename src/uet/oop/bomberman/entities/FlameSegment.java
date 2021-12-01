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
    public FlameSegment(int x, int y, Image img, int direction, boolean last) {
        super(x,y, img);
        _last = last;

        switch (direction) {
            case 0:
                if(!last) {
                    _sprite = Sprite.explosion_vertical2;
                    this.img = _sprite.getFxImage();
                } else {
                    _sprite = Sprite.explosion_vertical_top_last2;
                    this.img = _sprite.getFxImage();
                }
                break;
            case 1:
                if(!last) {
                    _sprite = Sprite.explosion_horizontal2;
                    this.img = _sprite.getFxImage();
                } else {
                    _sprite = Sprite.explosion_horizontal_right_last2;
                    this.img = _sprite.getFxImage();
                }
                break;
            case 2:
                if(!last) {
                    _sprite = Sprite.explosion_vertical2;
                    this.img = _sprite.getFxImage();
                } else {
                    _sprite = Sprite.explosion_vertical_down_last2;
                    this.img = _sprite.getFxImage();
                }
                break;
            case 3:
                if(!last) {
                    _sprite = Sprite.explosion_horizontal2;
                    this.img = _sprite.getFxImage();
                } else {
                    _sprite = Sprite.explosion_horizontal_left_last2;
                    this.img = _sprite.getFxImage();
                }
                break;
        }
    }

//    @Override
//    public void render(Screen screen) {
//        int xt = (int)_x << 4;
//        int yt = (int)_y << 4;
//
//        screen.renderEntity(xt, yt , this);
//    }

    @Override
    public void update() {}


    public boolean collide(Entity e) {
        // TODO: xử lý khi FlameSegment va chạm với Character

        return true;
    }


}