package composition;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.ProgramChanges.BASS;

public class BassInstrument extends Instrument {

  public BassInstrument(int instrument) {
    super(instrument);
  }

  @Override
  public void addPartToScore(Score score, double duration) {
    Part part = new Part("Bass", instrument, channel++);
    Phrase phrase = PhraseGenerator.generatePhrase(numNotes,
        ScaleGenerator.scale(baseNote, mode), pitches, lengths, (int) (duration / 4));

    Mod.transpose(phrase, -36);

    part.addPhrase(phrase);
    part.setDynamic(40 + (int) (volume * 20));
    score.addPart(part);
  }
}
