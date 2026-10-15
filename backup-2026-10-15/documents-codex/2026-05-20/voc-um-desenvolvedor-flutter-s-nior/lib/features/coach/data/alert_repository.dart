import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/constants/firestore_collections.dart';
import '../../../core/providers/firebase_providers.dart';
import '../domain/app_alert.dart';

class AlertRepository {
  const AlertRepository(this._firestore);

  final FirebaseFirestore _firestore;

  CollectionReference<Map<String, dynamic>> get _collection {
    return _firestore.collection(FirestoreCollections.alerts);
  }

  Future<void> saveAlert(AppAlert alert) {
    return _collection.doc(alert.id).set(alert.toMap());
  }

  Stream<List<AppAlert>> watchRiskAlertsByCoach(String coachId, {int limit = 30}) {
    return _collection
        .where('coachId', isEqualTo: coachId)
        .where('level', isEqualTo: 'risk')
        .orderBy('createdAt', descending: true)
        .limit(limit)
        .snapshots()
        .map((snapshot) => snapshot.docs.map((doc) => AppAlert.fromMap(doc.data(), doc.id)).toList());
  }
}

final alertRepositoryProvider = Provider<AlertRepository>((ref) {
  return AlertRepository(ref.watch(firestoreProvider));
});

final coachRiskAlertsProvider = StreamProvider.family<List<AppAlert>, String>((ref, coachId) {
  return ref.watch(alertRepositoryProvider).watchRiskAlertsByCoach(coachId);
});
