package uet.oop.bomberman;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import uet.oop.bomberman.entities.Balloom;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Oneal;
import uet.oop.bomberman.entities.Wall;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Audio;
import uet.oop.bomberman.graphics.Sprite;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

  public static final int WIDTH = 31;
  public static final int HEIGHT = 13;
  private int CountForCreateEnemy = 0;
  private GraphicsContext gc;
  private Canvas canvas;


  private static List<Entity> entities = new ArrayList<>();
  private static List<Entity> stillObjects = new ArrayList<>();

  private static List<Bomb> BombList = new ArrayList<>();
  private static List<Bomb> BombListOfEnemy = new ArrayList<>();
  private static List<Brick> BrickListExplode = new ArrayList<>();
  private static List<Entity> portalObjects = new ArrayList<>();
  private static List<Entity> FlamePowers = new ArrayList<>();
  private static List<Entity> SpeedPower = new ArrayList<>();
  private static List<Entity> BombPower = new ArrayList<>();
  private static List<Entity> EntitiesRemove = new ArrayList<>();

  private static List<String> data = new ArrayList<>();


  private Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), this);
  private KeyEvent preEvent = null;
  private static int stateTh = 0;
  private boolean printStage = false;
  private boolean win = false;
  private int level = 1;
  private int FPS = 30;
  private double averageFPS;
  private long startTime;
  private long URDTimeMillis;
  private long waitTime;
  private long totalTime = 0;
  private long preNanoTime = 0;

  private int frameCount = 0;
  private int maxFrameCount = 30;
  private long targetTime = 1000 / FPS;

  private int time = 200;
  private int scores = 0;
  private int left = 2;

  private static final int BombSet = 1;
  public static int BombCount = BombSet;
  public static int BombCountOfEnemy = BombSet;
  public static int BombRadius = 1;

  private AudioStream asSoundTrack;
  private AudioPlayer apSoundTrack;

  public static int getBombCountOfEnemy() {
    return BombCountOfEnemy;
  }

  public static void setBombCountOfEnemy(int bombCountOfEnemy) {
    BombCountOfEnemy = bombCountOfEnemy;
  }

  public static List<Bomb> getBombListOfEnemy() {
    return BombListOfEnemy;
  }

  public static void setBombListOfEnemy(List<Bomb> bombListOfEnemy) {
    BombListOfEnemy = bombListOfEnemy;
  }

  public static List<Entity> getEntitiesRemove() {
    return EntitiesRemove;
  }

  public static void setEntitiesRemove(List<Entity> entitiesRemove) {
    EntitiesRemove = entitiesRemove;
  }

  public GraphicsContext getGc() {
    return gc;
  }

  public int getCountForCreateEnemy() {
    return CountForCreateEnemy;
  }

  public void setCountForCreateEnemy(int countForCreateEnemy) {
    CountForCreateEnemy = countForCreateEnemy;
  }

  public static int getBombCount() {
    return BombCount;
  }

  public static void setBombCount(int bombCount) {
    BombCount = bombCount;
  }

  public static int getBombRadius() {
    return BombRadius;
  }

  public static void setBombRadius(int bombRadius) {
    BombRadius = bombRadius;
  }

  public void setGc(GraphicsContext gc) {
    this.gc = gc;
  }

  public Bomber getBomberman() {
    return bomberman;
  }

  public void setBomberman(Bomber bomberman) {
    this.bomberman = bomberman;
  }


  public static void main(String[] args) {
    Application.launch(BombermanGame.class);
  }

  public static List<Bomb> getBombList() {
    return BombList;
  }

  public static void setBombList(List<Bomb> bombList) {
    BombList = bombList;
  }

  @Override
  public void start(Stage stage) throws IOException {
    //TODO: Tao Canvas
    canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH,
        Sprite.SCALED_SIZE * HEIGHT + Sprite.SCALED_SIZE * 2);
    gc = canvas.getGraphicsContext2D();

    //TODO: Tao root container

    Group root = new Group();
    root.getChildren().add(canvas);

    //  TODO: Tao scene
    Scene scene = new Scene(root);

//        Audio m = new Audio();
//        final AudioStream[] as = {null};
//        AudioPlayer ap = AudioPlayer.player;
//        as[0] = m.footStepSound();
//
//
//        Scene scene = new Scene(root);
//        AudioStream finalAs = as[0];
//        AudioStream finalAs1 = as[0];
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        Audio m = new Audio();
        AudioStream as = null;
        AudioPlayer ap = AudioPlayer.player;
        as = m.footStepSound();
        long miliTime = 0;
        switch (event.getCode()) {
          case ENTER:
            if (stateTh == 0) {
              stateTh++;
            }
            break;
          case UP:
            if (stateTh == 0) {
              return;
            }
            if (preNanoTime != 0) {
              miliTime = (System.nanoTime() - preNanoTime) / 1000000;
            }
            if (preNanoTime == 0 || miliTime > 300) {
              preNanoTime = System.nanoTime();
              ap.start(as);
            }
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveUp();
//                            ap.start(finalAs);

            }
            break;
          case DOWN:
            if (stateTh == 0) {
              return;
            }
            if (preNanoTime != 0) {
              miliTime = (System.nanoTime() - preNanoTime) / 1000000;
            }
            if (preNanoTime == 0 || miliTime > 300) {
              preNanoTime = System.nanoTime();
              ap.start(as);
            }
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveDown();

