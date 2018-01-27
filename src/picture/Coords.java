package picture;

public class Coords {

  private int x;
  private int y;

  public Coords(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public boolean equals(Coords coords) {
    return this.x == coords.getX() && this.y == coords.getY();
  }
  
}
