package composition;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.ProgramChanges.MARIMBA;
import static jm.constants.ProgramChanges.VIOLIN;

public class SaxInstrument extends Instrument {

  public SaxInstrument() {
    super(MARIMBA);
  }

  @Override
  public void addPartToScore(Score score) {
    Part part = new Part("Sax", instrument, channel++);
    Phrase phrase = PhraseGenerator.generatePhrase(numNotes,
        ScaleGenerator.scale(baseNote, mode), pitches, lengths, 4);

    part.addPhrase(phrase);
    score.addPart(part);
  }
}
