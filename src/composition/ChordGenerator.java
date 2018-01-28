package composition;

public class ChordGenerator {

  public static int[] generateTriad(Scale scale, int degree) {
    int[] notes = new int[3];

    for (int i = 0; i < 3; i++) {
      notes[i] = scale.getNote(degree + i);
    }

    return notes;
  }
}
