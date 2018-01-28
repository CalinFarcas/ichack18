package picture;

import composition.Mode;
import composition.SongGenerator;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static java.lang.Math.*;

public class ImageAnalyzer {

  private static final int MIN_ZONE_SIZE = 20;
  private List<Zone> zones;
  private Picture image;
  private int[][] visitedPixels;
  private int zoneNumber;

  public ImageAnalyzer(Picture image) {
    this.image = image;
    this.zones = new ArrayList<>();
    this.visitedPixels = new int[image.getWidth()][image.getHeight()];
  }

  public void initGlobalVariants(SongGenerator songGenerator) {
    long nrHue = 0;
    long nrSat = 0;
    long nrLight  = 0;
    float avgSat = 0;
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        MyColor color = image.getPixel(i,j);
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(),hsb);
        if(hsb[0] <= 0.7F && hsb[0] > 0.2F) {
          ++nrHue;
        }
        if(hsb[1] > 0.5F) {
          ++nrSat;
        }
        if(hsb[2] > 0.5F) {
          ++nrLight;
        }
        avgSat+=hsb[1];
      }
    }

    System.out.println(nrHue);
    System.out.println(nrSat);
    System.out.println(nrLight);

    int nrPixels = image.getHeight()*image.getWidth();
    avgSat /= nrPixels;
    double mod = sqrt(cbrt((double) (nrHue * nrSat * nrLight))/ (double) nrPixels);
    System.out.println(mod);
    songGenerator.initGeneralParameters((int)avgSat*11 + 60, Mode.values()[Math.min(6, (int)(mod*7))]);
  }

  private Zone createZone(Coords startCoords) {
    zoneNumber++;
    Zone zone = new Zone();
    MyColor color = image.getPixel(startCoords);

    int avgRed = 0;
    int avgBlue = 0;
    int avgGreen = 0;
    int pixelNo = 0;
    double avgHue = 0;

    Deque<Coords> queue = new ArrayDeque<>();
    queue.add(startCoords);

    // bfs
    while (queue.size() > 0) {
      List<Coords> alikePixels = getAlikePixels(color, queue.pop()); // gets only unvisited

      for (Coords alikePixel : alikePixels) {
        // add to zone and color avg totals
          zone.add(alikePixel);
          MyColor pixel = image.getPixel(alikePixel);
          avgRed += pixel.getRed();
          avgBlue += pixel.getBlue();
          avgGreen += pixel.getGreen();
          float[] hsb = new float[3];
          Color.RGBtoHSB(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),hsb);
          avgHue += hsb[0];
          pixelNo++;
      }

      queue.addAll(alikePixels);
    }

    if (pixelNo > 0) {
      zone.setAvgMyColor(new MyColor(avgRed / pixelNo, avgGreen / pixelNo, avgBlue / pixelNo));

      avgHue /= pixelNo;
      double consistency = 0;

      queue = new ArrayDeque<>();
      queue.add(startCoords);

      // bfs
      while (queue.size() > 0) {
        List<Coords> alikePixels = getAlikePixels(color, queue.pop()); // gets only unvisited

        for (Coords alikePixel : alikePixels) {
          MyColor pixel = image.getPixel(alikePixel);
          float[] hsb = new float[3];
          Color.RGBtoHSB(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),hsb);
          consistency += Math.abs(avgHue - hsb[0]);
        }

        queue.addAll(alikePixels);
      }
      consistency /= pixelNo;
      zone.setConsistency(consistency);
    }

    return zone;
  }

  public void createAllZones() {
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        if (visitedPixels[i][j] == 0) {
          zones.add(createZone(new Coords(i, j)));
        }
      }
    }
  }

  public List<Zone> getZones() {
    return zones;
  }

  // only gets alike pixels that arent visited!!!!!!
  private List<Coords> getAlikePixels(MyColor color, Coords pixel) {
    List<Coords> pixels = new ArrayList<>();
    MyColor pixelMyColor;

    // get all neighbours w similar colour
    for (int i = pixel.getX() - 1; i <= pixel.getX() + 1; i++) {
      for (int j = pixel.getY() - 1; j <= pixel.getY() + 1; j++) {
        try {
          if (visitedPixels[i][j] == 0) {
            pixelMyColor = image.getPixel(i, j);
            if (pixelMyColor.isSimilar(color)) {
              pixels.add(new Coords(i, j));
              visitedPixels[i][j] = zoneNumber;
            }
          }
        } catch (IndexOutOfBoundsException e) {
          // skip
        }
      }
    }

    return pixels;
  }

  public void initInstruments(SongGenerator songGenerator) {
    Zone[] zonez = new Zone[4];
    zones.sort((x, y) -> (int) (y.getConsistency() - x.getConsistency()));
    int i = 0;
    double speed = 0;
    int drumSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size() >= MIN_ZONE_SIZE) {
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        speed = Math.max((hsb[1] + hsb[2]) / 2, 0.3);
        if (hsb[2] < 0.6F) {
          drumSize = zones.get(i).size();
          zonez[0] = zones.get(i);
        }
        break;
      }
    }

    ++i;

    int[] bassPitches = null;
    double[] bassLength = null;
    int bassSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size() >= MIN_ZONE_SIZE) {
        zonez[1] = zones.get(i);

        bassSize = zones.get(i).size();
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        bassPitches = generate_pitches(hsb[0], 8);
        bassLength = generate_lengths(bassPitches.length, hsb[1] * hsb[2]);
        break;
      }
    }

    ++i;
    int[] guitarPitches = null;
    double[] guitarLength = null;
    int guitarSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size() >= MIN_ZONE_SIZE) {
        zonez[2] = zones.get(i);

        guitarSize = zones.get(i).size();
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        guitarPitches = generate_pitches(hsb[0], 10);
        guitarLength = generate_lengths(guitarPitches.length, hsb[1] * hsb[2]);
        break;
      }
    }

    i++;
    int[] saxPitches = null;
    double[] saxLength = null;
    int saxSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size()>= MIN_ZONE_SIZE) {
        zonez[3] = zones.get(i);

        saxSize = zones.get(i).size();
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        saxPitches = generate_pitches(hsb[0], 16);
        saxLength = generate_lengths(saxPitches.length, hsb[1] * hsb[2]);
        break;
      }
    }

    int maxSize = max(max(drumSize,bassSize),max(guitarSize,saxSize));
    if(drumSize != 0) {
      Zone drumZone = zonez[0];

      float[] hsb = Color.RGBtoHSB(drumZone.getAvgMyColor().getRed(), drumZone.getAvgMyColor().getGreen(), drumZone.getAvgMyColor().getBlue(), null);

      boolean[] whereKick = new boolean[24];
      boolean[] whereSnare = new boolean[24];
      boolean[] whereHats = new boolean[24];

      long pula1 = (long) Math.floor((hsb[0] * Math.pow(2, 36)));
      long pula2 = (long) Math.floor((hsb[1] * Math.pow(2, 36)));
      long pula3 = (long) Math.floor((hsb[2] * Math.pow(2, 36)));


      for (int j = 36; j > 12; j--) {
        if (((1L << j) & pula1) != 0) {
          whereKick[36 - j] = true;
        }
      }

      for (int j = 36; j > 12; j--) {
        if (((1L << j) & pula2) != 0) {
          whereSnare[36 - j] = true;
        }
      }

      for (int j = 36; j > 12; j--) {
        if (((1L << j) & pula3) != 0) {
          whereHats[36 - j] = true;
        }
      }

      songGenerator.initDrums(speed, (double)drumSize/maxSize, whereKick, whereSnare, whereHats);
    } else {
      songGenerator.setSpeed(speed);
    }

    if(bassSize != 0) {
      songGenerator.initBass(bassPitches.length,bassPitches,bassLength,(double)bassSize/maxSize, zoneToBass(zonez[1]));
    }

    if(guitarSize != 0) {
      songGenerator.initGuitar(guitarPitches.length,guitarPitches,guitarLength,(double)guitarSize/maxSize, zoneToGuitar(zonez[2]));
    }

    if(saxSize != 0) {
      songGenerator.initSax(saxPitches.length,saxPitches,saxLength,(double)saxSize/maxSize, zoneToSax(zonez[3]));
    }
  }

  private int[] generate_pitches(float x, int minimum) {
    long seed = (long) Math.floor(x * Math.pow(2, 60));
    int numNotes = Math.max((int) (seed % 20 + 1), minimum);

    int[] pitches = new int[numNotes];

    for (int i = 0; i < numNotes; i++) {
      pitches[i] = (int) (seed % 8);
      seed /= 7;
    }

    return pitches;
  }

  private double[] generate_lengths(int n, float x) {
    long seed = (long) Math.floor(x * Math.pow(2, 60));
    double[] lengths = new double[n];
    double sum = 0;
    for (int i = 0; i < n - 1; i++) {
      lengths[i] = ((seed % 31 + 1) / 8.0);
      sum += lengths[i];
      seed /= 7;
    }
    int roundToMul8 = (int) Math.ceil(sum);
    while(roundToMul8 % 8 != 0 || roundToMul8 == 0) {
      ++roundToMul8;
    }

    lengths[n - 1] = roundToMul8 - sum;

    return lengths;
  }

  private int zoneToInstrument(Zone zone) {
    MyColor avgCol = zone.getAvgMyColor();
    float ket = Color.RGBtoHSB(avgCol.getRed(), avgCol.getGreen(), avgCol.getBlue(), null)[0];
    ket *= 128;
    return (int) Math.floor(ket);
  }

  private int zoneToBass(Zone zone) {
    MyColor avgCol =  zone.getAvgMyColor();
    float hue = Color.RGBtoHSB(avgCol.getRed(), avgCol.getGreen(), avgCol.getBlue(), null)[0];
    int[] bassInstruments = new int[]{32, 33, 34, 35, 36, 37, 38, 39};
    return bassInstruments[(int) (hue * bassInstruments.length)];
  }

  private int zoneToGuitar(Zone zone) {
    MyColor avgCol =  zone.getAvgMyColor();
    float hue = Color.RGBtoHSB(avgCol.getRed(), avgCol.getGreen(), avgCol.getBlue(), null)[0];
    int[] guitarInstruments = new int[20];
    for (int i = 0; i < 20; i++) {
      guitarInstruments[i] = i + 48;
    }
    return guitarInstruments[(int) (hue * guitarInstruments.length)];
  }

  private int zoneToSax(Zone zone) {
    MyColor avgCol =  zone.getAvgMyColor();
    float hue = Color.RGBtoHSB(avgCol.getRed(), avgCol.getGreen(), avgCol.getBlue(), null)[0];
    int[] saxInstruments = new int[32];
    for (int i = 0; i < 32; i++) {
      saxInstruments[i] = i;
    }
    return saxInstruments[(int) (hue * saxInstruments.length)];
  }

  public void printArray() {
    for (int i = 0; i < visitedPixels[0].length; i++) {
      for (int j = 0; j < visitedPixels.length; j++) {
        System.out.print(visitedPixels[j][i] + " ");
      }
      System.out.println();
    }
  }
}
