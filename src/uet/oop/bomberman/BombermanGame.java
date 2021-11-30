package uet.oop.bomberman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Balloom;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Oneal;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

  public static final int WIDTH = 31;
  public static final int HEIGHT = 13;

  private GraphicsContext gc;
  private Canvas canvas;
  private static List<Entity> entities = new ArrayList<>();
  private static List<Entity> stillObjects = new ArrayList<>();
  private static List<Entity> portalObjects = new ArrayList<>();
  private Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
  private static List<String> data = new ArrayList<>();
  private KeyEvent preEvent = null;
  private static int stateTh = 1;
  private boolean printStage = false;
  private int level = 1;
  private int FPS = 30;
  private double averageFPS;
  private long startTime;
  private long URDTimeMillis;
  private long waitTime;
  private long totalTime = 0;

  private int frameCount = 0;
  private int maxFrameCount = 30;

  private long targetTime = 1000 / FPS;

  public static void main(String[] args) {
    Application.launch(BombermanGame.class);
  }

  @Override
  public void start(Stage stage) {
    // Tao Canvas
    canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
    gc = canvas.getGraphicsContext2D();

    // Tao root container
    Group root = new Group();
    root.getChildren().add(canvas);

    // Tao scene
    Scene scene = new Scene(root);
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP:
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveUp();
            }
            break;
          case DOWN:
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveDown();
            }
            break;
          case LEFT:
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveLeft();
            }
            break;
          case RIGHT:
            if (stateTh == 1) {
              if (preEvent != null) {
                if (preEvent.getCode() != event.getCode()) {
                  bomberman.setTimes(0);
                }
              }
              preEvent = event;
              bomberman.moveRight();
            }
            break;
        }
      }
    });

    // Them scene vao stage
    stage.setScene(scene);
    stage.show();

    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(long l) {

        startTime = l;

        if (entities.isEmpty()) {
          try {
            printStage(stage);
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
          if (entities.size() == 1) {
            for (int i = 0; i < portalObjects.size(); i++) {
              if (portalObjects.get(i).getX() == bomberman.getX()
                  && portalObjects.get(i).getX() == bomberman.getY()) {
                level = 2;
                stateTh = 66;
                break;
              }
            }
          }
        } else if (stateTh >= 2 && stateTh <= 22) {
          bomberman.setImg(Sprite.player_dead1.getFxImage());
          stateTh++;
        } else if (stateTh >= 65 && stateTh <= 155) {
          if (stateTh == 66) {
            printStage = true;
          }
          if (stateTh == 65) {
            entities.remove(bomberman);
          }
          stateTh++;
        } else {
          try {
            playAgain();
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
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        totalTime += System.nanoTime() - startTime;
        frameCount++;
        if (frameCount == maxFrameCount) {
          averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
          frameCount = 0;
          totalTime = 0;
          System.out.println(averageFPS);
        }
      }
    };
    timer.start();
  }

  public void playAgain() throws IOException {
    // dang lam
    if (stateTh < 300) {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      Font font = new Font("Arial", 30);
      gc.setFont(font);
      gc.setFill(Color.WHITE);
      gc.fillText("Stage " + level, canvas.getWidth() / 2 - 64, canvas.getHeight() / 2);
    } else {
      printStage = false;
      stateTh = 0;
      entities.clear();
      stillObjects.clear();
      bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
      data.clear();
      preEvent = null;
      entities.add(bomberman);
      createMap();
    }
  }

  public void printStage(Stage stage) throws IOException {
    // dang lam
    if (stateTh < 300) {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
      Font font = new Font("Arial", 30);
      gc.setFont(font);
      gc.setFill(Color.WHITE);
      gc.fillText("Stage " + level, canvas.getWidth() / 2 - 64, canvas.getHeight() / 2);
    } else {
      stateTh = 0;
      entities.add(bomberman);
      createMap();
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
        Entity objectBrick = new Wall(i, j, Sprite.brick.getFxImage());
        if (data.get(j).charAt(i) == '#') {
          object = new Wall(i, j, Sprite.wall.getFxImage());
          stillObjects.add(object);
        } else if (data.get(j).charAt(i) == '*') {
          object = new Wall(i, j, Sprite.brick.getFxImage());
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
          stillObjects.add(object);
          stillObjects.add(objectBrick);
        } else if (data.get(j).charAt(i) == '1') {
          object = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
          stillObjects.add(objectGrass);
          entities.add(object);
        } else if (data.get(j).charAt(i) == '2') {
          object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
          stillObjects.add(objectGrass);
          entities.add(object);
        } else {
          stillObjects.add(objectGrass);
        }
      }
    }
  }

  public void update() {
    entities.forEach(Entity::update);
  }

  public void render() {
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    stillObjects.forEach(g -> g.render(gc));
    entities.forEach(g -> g.render(gc));
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
