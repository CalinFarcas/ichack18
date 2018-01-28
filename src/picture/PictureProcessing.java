package picture;

import composition.SongGenerator;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Write;

import static picture.Utils.createPicture;
import static picture.Utils.loadPicture;
import static picture.Utils.savePicture;

public class PictureProcessing {

  public static void saveZones(String source) {

    try {
      Picture picture = loadPicture(source);
      ImageAnalyzer analyzer = new ImageAnalyzer(picture);

      analyzer.createAllZones();

      analyzer.createAllZones();
      SongGenerator songGenerator = new SongGenerator();
      analyzer.initGlobalVariants(songGenerator);
      analyzer.initInstruments(songGenerator);

      Score score = songGenerator.generateSong(128);
      Write.midi(score);
      Play.midi(score);
      Picture savedPicture = createPicture(picture.getWidth(), picture.getHeight());

      for (Zone zone : analyzer.getZones()) {
        for (Coords coords : zone.getCoordsList()) {
          savedPicture.setPixel(coords.getX(), coords.getY(), zone.getAvgMyColor());
        }
      }

      savePicture(savedPicture, "testpics/generatedZones.png");

    } catch (NullPointerException e) {
      System.out.println("null picture, try again");
    }

  }
}
