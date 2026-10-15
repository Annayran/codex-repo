import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/constants/firestore_collections.dart';
import '../../../core/providers/firebase_providers.dart';
import '../domain/app_user.dart';

class UserRepository {
  const UserRepository(this._firestore);

  final FirebaseFirestore _firestore;

  DocumentReference<Map<String, dynamic>> _doc(String id) {
    return _firestore.collection(FirestoreCollections.users).doc(id);
  }

  Future<void> saveUser(AppUser user) => _doc(user.id).set(user.toMap(), SetOptions(merge: true));

  Stream<AppUser?> watchUser(String id) {
    return _doc(id).snapshots().map((doc) {
      final data = doc.data();
      if (data == null) return null;
      return AppUser.fromMap(data, doc.id);
    });
  }

  Future<AppUser?> getUser(String id) async {
    final doc = await _doc(id).get();
    final data = doc.data();
    if (data == null) return null;
    return AppUser.fromMap(data, doc.id);
  }
}

final userRepositoryProvider = Provider<UserRepository>((ref) {
  return UserRepository(ref.watch(firestoreProvider));
});
