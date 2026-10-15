import 'package:cloud_firestore/cloud_firestore.dart';

class Athlete {
  const Athlete({
    required this.id,
    required this.coachId,
    required this.userId,
    required this.name,
    required this.email,
    required this.sport,
    required this.level,
    required this.goal,
    required this.planName,
    required this.createdAt,
  });

  final String id;
  final String coachId;
  final String userId;
  final String name;
  final String email;
  final String sport;
  final String level;
  final String goal;
  final String planName;
  final DateTime createdAt;

  factory Athlete.fromMap(Map<String, dynamic> map, String id) {
    return Athlete(
      id: id,
      coachId: (map['coachId'] as String?) ?? '',
      userId: (map['userId'] as String?) ?? '',
      name: (map['name'] as String?) ?? '',
      email: (map['email'] as String?) ?? '',
      sport: (map['sport'] as String?) ?? 'Treinamento',
      level: (map['level'] as String?) ?? 'Inicial',
      goal: (map['goal'] as String?) ?? 'Performance e saúde',
      planName: (map['planName'] as String?) ?? 'Plano personalizado',
      createdAt: _readDate(map['createdAt']),
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'coachId': coachId,
      'userId': userId,
      'name': name,
      'email': email,
      'sport': sport,
      'level': level,
      'goal': goal,
      'planName': planName,
      'createdAt': Timestamp.fromDate(createdAt),
    };
  }

  Athlete copyWith({
    String? id,
    String? coachId,
    String? userId,
    String? name,
    String? email,
    String? sport,
    String? level,
    String? goal,
    String? planName,
    DateTime? createdAt,
  }) {
    return Athlete(
      id: id ?? this.id,
      coachId: coachId ?? this.coachId,
      userId: userId ?? this.userId,
      name: name ?? this.name,
      email: email ?? this.email,
      sport: sport ?? this.sport,
      level: level ?? this.level,
      goal: goal ?? this.goal,
      planName: planName ?? this.planName,
      createdAt: createdAt ?? this.createdAt,
    );
  }

  static DateTime _readDate(dynamic value) {
    if (value is Timestamp) return value.toDate();
    if (value is DateTime) return value;
    return DateTime.now();
  }
}
