import composition.Mode;
import composition.PhraseGenerator;
import composition.ScaleGenerator;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.util.Play;

import static jm.constants.Pitches.*;
import static jm.constants.ProgramChanges.*;

public class Main {

  public static void main(String[] args) {
    Score score = new Score();
    Part testPart = new Part("Penis", GUITAR, 1);


    testPart.add(PhraseGenerator.generatePhrase(8, ScaleGenerator.scale(D4, Mode.PHRYGIAN),
        new int[]{0, 1, 2, 3, 4, 5, 6, 7}, new double[]{0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}, 1));

    score.addPart(testPart);
    Play.midi(score);
  }
}
