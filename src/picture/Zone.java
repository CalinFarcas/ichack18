package picture;

import java.util.*;

public class Zone {

  private MyColor avgMyColor;
  private List<Coords> coordsList;
  private double consistency;

  public Zone() {
    this.coordsList = new ArrayList<>();
    this.avgMyColor = new MyColor(0,0,0);
    this.consistency = 0;
  }

  public MyColor getAvgMyColor() {
    return avgMyColor;
  }

  public List<Coords> getCoordsList() {
    return coordsList;
  }

  public void setAvgMyColor(MyColor avgMyColor) {
    this.avgMyColor = avgMyColor;
  }

  public void add(Coords coords) {
    coordsList.add(coords);
  }


  public double getConsistency() {
    return consistency;
  }

  public void setConsistency(double consistency) {
    this.consistency = consistency;
  }

  public int size() {
    return coordsList.size();
  }
}
