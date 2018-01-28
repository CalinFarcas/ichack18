package composition;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.ProgramChanges.BASS;

public class BassInstrument extends Instrument {

  public BassInstrument() {
    super(BASS);
  }

  @Override
  public void addPartToScore(Score score) {
    Part part = new Part("Bass", instrument, channel++);
    Phrase phrase = PhraseGenerator.generatePhrase(numNotes,
        ScaleGenerator.scale(baseNote, mode), pitches, lengths, 4);

    Mod.transpose(phrase, -36);

    part.addPhrase(phrase);
    score.addPart(part);
  }
}
