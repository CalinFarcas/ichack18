package composition;

import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

import static jm.constants.Pitches.REST;

public class DrumMachine {

  public void addPartToScore(Score score) {
    // drums
    Part kickPart = new Part("Kick", 0, 9);
    Part snarePart = new Part("Snare", 1, 9);
    Part hatsPart = new Part("Hats", 2, 9);
    Phrase kickPhrase = new Phrase();
    Phrase snarePhrase = new Phrase();
    Phrase hatsPhrase = new Phrase();

    // kick
    for(int i=0;i<2;i++) {
      Note n = new Note(36,2.0);
      kickPhrase.addNote(n);
    }

    //snare
    for(int i=0;i<2;i++) {
      Note r = new Note(REST,1.0);
      snarePhrase.addNote(r);
      Note n = new Note(38,1.0);
      snarePhrase.addNote(n);
    }

    //hats
    for(int i=0;i<16;i++) {
      Note n = new Note(42,0.25, (int)(Math.random()*127));
      hatsPhrase.addNote(n);
    }

    //pack into s score
    kickPart.addPhrase(kickPhrase);
    snarePart.addPhrase(snarePhrase);
    hatsPart.addPhrase(hatsPhrase);

    Mod.repeat(kickPart, 4);
    Mod.repeat(snarePart, 4);
    Mod.repeat(hatsPart, 4);

    score.addPart(kickPart);
    score.addPart(snarePart);
    score.addPart(hatsPart);
  }
}
