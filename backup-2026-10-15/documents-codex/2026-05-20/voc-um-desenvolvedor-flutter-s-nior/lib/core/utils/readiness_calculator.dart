enum AFScoreStatus { ready, attention, risk }

double calculateAFScore({
  required int sleep,
  required int energy,
  required int mood,
  required int stress,
  required int pain,
  int fatigue = 0,
}) {
  final rawScore =
      (sleep * 2) + (energy * 2) + (mood * 1.5) - (stress * 1.5) - (pain * 2) - (fatigue * 1.2);
  final normalized = (rawScore + 35) / 70 * 100;
  return normalized.clamp(0, 100).toDouble();
}

double calculateReadinessScore({
  required int sleep,
  required int energy,
  required int mood,
  required int stress,
  required int soreness,
}) {
  return calculateAFScore(
    sleep: sleep,
    energy: energy,
    mood: mood,
    stress: stress,
    pain: soreness,
  );
}

AFScoreStatus afScoreStatus(double score) {
  if (score >= 80) return AFScoreStatus.ready;
  if (score >= 60) return AFScoreStatus.attention;
  return AFScoreStatus.risk;
}

AFScoreStatus readinessStatus(double score) => afScoreStatus(score);

String afScoreStatusLabel(double score) {
  switch (afScoreStatus(score)) {
    case AFScoreStatus.ready:
      return 'Pronto';
    case AFScoreStatus.attention:
      return 'Atenção';
    case AFScoreStatus.risk:
      return 'Risco';
  }
}

String readinessStatusLabel(double score) => afScoreStatusLabel(score);

List<String> evaluateAlertReasons({
  required double afScore,
  required int sleep,
  required int pain,
  required int fatigue,
  required int rpe,
}) {
  final reasons = <String>[];
  if (afScore < 60) reasons.add('AF Score abaixo de 60%');
  if (pain >= 7 && sleep <= 5) reasons.add('Dor alta combinada com sono ruim');
  if (fatigue >= 7 && rpe >= 8) reasons.add('Fadiga alta após treino intenso');
  if (pain >= 8) reasons.add('Dor muito elevada reportada hoje');
  return reasons;
}

bool hasRiskAlert({
  required double afScore,
  required int sleep,
  required int pain,
  required int fatigue,
  required int rpe,
}) {
  return evaluateAlertReasons(
    afScore: afScore,
    sleep: sleep,
    pain: pain,
    fatigue: fatigue,
    rpe: rpe,
  ).isNotEmpty;
}
