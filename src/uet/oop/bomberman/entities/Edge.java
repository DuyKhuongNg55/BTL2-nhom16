package uet.oop.bomberman.entities;

public class Edge {
  private double weight;
  private Vert startVert;
  private Vert targetVert;

  public Edge(double weight, Vert startVert, Vert targetVert) {
    this.weight = weight;
    this.startVert = startVert;
    this.targetVert = targetVert;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public Vert getStartVert() {
    return startVert;
  }

  public void setStartVert(Vert startVert) {
    this.startVert = startVert;
  }

  public Vert getTargetVert() {
    return targetVert;
  }

  public void setTargetVert(Vert targetVert) {
    this.targetVert = targetVert;
  }
}