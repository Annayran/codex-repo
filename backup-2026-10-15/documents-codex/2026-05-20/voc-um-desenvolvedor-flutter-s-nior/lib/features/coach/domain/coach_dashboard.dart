import '../../athlete/domain/athlete.dart';
import '../../checkin/domain/daily_checkin.dart';

class AthleteSummary {
  const AthleteSummary({
    required this.athlete,
    required this.latestCheckin,
  });

  final Athlete athlete;
  final DailyCheckin? latestCheckin;
}

class CoachDashboard {
  const CoachDashboard({
    required this.items,
    required this.readyCount,
    required this.attentionCount,
    required this.riskCount,
  });

  final List<AthleteSummary> items;
  final int readyCount;
  final int attentionCount;
  final int riskCount;
}
