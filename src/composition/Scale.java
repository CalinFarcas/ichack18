package composition;

import java.util.List;

public class Scale {

  private final List<Integer> notes;

  public Scale(List<Integer> notes) {
    this.notes = notes;
  }

  public int getNote(int note) {
    return 12 * (note / notes.size()) + notes.get(note % notes.size());
  }
}
