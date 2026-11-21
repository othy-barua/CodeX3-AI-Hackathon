public class WeatherLagPercentage extends RiskCalculator {
    public int lagDays;

    public WeatherLagPercentage(String date, String city, String wardId, double avgTempC,
         double rainfallMm, double relativeHumidity, int lagDays) {
        super(date, city, wardId, avgTempC, rainfallMm, relativeHumidity);
        this.lagDays = lagDays;
        this.percentage = calculatePercentage();
    }

    @Override
    public double calculatePercentage() {
        double tempScore = 0.0;
        if (avgTempC >= 15.0 && avgTempC <= 40.0) {
            tempScore = ((avgTempC - 15.0) / 25.0) * 100.0;
        } else if (avgTempC > 40.0) {
            tempScore = 100.0;
        }
        double rainScore = (rainfallMm / 100.0) * 100.0;
        double humidityScore = relativeHumidity;
        double rawScore = (0.35 * tempScore) + (0.40 * rainScore) + (0.25 * humidityScore);
        this.percentage = clampPercentage(rawScore);
        return this.percentage;
    }
}
