import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/constants/firestore_collections.dart';
import '../../../core/providers/firebase_providers.dart';
import '../domain/athlete.dart';

class AthleteRepository {
  const AthleteRepository(this._firestore);

  final FirebaseFirestore _firestore;

  CollectionReference<Map<String, dynamic>> get _collection {
    return _firestore.collection(FirestoreCollections.athletes);
  }

  Stream<List<Athlete>> watchAthletesByCoach(String coachId) {
    return _collection
        .where('coachId', isEqualTo: coachId)
        .orderBy('createdAt', descending: true)
        .snapshots()
        .map((snapshot) => snapshot.docs.map((doc) => Athlete.fromMap(doc.data(), doc.id)).toList());
  }

  Stream<Athlete?> watchAthlete(String athleteId) {
    return _collection.doc(athleteId).snapshots().map((doc) {
      final data = doc.data();
      if (data == null) return null;
      return Athlete.fromMap(data, doc.id);
    });
  }

  Future<Athlete?> getAthleteByUserId(String userId) async {
    final snapshot = await _collection.where('userId', isEqualTo: userId).limit(1).get();
    if (snapshot.docs.isEmpty) return null;
    final doc = snapshot.docs.first;
    return Athlete.fromMap(doc.data(), doc.id);
  }

  Future<Athlete> ensureAthleteProfile({
    required String userId,
    required String name,
  }) async {
    final existing = await getAthleteByUserId(userId);
    if (existing != null) return existing;

    final doc = _collection.doc(userId);
    final athlete = Athlete(
      id: doc.id,
      coachId: '',
      userId: userId,
      name: name.isEmpty ? 'Atleta AF' : name,
      email: '',
      sport: 'Treinamento',
      level: 'Inicial',
      goal: 'Melhorar prontidão e performance',
      planName: 'Plano personalizado',
      createdAt: DateTime.now(),
    );
    await doc.set(athlete.toMap());
    return athlete;
  }
}

final athleteRepositoryProvider = Provider<AthleteRepository>((ref) {
  return AthleteRepository(ref.watch(firestoreProvider));
});

final athleteByUserProvider = FutureProvider.family<Athlete?, String>((ref, userId) {
  return ref.watch(athleteRepositoryProvider).getAthleteByUserId(userId);
});

final athleteProvider = StreamProvider.family<Athlete?, String>((ref, athleteId) {
  return ref.watch(athleteRepositoryProvider).watchAthlete(athleteId);
});
