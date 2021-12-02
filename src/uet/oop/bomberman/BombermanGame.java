package uet.oop.bomberman;
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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.OldCode.graphics.Sprite;
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
  private static List<Brick> BrickListExplode = new ArrayList<>();
  private static List<Entity> portalObjects = new ArrayList<>();
  private static List<Entity> FlamePowers = new ArrayList<>();
  private static List<Entity> SpeedPower = new ArrayList<>();
  private static List<Entity> BombPower = new ArrayList<>();
  private static List<Entity> EntitiesRemove = new ArrayList<>();
  private static List<String> data = new ArrayList<>();


  private Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(),this);
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
  //private int stateTh = 1;


  private static final int BombSet =1;
  public static int BombCount = BombSet;
  public static int BombRadius = 1;


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

    canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
    gc = canvas.getGraphicsContext2D();

    //TODO: Tao root container

    Group root = new Group();
    root.getChildren().add(canvas);

    //  TODO: Tao scene

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
          case SPACE:
            if (preEvent != null) {
              if (preEvent.getCode() != event.getCode()) {
                bomberman.setTimes(0);
              }
            }
            preEvent = event;
            bomberman.PlaceBomb();
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
                    && entities.get(i).getY() < bomberman.getY() + Sprite.SCALED_SIZE ) {
              bomberman.setImg(Sprite.player_dead1.getFxImage());
              stateTh++;
            }
          }
          //Tao va cham voi bomb
          for (int i = 0 ; i < BombList.size();i++){
            if(BombList.get(i).is_exploded())
            {
              Flame[] fl = BombList.get(i).get_flames();
              for(int j = 0 ; j< fl.length; j++){
                FlameSegment [] fls = fl[j].get_flameSegments();

                for(int k = 0 ; k < fls.length; k++){

                  for (int m = 0 ; m < BombermanGame.getEntities().size();m++){
                    if(fls[k].getX() / Sprite.SCALED_SIZE == bomberman.getX() / Sprite.SCALED_SIZE
                            && fls[k].getY() / Sprite.SCALED_SIZE == bomberman.getY() / Sprite.SCALED_SIZE){

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
//      public void handle(long l) {
//        render();
//        startTime = l;
//        if (entities.isEmpty()) {
//          try {
//            printStage(stage);
//          } catch (IOException e) {
//            e.printStackTrace();
//          }
//          stateTh++;
//          return;
//        }
//        if (!printStage) {
//          render();
//        }
//        if (stateTh >= 23 && stateTh <= 43) {
//          bomberman.setImg(Sprite.player_dead2.getFxImage());
//          stateTh++;
//        } else if (stateTh >= 44 && stateTh <= 64) {
//          bomberman.setImg(Sprite.player_dead3.getFxImage());
//          stateTh++;
//        } else if (stateTh == 1) {
//          for (int i = 1; i < entities.size(); i++) {
//            if (entities.get(i).getX() > bomberman.getX() - Sprite.SCALED_SIZE
//                && entities.get(i).getX() < bomberman.getX() + 20
//                && entities.get(i).getY() > bomberman.getY() - Sprite.SCALED_SIZE
//                && entities.get(i).getY() < bomberman.getY() + Sprite.SCALED_SIZE) {
//              bomberman.setImg(Sprite.player_dead1.getFxImage());
//              stateTh++;
//            }
//          }
//        } else if (stateTh >= 2 && stateTh <= 22) {
//          bomberman.setImg(Sprite.player_dead1.getFxImage());
//          stateTh++;
//        } else {
//          entities.remove(bomberman);
//        }
//        update();
//      }
//    };
//    timer.start();
//    entities.add(bomberman);
//    createMap();
//  }
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
    bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(),this);
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
        new InputStreamReader(new FileInputStream("res/levels/Level1.txt")));
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
        //public static Sprite powerup_bombs = new Sprite(DEFAULT_SIZE, 0, 10, SpriteSheet.tiles, 16, 16);
        //  public static Sprite powerup_flames = new Sprite(DEFAULT_SIZE, 1, 10, SpriteSheet.tiles, 16, 16);
        //  public static Sprite powerup_speed = new Sprite(DEFAULT_SIZE, 2, 10, SpriteSheet.tiles, 16, 16);
        //  public static Sprite powerup_wallpass = new Sprite(DEFAULT_SIZE, 3, 10, SpriteSheet.tiles, 16,
        //      16);
        //  public static Sprite powerup_detonator = new Sprite(DEFAULT_SIZE, 4, 10, SpriteSheet.tiles, 16,
        //      16);
        //  public static Sprite powerup_bombpass = new Sprite(DEFAULT_SIZE, 5, 10, SpriteSheet.tiles, 16,
        //      16);
        //  public static Sprite powerup_flamepass = new Sprite(DEFAULT_SIZE, 6, 10, SpriteSheet.tiles, 16,
        //      16);
        else if (data.get(j).charAt(i) == 'b') {
          object = new Grass(i, j, Sprite.powerup_bombs.getFxImage());
          BombPower.add(object);
          stillObjects.add(objectGrass);
          stillObjects.add(object);
          stillObjects.add(objectBrick);
        }
        else if (data.get(j).charAt(i) == 's') {
          object = new Grass(i, j, Sprite.powerup_speed.getFxImage());
          SpeedPower.add(object);
          stillObjects.add(objectGrass);
          stillObjects.add(object);
          stillObjects.add(objectBrick);
        }
        else if (data.get(j).charAt(i) == '1') {
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
    //    for (int i = 0 ; i < BombermanGame.getStillObjects().size();i++){
//        if(BombermanGame.getStillObjects().get(i).getX()/ Sprite.SCALED_SIZE == this.getX() / Sprite.SCALED_SIZE && BombermanGame.getStillObjects().get(i).getY()/  Sprite.SCALED_SIZE == this.getY() / Sprite.SCALED_SIZE){
//          if(BombermanGame.getStillObjects().get(i).getImg().equals(Sprite.powerup_flames.getFxImage()) ){
//            System.out.println("TIm thay Powerup flame");
//            for (int j = 0 ; j < BombermanGame.getBombList().size();j++){
//              BombermanGame.getBombList().get(j).setBombRadius(BombermanGame.getBombList().get(j).getBombRadius() +1);
//            }
//            BombermanGame.getStillObjects().remove(BombermanGame.getStillObjects().get(i));
//          }
//          if(BombermanGame.getStillObjects().get(i).getImg().equals(Sprite.powerup_bombs.getFxImage()) ){
//            System.out.println("TIm thay Powerup flame");
//            BombermanGame.BombCount = BombermanGame.BombCount +1;
//            BombermanGame.getStillObjects().remove(BombermanGame.getStillObjects().get(i));
//          }
//          if(BombermanGame.getStillObjects().get(i).getImg().equals(Sprite.powerup_speed.getFxImage())){
//            System.out.println("TIm thay Powerup flame");
//              this.speed+= 4;
//              BombermanGame.getStillObjects().remove(BombermanGame.getStillObjects().get(i));
//          }
//          if(BombermanGame.getStillObjects().get(i).getImg() != (Sprite.grass.getFxImage()) ){
//            System.out.println("khong phai roi");
//          }
//        }
//    }
    for(int i = 0 ; i < FlamePowers.size();i++){
      if(bomberman.getX() / Sprite.SCALED_SIZE == FlamePowers.get(i).getX() / Sprite.SCALED_SIZE
      && bomberman.getY() / Sprite.SCALED_SIZE == FlamePowers.get(i).getY() / Sprite.SCALED_SIZE){
            BombRadius++;
            BombermanGame.getStillObjects().remove(FlamePowers.get(i));
            FlamePowers.remove(FlamePowers.get(i));
      }
    }


    for(int i = 0 ; i < SpeedPower.size();i++){
      if(bomberman.getX() / Sprite.SCALED_SIZE == SpeedPower.get(i).getX() / Sprite.SCALED_SIZE
              && bomberman.getY() / Sprite.SCALED_SIZE == SpeedPower.get(i).getY() / Sprite.SCALED_SIZE){
             bomberman.setSpeed(bomberman.getSpeed()+4);
        BombermanGame.getStillObjects().remove(SpeedPower.get(i));
        SpeedPower.remove(SpeedPower.get(i));
      }
    }

    for(int i = 0 ; i < BombPower.size();i++){
      if(bomberman.getX() / Sprite.SCALED_SIZE == BombPower.get(i).getX() / Sprite.SCALED_SIZE
              && bomberman.getY() / Sprite.SCALED_SIZE == BombPower.get(i).getY() / Sprite.SCALED_SIZE){
              BombCount = BombCount +1;
        BombermanGame.getStillObjects().remove(BombPower.get(i));
        BombPower.remove(BombPower.get(i));
      }

    }
   //TODO: CHUA XONG
//    for (int h = 0 ; h < entities.size();h++) {
//      if (entities.get(h).isREMOVEFIXPROTECTTED()) {
//        if (entities.get(h).get_timeToDisapear() == 0) {
//          entities.remove(entities.get(h));
//        }
//      }
//    }
    for (int i = 0 ; i < BombList.size();i++) {
      if (BombList.get(i).is_exploded()) {
        Flame[] fl = BombList.get(i).get_flames();
        for (int z = 0; z < fl.length; z++) {
          fl[z].update();
        }
      }
    }
    entities.forEach(Entity::update);
    BombList.forEach(Entity::update);

    for(int i = 0 ; i < BrickListExplode.size();i++)
    {
      BrickListExplode.get(i).update();
      System.out.println(BrickListExplode.get(i).get_timeToDisapear() + " Thoi gian nay");

      if(BrickListExplode.get(i).get_timeToDisapear() == 0) {
        BrickListExplode.remove(BrickListExplode.get(i));
      }
    }
    for (int i = 0 ; i < BrickListExplode.size();i++){
         if(BrickListExplode.get(i).isRemoved() == true) {
           BrickListExplode.remove(BrickListExplode.get(i));
           System.out.println("Chay den day");
         }
    }
   for (Entity g: EntitiesRemove){
     entities.remove(g);
   }
  }

  public void render() {

    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    stillObjects.forEach(g -> g.render(gc));
    entities.forEach(g -> g.render(gc));
    BombList.forEach(g -> g.render(gc));

    for (int i = 0 ; i < BombList.size();i++){
      if(BombList.get(i).is_exploded())
      {
        Flame[] fl = BombList.get(i).get_flames();
          for (int j = 0; j < fl.length; j++) {
            FlameSegment[] fls = fl[j].get_flameSegments();
            for (int k = 0; k < fls.length; k++) {
              fls[k].set_animate(BombList.get(i).get_animate());
              fls[k].render(gc);
              for (int l = 0; l < BombermanGame.getEntities().size(); l++) {
                if (fls[k].getX() / Sprite.SCALED_SIZE == this.getEntities().get(l).getX() / Sprite.SCALED_SIZE
                        && fls[k].getY() / Sprite.SCALED_SIZE == this.getEntities().get(l).getY() / Sprite.SCALED_SIZE) {

                  //TODO: Xét enemy 2 mạng,CHUA XONG DAU
                  if (this.getEntities().get(l) instanceof Balloom) {
                    Balloom bl = (Balloom) this.getEntities().get(l);
                    //this.getEntities().remove(bl);
                    if (bl.isTwolife()) {
                      this.getEntities().remove(bl);
                      bl.setTwolife(false);
                      entities.add(bl);
                    }
                    if (!bl.isTwolife()) {
                      entities.get(l).remove();
                    }
                  } else if (this.getEntities().get(l) instanceof Bomber) {
                    bomberman.setExposedToBomb(true);
                    if (bomberman.isRemoved()) this.getEntities().remove(bomberman);
                  }
                   // entities.get(l).setREMOVEFIXPROTECTTED(true);
                  if(!entities.isEmpty())
                  entities.get(l).kill();
                }
              }
              for (int n = 0; n < this.getStillObjects().size(); n++) {
                if (fls[k].getX() / Sprite.SCALED_SIZE == this.getStillObjects().get(n).getX() / Sprite.SCALED_SIZE
                        && fls[k].getY() / Sprite.SCALED_SIZE == this.getStillObjects().get(n).getY() / Sprite.SCALED_SIZE) {
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

        BombCount++;
        if(BombList.get(i).get_timeToRenderFlameSegment() == 0){
        BombList.remove(BombList.get(i));
        }
      }
    }
    BrickListExplode.forEach(g -> g.render(gc));
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

