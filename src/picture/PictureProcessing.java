package picture;

import composition.SongGenerator;
import jm.music.data.Score;
import jm.util.Play;

import static picture.Utils.createPicture;
import static picture.Utils.loadPicture;
import static picture.Utils.savePicture;

public class PictureProcessing {

  public static void saveZones(String source) {

    Picture picture = loadPicture(source);

    ImageAnalyzer analyzer = new ImageAnalyzer(picture);

    analyzer.createAllZones();
    SongGenerator songGenerator = new SongGenerator();
    analyzer.initGlobalVariants(songGenerator);
    analyzer.initInstruments(songGenerator);

    Score score = songGenerator.generateSong();
    Play.midi(score);

    Picture savedPicture = createPicture(picture.getWidth(), picture.getHeight());

    for (Zone zone : analyzer.getZones()) {
      for (Coords coords : zone.getCoordsList()) {
        savedPicture.setPixel(coords.getX(), coords.getY(), zone.getAvgMyColor());
      }
    }

    savePicture(savedPicture, "testpics/generatedZones.png");
  }
}
