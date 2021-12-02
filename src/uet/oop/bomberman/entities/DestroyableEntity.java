package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.OldCode.graphics.Sprite;

public class DestroyableEntity extends Entity {
    protected final int MAX_ANIMATE = 7500;
    protected int _animate = 0;
    protected boolean _destroyed = false;
    protected int _timeToDisapear = 20;
    protected Sprite _belowSprite = Sprite.grass;
    public DestroyableEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void kill() {

    }
//
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
    public void update() {

    }

//    public DestroyableTile(int x, int y, Sprite sprite) {
//        super(x, y, sprite);
//    }

//    @Override
//    public void update() {
//        if(_destroyed) {
//            if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
//            if(_timeToDisapear > 0)
//                _timeToDisapear--;
//            else
//                remove();
//        }
//    }

    public void destroy() {
        _destroyed = true;
    }

    public boolean is_destroyed() {
        return _destroyed;
    }

    public void set_destroyed(boolean _destroyed) {
        this._destroyed = _destroyed;
    }

    public boolean collide(Entity e) {
        // TODO: xử lý khi va chạm với Flame
        if (e instanceof Flame)
            destroy();
        return false;
    }

    protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
        int calc = _animate % 30;

        if(calc < 10) {
            return normal;
        }

        if(calc < 20) {
            return x1;
        }

        return x2;
    }

}
