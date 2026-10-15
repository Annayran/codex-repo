import 'package:cloud_firestore/cloud_firestore.dart';

class AppAlert {
  const AppAlert({
    required this.id,
    required this.athleteId,
    required this.coachId,
    required this.checkinId,
    required this.level,
    required this.title,
    required this.message,
    required this.reasons,
    required this.isRead,
    required this.createdAt,
  });

  final String id;
  final String athleteId;
  final String coachId;
  final String checkinId;
  final String level;
  final String title;
  final String message;
  final List<String> reasons;
  final bool isRead;
  final DateTime createdAt;

  factory AppAlert.fromMap(Map<String, dynamic> map, String id) {
    return AppAlert(
      id: id,
      athleteId: (map['athleteId'] as String?) ?? '',
      coachId: (map['coachId'] as String?) ?? '',
      checkinId: (map['checkinId'] as String?) ?? '',
      level: (map['level'] as String?) ?? 'risk',
      title: (map['title'] as String?) ?? 'Alerta de risco',
      message: (map['message'] as String?) ?? '',
      reasons: List<String>.from((map['reasons'] as List?) ?? const []),
      isRead: (map['isRead'] as bool?) ?? false,
      createdAt: _readDate(map['createdAt']),
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'athleteId': athleteId,
      'coachId': coachId,
      'checkinId': checkinId,
      'level': level,
      'title': title,
      'message': message,
      'reasons': reasons,
      'isRead': isRead,
      'createdAt': Timestamp.fromDate(createdAt),
    };
  }

  AppAlert copyWith({
    String? id,
    String? athleteId,
    String? coachId,
    String? checkinId,
    String? level,
    String? title,
    String? message,
    List<String>? reasons,
    bool? isRead,
    DateTime? createdAt,
  }) {
    return AppAlert(
      id: id ?? this.id,
      athleteId: athleteId ?? this.athleteId,
      coachId: coachId ?? this.coachId,
      checkinId: checkinId ?? this.checkinId,
      level: level ?? this.level,
      title: title ?? this.title,
      message: message ?? this.message,
      reasons: reasons ?? this.reasons,
      isRead: isRead ?? this.isRead,
      createdAt: createdAt ?? this.createdAt,
    );
  }

  static DateTime _readDate(dynamic value) {
    if (value is Timestamp) return value.toDate();
    if (value is DateTime) return value;
    return DateTime.now();
  }
}
