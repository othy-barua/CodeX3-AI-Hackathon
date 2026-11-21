public class RiskCalculator {
    public String date;
    public String city;
    public String wardId;
    public double avgTempC;
    public double rainfallMm;
    public double relativeHumidity;
    public double percentage;

    public RiskCalculator(String date, String city, String wardId, double avgTempC,
         double rainfallMm, double relativeHumidity){

          this.date=date;
          this.city=city;
          this.wardId=wardId;
          this.avgTempC=avgTempC;
          this.rainfallMm=rainfallMm;
          this.relativeHumidity=relativeHumidity;
          this.percentage=0.0;
    }

    public double calculatePercentage(){
        return this.percentage;
    }

    public double clampPercentage(double value){
        if(Double.isNaN(value) || value<0.0){
            return 0.0;
        }
        else if(value>100.0){
            return 100.0;
        }
        else{
            return value;
        }
    }
}
