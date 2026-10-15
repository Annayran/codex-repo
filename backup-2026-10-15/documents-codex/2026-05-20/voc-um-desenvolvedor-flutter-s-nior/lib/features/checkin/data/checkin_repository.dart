import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/constants/firestore_collections.dart';
import '../../../core/providers/firebase_providers.dart';
import '../domain/daily_checkin.dart';

class CheckinRepository {
  const CheckinRepository(this._firestore);

  final FirebaseFirestore _firestore;

  CollectionReference<Map<String, dynamic>> get _collection {
    return _firestore.collection(FirestoreCollections.checkins);
  }

  Future<void> saveCheckin(DailyCheckin checkin) {
    return _collection.doc(checkin.id).set(checkin.toMap());
  }

  Stream<List<DailyCheckin>> watchByAthlete(String athleteId, {int limit = 20}) {
    return _collection
        .where('athleteId', isEqualTo: athleteId)
        .orderBy('date', descending: true)
        .limit(limit)
        .snapshots()
        .map((snapshot) => snapshot.docs.map((doc) => DailyCheckin.fromMap(doc.data(), doc.id)).toList());
  }

  Future<List<DailyCheckin>> latestByCoach(String coachId, {int limit = 100}) async {
    final snapshot = await _collection
        .where('coachId', isEqualTo: coachId)
        .orderBy('date', descending: true)
        .limit(limit)
        .get();
    return snapshot.docs.map((doc) => DailyCheckin.fromMap(doc.data(), doc.id)).toList();
  }
}

final checkinRepositoryProvider = Provider<CheckinRepository>((ref) {
  return CheckinRepository(ref.watch(firestoreProvider));
});

final athleteCheckinsProvider = StreamProvider.family<List<DailyCheckin>, String>((ref, athleteId) {
  return ref.watch(checkinRepositoryProvider).watchByAthlete(athleteId);
});
