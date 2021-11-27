package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity {

  private int speed = 1;
  private String preString = "";
  private int xTarget = 0;
  private int yTarget = 0;
  private int timeSpeed = 0;
  private static boolean[][] matrix = new boolean[13][31];
  private static List<Vert> vertList = new ArrayList<>();
  private static Vert oneal1;
  private static Vert oneal2;
  private static Vert bomber;
  private static PathFinder shortestPath = new PathFinder();

  public Oneal(int x, int y, Image img) {
    super(x, y, img);
  }

  @Override
  public void update() {
    createVert();
    createEdge();
    getShortestPath().ShortestP(bomber);
    if (oneal1 != null) {
      Vert onealCur;
      if (x / Sprite.SCALED_SIZE == oneal1.getX()
          && y / Sprite.SCALED_SIZE == oneal1.getY()) {
        onealCur = oneal1;
        System.out.println("Khoảng cách tối thiểu từ:");
        System.out.println("oneal1 đến p: " + onealCur.getDist());
        System.out.println("Đường đi ngắn nhất từ:");
        System.out.println("oneal1 đến p: " + getShortestPath().getShortestP(onealCur));
      } else {
        onealCur = oneal2;
        System.out.println("Khoảng cách tối thiểu từ:");
        System.out.println("oneal2 đến p: " + onealCur.getDist());
        System.out.println("Đường đi ngắn nhất từ:");
        System.out.println("oneal2 đến p: " + getShortestPath().getShortestP(onealCur));
      }
      String curString = preString;
      if (onealCur.getDist() == Double.MAX_VALUE) {
        if (xTarget != 0 && !(x == xTarget && y == yTarget)) {
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
          timeSpeed++;
          if (timeSpeed == 10) {
            speed *= 2;
          } else if (timeSpeed == 20) {
            speed /= 2;
            timeSpeed = 0;
          }
          //Di ngau nhien
          Vert vertCur = new Vert(null, 0, 0);
          for (int i = 0; i < vertList.size(); i++) {
            if (vertList.get(i).getX() == x / Sprite.SCALED_SIZE && vertList.get(i).getY() == y / Sprite.SCALED_SIZE) {
              vertCur = vertList.get(i);
              break;
            }
          }
          int ranNum = ThreadLocalRandom.current().nextInt(0, vertCur.getList().size());
          xTarget = vertCur.getList().get(ranNum).getTargetVert().getX() * Sprite.SCALED_SIZE;
          yTarget = vertCur.getList().get(ranNum).getTargetVert().getY() * Sprite.SCALED_SIZE;
          if (y / Sprite.SCALED_SIZE  > vertCur.getList().get(ranNum).getTargetVert().getY()) {
            curString = "Up";
          } else if (x / Sprite.SCALED_SIZE  > vertCur.getList().get(ranNum).getTargetVert().getX()) {
            curString = "Left";
          } else if (x / Sprite.SCALED_SIZE  < vertCur.getList().get(ranNum).getTargetVert().getX()) {
            curString = "Right";
          } else if (y / Sprite.SCALED_SIZE  < vertCur.getList().get(ranNum).getTargetVert().getY()) {
            curString = "Down";
          }
//          boolean again = true;
//          while (again) {
//            again = false;
//            int ranNum = ThreadLocalRandom.current().nextInt(1, 5);
//            if (ranNum == 1) {
//              curString = "Up";
//              if (!matrix[y / Sprite.SCALED_SIZE - 1][x / Sprite.SCALED_SIZE]) {
//                again = true;
//              } else {
//                VerticalComparator verticalComparator = new VerticalComparator();
//                vertList.sort(verticalComparator);
//                for (int i = 0; i < vertList.size(); i++) {
//                  if (vertList.get(i).getY() < y / Sprite.SCALED_SIZE) {
//                    yTarget = vertList.get(i).getY() * Sprite.SCALED_SIZE;
//                  } else {
//                    break;
//                  }
//                }
//              }
//            } else if (ranNum == 2) {
//              curString = "Left";
//              if (!matrix[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE - 1]) {
//                again = true;
//              } else {
//                HorizontalComparator horizontalComparator = new HorizontalComparator();
//                vertList.sort(horizontalComparator);
//                for (int i = 0; i < vertList.size(); i++) {
//                  if (vertList.get(i).getX() < x / Sprite.SCALED_SIZE) {
//                    xTarget = vertList.get(i).getX() * Sprite.SCALED_SIZE;
//                  } else {
//                    break;
//                  }
//                }
//              }
//            } else if (ranNum == 3) {
//              curString = "Right";
//              if (!matrix[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE + 1]) {
//                again = true;
//              } else {
//                HorizontalComparator horizontalComparator = new HorizontalComparator();
//                vertList.sort(horizontalComparator);
//                for (int i = 0; i < vertList.size(); i++) {
//                  if (vertList.get(i).getX() > x / Sprite.SCALED_SIZE) {
//                    xTarget = vertList.get(i).getX() * Sprite.SCALED_SIZE;
//                    break;
//                  }
//                }
//              }
//            } else if (ranNum == 4) {
//              curString = "Down";
//              if (!matrix[y / Sprite.SCALED_SIZE + 1][x / Sprite.SCALED_SIZE]) {
//                again = true;
//              } else {
//                VerticalComparator verticalComparator = new VerticalComparator();
//                vertList.sort(verticalComparator);
//                for (int i = 0; i < vertList.size(); i++) {
//                  if (vertList.get(i).getY() > y / Sprite.SCALED_SIZE) {
//                    yTarget = vertList.get(i).getY() * Sprite.SCALED_SIZE;
//                    break;
//                  }
//                }
//              }
//            }
//          }
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
          preString = curString;
        }
      } else {
        if ((x == xTarget && y == yTarget) || xTarget == 0) {
          timeSpeed++;
          if (timeSpeed == 10) {
            speed *= 2;
          } else if (timeSpeed == 20) {
            speed /= 2;
            timeSpeed = 0;
          }
          xTarget = getShortestPath()
              .getShortestP(onealCur).get(1).getX() * Sprite.SCALED_SIZE;
          yTarget = getShortestPath()
              .getShortestP(onealCur).get(1).getY() * Sprite.SCALED_SIZE;
          if (y / Sprite.SCALED_SIZE > getShortestPath()
              .getShortestP(onealCur).get(1).getY()) {
            curString = "Up";
          } else if (x / Sprite.SCALED_SIZE > getShortestPath()
              .getShortestP(onealCur).get(1).getX()) {
            curString = "Left";
          } else if (x / Sprite.SCALED_SIZE < getShortestPath()
              .getShortestP(onealCur).get(1).getX()) {
            curString = "Right";
          } else if (y / Sprite.SCALED_SIZE < getShortestPath()
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
    if (times % 12 >= 0 && times % 12 <= 3) {
      img = Sprite.oneal_right1.getFxImage();
    } else if (times % 12 >= 4 && times % 12 <= 7) {
      img = Sprite.oneal_right2.getFxImage();
    } else {
      img = Sprite.oneal_right3.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (x + Sprite.SCALED_SIZE + speed > (indexX + 1) * Sprite.SCALED_SIZE) {
      if (y % Sprite.SCALED_SIZE != 0) {
        return;
      }
      if (!matrix[indexY][indexX + 1]) {
        return;
      }
    }
    x += speed;
  }

  public void moveLeft() {
    times++;
    if (times % 12 >= 0 && times % 12 <= 3) {
      img = Sprite.oneal_left1.getFxImage();
    } else if (times % 12 >= 4 && times % 12 <= 7) {
      img = Sprite.oneal_left2.getFxImage();
    } else {
      img = Sprite.oneal_left3.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (x - speed < indexX * Sprite.SCALED_SIZE) {
      if (y % Sprite.SCALED_SIZE != 0) {
        return;
      }
      if (!matrix[indexY][indexX - 1]) {
        return;
      }
    }
    x -= speed;
  }

  public void moveUp() {
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (y - speed < indexY * Sprite.SCALED_SIZE) {
      if (x - indexX * Sprite.SCALED_SIZE > 0) {
        return;
      }
      if (!matrix[indexY - 1][indexX]) {
        return;
      }
    }
    y -= speed;
  }

  public void moveDown() {
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (y + Sprite.SCALED_SIZE + speed > (indexY + 1) * Sprite.SCALED_SIZE) {
      if (x - indexX * Sprite.SCALED_SIZE > 0) {
        return;
      }
      if (!matrix[indexY + 1][indexX]) {
        return;
      }
    }
    y += speed;
  }

  public void createMatrix() {
    for (int i = 0; i < BombermanGame.getStillObjects().size(); i++) {
      if (BombermanGame.getStillObjects().get(i) instanceof Wall) {
        matrix[BombermanGame.getStillObjects().get(i).getY() / Sprite.SCALED_SIZE][BombermanGame.getStillObjects().get(i).getX() / Sprite.SCALED_SIZE] = false;
      } else {
        matrix[BombermanGame.getStillObjects().get(i).getY() / Sprite.SCALED_SIZE][BombermanGame.getStillObjects().get(i).getX() / Sprite.SCALED_SIZE] = true;
      }
    }
    for (int i = 0; i < BombermanGame.getEntities().size(); i++) {
      if (BombermanGame.getEntities().get(i) instanceof Balloom || (BombermanGame.getEntities().get(i) instanceof Oneal && BombermanGame.getEntities().get(i).getX() != x && BombermanGame.getEntities().get(i).getY() != y)) {
        matrix[BombermanGame.getEntities().get(i).getY() / Sprite.SCALED_SIZE][BombermanGame.getEntities().get(i).getX() / Sprite.SCALED_SIZE] = false;
        if (BombermanGame.getEntities().get(i).getX() % Sprite.SCALED_SIZE != 0) {
          matrix[BombermanGame.getEntities().get(i).getY() / Sprite.SCALED_SIZE][BombermanGame.getEntities().get(i).getX() / Sprite.SCALED_SIZE + 1] = false;
        }
        if (BombermanGame.getEntities().get(i).getY() % Sprite.SCALED_SIZE != 0) {
          matrix[BombermanGame.getEntities().get(i).getY() / Sprite.SCALED_SIZE + 1][BombermanGame.getEntities().get(i).getX() / Sprite.SCALED_SIZE] = false;
        }
      }
    }
  }

  public void createVert() {
    vertList.clear();
    createMatrix();
    int onealTh = 1;
    for (int i = 1; i < 30; i++) {
      for (int j = 1; j < 12; j++) {
        if (matrix[j][i]) {
          int timesTemp = 0;
          if (!matrix[j - 1][i]) {
            timesTemp++;
          }
          if (!matrix[j][i - 1]) {
            timesTemp++;
          }
          if (!matrix[j][i + 1]) {
            timesTemp++;
          }
          if (!matrix[j + 1][i]) {
            timesTemp++;
          }
          boolean isBomber = false;
          boolean isOneal1 = false;
          boolean isOneal2 = false;
          if (BombermanGame.getEntities().get(0).getX() / Sprite.SCALED_SIZE == i && BombermanGame.getEntities().get(0).getY() / Sprite.SCALED_SIZE == j) {
            isBomber = true;
          } else {
            for (int k = 0; k < BombermanGame.getEntities().size() - 1; k++) {
              if (BombermanGame.getEntities().get(k) instanceof Oneal
                  && i == BombermanGame.getEntities().get(k).getX() / Sprite.SCALED_SIZE
                  && j == BombermanGame.getEntities().get(k).getY() / Sprite.SCALED_SIZE) {
                if (onealTh == 1) {
                  isOneal1 = true;
                } else {
                  isOneal2 = true;
                }
                onealTh++;
              }
            }
          }
          if (isBomber || isOneal1 || isOneal2 || timesTemp == 1 || timesTemp == 0 || (timesTemp == 2 && !(matrix[j][i - 1] && matrix[j][i + 1]) && !(matrix[j - 1][i] && matrix[j + 1][i]))) {
            vertList.add(new Vert(i + " " + j, i, j));
            if (isBomber) {
              bomber = vertList.get(vertList.size() - 1);
            } else if (isOneal1) {
              oneal1 = vertList.get(vertList.size() - 1);
            } else if (isOneal2) {
              oneal2 = vertList.get(vertList.size() - 1);
            }
          }
        }
      }
    }
  }

  class HorizontalComparator implements Comparator<Vert> {

    @Override
    public int compare(Vert o1, Vert o2) {
      if (o1.getY() < o2.getY()) {
        return -1;
      } else if (o1.getY() > o2.getY()) {
        return 1;
      } else {
        return Integer.compare(o1.getX(), o2.getX());
      }
    }
  }

  class VerticalComparator implements Comparator<Vert> {

    @Override
    public int compare(Vert o1, Vert o2) {
      if (o1.getX() < o2.getX()) {
        return -1;
      } else if (o1.getX() > o2.getX()) {
        return 1;
      } else {
        return Integer.compare(o1.getY(), o2.getY());
      }
    }
  }

  public void createEdge() {
    HorizontalComparator horizontalComparator = new HorizontalComparator();
    vertList.sort(horizontalComparator);
    for (int i = 0; i < vertList.size() - 1; i++) {
      if (vertList.get(i).getY() == vertList.get(i + 1).getY()) {
        int length = 1;
        boolean isEdge = true;
        for (int j = vertList.get(i).getX() + 1; j < vertList.get(i + 1).getX(); j++) {
          if (matrix[vertList.get(i).getY()][j]) {
            length++;
          } else {
            isEdge = false;
          }
        }
        if (isEdge) {
          vertList.get(i).addNeighbour(new Edge(length, vertList.get(i), vertList.get(i + 1)));
          vertList.get(i + 1).addNeighbour(new Edge(length, vertList.get(i + 1), vertList.get(i)));
        }
      }
    }
    //canh doc
    VerticalComparator verticalComparator = new VerticalComparator();
    vertList.sort(verticalComparator);
    for (int i = 0; i < vertList.size() - 1; i++) {
      if (vertList.get(i).getX() == vertList.get(i + 1).getX()) {
        int length = 1;
        boolean isEdge = true;
        for (int j = vertList.get(i).getY() + 1; j < vertList.get(i + 1).getY(); j++) {
          if (matrix[j][vertList.get(i).getX()]) {
            length++;
          } else {
            isEdge = false;
          }
        }
        if (isEdge) {
          vertList.get(i).addNeighbour(new Edge(length, vertList.get(i), vertList.get(i + 1)));
          vertList.get(i + 1).addNeighbour(new Edge(length, vertList.get(i + 1), vertList.get(i)));
        }
      }
    }
  }

  public static PathFinder getShortestPath() {
    return shortestPath;
  }

  public static Vert getOneal1() {
    return oneal1;
  }

  public static Vert getOneal2() {
    return oneal2;
  }

  public static Vert getBomber() {
    return bomber;
  }

  public static boolean[][] getMatrix() {
    return matrix;
  }
}