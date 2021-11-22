package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {

  public Bomber(int x, int y, Image img) {
    super(x, y, img);
  }

  @Override
  public void update() {

  }

  public void moveRight() {
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (BombermanGame.getData().get(indexY).charAt(indexX) )
    x += 8;
    System.out.println(x + " " + y);
  }

  public void moveLeft() {
    x -= 8;
    System.out.println(x + " " + y);
  }

  public void moveUp() {
    y -= 8;
    System.out.println(x + " " + y);
  }

  public void moveDown() {
    y += 8;
    System.out.println(x + " " + y);
  }
}
