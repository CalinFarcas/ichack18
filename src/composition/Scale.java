package composition;

import java.util.List;

public class Scale {

  private final List<Integer> notes;

  public Scale(List<Integer> notes) {
    this.notes = notes;
  }

  public int getNote(int note) {
    return notes.get(note % notes.size());
  }
}
