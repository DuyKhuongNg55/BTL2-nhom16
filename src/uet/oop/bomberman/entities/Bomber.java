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
    times++;
    if (times % 3 == 0) {
      img = Sprite.player_right.getFxImage();
    } else if (times % 3 == 1) {
      img = Sprite.player_right_1.getFxImage();
    } else {
      img = Sprite.player_right_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (x + 28 > indexX * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE) {
      if (y % Sprite.SCALED_SIZE != 0) {
        return;
      }
      for (int i = (indexX + 1) * 13 + indexY; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE && BombermanGame.getStillObjects().get(i) instanceof Wall) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX() > (indexX + 2) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    x += 8;
    System.out.println(x + " " + y);
  }

  public void moveLeft() {
    times++;
    if (times % 3 == 0) {
      img = Sprite.player_left.getFxImage();
    } else if (times % 3 == 1) {
      img = Sprite.player_left_1.getFxImage();
    } else {
      img = Sprite.player_left_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (x - 8 < indexX * Sprite.SCALED_SIZE) {
      if (y % Sprite.SCALED_SIZE != 0) {
        return;
      }
      for (int i = (indexX - 1) * 13 + indexY; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == (indexX - 1) * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i) instanceof Wall) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX()
            > (indexX + 1) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    x -= 8;
    System.out.println(x + " " + y);
  }

  public void moveUp() {
    times++;
    if (times % 3 == 0) {
      img = Sprite.player_up.getFxImage();
    } else if (times % 3 == 1) {
      img = Sprite.player_up_1.getFxImage();
    } else {
      img = Sprite.player_up_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (y - 8 < indexY * Sprite.SCALED_SIZE) {
      if (x - indexX * Sprite.SCALED_SIZE > 12) {
        return;
      }
      for (int i = indexX * 13 + indexY - 1; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == (indexY - 1) * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i) instanceof Wall) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX()
            > (indexX + 1) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    y -= 8;
    System.out.println(x + " " + y);
  }

  public void moveDown() {
    times++;
    if (times % 3 == 0) {
      img = Sprite.player_down.getFxImage();
    } else if (times % 3 == 1) {
      img = Sprite.player_down_1.getFxImage();
    } else {
      img = Sprite.player_down_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (y + Sprite.SCALED_SIZE + 8 > (indexY + 1) * Sprite.SCALED_SIZE) {
      if (x - indexX * Sprite.SCALED_SIZE > 12) {
        return;
      }
      for (int i = indexX * 13 + indexY + 1; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == (indexY + 1) * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i) instanceof Wall) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX()
            > (indexX + 1) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    y += 8;
    System.out.println(x + " " + y);
  }
}
