package composition;

import jm.music.data.Phrase;
import jm.music.tools.Mod;

public class PhraseGenerator {

  public static Phrase generatePhrase(int numNotes, Scale scale, int[] degrees, double[] lengths, int repetitions) {
    int[] pitches = new int[numNotes];

    for (int i = 0; i < numNotes; i++) {
      pitches[i] = scale.getNote(degrees[i]);
    }

    Phrase result = new Phrase();
    result.addNoteList(pitches, lengths);
    Mod.repeat(result, repetitions);
    return result;
  }
}
