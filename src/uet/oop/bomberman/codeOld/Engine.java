package uet.oop.bomberman.codeOld;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Engine {

  private static final int TIME_STEP = 30;
  private static int width = 10;
  private static int height = 10;
  private static int sizeEnemies = 5;
  private static Timer clockTimer = null;

  private Engine() {
  }

  public static void main(String[] args) {
    startGame();
  }

  public static void startGame() {
    Floor floor = new Floor(width, height, sizeEnemies);
    BombermanFrame frame = new BombermanFrame("Bomberman", floor);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    floor.addFloorListener(frame.getBombermanComponent());

    Action doOneStep = new AbstractAction() {
      public void actionPerformed(ActionEvent e) {
        tick(frame, floor);
      }
    };
    clockTimer = new Timer(TIME_STEP, doOneStep);
    clockTimer.setCoalesce(true);
    clockTimer.start();
  }

  private static void gameOver(BombermanFrame frame, Floor floor) {
    clockTimer.stop();
    frame.dispose();
    startGame();
  }

  private static void tick(BombermanFrame frame, Floor floor) {
    if (floor.getIsGameOver()) {
      gameOver(frame, floor);
    } else {
      floor.moveEnemies();
      floor.bombCountdown();
      floor.explosionHandler();
      floor.characterInExplosion();
      floor.notifyListeners();
    }
  }
}
