public class FinalRiskAssessment extends RiskCalculator {
    public int dengueCases;
    public double weatherPercentage;
    public double vulnerabilityPercentage;
    public double weatherSharePct;
    public double vulnerabilitySharePct;
    public double finalRiskPercentage;
    public String riskTier;
    public String mainDriver;

    public FinalRiskAssessment(String date, String city, String wardId, double avgTempC,
        double rainfallMm, double relativeHumidity, int dengueCases,double weatherPercentage, 
        double vulnerabilityPercentage,double weatherSharePct, double vulnerabilitySharePct) {
        super(date, city, wardId, avgTempC, rainfallMm, relativeHumidity);
        this.dengueCases = dengueCases;
        this.weatherPercentage = weatherPercentage;
        this.vulnerabilityPercentage = vulnerabilityPercentage;
        this.weatherSharePct = weatherSharePct;
        this.vulnerabilitySharePct = vulnerabilitySharePct;
        this.finalRiskPercentage = 0.0;
        this.riskTier = "Low";
        this.mainDriver = "";
    }

    @Override
    public double calculatePercentage() {
        double totalShare = this.weatherSharePct + this.vulnerabilitySharePct;
        double combined = 0.0;
        if (totalShare > 0.0) {
            combined = ((clampPercentage(this.weatherPercentage) * this.weatherSharePct)
                    + (clampPercentage(this.vulnerabilityPercentage) * this.vulnerabilitySharePct)) / totalShare;
        }

        double result = clampPercentage(combined);
        this.finalRiskPercentage = result;
        this.percentage = result;
        return this.finalRiskPercentage;
    }

    public String determineRiskTier() {
        if (this.finalRiskPercentage >= 75.0) {
            this.riskTier = "Critical";
        } else if (this.finalRiskPercentage >= 50.0) {
            this.riskTier = "High";
        } else if (this.finalRiskPercentage >= 25.0) {
            this.riskTier = "Medium";
        } else {
            this.riskTier = "Low";
        }
        return this.riskTier;
    }

    public String findMainDriver() {
        double weightedWeather = clampPercentage(this.weatherPercentage) * this.weatherSharePct;
        double weightedVulnerability = clampPercentage(this.vulnerabilityPercentage) * this.vulnerabilitySharePct;

        if (weightedWeather >= weightedVulnerability) {
            this.mainDriver = "Weather & Lag";
        } else {
            this.mainDriver = "Ward Vulnerability";
        }
        return this.mainDriver;
    }

    public String getSummary() {
        calculatePercentage();
        determineRiskTier();
        findMainDriver();
        String summary = "Date: " + this.date+ " | City: " + this.city+ " | Ward: " + this.wardId
        + " | Final Risk: " + this.finalRiskPercentage + "%"+ " | Tier: " + this.riskTier 
        + " | Main Driver: " + this.mainDriver;
        return summary;
    }
}
