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
    songGenerator.initGeneralParameters((int)avgSat*11 + 60, Mode.values()[Math.min(6, (int)(mod*8))]);
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
    List<Zone> zonez = new ArrayList<>();
    zones.sort((x, y) -> (int) (y.getConsistency() - x.getConsistency()));
    int i = 0;
    double speed = 0;
    int drumSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size() >= MIN_ZONE_SIZE) {
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        speed = (hsb[1] + hsb[2]) / 2;

        if (hsb[2] < 0.6F) {
          drumSize = zones.get(i).size();
        }
        break;
      }
    }

    ++i;

    int bassNrNotes = 0;
    int[] bassPitches = null;
    double[] bassLength = null;
    int bassSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size() >= MIN_ZONE_SIZE) {
        zonez.add(zones.get(i));

        bassSize = zones.get(i).size();
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        bassNrNotes = (int)(hsb[2]*4) + 1;
        double val = (double) hsb[0] * (double) hsb[1];
        switch (bassNrNotes) {
          case 1:
            bassPitches = new int[1];
            bassPitches[0] = (int)Math.floor(val*8);
            bassLength = new double[]{8};
            break;
          case 2:
            bassPitches = new int[2];
            bassPitches[0] = (int)Math.floor(val*8);
            bassPitches[1] = (int)Math.floor(val*8*8)%8;
            bassLength = new double[]{4,4};

            break;
          case 3:
            bassPitches = new int[3];
            bassPitches[0] = (int)Math.floor(val*8);
            bassPitches[1] = (int)Math.floor(val*8*8)%8;
            bassPitches[2] = (int)Math.floor(val*8*8*8)%8;
            bassLength = new double[]{3,3,2};

            break;
          default:
            bassPitches = new int[4];
            bassPitches[0] = (int)Math.floor(val*8);
            bassPitches[1] = (int)Math.floor(val*8*8)%8;
            bassPitches[2] = (int)Math.floor(val*8*8*8)%8;
            bassPitches[3] = (int)Math.floor(val*8*8*8*8)%8;
            bassLength = new double[]{2,2,2,2};
        }
        break;
      }
    }

    ++i;
    int[] guitarPitches = null;
    double[] guitarLength = null;
    int guitarNrNotes = 0;
    int guitarSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size() >= MIN_ZONE_SIZE) {
        zonez.add(zones.get(i));

        guitarSize = zones.get(i).size();
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        int caz = (int)(hsb[2] *6);
        double val = (double) hsb[0] * (double) hsb[1];

        switch (caz) {
          case 0:
            guitarNrNotes = 1;
            guitarPitches = new int[1];
            guitarPitches[0] = (int)Math.floor(val*8);
            guitarLength = new double[]{4};
            break;
          case 1:
            guitarNrNotes = 2;
            guitarPitches = new int[2];
            guitarPitches[0] = (int)Math.floor(val*8);
            guitarPitches[1] = (int)Math.floor(val*8*8)%8;
            guitarLength = new double[]{2,2};
            break;
          case 2:
            guitarNrNotes = 3;
            guitarPitches = new int[3];
            guitarPitches[0] = (int)Math.floor(val*8);
            guitarPitches[1] = (int)Math.floor(val*8*8)%8;
            guitarPitches[2] = (int)Math.floor(val*8*8*8)%8;
            guitarLength = new double[]{2,1,1};
            break;
          case 3:
            guitarNrNotes = 1;
            guitarPitches = new int[1];
            guitarPitches[0] = (int)Math.floor(val*8);
            guitarLength = new double[]{8};
            break;
          case 4:
            guitarNrNotes = 2;
            guitarPitches = new int[2];
            guitarPitches[0] = (int)Math.floor(val*8);
            guitarPitches[1] = (int)Math.floor(val*8*8)%8;
            guitarLength = new double[]{4,4};
            break;
          default:
            guitarNrNotes = 3;
            guitarPitches = new int[3];
            guitarPitches[0] = (int)Math.floor(val*8);
            guitarPitches[1] = (int)Math.floor(val*8*8)%8;
            guitarPitches[2] = (int)Math.floor(val*8*8*8)%8;
            guitarLength = new double[]{3,3,2};
            break;
        }
        break;
      }
    }

    i++;
    int[] saxPitches = null;
    double[] saxLength = null;
    int saxNrNotes = 0;
    int saxSize = 0;
    for(; i < zones.size(); ++i) {
      if(zones.get(i).size()>= MIN_ZONE_SIZE) {
        zonez.add(zones.get(i));

        saxSize = zones.get(i).size();
        MyColor avgColor = zones.get(i).getAvgMyColor();
        float[] hsb = new float[3];
        Color.RGBtoHSB(avgColor.getRed(), avgColor.getGreen(), avgColor.getBlue(), hsb);
        saxNrNotes = (int) (hsb[2]*4 + 5);
        double val = (double) hsb[0] * (double) hsb[1];

        switch (saxNrNotes) {
          case 5:
            saxPitches = new int[5];
            saxPitches[0] = (int)Math.floor(val*8);
            saxPitches[1] = (int)Math.floor(val*8*8)%8;
            saxPitches[2] = (int)Math.floor(val*8*8*8)%8;
            saxPitches[3] = (int)Math.floor(val*8*8*8*8)%8;
            saxPitches[4] = (int)Math.floor(val*8*8*8*8*8)%8;
            saxLength = new double[]{1,2,1,1,2};
            break;
          case 6:
            saxPitches = new int[6];
            saxPitches[0] = (int)Math.floor(val*8);
            saxPitches[1] = (int)Math.floor(val*8*8)%8;
            saxPitches[2] = (int)Math.floor(val*8*8*8)%8;
            saxPitches[3] = (int)Math.floor(val*8*8*8*8)%8;
            saxPitches[4] = (int)Math.floor(val*8*8*8*8*8)%8;
            saxPitches[5] = (int)Math.floor(val*8*8*8*8*8*8)%8;
            saxLength = new double[]{1,2,1,1,2,1};
            break;
          case 7:
            saxPitches = new int[7];
            saxPitches[0] = (int)Math.floor(val*8);
            saxPitches[1] = (int)Math.floor(val*8*8)%8;
            saxPitches[2] = (int)Math.floor(val*8*8*8)%8;
            saxPitches[3] = (int)Math.floor(val*8*8*8*8)%8;
            saxPitches[4] = (int)Math.floor(val*8*8*8*8*8)%8;
            saxPitches[5] = (int)Math.floor(val*8*8*8*8*8*8)%8;
            saxPitches[6] = (int)Math.floor(val*8*8*8*8*8*8*8)%8;
            saxLength = new double[]{1,1,1,1,1,2,1};
            break;
          default:
            saxPitches = new int[8];
            saxPitches[0] = (int)Math.floor(val*8);
            saxPitches[1] = (int)Math.floor(val*8*8)%8;
            saxPitches[2] = (int)Math.floor(val*8*8*8)%8;
            saxPitches[3] = (int)Math.floor(val*8*8*8*8)%8;
            saxPitches[4] = (int)Math.floor(val*8*8*8*8*8)%8;
            saxPitches[5] = (int)Math.floor(val*8*8*8*8*8*8)%8;
            saxPitches[6] = (int)Math.floor(val*8*8*8*8*8*8*8)%8;
            saxPitches[7] = (int)Math.floor(val*8*8*8*8*8*8*8*8)%8;
            saxLength = new double[]{1,1,1,1,1,1,1,1};
            break;
        }
        break;
      }
    }

    int maxSize = max(max(drumSize,bassSize),max(guitarSize,saxSize));
    if(drumSize != 0) {
      songGenerator.initDrums(speed, (double) drumSize/maxSize);
    } else {
      songGenerator.setSpeed(speed);
    }

    if(bassSize != 0) {
      songGenerator.initBass(bassNrNotes,bassPitches,bassLength,(double)bassSize/maxSize, zoneToInstrument(zonez.get(0)));
    }

    if(guitarSize != 0) {
      songGenerator.initGuitar(guitarNrNotes,guitarPitches,guitarLength,(double)guitarSize/maxSize, zoneToInstrument(zonez.get(1)));
    }

    if(saxSize != 0) {
      songGenerator.initSax(saxNrNotes,saxPitches,saxLength,(double)saxSize/maxSize, zoneToInstrument(zonez.get(2)));
    }
  }

  private int zoneToInstrument(Zone zone) {
    MyColor avgCol = zone.getAvgMyColor();
    return (int) (Color.RGBtoHSB(avgCol.getRed(), avgCol.getGreen(), avgCol.getBlue(), null)[0] * 127);
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
