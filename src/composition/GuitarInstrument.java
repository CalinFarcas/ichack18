package composition;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.ProgramChanges.GUITAR;

public class GuitarInstrument extends Instrument {

  public GuitarInstrument() {
    super(GUITAR);
  }

  @Override
  public void addPartToScore(Score score) {
    Part part = new Part("Guitar", instrument, channel++);
    CPhrase phrase = PhraseGenerator.generateCPhrase(numNotes,
        ScaleGenerator.scale(baseNote, mode), pitches, lengths, 1);

    part.addCPhrase(phrase);
    Mod.repeat(part, 4);
    score.addPart(part);
  }
}
