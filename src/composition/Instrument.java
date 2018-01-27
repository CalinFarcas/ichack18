package composition;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public abstract class Instrument {

  private final int instrument;
  private int numNotes;
  private int[] pitches;
  private double[] lengths;
  private double volume;
  private int baseNote;
  private Mode mode;
  private static int channel = 0;

  protected Instrument(int instrument, int baseNote, Mode mode) {
    this.instrument = instrument;
  }

  public void setParameters(int numNotes, int[] pitches, double[] lengths, double volume) {
    this.numNotes = numNotes;
    this.pitches = pitches;
    this.lengths = lengths;
    this.volume = volume;
  }

  protected void setBaseNoteAndMode(int baseNote, Mode mode) {
    this.baseNote = baseNote;
    this.mode = mode;
  }

  public void addPartToScore(Score score) {
    Part part = new Part("Instrument", instrument, channel++);
    Phrase phrase = PhraseGenerator.generatePhrase(numNotes,
        ScaleGenerator.scale(baseNote, mode), pitches, lengths, 4);
    part.addPhrase(phrase);
    score.addPart(part);
  }
}
