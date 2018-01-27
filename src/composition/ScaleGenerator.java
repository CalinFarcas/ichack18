package composition;

import java.util.ArrayList;
import java.util.List;

public class ScaleGenerator {

  public static List<Integer> scale(int baseNote, Mode mode) {
    List<Integer> pitches = new ArrayList<>();

    for (int i = 0; i < mode.getIntervals().length; i++) {
      pitches.add(baseNote + mode.getIntervals()[i]);
    }

    return pitches;
  }
}
