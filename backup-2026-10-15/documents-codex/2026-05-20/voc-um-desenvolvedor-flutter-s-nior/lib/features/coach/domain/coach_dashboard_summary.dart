class CoachDashboardSummary {
  const CoachDashboardSummary({
    required this.totalAthletes,
    required this.readyCount,
    required this.attentionCount,
    required this.riskCount,
    required this.averageScore,
  });

  final int totalAthletes;
  final int readyCount;
  final int attentionCount;
  final int riskCount;
  final double averageScore;
}
