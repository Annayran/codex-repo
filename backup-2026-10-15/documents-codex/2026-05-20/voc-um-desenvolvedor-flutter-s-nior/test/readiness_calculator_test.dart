import 'package:af_health_lab/core/utils/readiness_calculator.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('calculateReadinessScore clamps score between 0 and 100', () {
    final high = calculateReadinessScore(
      sleep: 10,
      energy: 10,
      mood: 10,
      stress: 0,
      soreness: 0,
    );
    final low = calculateReadinessScore(
      sleep: 0,
      energy: 0,
      mood: 0,
      stress: 10,
      soreness: 10,
    );

    expect(high, 100);
    expect(low, 0);
  });

  test('readinessStatusLabel maps score ranges', () {
    expect(readinessStatusLabel(85), 'Pronto');
    expect(readinessStatusLabel(70), 'Atenção');
    expect(readinessStatusLabel(45), 'Risco');
  });

  test('evaluateAlertReasons flags direct risk rules', () {
    final reasons = evaluateAlertReasons(
      afScore: 72,
      sleep: 4,
      pain: 8,
      fatigue: 3,
      rpe: 5,
    );

    expect(reasons, contains('Dor alta combinada com sono ruim'));
  });
}
