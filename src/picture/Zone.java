package picture;

import java.util.*;

public class Zone {

  private Color avgColor;
  private List<Coords> coordsList;

  public Zone() {
    this.coordsList = new ArrayList<>();
    this.avgColor = new Color(0,0,0);
  }

  public Color getAvgColor() {
    return avgColor;
  }

  public List<Coords> getCoordsList() {
    return coordsList;
  }

  public void setAvgColor(Color avgColor) {
    this.avgColor = avgColor;
  }

  public void add(Coords coords) {
    coordsList.add(coords);
  }

}
