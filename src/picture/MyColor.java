package picture;

import java.awt.*;

/**
 * Encapsulate the colours using the RGB direct color-model. The individual red,
 * green and blue components of a colour are assigned a value ranging from 0 to
 * 255. A component value of 0 signifies no contribution is made to the color.
 */
public class MyColor {

  /**
   * the intensity of the red component
   */
  private int red;

  /**
   * the intensity of the green component
   */
  private int green;

  /**
   * the intensity of the blue component
   */
  private int blue;

  private final static float BIAS = 0.1F;

  /**
   * Default Construct. Construct a new MyColor object with the specified
   * intensity values for the red, green and blue components.
   *
   * @param red
   *          the intensity of the red component contributed to this MyColor.
   * @param green
   *          the intensity of the green component contributed to this MyColor.
   * @param blue
   *          the intensity of the blue component contributed to this MyColor.
   */
  public MyColor(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Return the contribution of the red component to <tt>this</tt> MyColor.
   *
   * @return the intensity of the red component.
   */
  public int getRed() {
    return red;
  }

  /**
   * Return the contribution of the green component to <tt>this</tt> MyColor.
   *
   * @return the intensity of the green component.
   */
  public int getGreen() {
    return green;
  }

  /**
   * Return the contribution of the blue component to <tt>this</tt> MyColor.
   *
   * @return the intensity of the blue component.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Set the contribution of the red component to <tt>this</tt> MyColor.
   *
   * @param red
   *          the new intensity value of the red component.
   */
  public void setRed(int red) {
    this.red = red;
  }

  /**
   * Set the contribution of the green component to <tt>this</tt> MyColor.
   *
   * @param green
   *          the new intensity value of the green component.
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   * Set the contribution of the blue component to <tt>this</tt> MyColor.
   *
   * @param blue
   *          the new intensity value of the blue component.
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

  public boolean isSimilarRGB(MyColor pixel) {
    return  (this.red - BIAS) <= pixel.getRed() && (this.red + BIAS) >= pixel.getRed() &&
            (this.blue - BIAS) <= pixel.getBlue() && (this.blue + BIAS) >= pixel.getBlue() &&
            (this.green - BIAS) <= pixel.getGreen() && (this.green + BIAS) >= pixel.getGreen();
  }

  public boolean isSimilar(MyColor pixel) {
    float[] thisHSV = Color.RGBtoHSB(red, green, blue, null);
    float[] pixelHSV = Color.RGBtoHSB(pixel.getRed(), pixel.getGreen(), pixel.getBlue(), null);

    return Math.abs(thisHSV[0] - pixelHSV[0]) <= BIAS && Math.abs(thisHSV[2] - pixelHSV[2]) <= BIAS + 0.3;
  }
}
