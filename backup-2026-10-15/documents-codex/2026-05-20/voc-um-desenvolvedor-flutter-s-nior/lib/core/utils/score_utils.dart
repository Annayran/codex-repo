import 'package:flutter/material.dart';

import '../../app/theme.dart';
import 'readiness_calculator.dart';

export 'readiness_calculator.dart';

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

Color readinessStatusColor(double score) {
  return switch (afScoreStatus(score)) {
    AFScoreStatus.ready => AppColors.green,
    AFScoreStatus.attention => AppColors.attention,
    AFScoreStatus.risk => AppColors.risk,
  };
}

String readinessStatusText(double score) => afScoreStatusLabel(score);

String readinessStatusDescription(double score) {
  return switch (afScoreStatus(score)) {
    AFScoreStatus.ready => 'Muito bem! Seu corpo está respondendo bem.',
    AFScoreStatus.attention => 'Atenção aos sinais. Ajuste a intensidade se necessário.',
    AFScoreStatus.risk => 'Risco elevado. Priorize recuperação ou procure orientação.',
  };
}
