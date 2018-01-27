package picture;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ImageAnalyzer {

  private List<Zone> zones;
  private Picture image;
  private boolean[][] visitedPixels;

  public ImageAnalyzer(Picture image) {
    this.image = image;
    this.zones = new ArrayList<>();
    this.visitedPixels = new boolean[image.getWidth()][image.getHeight()];
  }

  public Zone createZone(Coords startCoords) {
    Zone zone = new Zone();

    int avgRed = 0;
    int avgBlue = 0;
    int avgGreen = 0;
    int pixelNo = 0;

    Deque<Coords> queue = new ArrayDeque<>();
    queue.add(startCoords);

    // bfs
    while (queue.size() > 0) {
      List<Coords> alikePixels = getAlikePixels(queue.pop()); // gets only unvisited

      for (Coords alikePixel : alikePixels) {
        // add to zone and color avg totals
          zone.add(alikePixel);
          avgRed += image.getPixel(alikePixel).getRed();
          avgBlue += image.getPixel(alikePixel).getBlue();
          avgGreen += image.getPixel(alikePixel).getGreen();
          pixelNo++;
      }

      queue.addAll(alikePixels);
    }

    if (pixelNo > 0) {
      zone.setAvgColor(new Color(avgRed / pixelNo, avgGreen / pixelNo, avgBlue / pixelNo));
    }

    return zone;
  }

  private void createAllZones() {
    while (findNextUnvisited() != null) {
      zones.add(createZone(findNextUnvisited()));
    }
  }

  private Coords findNextUnvisited() {
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        if (!visitedPixels[i][j]) {
          return new Coords(i, j);
        }
      }
    }
    return null;
  }

  public List<Zone> getZones() {
    return zones;
  }

  // only gets alike pixels that arent visited!!!!!!
  private List<Coords> getAlikePixels(Coords pixel) {
    List<Coords> pixels = new ArrayList<>();
    Color pixelColor;
    Color referenceColor = image.getPixel(pixel);

    // get all neighbours w similar colour
    for (int i = pixel.getX() - 1; i <= pixel.getX() + 1; i++) {
      for (int j = pixel.getY() - 1; j <= pixel.getY() + 1; j++) {
        if (!visitedPixels[i][j]) {
          try {
            pixelColor = image.getPixel(i, j);
            if (pixelColor.isSimilar(referenceColor)) {
              pixels.add(new Coords(i, j));
            }
          } catch (IndexOutOfBoundsException e) {
            // skip
          }
        }
      }
    }

    return pixels;
  }
}
