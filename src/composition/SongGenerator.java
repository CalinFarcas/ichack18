package composition;

import jm.music.data.Score;

public class SongGenerator {

  private double speed;
  private Instrument guitarInstrument;
  private Instrument bassInstrument;
  private Instrument saxInstrument;
  private final DrumMachine drumMachine;
  private boolean isGuitar;
  private boolean isBass;
  private boolean isSax;
  private boolean isDrums;
  private int baseNote;
  private Mode mode;

  public SongGenerator() {
    speed = 120;
    drumMachine = new DrumMachine();
  }

  public void initGeneralParameters(int baseNote, Mode mode) {
    this.baseNote = baseNote;
    this.mode = mode;
    System.out.println(mode.toString());
  }

  public void initGuitar(int numNotes, int[] pitches, double[] lengths, double volume, int instrument) {
    guitarInstrument = new GuitarInstrument(instrument);
    isGuitar = true;
    guitarInstrument.setParameters(numNotes, pitches, lengths, volume);
    guitarInstrument.setBaseNoteAndMode(baseNote, mode);
  }

  public void initBass(int numNotes, int[] pitches, double[] lengths, double volume, int instrument) {
    bassInstrument = new BassInstrument(instrument);
    isBass = true;
    bassInstrument.setParameters(numNotes, pitches, lengths, volume);
    bassInstrument.setBaseNoteAndMode(baseNote, mode);
  }

  public void initSax(int numNotes, int[] pitches, double[] lengths, double volume, int instrument) {
    saxInstrument = new SaxInstrument(instrument);
    isSax = true;
    saxInstrument.setParameters(numNotes, pitches, lengths, volume);
    saxInstrument.setBaseNoteAndMode(baseNote, mode);
  }

  public void initDrums(double speed, double volume) {
    isDrums = true;
    this.speed = speed;
  }

  public Score generateSong(double duration) {
    Score score = new Score();
    score.setTempo(speed * 200);

    if (isDrums) {
      drumMachine.addPartToScore(score, duration);
    }

    if (isGuitar) {
      guitarInstrument.addPartToScore(score, duration);
    }

    if (isBass) {
      bassInstrument.addPartToScore(score, duration);
    }

    if (isSax) {
      saxInstrument.addPartToScore(score, duration);
    }

    return score;
  }
}
