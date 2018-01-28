package composition;

import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.ProgramChanges.BASS;

public abstract class Instrument {

  protected final int instrument;
  protected int numNotes;
  protected int[] pitches;
  protected double[] lengths;
  protected double volume;
  protected int baseNote;
  protected Mode mode;
  protected static int channel = 0;

  protected Instrument(int instrument) {
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

  public  abstract void addPartToScore(Score score, double duration);
}
