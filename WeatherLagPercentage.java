public class WeatherLagPercentage extends RiskCalculator {
    public double maxTempC;
    public double minTempC;
    public double lag2WeekRainMm;
    public double lag3WeekRainMm;
    public double lag4WeekRainMm;
    public double rainLagWeightPct;
    public double tempWeightPct;
    public double humidityWeightPct;

    public WeatherLagPercentage(String date, String city, String wardId, double avgTempC,
         double maxTempC, double minTempC, double rainfallMm,double relativeHumidity, 
         double lag2WeekRainMm,double lag3WeekRainMm, double lag4WeekRainMm, 
         double rainLagWeightPct, double tempWeightPct,double humidityWeightPct) {
        super(date, city, wardId, avgTempC, rainfallMm, relativeHumidity);
        this.maxTempC = maxTempC;
        this.minTempC = minTempC;
        this.lag2WeekRainMm = lag2WeekRainMm;
        this.lag3WeekRainMm = lag3WeekRainMm;
        this.lag4WeekRainMm = lag4WeekRainMm;
        this.rainLagWeightPct = rainLagWeightPct;
        this.tempWeightPct = tempWeightPct;
        this.humidityWeightPct = humidityWeightPct;
    }

    private double computeWeatherScore(double lagWeight, double tWeight, double hWeight) {
        double avgLagRain = (this.lag2WeekRainMm + this.lag3WeekRainMm + this.lag4WeekRainMm) / 3.0;
        double lagScore = clampPercentage((avgLagRain / 100.0) * 100.0);
        double tempScore = 0.0;
        if (this.avgTempC >= 15.0 && this.avgTempC <= 40.0) {
            tempScore = ((this.avgTempC - 15.0) / 25.0) * 100.0;
        } else if (this.avgTempC > 40.0) {
            tempScore = 100.0;
        }
        tempScore = clampPercentage(tempScore);
        double humidityScore = clampPercentage(this.relativeHumidity);
        double totalWeight = lagWeight + tWeight + hWeight;
        double rawScore = 0.0;
        if (totalWeight > 0.0) {
            rawScore = ((lagScore * lagWeight) + (tempScore * tWeight) + (humidityScore * hWeight)) / totalWeight;
        }
        double result = clampPercentage(rawScore);
        this.percentage = result;
        return this.percentage;
    }

    public static void populateWardRainfallLags(java.util.List<WeatherLagPercentage> wardRecords) {
        if (wardRecords == null || wardRecords.isEmpty()) {
            return;
        }
        wardRecords.sort(java.util.Comparator.comparing(r -> r.date));
        for (int i = 0; i < wardRecords.size(); i++) {
            WeatherLagPercentage current = wardRecords.get(i);
            current.lag2WeekRainMm = (i >= 2) ? wardRecords.get(i - 2).rainfallMm : 0.0;
            current.lag3WeekRainMm = (i >= 3) ? wardRecords.get(i - 3).rainfallMm : 0.0;
            current.lag4WeekRainMm = (i >= 4) ? wardRecords.get(i - 4).rainfallMm : 0.0;
        }
    }

    @Override
    public double calculatePercentage() {
        return computeWeatherScore(this.rainLagWeightPct, this.tempWeightPct, this.humidityWeightPct);
    }

    public double calculatePercentage(double customLagWeightPct, double customTempWeightPct, double customHumidityWeightPct) {
        return computeWeatherScore(customLagWeightPct, customTempWeightPct, customHumidityWeightPct);
    }
}