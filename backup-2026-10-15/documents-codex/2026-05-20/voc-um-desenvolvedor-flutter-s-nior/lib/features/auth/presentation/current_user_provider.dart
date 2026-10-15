import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../data/auth_repository.dart';
import '../data/user_repository.dart';
import '../domain/app_user.dart';

final currentUserProvider = StreamProvider<AppUser?>((ref) {
  final authState = ref.watch(authStateProvider);
  final repository = ref.watch(userRepositoryProvider);

  return authState.when(
    data: (firebaseUser) {
      if (firebaseUser == null) return Stream<AppUser?>.value(null);
      return repository.watchUser(firebaseUser.uid);
    },
    loading: () => Stream<AppUser?>.value(null),
    error: (_, __) => Stream<AppUser?>.value(null),
  );
});
