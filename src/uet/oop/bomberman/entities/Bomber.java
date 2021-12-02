package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.stream.IntStream;

public class Bomber extends Entity {

  private int speed = 4;
  private int TimeToRenderBomberman = 20;
  private boolean ExposedToBomb = false;
  private BombermanGame bbg = null;
  public int getTimeToRenderBomberman() {
    return TimeToRenderBomberman;
  }

  public void setTimeToRenderBomberman(int timeToRenderBomberman) {
    TimeToRenderBomberman = timeToRenderBomberman;
  }
  double computeSnapshotSimilarity(final Image image1, final Image image2) {
    final int width = (int) image1.getWidth();
    final int height = (int) image1.getHeight();
    final PixelReader reader1 = image1.getPixelReader();
    final PixelReader reader2 = image2.getPixelReader();

    final double nbNonSimilarPixels = IntStream.range(0, width).parallel().
            mapToLong(i -> IntStream.range(0, height).parallel().filter(j -> reader1.getArgb(i, j) != reader2.getArgb(i, j)).count()).sum();

    return 100d - nbNonSimilarPixels / (width * height) * 100d;
  }


  public boolean isExposedToBomb() {
    return ExposedToBomb;
  }

  public void setExposedToBomb(boolean exposedToBomb) {
    ExposedToBomb = exposedToBomb;
  }

  public Bomber(int x, int y, Image img, BombermanGame bbg) {
    super(x, y, img);
    this.bbg = bbg;
  }
//
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
     if(ExposedToBomb) {
       if(TimeToRenderBomberman != 0){
         TimeToRenderBomberman--;
       }
       else
         remove();
     }
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
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }



  public void PlaceBomb(){
    if(BombermanGame.BombCount > 0){
      BombermanGame.getBombList().add(new Bomb(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.bomb.getFxImage(),BombermanGame.getBombRadius()));
      BombermanGame.BombCount--;
    }
  }
  public void moveRight() {
    times++;
    if (times % 12 >= 0 && times % 12 <= 3) {
      img = Sprite.player_right.getFxImage();
    } else if (times % 12 >= 4 && times % 12 <= 7) {
      img = Sprite.player_right_1.getFxImage();
    } else {
      img = Sprite.player_right_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (x + 20 + speed > indexX * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE) {
      if (y % Sprite.SCALED_SIZE != 0) {
        return;
      }
      for (int i = (indexX + 1) * 13 + indexY; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE && BombermanGame.getStillObjects().get(i) instanceof Wall
        || BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE + Sprite.SCALED_SIZE
                && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE && BombermanGame.getStillObjects().get(i) instanceof Brick) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX() > (indexX + 2) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    x += speed;
    System.out.println(x + " " + y);
  }

  public void moveLeft() {
    times++;
    if (times % 12 >= 0 && times % 12 <= 3) {
      img = Sprite.player_left.getFxImage();
    } else if (times % 12 >= 4 && times % 12 <= 7) {
      img = Sprite.player_left_1.getFxImage();
    } else {
      img = Sprite.player_left_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (x - speed < indexX * Sprite.SCALED_SIZE) {
      if (y % Sprite.SCALED_SIZE != 0) {
        return;
      }
      for (int i = (indexX - 1) * 13 + indexY; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == (indexX - 1) * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i) instanceof Wall || BombermanGame.getStillObjects().get(i).getX() == (indexX - 1) * Sprite.SCALED_SIZE
                && BombermanGame.getStillObjects().get(i).getY() == indexY * Sprite.SCALED_SIZE
                && BombermanGame.getStillObjects().get(i) instanceof Brick) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX()
            > (indexX + 1) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    x -= speed;
    System.out.println(x + " " + y);
  }

  public void moveUp() {
    times++;
    if (times % 12 >= 0 && times % 12 <= 3) {
      img = Sprite.player_up.getFxImage();
    } else if (times % 12 >= 4 && times % 12 <= 7) {
      img = Sprite.player_up_1.getFxImage();
    } else {
      img = Sprite.player_up_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (y - speed < indexY * Sprite.SCALED_SIZE) {
      if (x - indexX * Sprite.SCALED_SIZE > 12) {
        return;
      }
      for (int i = indexX * 13 + indexY - 1; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == (indexY - 1) * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i) instanceof Wall || BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
                && BombermanGame.getStillObjects().get(i).getY() == (indexY - 1) * Sprite.SCALED_SIZE
                && BombermanGame.getStillObjects().get(i) instanceof Brick) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX()
            > (indexX + 1) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    y -= speed;
    System.out.println(x + " " + y);
  }

  public void moveDown() {
    times++;
    if (times % 12 >= 0 && times % 12 <= 3) {
      img = Sprite.player_down.getFxImage();
    } else if (times % 12 >= 4 && times % 12 <= 7) {
      img = Sprite.player_down_1.getFxImage();
    } else {
      img = Sprite.player_down_2.getFxImage();
    }
    int indexX = x / Sprite.SCALED_SIZE;
    int indexY = y / Sprite.SCALED_SIZE;
    if (y + Sprite.SCALED_SIZE + speed > (indexY + 1) * Sprite.SCALED_SIZE) {
      if (x - indexX * Sprite.SCALED_SIZE > 12) {
        return;
      }
      for (int i = indexX * 13 + indexY + 1; i < BombermanGame.getStillObjects().size(); i++) {
        if (BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i).getY() == (indexY + 1) * Sprite.SCALED_SIZE
            && BombermanGame.getStillObjects().get(i) instanceof Wall || BombermanGame.getStillObjects().get(i).getX() == indexX * Sprite.SCALED_SIZE
                && BombermanGame.getStillObjects().get(i).getY() == (indexY + 1) * Sprite.SCALED_SIZE
                && BombermanGame.getStillObjects().get(i) instanceof Brick) {
          return;
        } else if (BombermanGame.getStillObjects().get(i).getX()
            > (indexX + 1) * Sprite.SCALED_SIZE) {
          break;
        }
      }
    }
    y += speed;
    System.out.println(x + " " + y);
  }
}
