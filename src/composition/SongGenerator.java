package composition;

import jm.music.data.Score;

public class SongGenerator {

  private double speed;
  private final Instrument guitarInstrument;
  private final Instrument bassInstrument;
  private final Instrument saxInstrument;
  private final DrumMachine drumMachine;
  private boolean isGuitar;
  private boolean isBass;
  private boolean isSax;
  private boolean isDrums;

  public SongGenerator() {
    speed = 120;
    guitarInstrument = new GuitarInstrument();
    bassInstrument = new BassInstrument();
    saxInstrument = new SaxInstrument();
    drumMachine = new DrumMachine();
  }

  public void initGeneralParameters(double speed) {
    this.speed = speed;
  }

  public void initGuitar(int numNotes, int[] pitches, double[] lengths, double volume) {
    isGuitar = true;
    guitarInstrument.setParameters(numNotes, pitches, lengths, volume);
  }

  public void initBass(int numNotes, int[] pitches, double[] lengths, double volume) {
    isBass = true;
    bassInstrument.setParameters(numNotes, pitches, lengths, volume);
  }

  public void initSax(int numNotes, int[] pitches, double[] lengths, double volume) {
    isSax = true;
    saxInstrument.setParameters(numNotes, pitches, lengths, volume);
  }

  public void initDrums() {
    isDrums = true;
  }

  public Score generateSong() {
    Score score = new Score();

    if (isDrums) {
      drumMachine.addPartToScore(score);
    }

    if (isGuitar) {
      guitarInstrument.addPartToScore(score);
    }

    if (isBass) {
      bassInstrument.addPartToScore(score);
    }

    if (isSax) {
      saxInstrument.addPartToScore(score);
    }

    return score;
  }
}
