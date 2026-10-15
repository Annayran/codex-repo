import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../core/widgets/af_empty_state.dart';
import '../../../core/widgets/af_loading_view.dart';
import '../../auth/presentation/current_user_provider.dart';
import '../data/alert_repository.dart';

class CoachAlertsScreen extends ConsumerWidget {
  const CoachAlertsScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final user = ref.watch(currentUserProvider).valueOrNull;

    if (user == null) {
      return const Scaffold(body: AFLoadingView());
    }

    final alertsState = ref.watch(coachRiskAlertsProvider(user.id));

    return Scaffold(
      appBar: AppBar(title: const Text('Alertas de hoje')),
      body: alertsState.when(
        loading: () => const AFLoadingView(message: 'Buscando alertas...'),
        error: (error, _) => AFEmptyState(title: 'Erro nos alertas', message: error.toString()),
        data: (alerts) {
          if (alerts.isEmpty) {
            return const AFEmptyState(
              title: 'Nenhum alerta de risco',
              message: 'Quando um atleta registrar sinais de risco, o alerta aparecerá aqui.',
              icon: Icons.verified_rounded,
            );
          }

          return ListView.separated(
            padding: const EdgeInsets.all(20),
            itemCount: alerts.length,
            separatorBuilder: (_, __) => const SizedBox(height: 12),
            itemBuilder: (context, index) {
              final alert = alerts[index];
              return Card(
                child: Padding(
                  padding: const EdgeInsets.all(18),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          Icon(Icons.warning_amber_rounded, color: Theme.of(context).colorScheme.error),
                          const SizedBox(width: 10),
                          Expanded(
                            child: Text(alert.title, style: Theme.of(context).textTheme.titleLarge),
                          ),
                        ],
                      ),
                      const SizedBox(height: 10),
                      Text(alert.message),
                      const SizedBox(height: 10),
                      for (final reason in alert.reasons)
                        Padding(
                          padding: const EdgeInsets.only(top: 4),
                          child: Text('• $reason'),
                        ),
                    ],
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
