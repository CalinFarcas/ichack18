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

  public void initGeneralParameters(int baseNote, Mode mode) {
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

  public void initDrums(double speed, double volume) {
    isDrums = true;
    this.speed = speed;
  }

  public Score generateSong() {
    Score score = new Score();
    score.setTempo(speed * 400);

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
