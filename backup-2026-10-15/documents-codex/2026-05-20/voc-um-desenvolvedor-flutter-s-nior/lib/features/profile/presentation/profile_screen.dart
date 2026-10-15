import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/widgets/af_app_bar.dart';
import '../../../core/widgets/af_logo.dart';
import '../../../features/auth/data/auth_repository.dart';
import '../../../features/auth/presentation/current_user_provider.dart';

class ProfileScreen extends ConsumerWidget {
  const ProfileScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final user = ref.watch(currentUserProvider);

    return Scaffold(
      appBar: const AFAppBar(title: 'Perfil'),
      body: ListView(
        padding: const EdgeInsets.all(20),
        children: [
          const Center(child: AFAppIconPreview(size: 104)),
          const SizedBox(height: 18),
          Center(child: Text('AF Health Lab', style: Theme.of(context).textTheme.headlineSmall)),
          const SizedBox(height: 4),
          const Center(child: Text('Dados. Ciência. Performance.', style: TextStyle(color: AppColors.green, letterSpacing: 2))),
          const SizedBox(height: 24),
          user.when(
            loading: () => const Center(child: CircularProgressIndicator(color: AppColors.green)),
            error: (_, __) => const Text('Não foi possível carregar seu perfil.'),
            data: (appUser) => Card(
              child: Column(
                children: [
                  ListTile(
                    leading: const Icon(Icons.person_outline_rounded),
                    title: Text(appUser?.name ?? 'Usuário AF'),
                    subtitle: Text(appUser?.email ?? 'Perfil local'),
                  ),
                  const Divider(height: 1),
                  ListTile(
                    leading: const Icon(Icons.verified_user_outlined),
                    title: const Text('Perfil'),
                    subtitle: Text(appUser?.role == 'coach' ? 'Coach' : 'Aluno'),
                  ),
                ],
              ),
            ),
          ),
          const SizedBox(height: 16),
          FilledButton.icon(
            onPressed: () async {
              await ref.read(authRepositoryProvider).signOut();
              if (context.mounted) context.go('/login');
            },
            icon: const Icon(Icons.logout_rounded),
            label: const Text('Sair'),
          ),
        ],
      ),
    );
  }
}
