package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {

  protected double _timeToExplode = 70; //2 seconds
  protected double _timeToRenderFlameSegment = 10; //2 seconds
  public int _timeAfter = 5;
  protected Flame[] _flames;
  public  boolean _exploded = false;
  protected boolean _allowedToPassThru = true;
  protected int BombRadius;
  protected int _animate = 0;
  protected final int MAX_ANIMATE = 1000;


  protected void animate() {
    if(_animate < MAX_ANIMATE) _animate++; else _animate = 0;
  }

  public Bomb(int xUnit, int yUnit, Image img, int _radius) {
    super(xUnit, yUnit, img);
    BombRadius = _radius;
  }

  public double get_timeToExplode() {
    return _timeToExplode;
  }

  public void set_timeToExplode(double _timeToExplode) {
    this._timeToExplode = _timeToExplode;
  }

  public int get_timeAfter() {
    return _timeAfter;
  }

  public void set_timeAfter(int _timeAfter) {
    this._timeAfter = _timeAfter;
  }

  public Flame[] get_flames() {
    return _flames;
  }

  public void set_flames(Flame[] _flames) {
    this._flames = _flames;
  }

  public  boolean is_exploded() {
    return _exploded;
  }

  public void set_exploded(boolean _exploded) {
    this._exploded = _exploded;
  }

  public boolean is_allowedToPassThru() {
    return _allowedToPassThru;
  }

  public void set_allowedToPassThru(boolean _allowedToPassThru) {
    this._allowedToPassThru = _allowedToPassThru;
  }

  public int getBombRadius() {
    return BombRadius;
  }

  public void setBombRadius(int bombRadius) {
    BombRadius = bombRadius;
  }

  public int get_animate() {
    return _animate;
  }

  public void set_animate(int _animate) {
    this._animate = _animate;
  }

  public int getMAX_ANIMATE() {
    return MAX_ANIMATE;
  }

  public double get_timeToRenderFlameSegment() {
    return _timeToRenderFlameSegment;
  }

  public void set_timeToRenderFlameSegment(double _timeToRenderFlameSegment) {
    this._timeToRenderFlameSegment = _timeToRenderFlameSegment;
  }

//  @Override
//  public boolean isREMOVEFIXPROTECTTED() {
//    return false;
//  }
//
//  @Override
//  public void setREMOVEFIXPROTECTTED(boolean REMOVEFIXPROTECTTED) {
//
//  }

  @Override
  public void kill() {

  }

  @Override
  public void update() {
    Entity a = null;
    for (int i = 0 ; i < BombermanGame.getEntities().size();i++){
      if(BombermanGame.getEntities().get(i).getX()/ Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE &&
              BombermanGame.getEntities().get(i).getY()/ Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE) {
        a = BombermanGame.getEntities().get(i);
//        System.out.println("Chay den day");
      }
    }
    if(a == null || !(a instanceof Bomber) || !(a instanceof EnemyWithBomb)) _allowedToPassThru = false;


    if(_timeToExplode > 0)
      _timeToExplode--;
    else {
      if(!_exploded)
        explode();
//			else
//				updateFlames();

      if(_timeAfter > 0)
        _timeAfter--;
      else
        _timeToRenderFlameSegment--;
        remove();
    }
    animate();
    if(_exploded) {
      _sprite =  Sprite.movingSprite(Sprite.bomb_exploded2, Sprite.bomb_exploded1, Sprite.bomb_exploded, _animate, 60);
      img = _sprite.getFxImage();
    } else
      _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
    img = _sprite.getFxImage();
  }

//  @Override
//  public void render(Screen screen) {
//    if(_exploded) {
//      _sprite =  Sprite.bomb_exploded2;
//      renderFlames(screen);
//    } else
//      _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
//
//    screen.renderEntity(xt, yt , this);
//  }

//  public void renderFlames(Screen screen) {
//    for (int i = 0; i < _flames.length; i++) {
//      _flames[i].render(screen);
//    }
//  }

//  public void updateFlames() {
//    for (int i = 0; i < _flames.length; i++) {
//      _flames[i].update();
//    }
//  }

  /**
   * Xử lý Bomb nổ
   */
  protected void explode() {

    // TODO: xử lý khi Character đứng tại vị trí Bomb
    //_allowedToPassThru = true;
    _exploded = true;
    Entity a = null;
    for (int i = 0 ; i < BombermanGame.getEntities().size();i++){
       if(BombermanGame.getEntities().get(i).getX()/ Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE &&
               BombermanGame.getEntities().get(i).getY()/ Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE){
         //System.out.println("Chay den day");
         a = BombermanGame.getEntities().get(i);

//         if(!(a instanceof Bomber) || a == null){
//           _allowedToPassThru = false;
//         }
       }
    }



    for (int i = 0 ; i < BombermanGame.getBombList().size();i++){

    }
    for (int i = 0 ; i < BombermanGame.getStillObjects().size();i++){

    }
    if (a!= null)
    {
      a.kill();
    }

    // TODO: tạo các Flame
    _flames = new Flame[4];
    for (int i = 0; i < _flames.length; i++)
      _flames[i] = new Flame(this.x / Sprite.SCALED_SIZE,this.y /Sprite.SCALED_SIZE,i, BombRadius, Sprite.explosion_horizontal2.getFxImage());
  }
}