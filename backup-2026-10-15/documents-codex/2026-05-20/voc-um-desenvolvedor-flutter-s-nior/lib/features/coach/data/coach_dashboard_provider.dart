import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/utils/readiness_calculator.dart';
import '../../athlete/data/athlete_repository.dart';
import '../../athlete/domain/athlete.dart';
import '../../auth/presentation/current_user_provider.dart';
import '../../checkin/data/checkin_repository.dart';
import '../../checkin/domain/daily_checkin.dart';
import '../domain/coach_dashboard.dart';

final coachAthletesProvider = StreamProvider.family<List<Athlete>, String>((ref, coachId) {
  return ref.watch(athleteRepositoryProvider).watchAthletesByCoach(coachId);
});

final coachDashboardProvider = FutureProvider<CoachDashboard>((ref) async {
  final user = await ref.watch(currentUserProvider.future);
  if (user == null) return const CoachDashboard(items: [], readyCount: 0, attentionCount: 0, riskCount: 0);

  final athletes = await ref.watch(coachAthletesProvider(user.id).future);
  final checkins = await ref.watch(checkinRepositoryProvider).latestByCoach(user.id);
  final latestByAthlete = <String, DailyCheckin>{};

  for (final checkin in checkins) {
    latestByAthlete.putIfAbsent(checkin.athleteId, () => checkin);
  }

  final items = athletes
      .map((athlete) => AthleteSummary(
            athlete: athlete,
            latestCheckin: latestByAthlete[athlete.id],
          ))
      .toList();

  var ready = 0;
  var attention = 0;
  var risk = 0;

  for (final item in items) {
    final score = item.latestCheckin?.afScore;
    if (score == null) continue;
    switch (afScoreStatus(score)) {
      case AFScoreStatus.ready:
        ready++;
      case AFScoreStatus.attention:
        attention++;
      case AFScoreStatus.risk:
        risk++;
    }
  }

  return CoachDashboard(
    items: items,
    readyCount: ready,
    attentionCount: attention,
    riskCount: risk,
  );
});
