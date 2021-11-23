package uet.oop.bomberman.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class PathFinder {
  public void ShortestP(Vert sourceV) {
    sourceV.setDist(0);
    PriorityQueue<Vert> priorityQueue = new PriorityQueue<>();
    priorityQueue.add(sourceV);
    sourceV.setVisited(true);

    while (!priorityQueue.isEmpty()) {
      Vert actualVertex = priorityQueue.poll();
      for (Edge edge : actualVertex.getList()) {
        Vert v = edge.getTargetVert();

        if (!v.Visited()) {
          double newDistance = actualVertex.getDist()
              + edge.getWeight();
          if (newDistance < v.getDist()) {
            priorityQueue.remove(v);
            v.setDist(newDistance);
            v.setPr(actualVertex);
            priorityQueue.add(v);
          }
        }
      }
      actualVertex.setVisited(true);
    }
  }

  public List<Vert> getShortestP(Vert targetVertex) {
    List<Vert> path = new ArrayList<>();
    for (Vert vertex = targetVertex; vertex != null; vertex = vertex.getPr()) {
      path.add(vertex);
    }
//    Collections.reverse(path);
    return path;
  }
}