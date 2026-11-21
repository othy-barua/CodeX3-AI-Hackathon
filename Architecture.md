# System Architecture — AI for Urban Dengue Outbreak[cite: 2]

## 1. System Data Flow
```text
[Cleaned CSV Dataset]
        │
        ▼
[Solution.java] ──(groups by ward_id)──► [WeatherLagPercentage.java]
                                              │ (sorts by date, computes
                                              │  2-, 3-, 4-week rain lags)
                                              ▼
                                    [VulnerabilityPercentage.java]
                                              │ (uses RiskCalculator base &
                                              │  percentage clamping)
                                              ▼
                                    [FinalRiskAssessment.java]
                                              │ (risk score, tier, main driver,
                                              │  and formatted summary)
                                              ▼
                                      [Console Output]

1. Data Ingestion & Ward Grouping (Solution.java): Reads rows from the cleaned dataset (where duplicate rows and negative values were removed while keeping valid 0 values) and groups records by ward_id.  

2.  Chronological Multi-Week Lag Calculation (WeatherLagPercentage.java): Sorts a ward's records by date and computes 2-, 3-, and 4-week lagged rainfall values (lag2WeekRainMm, lag3WeekRainMm, lag4WeekRainMm) to reflect mosquito breeding cycles and incubation periods before outbreak surges.

3. Weather & Vulnerability Scoring (WeatherLagPercentage.java, VulnerabilityPercentage.java, RiskCalculator.java): Computes normalized percentage scores using shared base fields and clamping logic in RiskCalculator.

4. Final Risk Assessment (FinalRiskAssessment.java): Combines weather and vulnerability scores along with case context to calculate the overall risk percentage, classify the risk tier (Low, Medium, High, Critical), identify the main risk driver, and build the final summary.


Design Choices, Model Selection & Chronological Validation Split

1. Root-Folder Java Architecture: All source files (RiskCalculator.java, WeatherLagPercentage.java, VulnerabilityPercentage.java, FinalRiskAssessment.java, and Solution.java) live together in the root directory in core Java so anyone can compile with javac *.java and run with java Solution without extra framework setup.
2. Per-Ward Chronological Isolation: Grouping by ward_id and ordering by date keeps each ward's rainfall timeline separate so lag values do not cross between wards.
3. Model Selection Rationale: This Stage 1 prototype uses structured domain-weighted scoring and risk-tier classification in Java rather than a serialized ML model (joblib/pickle), keeping calculations transparent, fast, and aligned with 2-to-4-week lag and ward vulnerability factors. Future stages can train statistical or ML models on historical ward lag and vulnerability features.
4. Chronological Validation Split: Because rainfall lags and dengue risk are time-dependent, validation should use a chronological split (training/calibrating on earlier time periods and testing on later time periods) rather than a random split, avoiding data leakage from future weeks into earlier lag windows.


Compute, Caching & API Cost Efficiency

1. Zero External API / LLM Token Cost: The pipeline makes no external API calls or LLM requests, avoiding API fees, token costs, and rate limits.  
2.  Fast Local Execution & Caching Strategy: Records are read via BufferedReader and grouped in memory (Map / List) for low-overhead local execution. For repeated runs or larger datasets, precomputed ward lag tables or cached summary results can be reused instead of recomputing unchanged historical records[cite: 3, 14].


Resilience & Error Handling

1. Clamping & Guard Checks: Percentage calculations clamp out-of-range values and check for zero weight/share totals to prevent invalid outputs or division issues.  
2. Bounds & Empty-List Safety: populateWardRainfallLags safely handles null or empty lists and checks index bounds before reading earlier weeks.
3. File & Input Safety: Blank lines are skipped during CSV reading, valid 0 values (such as zero rainfall or zero cases) are preserved, and IOException errors are caught cleanly.