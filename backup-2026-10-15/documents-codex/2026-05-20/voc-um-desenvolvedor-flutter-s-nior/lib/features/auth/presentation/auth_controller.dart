import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../data/auth_repository.dart';
import '../data/user_repository.dart';
import '../domain/app_user.dart';

final authControllerProvider = StateNotifierProvider<AuthController, AsyncValue<void>>((ref) {
  return AuthController(
    authRepository: ref.watch(authRepositoryProvider),
    userRepository: ref.watch(userRepositoryProvider),
  );
});

class AuthController extends StateNotifier<AsyncValue<void>> {
  AuthController({
    required AuthRepository authRepository,
    required UserRepository userRepository,
  })  : _authRepository = authRepository,
        _userRepository = userRepository,
        super(const AsyncData(null));

  final AuthRepository _authRepository;
  final UserRepository _userRepository;

  Future<void> loginWithEmail({
    required String email,
    required String password,
  }) {
    return _guard(() async {
      final credential = await _authRepository.signInWithEmail(
        email: email.trim(),
        password: password,
      );
      final firebaseUser = credential.user;
      if (firebaseUser == null) {
        throw const AuthFlowException('Não foi possível entrar com esta conta.');
      }

      await _ensureUserProfile(
        firebaseUser: firebaseUser,
        fallbackName: _nameFromEmail(firebaseUser.email),
        defaultRole: 'athlete',
      );
    });
  }

  Future<void> registerWithEmail({
    required String name,
    required String email,
    required String password,
    required String role,
  }) {
    return _guard(() async {
      final cleanName = name.trim();
      final credential = await _authRepository.registerWithEmail(
        email: email.trim(),
        password: password,
      );
      final firebaseUser = credential.user;
      if (firebaseUser == null) {
        throw const AuthFlowException('Não foi possível criar o usuário.');
      }

      await firebaseUser.updateDisplayName(cleanName);
      await _saveUserProfile(
        firebaseUser: firebaseUser,
        fallbackName: cleanName,
        role: role,
      );
    });
  }

  Future<void> loginWithGoogle({String defaultRole = 'athlete'}) {
    return _guard(() async {
      final credential = await _authRepository.signInWithGoogle();
      final firebaseUser = credential.user;
      if (firebaseUser == null) {
        throw const AuthFlowException('Login com Google não retornou usuário.');
      }

      await _ensureUserProfile(
        firebaseUser: firebaseUser,
        fallbackName: 'Usuário AF',
        defaultRole: defaultRole,
      );
    });
  }

  Future<void> sendPasswordReset(String email) {
    return _guard(() async {
      final cleanEmail = email.trim();
      if (!cleanEmail.contains('@')) {
        throw const AuthFlowException('Informe seu e-mail para recuperar a senha.');
      }
      await _authRepository.sendPasswordResetEmail(cleanEmail);
    });
  }

  Future<void> signOut() {
    return _guard(_authRepository.signOut);
  }

  Future<void> _ensureUserProfile({
    required User firebaseUser,
    required String fallbackName,
    required String defaultRole,
  }) async {
    final existing = await _userRepository.getUser(firebaseUser.uid);
    if (existing != null) return;

    await _saveUserProfile(
      firebaseUser: firebaseUser,
      fallbackName: fallbackName,
      role: defaultRole,
    );
  }

  Future<void> _saveUserProfile({
    required User firebaseUser,
    required String fallbackName,
    required String role,
  }) async {
    final now = DateTime.now();
    final displayName = firebaseUser.displayName?.trim();
    await _userRepository.saveUser(
      AppUser(
        id: firebaseUser.uid,
        name: displayName != null && displayName.isNotEmpty ? displayName : fallbackName,
        email: firebaseUser.email ?? '',
        role: role,
        createdAt: now,
        updatedAt: now,
        onboardingCompleted: true,
      ),
    );
  }

  Future<void> _guard(Future<void> Function() action) async {
    state = const AsyncLoading();
    try {
      await action();
      state = const AsyncData(null);
    } catch (error, stackTrace) {
      final friendlyError = _friendlyError(error);
      state = AsyncError(friendlyError, stackTrace);
      throw friendlyError;
    }
  }

  String _nameFromEmail(String? email) {
    final cleanEmail = email?.trim();
    if (cleanEmail == null || cleanEmail.isEmpty || !cleanEmail.contains('@')) {
      return 'Atleta AF';
    }
    final prefix = cleanEmail.split('@').first.replaceAll('.', ' ').replaceAll('_', ' ');
    if (prefix.trim().isEmpty) return 'Atleta AF';
    return prefix
        .split(' ')
        .where((part) => part.isNotEmpty)
        .map((part) => '${part[0].toUpperCase()}${part.substring(1).toLowerCase()}')
        .join(' ');
  }

  AuthFlowException _friendlyError(Object error) {
    if (error is AuthFlowException) return error;
    if (error is FirebaseAuthException) {
      return AuthFlowException(switch (error.code) {
        'invalid-email' => 'E-mail inválido.',
        'user-disabled' => 'Esta conta foi desativada.',
        'user-not-found' => 'Não encontramos uma conta com este e-mail.',
        'wrong-password' => 'Senha incorreta.',
        'invalid-credential' => 'E-mail ou senha inválidos.',
        'email-already-in-use' => 'Este e-mail já está cadastrado.',
        'weak-password' => 'Crie uma senha mais forte.',
        'network-request-failed' => 'Sem conexão com a internet.',
        'google-cancelled' => 'Login com Google cancelado.',
        'account-exists-with-different-credential' => 'Já existe uma conta com este e-mail usando outro método de login.',
        'operation-not-allowed' => 'Este método de login ainda não está habilitado no Firebase.',
        _ => error.message ?? 'Não foi possível concluir a autenticação.',
      });
    }
    return AuthFlowException(error.toString().replaceFirst('Exception: ', ''));
  }
}

class AuthFlowException implements Exception {
  const AuthFlowException(this.message);

  final String message;

  @override
  String toString() => message;
}
