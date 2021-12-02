package uet.oop.bomberman.codeOld;

public class BombRadiusPU extends AbstractPowerup {

  public BombRadiusPU(int rowIndex, int colIndex) {
    super(colIndex, rowIndex);
  }

  public void addToPlayer(Player player) {
    int currentExplosionRadius = player.getExplosionRadius();
    player.setExplosionRadius(currentExplosionRadius + 1);
  }

  public String getName() {
    final String name = "BombRadius";
    return name;
  }
}