//                            ap.start(finalAs);
            }
            break;
          case LEFT:
            if (stateTh == 0) {
              return;
            }
            if (preNanoTime != 0) {
              miliTime = (System.nanoTime() - preNanoTime) / 1000000;
            }
            if (preNanoTime == 0 || miliTime > 300) {
              preNanoTime = System.nanoTime();
              ap.start(as);
            }
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveLeft();

//                            ap.start(finalAs);
            }
            break;
          case RIGHT:
            if (stateTh == 0) {
              return;
            }
            if (preNanoTime != 0) {
              miliTime = (System.nanoTime() - preNanoTime) / 1000000;
            }
            if (preNanoTime == 0 || miliTime > 300) {
              preNanoTime = System.nanoTime();
              ap.start(as);
            }
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveRight();

//                            ap.start(finalAs1);
            }
            break;
          case SPACE:
            if (stateTh == 0) {
              return;
            }
            bomberman.PlaceBomb();
//                        as[0] = m.plantBombSound();
//                        ap.start(as[0]);
            break;
        }
      }
    });

    //TODO: Them scene vao stage

    stage.setScene(scene);
    stage.show();

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long l) {

        startTime = l;
        if (stillObjects.isEmpty() && stateTh == 0) {
          try {
            menuGame();
          } catch (IOException e) {
            e.printStackTrace();
          }
          return;
        }

        if (stillObjects.isEmpty()) {
          try {
            printStage();
          } catch (IOException e) {
            e.printStackTrace();
          }
          stateTh++;
          return;
        }
        if (!printStage) {
          render();
        }
        if (stateTh >= 23 && stateTh <= 43) {
          bomberman.setImg(Sprite.player_dead2.getFxImage());
          stateTh++;
        } else if (stateTh >= 44 && stateTh <= 64) {
          bomberman.setImg(Sprite.player_dead3.getFxImage());
          stateTh++;
        } else if (stateTh == 1) {
          for (int i = 1; i < entities.size(); i++) {
            if (entities.get(i).getX() > bomberman.getX() - Sprite.SCALED_SIZE
                && entities.get(i).getX() < bomberman.getX() + 20
                && entities.get(i).getY() > bomberman.getY() - Sprite.SCALED_SIZE
                && entities.get(i).getY() < bomberman.getY() + Sprite.SCALED_SIZE) {
              bomberman.setImg(Sprite.player_dead1.getFxImage());
              stateTh++;
            }
          }

          //Tao va cham voi bomb
          for (int i = 0; i < BombList.size(); i++) {
            if (BombList.get(i).is_exploded()) {
              int IndexXOfBomb = BombList.get(i).getX() / Sprite.SCALED_SIZE;
              int IndexYOfBomb = BombList.get(i).getY() / Sprite.SCALED_SIZE;
              Flame[] fl = BombList.get(i).get_flames();
              for (int j = 0; j < fl.length; j++) {
                FlameSegment[] fls = fl[j].get_flameSegments();

                for (int k = 0; k < fls.length; k++) {

                  for (int m = 0; m < BombermanGame.getEntities().size(); m++) {
                    if ((entities.get(m).getX() / Sprite.SCALED_SIZE == IndexXOfBomb &&
                        entities.get(m).getY() / Sprite.SCALED_SIZE == IndexYOfBomb) || (
                        fls[k].getX() / Sprite.SCALED_SIZE
                            == bomberman.getX() / Sprite.SCALED_SIZE
                            && fls[k].getY() / Sprite.SCALED_SIZE
                            == bomberman.getY() / Sprite.SCALED_SIZE)) {
                      bomberman.setImg(Sprite.player_dead1.getFxImage());
                      stateTh++;
                    }
                  }
                }
              }
            }
          }

          for (int i = 0; i < BombListOfEnemy.size(); i++) {
            if (BombListOfEnemy.get(i).is_exploded()) {
              Flame[] fl = BombListOfEnemy.get(i).get_flames();
              for (int j = 0; j < fl.length; j++) {
                FlameSegment[] fls = fl[j].get_flameSegments();
                for (int k = 0; k < fls.length; k++) {
                  if (fls[k].getX() / Sprite.SCALED_SIZE
                      == bomberman.getX() / Sprite.SCALED_SIZE
                      && fls[k].getY() / Sprite.SCALED_SIZE
                      == bomberman.getY() / Sprite.SCALED_SIZE) {

                    bomberman.setImg(Sprite.player_dead1.getFxImage());
                    stateTh++;
                  }
                  /**
                   *
                   * @param x
                   * @param y
                   * @param direction
                   * @param last cho biết segment này là cuối cùng của Flame hay không,
                   *                segment cuối có sprite khác so với các segment còn lại
                   *             * @return hướng đi   lên/phải/xuống/trái    tương ứng với các giá trị 0/1/2/3
                   */
                  // dang xet
                  if (fls[k].get_direction() == 0) {
                    if (((bomberman.getX()) / Sprite.SCALED_SIZE
                        == fls[k].getX() / Sprite.SCALED_SIZE) && (bomberman.getY()
                        - fls[k].getY() < Sprite.SCALED_SIZE && bomberman.getY()
                        - fls[k].getY() > 0)) {
                      bomberman.setImg(Sprite.player_dead1.getFxImage());
                      stateTh++;
                    }
                  } else if (fls[k].get_direction() == 1) {
                    if ((bomberman.getX() - fls[k].getX() < Sprite.SCALED_SIZE
                        && bomberman.getX() - fls[k].getX() > 0) && ((bomberman.getY())
                        / Sprite.SCALED_SIZE) == fls[k].getY() / Sprite.SCALED_SIZE) {
                      bomberman.setImg(Sprite.player_dead1.getFxImage());
                      stateTh++;
                    }
                  } else if (fls[k].get_direction() == 2) {
                    if (((bomberman.getX()) / Sprite.SCALED_SIZE
                        == fls[k].getX() / Sprite.SCALED_SIZE) && (bomberman.getY()
                        - fls[k].getY() < Sprite.SCALED_SIZE && bomberman.getY()
                        - fls[k].getY() > 0)) {
                      bomberman.setImg(Sprite.player_dead1.getFxImage());
                      stateTh++;
                    }
                  } else if (fls[k].get_direction() == 3) {
                    if ((bomberman.getX() - fls[k].getX() < Sprite.SCALED_SIZE &&
                        bomberman.getX() - fls[k].getX() > 0) && ((bomberman.getY())
                        / Sprite.SCALED_SIZE) == fls[k].getY() / Sprite.SCALED_SIZE) {
                      bomberman.setImg(Sprite.player_dead1.getFxImage());
                      stateTh++;
                    }
                  }
                }
              }
            }
          }
          if (entities.size() == 1) {
            for (int i = 0; i < portalObjects.size(); i++) {
              if (bomberman.getX() - portalObjects.get(i).getX() >= 0
                  && bomberman.getX() - portalObjects.get(i).getX()
                  <= Sprite.SCALED_SIZE - 20
                  && portalObjects.get(i).getY() == bomberman.getY()) {
                level++;
                win = true;
                stateTh = 66;
                break;
              }
            }
          }
        } else if (stateTh >= 2 && stateTh <= 22) {
          apSoundTrack.stop(asSoundTrack);
          // Chuẩn hóa x của bomberman khi bomberman chết ở sát bên trái tường để không bị đè ảnh của bomberman lên tường
          if (bomberman.getY() % Sprite.SCALED_SIZE == 0
              &&
              bomberman.getX() - bomberman.getX() / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE
                  <= Sprite.SCALED_SIZE - 10
              &&
              bomberman.getX() - bomberman.getX() / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE
                  >= 0) {
            int indexX = bomberman.getX() / Sprite.SCALED_SIZE;
            int indexY = bomberman.getY() / Sprite.SCALED_SIZE;
            for (int i = 0; i < BrickListExplode.size(); i++) {
              if (BrickListExplode.get(i).getX() == (indexX + 1) * Sprite.SCALED_SIZE
                  && BrickListExplode.get(i).getY() == indexY * Sprite.SCALED_SIZE) {
                bomberman.setX(bomberman.getX() / Sprite.SCALED_SIZE * Sprite.SCALED_SIZE);
              }
            }
          }
          bomberman.setImg(Sprite.player_dead1.getFxImage());
          stateTh++;
        } else if (stateTh >= 65 && stateTh <= 155) {
          if (stateTh == 66) {
            printStage = true;
            if (win) {
              apSoundTrack.stop(asSoundTrack);
              left++;
              win = false;
            } else {
              left--;
            }
          }
          if (stateTh == 65) {
            entities.remove(bomberman);
          }
          stateTh++;
        } else {
          try {
            if (left >= 0) {
              playAgain();
            } else {
              printLose();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          stateTh++;
        }
        if (!printStage) {
          update();
        }

        URDTimeMillis = (System.nanoTime() - startTime) / 1000000;

        waitTime = targetTime - URDTimeMillis;

        try {
          if (waitTime > 0) {
            Thread.sleep(waitTime);
          }
        } catch (
            InterruptedException e) {
          e.printStackTrace();
        }

        totalTime += System.nanoTime() - startTime;
        frameCount++;
        if (frameCount == maxFrameCount) {
          if (time == 0) {
            if (stateTh == 1) {
              stateTh++;
            }
          } else {
            time--;
          }
          averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
          frameCount = 0;
          totalTime = 0;
//                    System.out.println(averageFPS);
        }
      }
    };
    timer.start();
  }

  public void update() {
    for (int i = 0; i < FlamePowers.size(); i++) {
      if (((bomberman.getX()) / Sprite.SCALED_SIZE
          == FlamePowers.get(i).getX() / Sprite.SCALED_SIZE) && (
          FlamePowers.get(i).getY() - bomberman.getY()
              < 32 && FlamePowers.get(i).getY() - bomberman.getY()
              > 0)) {
        BombRadius++;
        for (int j = 0; j < BombList.size(); j++) {
          BombList.get(j).setBombRadius(BombRadius);
        }
        BombermanGame.getStillObjects().remove(FlamePowers.get(i));
        FlamePowers.remove(FlamePowers.get(i));
        scores += 1000;
      } else if ((bomberman.getX() - FlamePowers.get(i).getX() < 32
          && bomberman.getX() - FlamePowers.get(i).getX() > 0) && ((bomberman.getY())
          / Sprite.SCALED_SIZE) == FlamePowers.get(i).getY() / Sprite.SCALED_SIZE) {

        for (int j = 0; j < BombList.size(); j++) {
          BombList.get(j).setBombRadius(BombRadius);
        }
        BombermanGame.getStillObjects().remove(FlamePowers.get(i));
        FlamePowers.remove(FlamePowers.get(i));
        scores += 1000;
      } else if (((bomberman.getX()) / Sprite.SCALED_SIZE
          == FlamePowers.get(i).getX() / Sprite.SCALED_SIZE) && (bomberman.getY()
          - FlamePowers.get(i).getY() < 32 && bomberman.getY()
          - FlamePowers.get(i).getY() > 0)) {
        BombRadius++;
        for (int j = 0; j < BombList.size(); j++) {
          BombList.get(j).setBombRadius(BombRadius);
        }
        BombermanGame.getStillObjects().remove(FlamePowers.get(i));
        FlamePowers.remove(FlamePowers.get(i));
        scores += 1000;
      } else if ((FlamePowers.get(i).getX() - bomberman.getX() < 20
          && FlamePowers.get(i).getX() - bomberman.getX() > 0) && ((bomberman.getY())
      ) == FlamePowers.get(i).getY()) {
        BombRadius++;
        for (int j = 0; j < BombList.size(); j++) {
          BombList.get(j).setBombRadius(BombRadius);
        }
        BombermanGame.getStillObjects().remove(FlamePowers.get(i));
        FlamePowers.remove(FlamePowers.get(i));
        scores += 1000;
      }
    }

    for (int i = 0; i < SpeedPower.size(); i++) {

      if (((bomberman.getX()) / Sprite.SCALED_SIZE
          == SpeedPower.get(i).getX() / Sprite.SCALED_SIZE)
          && (SpeedPower.get(i).getY() - bomberman.getY()
          < 32 && SpeedPower.get(i).getY() - bomberman.getY()
          > 0)) {
        bomberman.setSpeed(bomberman.getSpeed() + 4);
        BombermanGame.getStillObjects().remove(SpeedPower.get(i));
        SpeedPower.remove(SpeedPower.get(i));
        scores += 1000;
      } else if ((bomberman.getX() - SpeedPower.get(i).getX() < 32
          && bomberman.getX() - SpeedPower.get(i).getX() > 0) && ((bomberman.getY())
          / Sprite.SCALED_SIZE) == SpeedPower.get(i).getY() / Sprite.SCALED_SIZE) {
        bomberman.setSpeed(bomberman.getSpeed() + 4);
        BombermanGame.getStillObjects().remove(SpeedPower.get(i));
        SpeedPower.remove(SpeedPower.get(i));
        scores += 1000;
      } else if (
          ((bomberman.getX()) / Sprite.SCALED_SIZE
              == SpeedPower.get(i).getX() / Sprite.SCALED_SIZE)
              && (bomberman.getY()
              - SpeedPower.get(i).getY() < 32 && bomberman.getY()
              - SpeedPower.get(i).getY() > 0)) {
        bomberman.setSpeed(bomberman.getSpeed() + 4);
        BombermanGame.getStillObjects().remove(SpeedPower.get(i));
        SpeedPower.remove(SpeedPower.get(i));
        scores += 1000;
      } else if ((SpeedPower.get(i).getX() - bomberman.getX() < 20
          && SpeedPower.get(i).getX() - bomberman.getX() > 0) && ((bomberman.getY())
      ) == SpeedPower.get(i).getY()) {
        bomberman.setSpeed(bomberman.getSpeed() + 4);
        BombermanGame.getStillObjects().remove(SpeedPower.get(i));
        SpeedPower.remove(SpeedPower.get(i));
        scores += 1000;
      }
    }

    for (int i = 0; i < BombPower.size(); i++) {
      if (((bomberman.getX()) / Sprite.SCALED_SIZE
          == BombPower.get(i).getX() / Sprite.SCALED_SIZE)
          && (BombPower.get(i).getY() - bomberman.getY()
          < 32 && BombPower.get(i).getY() - bomberman.getY()
          > 0)) {
        BombCount = BombCount + 1;
        BombermanGame.getStillObjects().remove(BombPower.get(i));
        BombPower.remove(BombPower.get(i));
        scores += 1000;
      } else if ((bomberman.getX() - BombPower.get(i).getX() < 32
          && bomberman.getX() - BombPower.get(i).getX() > 0) && ((bomberman.getY())
          / Sprite.SCALED_SIZE) == BombPower.get(i).getY() / Sprite.SCALED_SIZE) {
        BombCount = BombCount + 1;
        BombermanGame.getStillObjects().remove(BombPower.get(i));
        BombPower.remove(BombPower.get(i));
        scores += 1000;
      } else if (
          ((bomberman.getX()) / Sprite.SCALED_SIZE
              == BombPower.get(i).getX() / Sprite.SCALED_SIZE)
              && (bomberman.getY()
              - BombPower.get(i).getY() < 32 && bomberman.getY()
              - BombPower.get(i).getY() > 0)) {
        BombCount = BombCount + 1;
        BombermanGame.getStillObjects().remove(BombPower.get(i));
        BombPower.remove(BombPower.get(i));
        scores += 1000;
      } else if ((BombPower.get(i).getX() - bomberman.getX() < 20
          && BombPower.get(i).getX() - bomberman.getX() > 0) && ((bomberman.getY())
      ) == BombPower.get(i).getY()) {
        BombCount = BombCount + 1;
        BombermanGame.getStillObjects().remove(BombPower.get(i));
        BombPower.remove(BombPower.get(i));
        scores += 1000;
      }
    }

    for (int i = 0; i < BombList.size(); i++) {
      if (BombList.get(i).is_exploded()) {
        Flame[] fl = BombList.get(i).get_flames();
        for (int z = 0; z < fl.length; z++) {
          fl[z].update();
        }
      }
    }

    for (int i = 0; i < BombListOfEnemy.size(); i++) {
      if (BombListOfEnemy.get(i).is_exploded()) {
        Flame[] fl = BombListOfEnemy.get(i).get_flames();
        for (int z = 0; z < fl.length; z++) {
          fl[z].update();
        }
      }
    }

    entities.forEach(Entity::update);
    BombList.forEach(Entity::update);
    BombListOfEnemy.forEach(Entity::update);
    for (int i = 0; i < BrickListExplode.size(); i++) {
      BrickListExplode.get(i).update();
      if (BrickListExplode.get(i).get_timeToDisapear() == 0) {
        BrickListExplode.remove(BrickListExplode.get(i));
      }
    }
    for (int i = 0; i < BrickListExplode.size(); i++) {
      if (BrickListExplode.get(i).isRemoved() == true) {
        BrickListExplode.remove(BrickListExplode.get(i));
      }
    }
    for (Entity g : EntitiesRemove) {
      if (entities.remove(g)) {
        if (g instanceof Balloom) {
          scores += 100;
        } else if (g instanceof EnemyWithTwoLife) {
          scores += 200;
        } else if (g instanceof EnemyWithBomb) {
          scores += 300;
        } else if (g instanceof Oneal) {
          scores += 400;
        }
      }
    }
  }

  public void menuGame() throws IOException {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    Font font = new Font("Arial", 80);
    gc.setFont(font);
    gc.setFill(Color.YELLOW);
    gc.fillText("BOMBER MAN", 220, 100);
    Font font1 = new Font("Arial", 25);
    gc.setFont(font1);
    gc.fillText("Press Enter to start", canvas.getWidth() / 2 - 110, canvas.getHeight() / 2);
    gc.setFill(Color.WHITE);
    gc.fillText("Made by team16", canvas.getWidth() / 2 - 100, canvas.getHeight() - 50);
  }

  public void playAgain() throws IOException {
    if (stateTh < 300) {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      Font font = new Font("Arial", 30);
      gc.setFont(font);
      gc.setFill(Color.WHITE);
      gc.fillText("Stage " + level, canvas.getWidth() / 2 - 64, canvas.getHeight() / 2);
    } else {
      time = 200;
      printStage = false;
      stateTh = 0;
      BombCount = BombSet;
      BombCountOfEnemy = BombSet;
      BombRadius = 1;
      entities.clear();
      stillObjects.clear();
      data.clear();
      portalObjects.clear();
      BombList.clear();
      BombListOfEnemy.clear();
      BrickListExplode.clear();
      EntitiesRemove.clear();
      FlamePowers.clear();
      SpeedPower.clear();
      BombPower.clear();
      BombListOfEnemy.clear();
      preEvent = null;
      bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), this);
      entities.add(bomberman);
      createMap();
    }
  }

  public void printStage() throws IOException {
    // dang lam
    if (stateTh < 300) {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      Font font = new Font("Arial", 30);
      gc.setFont(font);
      gc.setFill(Color.WHITE);
      gc.fillText("Stage " + level, canvas.getWidth() / 2 - 64, canvas.getHeight() / 2);
     /* gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      Font font = new Font("Arial", 80);
      gc.setFont(font);
      gc.setFill(Color.YELLOW);
      gc.fillText("BOMBER MAN", 200, 100);
      Font font1 = new Font("Arial", 25);
      gc.setFont(font1);
      gc.fillText("Press Enter to start", canvas.getWidth() / 2 - 130, canvas.getHeight() / 2);
      gc.setFill(Color.WHITE);
      gc.fillText("Made by team16", 0, canvas.getHeight()); */
    } else {
      stateTh = 0;
      entities.add(bomberman);
      createMap();
    }
  }

  public void printLose() throws IOException {
    if (stateTh < 300) {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      Font font = new Font("Arial", 30);
      gc.setFont(font);
      gc.setFill(Color.WHITE);
      gc.fillText("GAME OVER ", canvas.getWidth() / 2 - Sprite.SCALED_SIZE * 3,
          canvas.getHeight() / 2);
    }
  }

  public void createMap() throws IOException {
    BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(new FileInputStream("res/levels/Level" + level + ".txt")));
    bufferedReader.readLine();
    String line = bufferedReader.readLine();
    while (!Objects.equals(line, "")) {
      data.add(line);
      line = bufferedReader.readLine();
    }
    for (int i = 0; i < 31; i++) {
      for (int j = 0; j < 13; j++) {
        Entity object;
        Entity objectGrass = new Grass(i, j, Sprite.grass.getFxImage());
        Entity objectBrick = new Brick(i, j, Sprite.brick.getFxImage());
        if (data.get(j).charAt(i) == '#') {
          object = new Wall(i, j, Sprite.wall.getFxImage());
          stillObjects.add(object);
        } else if (data.get(j).charAt(i) == '*') {
          object = new Brick(i, j, Sprite.brick.getFxImage());
          stillObjects.add(objectGrass);
          stillObjects.add(object);
        } else if (data.get(j).charAt(i) == 'x') {
          object = new Grass(i, j, Sprite.portal.getFxImage());
          portalObjects.add(object);
          stillObjects.add(objectGrass);
          stillObjects.add(object);
          stillObjects.add(objectBrick);
        } else if (data.get(j).charAt(i) == 'f') {
          object = new Grass(i, j, Sprite.powerup_flames.getFxImage());
          FlamePowers.add(object);
          stillObjects.add(objectGrass);
          stillObjects.add(object);
          stillObjects.add(objectBrick);
        }
        //TODO: b - Bomb Item
        //TODO: f - Flame Item
        //TODO: s - Speed Item

        else if (data.get(j).charAt(i) == 'b') {
          object = new Grass(i, j, Sprite.powerup_bombs.getFxImage());
          BombPower.add(object);
          stillObjects.add(objectGrass);
          stillObjects.add(object);
          stillObjects.add(objectBrick);
        } else if (data.get(j).charAt(i) == 's') {
          object = new Grass(i, j, Sprite.powerup_speed.getFxImage());
          SpeedPower.add(object);
          stillObjects.add(objectGrass);
          stillObjects.add(object);
          stillObjects.add(objectBrick);
        } else if (data.get(j).charAt(i) == '1') {
          object = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
//          if (i == 7 && j == 4) {
//            object.setY(object.getY() + 4);
//          }
          stillObjects.add(objectGrass);
          entities.add(object);
        } else if (data.get(j).charAt(i) == '2') {
          object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
          stillObjects.add(objectGrass);
          entities.add(object);
        } else if (data.get(j).charAt(i) == '3') {
          object = new EnemyWithTwoLife(i, j, Sprite.doll_left1.getFxImage());
          stillObjects.add(objectGrass);
          entities.add(object);
        } else if (data.get(j).charAt(i) == '4') {
          object = new EnemyWithBomb(i, j, Sprite.minvo_left1.getFxImage());
          stillObjects.add(objectGrass);
          entities.add(object);
        } else {
          stillObjects.add(objectGrass);
        }
      }
    }
    Audio m = new Audio();
    asSoundTrack = null;
    apSoundTrack = AudioPlayer.player;
    asSoundTrack = m.soundTrack();
    apSoundTrack.start(asSoundTrack);
  }

  public static List<Brick> getBrickListExplode() {
    return BrickListExplode;
  }

  public static void setBrickListExplode(List<Brick> brickListExplode) {
    BrickListExplode = brickListExplode;
  }

  public void render() {
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    gc.setFill(Color.PALEGREEN);
    gc.fillRect(0, 0, canvas.getWidth(), Sprite.SCALED_SIZE * 2);
    Font font = new Font("Arial", 40);
    gc.setFont(font);
    gc.setFill(Color.BLACK);
    gc.fillText("TIME: " + time, 16, 48);
    gc.fillText("SCORES: " + scores, canvas.getWidth() / 2 - Sprite.SCALED_SIZE * 3, 48);
    gc.fillText("LEFT: " + left, canvas.getWidth() - Sprite.SCALED_SIZE * 5, 48);

    stillObjects.forEach(g -> g.render(gc));
    entities.forEach(g -> g.render(gc));
    BombList.forEach(g -> g.render(gc));
    BombListOfEnemy.forEach(g -> g.render(gc));
    EnemyWithBomb ENB = null;

    for (int l = 0; l < BombermanGame.getEntities().size(); l++) {
      if (entities.get(l) instanceof EnemyWithBomb) {
        ENB = (EnemyWithBomb) entities.get(l);
      }
    }

    for (int i = 0; i < BombList.size(); i++) {
      if (BombList.get(i).is_exploded()) {
        int IndexXOfBomb = BombList.get(i).getX() / Sprite.SCALED_SIZE;
        int IndexYOfBomb = BombList.get(i).getY() / Sprite.SCALED_SIZE;
        if (bomberman.getX() / Sprite.SCALED_SIZE == IndexXOfBomb
            && bomberman.getY() / Sprite.SCALED_SIZE == IndexYOfBomb) {
          bomberman.setExposedToBomb(true);
          bomberman.render(gc);
        }
        EnemyWithTwoLife bl = null;
        //TODO: AM THANH BOMB
        if (BombList.get(i).isStartAudioFirst()) {
          Audio m = new Audio();
          AudioStream as = null;
          AudioPlayer ap = AudioPlayer.player;
          as = m.explosionSound();
          ap.start(as);
          BombList.get(i).setStartAudioFirst(false);
        }
//        try {
//          Thread.sleep(2000);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
        Flame[] fl = BombList.get(i).get_flames();
        for (int j = 0; j < fl.length; j++) {
          FlameSegment[] fls = fl[j].get_flameSegments();
          for (int k = 0; k < fls.length; k++) {
            fls[k].set_animate(BombList.get(i).get_animate());
            fls[k].render(gc);

            /**
             *             * @return hướng đi   lên/phải/xuống/trái    tương ứng với các giá trị 0/1/2/3
             */
            if (fls[k].get_direction() == 0) {
              if (((bomberman.getX()) / Sprite.SCALED_SIZE
                  == fls[k].getX() / Sprite.SCALED_SIZE)
                  && (fls[k].getY() - bomberman.getY()
                  < 32 && fls[k].getY() - bomberman.getY()
                  > 0)) {
                stateTh++;
              }
            } else if (fls[k].get_direction() == 1) {
              if ((bomberman.getX() - fls[k].getX() < 32
                  && bomberman.getX() - fls[k].getX() > 0)
                  && ((bomberman.getY())
                  / Sprite.SCALED_SIZE) == fls[k].getY() / Sprite.SCALED_SIZE) {
                stateTh++;
              }
            } else if (fls[k].get_direction() == 2) {
              if (((bomberman.getX()) / Sprite.SCALED_SIZE
                  == fls[k].getX() / Sprite.SCALED_SIZE)
                  && (bomberman.getY()
                  - fls[k].getY() < 32 && bomberman.getY()
                  - fls[k].getY() > 0)) {
                stateTh++;
              }
            } else if (fls[k].get_direction() == 3) {
              if ((fls[k].getX() - bomberman.getX() < 20
                  && fls[k].getX() - bomberman.getX() > 0)
                  && ((bomberman.getY())
              ) == fls[k].getY()) {
                stateTh++;
              }
            }

            for (int t = 0; t < BombListOfEnemy.size(); t++) {
              if (BombListOfEnemy.get(t).getX() / Sprite.SCALED_SIZE
                  == fls[k].getX() / Sprite.SCALED_SIZE &&
                  BombListOfEnemy.get(t).getY() / Sprite.SCALED_SIZE
                      == fls[k].getY() / Sprite.SCALED_SIZE) {
                BombListOfEnemy.get(t).set_timeToExplode(0);
              }
            }
            for (int e = i; e < BombList.size(); e++) {
              if (e < BombList.size()) {
                if (BombList.get(e).getX() / Sprite.SCALED_SIZE
                    == fls[k].getX() / Sprite.SCALED_SIZE &&
                    BombList.get(e).getY() / Sprite.SCALED_SIZE
                        == fls[k].getY() / Sprite.SCALED_SIZE) {
                  BombList.get(e).set_timeToExplode(0);
                }
              }
            }
            for (int l = 0; l < BombermanGame.getEntities().size(); l++) {
              if ((this.getEntities().get(l).getX() / Sprite.SCALED_SIZE == IndexXOfBomb &&
                  this.getEntities().get(l).getY() / Sprite.SCALED_SIZE == IndexYOfBomb) || (
                  fls[k].getX() / Sprite.SCALED_SIZE
                      == this.getEntities().get(l).getX() / Sprite.SCALED_SIZE
                      && fls[k].getY() / Sprite.SCALED_SIZE
                      == this.getEntities().get(l).getY() / Sprite.SCALED_SIZE)) {

                //TODO: Xét enemy 2 mạng, xong tạm thời nhớ để ý khi muốn giết con nào thì phải dùng hàm  kill

                if (this.getEntities().get(l) instanceof EnemyWithTwoLife) {
                  bl = (EnemyWithTwoLife) this.getEntities().get(l);
                  bl.setIsExposeToflame(true);
                  if (!bl.isTwolife()) {
                    bl.kill();
                  }
                }
                if (this.getEntities().get(l) instanceof Bomber) {
                  bomberman.setExposedToBomb(true);
                  if (bomberman.isRemoved()) {
                    this.getEntities().remove(bomberman);
                  }
                }

                if (!entities.isEmpty() && !(entities.get(l) instanceof EnemyWithTwoLife)) {
                  entities.get(l).kill();
                }
              }
              entities.get(l).render(gc);
            }

            for (int n = 0; n < this.getStillObjects().size(); n++) {
              if (fls[k].getX() / Sprite.SCALED_SIZE
                  == this.getStillObjects().get(n).getX() / Sprite.SCALED_SIZE
                  && fls[k].getY() / Sprite.SCALED_SIZE
                  == this.getStillObjects().get(n).getY() / Sprite.SCALED_SIZE) {
                if (this.getStillObjects().get(n) instanceof Brick) {
                  Brick br = (Brick) this.getStillObjects().get(n);
                  br.set_destroyed(false);
                  BrickListExplode.add(br);
                  this.stillObjects.remove(this.stillObjects.get(n));
                }
              }
            }
          }
        }

        if (BombList.get(i).get_timeToRenderFlameSegment() == 0) {
          BombList.remove(BombList.get(i));
          BombCount++;
          if (bl != null) {
            if (bl.isIsExposeToflame()) {
              bl.setTwolife(false);
            }
          }
        }
      }
    }

    /**
     *  //TODO: Xu ly bomb cua enemy day ne
     */

    if (BombCountOfEnemy >= 1 && BombCountOfEnemy < 2) {
      if (ENB != null) {
        BombermanGame.getBombListOfEnemy().add(
            new Bomb(ENB.getX() / Sprite.SCALED_SIZE, ENB.getY() / Sprite.SCALED_SIZE,
                Sprite.bomb.getFxImage(), BombermanGame.getBombRadius()));
      }
      BombCountOfEnemy--;
    }

    for (int i = 0; i < BombListOfEnemy.size(); i++) {
      if (BombListOfEnemy.get(i).is_exploded()) {

        Flame[] fl = BombListOfEnemy.get(i).get_flames();

        for (int j = 0; j < fl.length; j++) {

          FlameSegment[] fls = fl[j].get_flameSegments();
          fl[j].set_radius(1);
          for (int k = 0; k < fls.length; k++) {

            fls[k].set_animate(BombListOfEnemy.get(i).get_animate());
            fls[k].render(gc);
            for (int t = 0; t < BombList.size(); t++) {
              if (BombList.get(t).getX() / Sprite.SCALED_SIZE
                  == fls[k].getX() / Sprite.SCALED_SIZE
                  &&
                  BombList.get(t).getY() / Sprite.SCALED_SIZE
                      == fls[k].getY() / Sprite.SCALED_SIZE) {
                BombList.get(t).set_timeToExplode(0);
              }
            }
            for (int l = 0; l < BombermanGame.getEntities().size(); l++) {
              if (fls[k].get_direction() == 0) {
                if (((bomberman.getX()) / Sprite.SCALED_SIZE
                    == fls[k].getX() / Sprite.SCALED_SIZE)
                    && (fls[k].getY() - bomberman.getY()
                    < 32 && fls[k].getY() - bomberman.getY()
                    > 0)) {
                  stateTh++;
                }
              } else if (fls[k].get_direction() == 1) {
                if ((bomberman.getX() - fls[k].getX() < 32
                    && bomberman.getX() - fls[k].getX() > 0)
                    && ((bomberman.getY())
                    / Sprite.SCALED_SIZE) == fls[k].getY() / Sprite.SCALED_SIZE) {
                  stateTh++;
                }
              } else if (fls[k].get_direction() == 2) {
                if (((bomberman.getX()) / Sprite.SCALED_SIZE
                    == fls[k].getX() / Sprite.SCALED_SIZE)
                    && (bomberman.getY()
                    - fls[k].getY() < 32 && bomberman.getY()
                    - fls[k].getY() > 0)) {
                  stateTh++;
                }
              } else if (fls[k].get_direction() == 3) {
                if ((fls[k].getX() - bomberman.getX() < 20
                    && fls[k].getX() - bomberman.getX() > 0)
                    && ((bomberman.getY())
                ) == fls[k].getY()) {
                  stateTh++;
                }
              }
            }

            for (int n = 0; n < this.getStillObjects().size(); n++) {

              if (fls[k].getX() / Sprite.SCALED_SIZE
                  == this.getStillObjects().get(n).getX() / Sprite.SCALED_SIZE
                  && fls[k].getY() / Sprite.SCALED_SIZE
                  == this.getStillObjects().get(n).getY() / Sprite.SCALED_SIZE) {
                if (this.getStillObjects().get(n) instanceof Brick) {
                  Brick br = (Brick) this.getStillObjects().get(n);
                  br.set_destroyed(false);
                  BrickListExplode.add(br);
                  this.stillObjects.remove(this.stillObjects.get(n));
                }
              }
            }
          }
        }
        if (BombListOfEnemy.get(i).get_timeToRenderFlameSegment() == 0) {
          BombListOfEnemy.remove(BombListOfEnemy.get(i));
        }
      }
    }
    BrickListExplode.forEach(g -> g.render(gc));
    if (BombCountOfEnemy == 0 && BombListOfEnemy.size() == 0) {
      BombCountOfEnemy = 1;
    }
  }

  public static List<String> getData() {
    return data;
  }

  public void setData(List<String> data) {
    BombermanGame.data = data;
  }

  public static List<Entity> getEntities() {
    return entities;
  }

  public static void setEntities(List<Entity> entities) {
    BombermanGame.entities = entities;
  }

  public static List<Entity> getStillObjects() {
    return stillObjects;
  }

  public static void setStillObjects(List<Entity> stillObjects) {
    BombermanGame.stillObjects = stillObjects;
  }

  public static int getStateTh() {
    return stateTh;
  }
}

