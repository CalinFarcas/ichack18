package composition;

import jm.music.data.CPhrase;
import jm.music.data.Phrase;
import jm.music.tools.Mod;

import static jm.constants.Pitches.REST;

public class PhraseGenerator {

  public static Phrase generatePhrase(int numNotes, Scale scale, int[] degrees, double[] lengths, int repetitions) {
    int[] pitches = new int[numNotes];

    for (int i = 0; i < numNotes - 1; i++) {
      pitches[i] = scale.getNote(degrees[i]);
    }

    pitches[numNotes - 1] = REST;

    Phrase result = new Phrase();
    result.addNoteList(pitches, lengths);
    Mod.repeat(result, repetitions);
    return result;
  }

  public static CPhrase generateCPhrase(int numNotes, Scale scale, int[] degrees, double[] lengths, int repetitions) {
    int[][] chords = new int[numNotes][3];

    for (int i = 0; i < numNotes; i++) {
      chords[i] = ChordGenerator.generateTriad(scale, degrees[i]);
    }

    CPhrase result = new CPhrase();

    for (int i = 0; i < numNotes; i++) {
      result.addChord(chords[i], lengths[i]);
    }

    Mod.repeat(result, repetitions);
    return result;
  }
}
