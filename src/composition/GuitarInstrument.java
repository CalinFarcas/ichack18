package composition;

import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.ProgramChanges.GUITAR;

public class GuitarInstrument extends Instrument {

  public GuitarInstrument(int instrument) {
    super(instrument);
  }

  @Override
  public void addPartToScore(Score score, double duration) {
    Part part = new Part("Guitar", instrument, channel++);
    CPhrase phrase = PhraseGenerator.generateCPhrase(numNotes,
        ScaleGenerator.scale(baseNote, mode), pitches, lengths, 1);

    part.addCPhrase(phrase);
    part.setDynamic(40 + (int) (volume * 20));
    Mod.repeat(part, (int) (duration / 4));
    score.addPart(part);
  }
}
