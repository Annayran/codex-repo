import '../../features/athlete/domain/athlete.dart';
import '../../features/checkin/domain/daily_checkin.dart';

class MockData {
  const MockData._();

  // Fallback visual para o MVP quando o Firestore ainda estiver vazio.
  static List<Athlete> athletes() {
    final now = DateTime.now();
    return [
      Athlete(
        id: 'mock-joao',
        coachId: 'mock-coach',
        userId: 'mock-joao',
        name: 'João Silva',
        email: 'joao@aflab.app',
        sport: 'Funcional',
        level: 'Avançado',
        goal: 'Performance esportiva',
        planName: 'Plano personalizado',
        createdAt: now,
      ),
      Athlete(
        id: 'mock-lucas',
        coachId: 'mock-coach',
        userId: 'mock-lucas',
        name: 'Lucas Ferreira',
        email: 'lucas@aflab.app',
        sport: 'Musculação',
        level: 'Intermediário',
        goal: 'Reduzir fadiga',
        planName: 'Plano personalizado',
        createdAt: now,
      ),
      Athlete(
        id: 'mock-pedro',
        coachId: 'mock-coach',
        userId: 'mock-pedro',
        name: 'Pedro Almeida',
        email: 'pedro@aflab.app',
        sport: 'Corrida',
        level: 'Inicial',
        goal: 'Voltar sem dor',
        planName: 'Plano personalizado',
        createdAt: now,
      ),
      Athlete(
        id: 'mock-mariana',
        coachId: 'mock-coach',
        userId: 'mock-mariana',
        name: 'Mariana Costa',
        email: 'mariana@aflab.app',
        sport: 'Cross training',
        level: 'Intermediário',
        goal: 'Evolução semanal',
        planName: 'Plano personalizado',
        createdAt: now,
      ),
    ];
  }

  static List<DailyCheckin> checkins() {
    final now = DateTime.now();
    return [
      _checkin('mock-joao', 84, now, 'Pronto', sleep: 8, energy: 8, stress: 3, pain: 2, mood: 8),
      _checkin('mock-lucas', 63, now, 'Atenção', sleep: 6, energy: 6, stress: 6, pain: 5, mood: 6, painAreas: ['Joelho']),
      _checkin('mock-pedro', 42, now, 'Risco', sleep: 4, energy: 4, stress: 8, pain: 8, mood: 4, painAreas: ['Lombar', 'Joelho']),
      _checkin('mock-mariana', 78, now, 'Atenção', sleep: 7, energy: 7, stress: 4, pain: 4, mood: 7, painAreas: ['Ombro']),
    ];
  }

  static DailyCheckin _checkin(
    String athleteId,
    double score,
    DateTime date,
    String status, {
    required int sleep,
    required int energy,
    required int stress,
    required int pain,
    required int mood,
    List<String> painAreas = const [],
  }) {
    return DailyCheckin(
      id: 'checkin-$athleteId',
      athleteId: athleteId,
      coachId: 'mock-coach',
      date: date,
      sleep: sleep,
      energy: energy,
      stress: stress,
      pain: pain,
      fatigue: pain,
      mood: mood,
      rpe: 6,
      trainingDurationMinutes: 45,
      trainingLoad: 270,
      nutritionQuality: 'Boa',
      waterLiters: 2.3,
      waterScore: 7,
      painAreas: painAreas,
      afScore: score,
      status: status,
      alertLevel: score < 60 ? 'risk' : 'none',
      alertReasons: score < 60 ? const ['AF Score em risco no check-in de hoje'] : const [],
      notes: '',
      createdAt: date,
    );
  }
}
