package picture;

import static picture.Utils.*;

public class ZoneTest {
  public static void main(String[] args) {
    // java ZoneTest source

    Picture picture = loadPicture(args[0]);

    ImageAnalyzer analyzer = new ImageAnalyzer(picture);

    analyzer.createAllZones();
    analyzer.printArray();

  }
}
