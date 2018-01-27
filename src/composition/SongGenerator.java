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
  private int baseNote;
  private Mode mode;

  public SongGenerator() {
    speed = 120;
    guitarInstrument = new GuitarInstrument();
    bassInstrument = new BassInstrument();
    saxInstrument = new SaxInstrument();
    drumMachine = new DrumMachine();
  }

  public void initGeneralParameters(double speed, int baseNote, Mode mode) {
    this.speed = speed;
    this.baseNote = baseNote;
    this.mode = mode;
  }

  public void initGuitar(int numNotes, int[] pitches, double[] lengths, double volume) {
    isGuitar = true;
    guitarInstrument.setParameters(numNotes, pitches, lengths, volume);
    guitarInstrument.setBaseNoteAndMode(baseNote, mode);
  }

  public void initBass(int numNotes, int[] pitches, double[] lengths, double volume) {
    isBass = true;
    bassInstrument.setParameters(numNotes, pitches, lengths, volume);
    bassInstrument.setBaseNoteAndMode(baseNote, mode);
  }

  public void initSax(int numNotes, int[] pitches, double[] lengths, double volume) {
    isSax = true;
    saxInstrument.setParameters(numNotes, pitches, lengths, volume);
    saxInstrument.setBaseNoteAndMode(baseNote, mode);
  }

  public void initDrums() {
    isDrums = true;
  }

  public Score generateSong() {
    Score score = new Score();
    score.setTempo(speed * 200);

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
