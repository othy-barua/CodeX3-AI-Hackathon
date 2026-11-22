class Solution {
   
    public static void main(String[] args) {
        String csvFile = "dengue_cleaned1.csv"; 
        Map<String, List<DengueRecord>> recordsByWard = new LinkedHashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (isFirstLine) {
                    isFirstLine = false;
                    if (line.toLowerCase().startsWith("date")) {
                        continue; // Skip header row
                    }
                }

                String[] parts = line.split(",");
                if (parts.length < 12) {
                    continue; // Skip incomplete rows safely
                }

                String date = parts[0].trim();
                String city = parts[1].trim();
                String wardId = parts[2].trim();
                double popDensity = Double.parseDouble(parts[3].trim());
                double waterLoggingRisk = Double.parseDouble(parts[4].trim());
                double sanitationScore = Double.parseDouble(parts[5].trim());
                double avgTempC = Double.parseDouble(parts[6].trim());
                double maxTempC = Double.parseDouble(parts[7].trim());
                double minTempC = Double.parseDouble(parts[8].trim());
                double rainfallMm = Double.parseDouble(parts[9].trim());
                double relativeHumidity = Double.parseDouble(parts[10].trim());
                int dengueCases = (int) Double.parseDouble(parts[11].trim());

                DengueRecord record = new DengueRecord(
                        date, city, wardId, popDensity, waterLoggingRisk,
                        sanitationScore, avgTempC, maxTempC, minTempC,
                        rainfallMm, relativeHumidity, dengueCases
                );

                recordsByWard.computeIfAbsent(wardId, k -> new ArrayList<>()).add(record);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return;
        }

        for (Map.Entry<String, List<DengueRecord>> entry : recordsByWard.entrySet()) {
            String wardId = entry.getKey();
            List<DengueRecord> wardRows = entry.getValue();

           
            wardRows.sort(Comparator.comparing(r -> r.date));

            
            System.out.println("Ward: " + wardId + " | Rows loaded: " + wardRows.size());
        }
    }

    static class DengueRecord {
        final String date;
        final String city;
        final String wardId;
        final double popDensity;
        final double waterLoggingRisk;
        final double sanitationScore;
        final double avgTempC;
        final double maxTempC;
        final double minTempC;
        final double rainfallMm;
        final double relativeHumidity;
        final int dengueCases;

        DengueRecord(
                String date, String city, String wardId, double popDensity,
                double waterLoggingRisk, double sanitationScore, double avgTempC,
                double maxTempC, double minTempC, double rainfallMm,
                double relativeHumidity, int dengueCases
        ) {
            this.date = date;
            this.city = city;
            this.wardId = wardId;
            this.popDensity = popDensity;
            this.waterLoggingRisk = waterLoggingRisk;
            this.sanitationScore = sanitationScore;
            this.avgTempC = avgTempC;
            this.maxTempC = maxTempC;
            this.minTempC = minTempC;
            this.rainfallMm = rainfallMm;
            this.relativeHumidity = relativeHumidity;
            this.dengueCases = dengueCases;
        }
    }
}


