public class main {
   
    public static void main(String[] args) {
        
     {

        String date;
        String city;
        String wardId;

        double avgTempC;
        double maxTempC;
        double minTempC;
        double rainfallMm;
        double relativeHumidity;

        double lag2WeekRainMm;
        double lag3WeekRainMm;
        double lag4WeekRainMm;

        double rainLagWeightPct;
        double tempWeightPct;
        double humidityWeightPct;

        double populationDensitySqkm;
        double waterLoggingRiskIndex;
        double sanitationScore;

        double densityWeightPct;
        double waterLoggingWeightPct;
        double sanitationWeightPct;

        int dengueCases;

        double weatherSharePct;
        double vulnerabilitySharePct;


       
        WeatherLagPercentage weather = new WeatherLagPercentage(
                date, city, wardId,
                avgTempC, maxTempC, minTempC,
                rainfallMm, relativeHumidity,
                lag2WeekRainMm, lag3WeekRainMm, lag4WeekRainMm,
                rainLagWeightPct, tempWeightPct, humidityWeightPct
            );

        
        double weatherPercentage = weather.calculatePercentage();


        
        VulnerabilityPercentage vulnerability =  new VulnerabilityPercentage(
                date, city, wardId,
                avgTempC, rainfallMm, relativeHumidity,
                populationDensitySqkm,
                waterLoggingRiskIndex,
                sanitationScore,
                densityWeightPct,
                waterLoggingWeightPct,
                sanitationWeightPct
            );

       
        double vulnerabilityPercentage =
            vulnerability.calculatePercentage();


        // 3. Create FinalRiskAssessment object
        FinalRiskAssessment assessment =  new FinalRiskAssessment(
                date, city, wardId,
                avgTempC, rainfallMm, relativeHumidity,
                dengueCases,
                weatherPercentage,
                vulnerabilityPercentage,
                weatherSharePct,
                vulnerabilitySharePct
            );

        // Calculate final risk percentage
        assessment.calculatePercentage();

        // Determine risk tier
        assessment.determineRiskTier();

        // Find main driver
        assessment.findMainDriver();


        // 4. Display final results
        System.out.println("===== DENGUE RISK ASSESSMENT =====");

        System.out.println(assessment.getSummary());

        System.out.println("==================================");
    
}
}
