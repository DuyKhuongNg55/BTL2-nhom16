package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {

  private static boolean[][] matrix = new boolean[13][31];
  private static List<Vert> vertList = new ArrayList<>();
  private static Vert oneal1;
  private static Vert oneal2;
  private static Vert bomber;
  private static PathFinder shortestPath = new PathFinder();

  public Bomber(int x, int y, Image img) {
    super(x, y, img);
  }

  @Override
  public void update() {
    createVert();
    createEdge();
    shortestPath.ShortestP(bomber);
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

  public static void createMatrix() {
    for (int i = 0; i < BombermanGame.getStillObjects().size(); i++) {
      if (BombermanGame.getStillObjects().get(i) instanceof Wall) {
        matrix[BombermanGame.getStillObjects().get(i).getY() / Sprite.SCALED_SIZE][BombermanGame.getStillObjects().get(i).getX() / Sprite.SCALED_SIZE] = false;
      } else {
        matrix[BombermanGame.getStillObjects().get(i).getY() / Sprite.SCALED_SIZE][BombermanGame.getStillObjects().get(i).getX() / Sprite.SCALED_SIZE] = true;
      }
    }
    for (int i = 0; i < BombermanGame.getEntities().size(); i++) {
      if (BombermanGame.getEntities().get(i) instanceof Balloom) {
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

  public static void createVert() {
    vertList.clear();
    createMatrix();
    int onealTh = 1;
    for (int i = 1; i < 30; i++) {
      for (int j = 1; j < 12; j++) {
//        if (i == 9 && j == 3) {
//          System.out.print("");
//        }
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

  static class HorizontalComparator implements Comparator<Vert> {

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

  static class VerticalComparator implements Comparator<Vert> {

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

  public static void createEdge() {
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
}
