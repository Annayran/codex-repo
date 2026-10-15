import 'package:cloud_firestore/cloud_firestore.dart';

class DailyCheckin {
  const DailyCheckin({
    required this.id,
    required this.athleteId,
    required this.coachId,
    required this.date,
    required this.sleep,
    required this.energy,
    required this.stress,
    required this.pain,
    required this.fatigue,
    required this.mood,
    required this.rpe,
    required this.trainingDurationMinutes,
    required this.trainingLoad,
    required this.nutritionQuality,
    required this.waterLiters,
    required this.waterScore,
    required this.painAreas,
    required this.afScore,
    required this.status,
    required this.alertLevel,
    required this.alertReasons,
    required this.notes,
    required this.createdAt,
  });

  final String id;
  final String athleteId;
  final String coachId;
  final DateTime date;
  final int sleep;
  final int energy;
  final int stress;
  final int pain;
  final int fatigue;
  final int mood;
  final int rpe;
  final int trainingDurationMinutes;
  final int trainingLoad;
  final String nutritionQuality;
  final double waterLiters;
  final int waterScore;
  final List<String> painAreas;
  final double afScore;
  final String status;
  final String alertLevel;
  final List<String> alertReasons;
  final String notes;
  final DateTime createdAt;

  double get readinessScore => afScore;
  int get soreness => pain;
  bool get hasRiskAlert => alertLevel == 'risk';

  factory DailyCheckin.fromMap(Map<String, dynamic> map, String id) {
    final score = (map['afScore'] as num?)?.toDouble() ?? (map['readinessScore'] as num?)?.toDouble() ?? 0;
    return DailyCheckin(
      id: id,
      athleteId: (map['athleteId'] as String?) ?? '',
      coachId: (map['coachId'] as String?) ?? '',
      date: _readDate(map['date']),
      sleep: (map['sleep'] as num?)?.toInt() ?? 0,
      energy: (map['energy'] as num?)?.toInt() ?? 0,
      stress: (map['stress'] as num?)?.toInt() ?? 0,
      pain: (map['pain'] as num?)?.toInt() ?? (map['soreness'] as num?)?.toInt() ?? 0,
      fatigue: (map['fatigue'] as num?)?.toInt() ?? 0,
      mood: (map['mood'] as num?)?.toInt() ?? 0,
      rpe: (map['rpe'] as num?)?.toInt() ?? 0,
      trainingDurationMinutes: (map['trainingDurationMinutes'] as num?)?.toInt() ?? 0,
      trainingLoad: (map['trainingLoad'] as num?)?.toInt() ?? 0,
      nutritionQuality: (map['nutritionQuality'] as String?) ?? 'Regular',
      waterLiters: (map['waterLiters'] as num?)?.toDouble() ?? 0,
      waterScore: (map['waterScore'] as num?)?.toInt() ?? 0,
      painAreas: List<String>.from((map['painAreas'] as List?) ?? const []),
      afScore: score,
      status: (map['status'] as String?) ?? _statusFromScore(score),
      alertLevel: (map['alertLevel'] as String?) ?? 'none',
      alertReasons: List<String>.from((map['alertReasons'] as List?) ?? const []),
      notes: (map['notes'] as String?) ?? '',
      createdAt: _readDate(map['createdAt']),
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'athleteId': athleteId,
      'coachId': coachId,
      'date': Timestamp.fromDate(date),
      'sleep': sleep,
      'energy': energy,
      'stress': stress,
      'soreness': pain,
      'pain': pain,
      'fatigue': fatigue,
      'mood': mood,
      'rpe': rpe,
      'trainingDurationMinutes': trainingDurationMinutes,
      'trainingLoad': trainingLoad,
      'nutritionQuality': nutritionQuality,
      'waterLiters': waterLiters,
      'waterScore': waterScore,
      'painAreas': painAreas,
      'readinessScore': afScore,
      'afScore': afScore,
      'status': status,
      'alertLevel': alertLevel,
      'alertReasons': alertReasons,
      'notes': notes,
      'createdAt': Timestamp.fromDate(createdAt),
    };
  }

  DailyCheckin copyWith({
    String? id,
    String? athleteId,
    String? coachId,
    DateTime? date,
    int? sleep,
    int? energy,
    int? stress,
    int? pain,
    int? fatigue,
    int? mood,
    int? rpe,
    int? trainingDurationMinutes,
    int? trainingLoad,
    String? nutritionQuality,
    double? waterLiters,
    int? waterScore,
    List<String>? painAreas,
    double? afScore,
    String? status,
    String? alertLevel,
    List<String>? alertReasons,
    String? notes,
    DateTime? createdAt,
  }) {
    return DailyCheckin(
      id: id ?? this.id,
      athleteId: athleteId ?? this.athleteId,
      coachId: coachId ?? this.coachId,
      date: date ?? this.date,
      sleep: sleep ?? this.sleep,
      energy: energy ?? this.energy,
      stress: stress ?? this.stress,
      pain: pain ?? this.pain,
      fatigue: fatigue ?? this.fatigue,
      mood: mood ?? this.mood,
      rpe: rpe ?? this.rpe,
      trainingDurationMinutes: trainingDurationMinutes ?? this.trainingDurationMinutes,
      trainingLoad: trainingLoad ?? this.trainingLoad,
      nutritionQuality: nutritionQuality ?? this.nutritionQuality,
      waterLiters: waterLiters ?? this.waterLiters,
      waterScore: waterScore ?? this.waterScore,
      painAreas: painAreas ?? this.painAreas,
      afScore: afScore ?? this.afScore,
      status: status ?? this.status,
      alertLevel: alertLevel ?? this.alertLevel,
      alertReasons: alertReasons ?? this.alertReasons,
      notes: notes ?? this.notes,
      createdAt: createdAt ?? this.createdAt,
    );
  }

  static DateTime _readDate(dynamic value) {
    if (value is Timestamp) return value.toDate();
    if (value is DateTime) return value;
    return DateTime.now();
  }

  static String _statusFromScore(double score) {
    if (score >= 80) return 'Pronto';
    if (score >= 60) return 'Atenção';
    return 'Risco';
  }
}
