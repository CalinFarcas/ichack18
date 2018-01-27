import inst.SawtoothInst;
import jm.JMC;
import jm.music.data.*;
import jm.music.tools.Mod;
import jm.util.*;
import jm.audio.*;

import static jm.constants.Chords.MAJOR;

public final class Test implements JMC{

  public static void main(String[] args){

    // the score for the song
    Score score = new Score();

    Part bass = new Part("Bass", BASS, 0);
    Part guitar = new Part("Guitar", FLUTE, 1);

    // bass
    double[] bassRhythm = {QUARTER_NOTE, QUARTER_NOTE, QUARTER_NOTE, QUARTER_NOTE, QUARTER_NOTE, QUARTER_NOTE, QUARTER_NOTE, QUARTER_NOTE};
    int[] bassNotes = {C1, C2, C1, C2, C1, C2, C1, C2};
    Phrase bassPhrase = new Phrase();
    bassPhrase.addNoteList(bassNotes, bassRhythm);
    bass.add(bassPhrase);
    Mod.repeat(bassPhrase, 2);
    score.add(bass);

    // guitar
    double[] guitarRhythm = {HALF_NOTE, HALF_NOTE, HALF_NOTE, HALF_NOTE};
    int[] chord1 = {C3, C3 + MAJOR[0], C3 + MAJOR[1]};
    int[] chord2 = {D3, A3, C4};
    int[] chord3 = {G3, B3, D3};
    int[] chord4 = {A3, C4, G4};

    CPhrase guitarPhrase = new CPhrase();
    guitarPhrase.addChord(chord1, guitarRhythm[0]);
    guitarPhrase.addChord(chord2, guitarRhythm[1]);
    guitarPhrase.addChord(chord3, guitarRhythm[2]);
    guitarPhrase.addChord(chord4, guitarRhythm[3]);
    guitar.addCPhrase(guitarPhrase);
    Mod.repeat(guitar, 2);
    score.add(guitar);

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

    View.show(score, 50, 20);
    Play.midi(score);
  }

}