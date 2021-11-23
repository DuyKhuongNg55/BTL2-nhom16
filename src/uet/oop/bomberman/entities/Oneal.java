package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity {

  private int speed = 1;
  private String preString = "";
  private int xTarget = 0;
  private int yTarget = 0;

  public Oneal(int x, int y, Image img) {
    super(x, y, img);
  }

  @Override
  public void update() {
    if (Bomber.getOneal1() != null) {
      Vert onealCur;
      if (x / Sprite.SCALED_SIZE == Bomber.getOneal1().getX()
          && y / Sprite.SCALED_SIZE == Bomber.getOneal1().getY()) {
        onealCur = Bomber.getOneal1();
        System.out.println("Khoảng cách tối thiểu từ:");
        System.out.println("oneal1 đến p: " + onealCur.getDist());
        System.out.println("Đường đi ngắn nhất từ:");
        System.out.println("oneal1 đến p: " + Bomber.getShortestPath().getShortestP(onealCur));
      } else {
        onealCur = Bomber.getOneal2();
        System.out.println("Khoảng cách tối thiểu từ:");
        System.out.println("oneal2 đến p: " + onealCur.getDist());
        System.out.println("Đường đi ngắn nhất từ:");
        System.out.println("oneal2 đến p: " + Bomber.getShortestPath().getShortestP(onealCur));
      }
      String curString = preString;
      if (onealCur.getDist() == Double.MAX_VALUE) {
        if (!(x == xTarget && y == yTarget)) {
          switch (curString) {
            case "Up":
              moveUp();
              break;
            case "Left":
              moveLeft();
              break;
            case "Right":
              moveRight();
              break;
            case "Down":
              moveDown();
              break;
          }
        } else {
          //Di ngau nhien
        }
      } else {
        if ((x == xTarget && y == yTarget) || xTarget == 0) {
          xTarget = Bomber.getShortestPath()
              .getShortestP(onealCur).get(1).getX() * Sprite.SCALED_SIZE;
          yTarget = Bomber.getShortestPath()
              .getShortestP(onealCur).get(1).getY() * Sprite.SCALED_SIZE;
          if (y / Sprite.SCALED_SIZE > Bomber.getShortestPath()
              .getShortestP(onealCur).get(1).getY()) {
            curString = "Up";
          } else if (x / Sprite.SCALED_SIZE > Bomber.getShortestPath()
              .getShortestP(onealCur).get(1).getX()) {
            curString = "Left";
          } else if (x / Sprite.SCALED_SIZE < Bomber.getShortestPath()
              .getShortestP(onealCur).get(1).getX()) {
            curString = "Right";
          } else if (y / Sprite.SCALED_SIZE < Bomber.getShortestPath()
              .getShortestP(onealCur).get(1).getY()) {
            curString = "Down";
          }
        }
        if (preString != null) {
          if (!preString.equals(curString)) {
            times = 0;
          }
        }
        preString = curString;
        switch (curString) {
          case "Up":
            moveUp();
            break;
          case "Left":
            moveLeft();
            break;
          case "Right":
            moveRight();
            break;
          case "Down":
            moveDown();
            break;
        }
      }
    }
  }

  public void moveRight() {
    times++;
    if (times % 3 == 0) {
      img = Sprite.oneal_right1.getFxImage();
    } else if (times % 3 == 1) {
      img = Sprite.oneal_right2.getFxImage();
    } else {
      img = Sprite.oneal_right3.getFxImage();
    }
//    int indexX = x / Sprite.SCALED_SIZE;
//    int indexY = y / Sprite.SCALED_SIZE;
//    if (x + Sprite.SCALED_SIZE + speed > (indexX + 1) * Sprite.SCALED_SIZE) {
//      if (y % Sprite.SCALED_SIZE != 0) {
//        return;
//      }
//      for (int i = (indexX + 1) * 13 + indexY; i < BombermanGame.getStillObjects().size(); i++) {
//        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE
//            && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE && BombermanGame.getStillObjects().get(i) instanceof Wall) {
//          return;
//        } else if (BombermanGame.getStillObjects().get(i).getX() > (indexX + 2) * Sprite.SCALED_SIZE) {
//          break;
//        }
//      }
//    }
    x += speed;
  }

  public void moveLeft() {
    times++;
    if (times % 3 == 0) {
      img = Sprite.oneal_left1.getFxImage();
    } else if (times % 3 == 1) {
      img = Sprite.oneal_left2.getFxImage();
    } else {
      img = Sprite.oneal_left3.getFxImage();
    }
//    int indexX = x / Sprite.SCALED_SIZE;
//    int indexY = y / Sprite.SCALED_SIZE;
//    if (x - 8 < indexX * Sprite.SCALED_SIZE) {
//      if (y % Sprite.SCALED_SIZE != 0) {
//        return;
//      }
//      for (int i = (indexX - 1) * 13 + indexY; i < BombermanGame.getStillObjects().size(); i++) {
//        if (BombermanGame.getStillObjects().get(i).getX() == (indexX - 1) * Sprite.SCALED_SIZE
//            && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE
//            && BombermanGame.getStillObjects().get(i) instanceof Wall) {
//          return;
//        } else if (BombermanGame.getStillObjects().get(i).getX()
//            > (indexX + 1) * Sprite.SCALED_SIZE) {
//          break;
//        }
//      }
//    }
    x -= speed;
  }

  public void moveUp() {
//    int indexX = x / Sprite.SCALED_SIZE;
//    int indexY = y / Sprite.SCALED_SIZE;
//    if (y - 8 < indexY * Sprite.SCALED_SIZE) {
//      if (x - indexX * Sprite.SCALED_SIZE > 12) {
//        return;
//      }
//      for (int i = indexX * 13 + indexY - 1; i < BombermanGame.getStillObjects().size(); i++) {
//        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
//            && BombermanGame.getStillObjects().get(i).getY() == (indexY - 1) * Sprite.SCALED_SIZE
//            && BombermanGame.getStillObjects().get(i) instanceof Wall) {
//          return;
//        } else if (BombermanGame.getStillObjects().get(i).getX()
//            > (indexX + 1) * Sprite.SCALED_SIZE) {
//          break;
//        }
//      }
//    }
    y -= speed;
  }

  public void moveDown() {
//    int indexX = x / Sprite.SCALED_SIZE;
//    int indexY = y / Sprite.SCALED_SIZE;
//    if (y + Sprite.SCALED_SIZE + 8 > (indexY + 1) * Sprite.SCALED_SIZE) {
//      if (x - indexX * Sprite.SCALED_SIZE > 12) {
//        return;
//      }
//      for (int i = indexX * 13 + indexY + 1; i < BombermanGame.getStillObjects().size(); i++) {
//        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
//            && BombermanGame.getStillObjects().get(i).getY() == (indexY + 1) * Sprite.SCALED_SIZE
//            && BombermanGame.getStillObjects().get(i) instanceof Wall) {
//          return;
//        } else if (BombermanGame.getStillObjects().get(i).getX()
//            > (indexX + 1) * Sprite.SCALED_SIZE) {
//          break;
//        }
//      }
//    }
    y += speed;
  }
}
