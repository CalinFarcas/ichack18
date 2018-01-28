package composition;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.Pitches.REST;

public class DrumMachine {

  private boolean[] whereKick;
  private boolean[] whereSnare;
  private boolean[] whereHats;

  public void addPartToScore(Score score, double duration) {
    // drums
    Part kickPart = new Part("Kick", 0, 9);
    Part snarePart = new Part("Snare", 1, 9);
    Part hatsPart = new Part("Hats", 2, 9);
    Phrase kickPhrase = new Phrase();
    Phrase snarePhrase = new Phrase();
    Phrase hatsPhrase = new Phrase();

    // kick
    for(int i=0;i<24;i++) {
      if (whereKick[i]) {
        Note n = new Note(36,1.0);
        kickPhrase.addNote(n);
      }
    }

    //snare
    for(int i=0;i<24;i++) {
      if (whereSnare[i]) {
        Note r = new Note(REST, 0.5);
        snarePhrase.addNote(r);
        Note n = new Note(38, 0.5);
        snarePhrase.addNote(n);
      }
    }

    //hats
    for(int i=0;i<24;i++) {
      if (whereHats[i]) {
        Note n = new Note(42, 1.0, (int) (Math.random() * 127));
        hatsPhrase.addNote(n);
      }
    }

    //pack into s score
    kickPart.addPhrase(kickPhrase);
    snarePart.addPhrase(snarePhrase);
    hatsPart.addPhrase(hatsPhrase);

    Mod.repeat(kickPart, (int) (duration / 12));
    Mod.repeat(snarePart, (int) (duration / 12));
    Mod.repeat(hatsPart, (int) (duration / 12));

    score.addPart(kickPart);
    score.addPart(snarePart);
    score.addPart(hatsPart);
  }

  public void setBeat(boolean[] whereKick, boolean[] whereSnare, boolean[] whereHats) {
    this.whereKick = whereKick;
    this.whereSnare = whereSnare;
    this.whereHats = whereHats;
  }
}
