import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/utils/score_utils.dart';
import '../../athlete/domain/athlete.dart';
import '../../checkin/domain/daily_checkin.dart';
import '../domain/coach_dashboard_summary.dart';

class CoachRepository {
  const CoachRepository();

  CoachDashboardSummary buildSummary({
    required List<Athlete> athletes,
    required List<DailyCheckin> latestCheckins,
  }) {
    var ready = 0;
    var attention = 0;
    var risk = 0;
    var totalScore = 0.0;

    for (final checkin in latestCheckins) {
      totalScore += checkin.afScore;
      switch (afScoreStatus(checkin.afScore)) {
        case AFScoreStatus.ready:
          ready++;
        case AFScoreStatus.attention:
          attention++;
        case AFScoreStatus.risk:
          risk++;
      }
    }

    return CoachDashboardSummary(
      totalAthletes: athletes.length,
      readyCount: ready,
      attentionCount: attention,
      riskCount: risk,
      averageScore: latestCheckins.isEmpty ? 0 : totalScore / latestCheckins.length,
    );
  }
}

final coachRepositoryProvider = Provider<CoachRepository>((ref) => const CoachRepository());
