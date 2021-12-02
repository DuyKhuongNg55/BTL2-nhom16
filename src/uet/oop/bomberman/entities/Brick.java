package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends DestroyableEntity{
    protected boolean _destroyed = false;
    protected int _timeToDisapear = 20;
    protected Sprite _belowSprite = Sprite.grass;
    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    @Override
    public void update() {

        if(!_destroyed) {
            if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
            _sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
            this.img = _sprite.getFxImage();
            if(_timeToDisapear > 0)
                _timeToDisapear--;
        }
//        }
//        if(_destroyed) {
//            if(_timeToDisapear > 0)
//                _timeToDisapear--;
//            else
//                remove();
//        }
    }

    @Override
    public boolean is_destroyed() {
        return _destroyed;
    }

    @Override
    public void set_destroyed(boolean _destroyed) {
        this._destroyed = _destroyed;
    }

    public int get_timeToDisapear() {
        return _timeToDisapear;
    }

    public void set_timeToDisapear(int _timeToDisapear) {
        this._timeToDisapear = _timeToDisapear;
    }

    public Sprite get_belowSprite() {
        return _belowSprite;
    }

    public void set_belowSprite(Sprite _belowSprite) {
        this._belowSprite = _belowSprite;
    }
}
