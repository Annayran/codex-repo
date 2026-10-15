import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/constants/asset_paths.dart';
import '../../../core/widgets/af_logo.dart';
import '../data/auth_repository.dart';
import '../data/user_repository.dart';
import '../domain/app_user.dart';
import 'widgets/af_auth_widgets.dart';

class SplashScreen extends ConsumerStatefulWidget {
  const SplashScreen({super.key});

  @override
  ConsumerState<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends ConsumerState<SplashScreen> {
  String _message = 'Abrindo AF Health Lab...';
  bool _hasError = false;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) => _bootstrap());
  }

  Future<void> _bootstrap() async {
    try {
      await Future<void>.delayed(const Duration(milliseconds: 450));
      if (!mounted) return;

      final authRepository = ref.read(authRepositoryProvider);
      final userRepository = ref.read(userRepositoryProvider);
      final firebaseUser = authRepository.currentUser ??
          await authRepository
              .authStateChanges()
              .first
              .timeout(const Duration(seconds: 4), onTimeout: () => null);

      if (!mounted) return;
      if (firebaseUser == null) {
        context.go('/auth');
        return;
      }

      setState(() => _message = 'Carregando seu perfil...');

      final appUser = await userRepository
          .getUser(firebaseUser.uid)
          .timeout(const Duration(seconds: 5), onTimeout: () => null);

      if (!mounted) return;
      if (appUser == null) {
        await userRepository.saveUser(
          AppUser(
            id: firebaseUser.uid,
            name: _safeName(firebaseUser.displayName),
            email: firebaseUser.email ?? '',
            role: 'athlete',
            createdAt: DateTime.now(),
            updatedAt: DateTime.now(),
          ),
        );
        if (!mounted) return;
        context.go('/athlete-home');
        return;
      }

      context.go(appUser.isCoach ? '/coach-home' : '/athlete-home');
    } catch (_) {
      if (!mounted) return;
      setState(() {
        _hasError = true;
        _message = 'Não foi possível iniciar agora.';
      });
    }
  }

  String _safeName(String? displayName) {
    final name = displayName?.trim();
    if (name != null && name.isNotEmpty) return name;
    return 'Atleta AF';
  }

  @override
  Widget build(BuildContext context) {
    if (_hasError) {
      return _SplashError(
        onRetry: () {
          setState(() {
            _hasError = false;
            _message = 'Abrindo AF Health Lab...';
          });
          _bootstrap();
        },
        onLogin: () => context.go('/auth'),
      );
    }

    return Scaffold(body: _BrandedSplash(message: _message));
  }
}

class _BrandedSplash extends StatelessWidget {
  const _BrandedSplash({required this.message});

  final String message;

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: AFTheme.background),
      child: Stack(
        children: [
          Positioned.fill(
            child: Opacity(
              opacity: 0.16,
              child: Image.asset(
                AssetPaths.runnerAuthFullPng,
                fit: BoxFit.cover,
                alignment: const Alignment(0, -0.08),
              ),
            ),
          ),
          Positioned.fill(
            child: DecoratedBox(
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  begin: Alignment.topCenter,
                  end: Alignment.bottomCenter,
                  colors: [
                    Colors.black.withValues(alpha: 0.72),
                    AFTheme.background.withValues(alpha: 0.90),
                    Colors.black,
                  ],
                ),
              ),
            ),
          ),
          SafeArea(
            child: Center(
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 34),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const AFLogo(variant: AFLogoVariant.vertical, size: 142),
                    const SizedBox(height: 34),
                    const AFBrandSlogan(centered: true),
                    const SizedBox(height: 42),
                    const _SplashProgress(),
                    const SizedBox(height: 18),
                    Text(
                      message,
                      textAlign: TextAlign.center,
                      style: const TextStyle(
                        color: AFTheme.muted,
                        fontSize: 13,
                        fontWeight: FontWeight.w600,
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class _SplashProgress extends StatelessWidget {
  const _SplashProgress();

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 190,
      child: ClipRRect(
        borderRadius: BorderRadius.circular(999),
        child: LinearProgressIndicator(
          minHeight: 5,
          backgroundColor: Colors.white.withValues(alpha: 0.08),
          color: AFTheme.primary,
        ),
      ),
    );
  }
}

class _SplashError extends StatelessWidget {
  const _SplashError({required this.onRetry, required this.onLogin});

  final VoidCallback onRetry;
  final VoidCallback onLogin;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: AFAuthBackground(
        child: Center(
          child: AFAuthPanel(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                const Icon(Icons.error_outline_rounded, size: 46, color: AFTheme.danger),
                const SizedBox(height: 16),
                const Text('Não foi possível iniciar o app.'),
                const SizedBox(height: 16),
                FilledButton(onPressed: onRetry, child: const Text('Tentar novamente')),
                TextButton(onPressed: onLogin, child: const Text('Ir para login')),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
