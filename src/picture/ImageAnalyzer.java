package picture;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ImageAnalyzer {

  private List<Zone> zones;
  private Picture image;

  public ImageAnalyzer(Picture image) {
    this.image = image;
    zones = new ArrayList<>();
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
      List<Coords> alikePixels = getAlikePixels(queue.pop());

      for (Coords alikePixel : alikePixels) {
        // remove already visited
        if (queue.contains(alikePixel) || zone.getCoordsList().contains(alikePixel)) {
          alikePixels.remove(alikePixel);
        } else { // add to zone and color avg totals
          zone.add(alikePixel);
          avgRed += image.getPixel(alikePixel).getRed();
          avgBlue += image.getPixel(alikePixel).getBlue();
          avgGreen += image.getPixel(alikePixel).getGreen();
          pixelNo++;
        }
      }

      queue.addAll(alikePixels);
    }

    if (pixelNo > 0) {
      zone.setAvgColor(new Color(avgRed / pixelNo, avgGreen / pixelNo, avgBlue / pixelNo));
    }

    return zone;
  }

  //bfs pe toata imaginea TODO


  private List<Coords> getAlikePixels(Coords pixel) {
    List<Coords> pixels = new ArrayList<>();
    Color pixelColor;
    Color referenceColor = image.getPixel(pixel);

    // get all neighbours w similar colour
    for (int i = pixel.getX() - 1; i <= pixel.getX() + 1; i++) {
      for (int j = pixel.getY() - 1; j <= pixel.getY() + 1; j++) {
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

    return pixels;
  }
}
