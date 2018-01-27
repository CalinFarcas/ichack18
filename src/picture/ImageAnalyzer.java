package picture;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ImageAnalyzer {

  private List<Zone> zones;
  private Picture image;
  private int[][] visitedPixels;
  private int zoneNumber;

  public ImageAnalyzer(Picture image) {
    this.image = image;
    this.zones = new ArrayList<>();
    this.visitedPixels = new int[image.getWidth()][image.getHeight()];
  }

  private Zone createZone(Coords startCoords) {
    zoneNumber++;
    Zone zone = new Zone();
    MyColor color = image.getPixel(startCoords);

    int avgRed = 0;
    int avgBlue = 0;
    int avgGreen = 0;
    int pixelNo = 0;

    Deque<Coords> queue = new ArrayDeque<>();
    queue.add(startCoords);

    // bfs
    while (queue.size() > 0) {
      List<Coords> alikePixels = getAlikePixels(color, queue.pop()); // gets only unvisited

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
      zone.setAvgMyColor(new MyColor(avgRed / pixelNo, avgGreen / pixelNo, avgBlue / pixelNo));
    }

    return zone;
  }

  public void createAllZones() {
    while (findNextUnvisited() != null) {
      zones.add(createZone(findNextUnvisited()));
    }
  }

  private Coords findNextUnvisited() {
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        if (visitedPixels[i][j] == 0) {
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
  private List<Coords> getAlikePixels(MyColor color, Coords pixel) {
    List<Coords> pixels = new ArrayList<>();
    MyColor pixelMyColor;

    // get all neighbours w similar colour
    for (int i = pixel.getX() - 1; i <= pixel.getX() + 1; i++) {
      for (int j = pixel.getY() - 1; j <= pixel.getY() + 1; j++) {
        try {
          if (visitedPixels[i][j] == 0) {
            pixelMyColor = image.getPixel(i, j);
            if (pixelMyColor.isSimilar(color)) {
              pixels.add(new Coords(i, j));
              visitedPixels[i][j] = zoneNumber;
            }
          }
        } catch (IndexOutOfBoundsException e) {
          // skip
        }
      }
    }

    return pixels;
  }

  public void printArray() {
    for (int i = 0; i < visitedPixels[0].length; i++) {
      for (int j = 0; j < visitedPixels.length; j++) {
        System.out.print(visitedPixels[j][i] + " ");
      }
      System.out.println();
    }
  }
}
