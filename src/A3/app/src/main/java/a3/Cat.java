package a3;

public class Cat implements Comparable<Cat> {
  private String name;
  private int monthHired;
  private int furThickness;
  private int daysToNextGrooming;
  private double expectedGroomingCost;

  public Cat(
      String name, int monthHired, int furThickness, int daysToNextGrooming, double groomingCost) {
    this.name = name;
    this.monthHired = monthHired;
    this.furThickness = furThickness;
    this.daysToNextGrooming = daysToNextGrooming;
    this.expectedGroomingCost = groomingCost;
  }

  public String toString() {
    String result = this.name + "(" + this.monthHired + " , " + this.furThickness + ")";
    return result;
  }

  public int getMonthHired() {
    return this.monthHired;
  }

  public int getFurThickness() {
    return this.furThickness;
  }

  public int getDaysToNextGrooming() {
    return this.daysToNextGrooming;
  }

  public double getExpectedGroomingCost() {
    return this.expectedGroomingCost;
  }

  // "Note: this class has a natural ordering that is inconsistent with equals."
  public int compareTo(Cat d) {
    int seniority = d.monthHired - this.monthHired; // positive if this has seniority
    return seniority;
  }

  public boolean equals(Object obj) {
    return obj instanceof Cat
        && this.compareTo((Cat) obj) == 0
        && this.furThickness == ((Cat) obj).furThickness
        && this.name.equals(((Cat) obj).name);
  }
}
