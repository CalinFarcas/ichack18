package composition;

public enum Mode {

  IONIAN(0, 2, 4, 5, 7, 9, 11),
  DORIAN(0, 2, 3, 5, 7, 9, 10),
  PHRYGIAN(0, 1, 3, 5, 7, 8, 10),
  LYDIAN(0, 2, 4, 6, 7, 9, 11),
  MIXOLYDIAN(0, 2, 4, 5, 7, 9, 10),
  AEOLIAN(0, 2, 3, 5, 7, 8, 10),
  LOCRIAN(0, 1, 3, 5, 6, 8, 10);

  private final int[] intervals;

  Mode(int... intervals) {
    this.intervals = intervals;
  }

  public int[] getIntervals() {
    return intervals;
  }
}
