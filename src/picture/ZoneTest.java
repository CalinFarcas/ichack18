package picture;

import static picture.Utils.*;

public class ZoneTest {
  public static void main(String[] args) {
    // args 0 = source

    Picture picture = loadPicture(args[0]);

    ImageAnalyzer analyzer = new ImageAnalyzer(picture);

    analyzer.createAllZones();

    Picture savedPicture = createPicture(picture.getWidth(), picture.getHeight());

    for (Zone zone : analyzer.getZones()) {
      for (Coords coords : zone.getCoordsList()) {
        savedPicture.setPixel(coords.getX(), coords.getY(), zone.getAvgMyColor());
      }
    }

    savePicture(savedPicture, "testpics/generatedZones.png");

  }
}
