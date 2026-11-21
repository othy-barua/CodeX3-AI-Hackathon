# CodeX3 – Dengue Risk Assessment

A Java-based dengue risk assessment system that calculates potential dengue risk using weather conditions, historical rainfall patterns, and ward-level environmental vulnerability.

## Overview

The project aims to estimate dengue risk for a specific city ward by combining two major factors:

- **Weather & Lag Analysis:** Evaluates temperature, humidity, and rainfall from previous weeks.
- **Ward Vulnerability:** Evaluates population density, waterlogging risk, and sanitation conditions.

These factors are combined into a final risk percentage, which is classified into four risk levels.

## Key Features

- Weather-based risk calculation.
- Historical rainfall lag analysis (2, 3, and 4 weeks).
- Population density assessment.
- Waterlogging and sanitation risk evaluation.
- Weighted risk calculation.
- Automatic risk classification.
- Identification of the main contributing risk factor.

## Risk Classification

| Risk Percentage | Risk Level |
|---|---|
| 0–24.99% | Low |
| 25–49.99% | Medium |
| 50–74.99% | High |
| 75–100% | Critical |

## Project Structure

```text
CodeX3-AI-Hackathon/
│
├── Solution.java
├── RiskCalculator.java
├── WeatherLagPercentage.java
├── VulnerabilityPercentage.java
├── FinalRiskAssessment.java
│
└── README.md
```

## How It Works

### 1. Weather & Lag Analysis

Calculates weather-related risk using:
- Average temperature
- Relative humidity
- Rainfall from 2 weeks ago
- Rainfall from 3 weeks ago
- Rainfall from 4 weeks ago

### 2. Ward Vulnerability Analysis

Calculates environmental vulnerability using:
- Population density
- Waterlogging risk index
- Sanitation score

### 3. Final Risk Assessment

Combines weather risk and ward vulnerability using configurable weights to calculate the final dengue risk percentage.

### 4. Risk Classification

The system determines:
- Final risk percentage
- Risk tier
- Main contributing factor (Weather & Lag or Ward Vulnerability)

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Weighted Percentage Calculation
- Mathematical Risk Scoring

## Getting Started

### Prerequisites

- Java JDK 17 or later
- VS Code or any Java-supported IDE

### Installation

Clone the repository:

```bash
git clone https://github.com/othy-barua/CodeX3-AI-Hackathon.git
```

Navigate to the project directory:

```bash
cd CodeX3-AI-Hackathon
```

### Run the Project

Compile the Java files:

```bash
javac *.java
```

Run the main class:

```bash
java Solution
```

**Note:** The current `Solution.java` contains input variable declarations as placeholders. Assign actual input values before compiling and running the program.

## Future Improvements

- Integration with real-world weather datasets.
- CSV-based data input.
- Automated data processing for multiple wards.
- Interactive risk visualization.
- Machine learning-based dengue prediction.
- City-wide dengue risk monitoring.

## Contributors

Developed as part of the **CodeX3 AI Hackathon**.

---

**Project:** CodeX3 – Dengue Risk Assessment  
**Language:** Java  
**Category:** AI / Data-Driven Risk Assessment
