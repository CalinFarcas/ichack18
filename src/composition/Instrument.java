package composition;

import jm.music.data.Part;
import jm.music.data.Score;

public abstract class Instrument {

  private final int instrument;
  private int numNotes;
  private int[] pitches;
  private double[] lengths;
  private double volume;
  private int baseNote;

  protected Instrument(int instrument, int baseNote, Mode mode) {
    this.instrument = instrument;
  }

  public void setParameters(int numNotes, int[] pitches, double[] lengths, double volume) {
    this.numNotes = numNotes;
    this.pitches = pitches;
    this.lengths = lengths;
    this.volume = volume;
  }

  public void addPartToScore(Score score) {
    //TODO
    Part part = new Part("Instrument", instrument, 1);
  }
}
