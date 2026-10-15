import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';

import '../../../app/theme.dart';
import '../../../core/constants/mock_data.dart';
import '../../../core/utils/readiness_calculator.dart';
import '../../../core/widgets/af_app_shell.dart';
import '../../../core/widgets/af_empty_state.dart';
import '../../../core/widgets/af_loading_view.dart';
import '../../auth/data/auth_repository.dart';
import '../../auth/presentation/current_user_provider.dart';
import '../../checkin/domain/daily_checkin.dart';
import '../data/alert_repository.dart';
import '../data/coach_dashboard_provider.dart';

class CoachHomeScreen extends ConsumerWidget {
  const CoachHomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final user = ref.watch(currentUserProvider).valueOrNull;
    final dashboardState = ref.watch(coachDashboardProvider);
    final alertsCount = user == null ? 0 : (ref.watch(coachRiskAlertsProvider(user.id)).valueOrNull?.length ?? 0);

    return AFAppShell(
      currentIndex: 0,
      initials: user == null ? null : initialsFromName(user.name),
      onNotifications: () => context.go('/coach-alerts'),
      onProfile: () => context.go('/profile'),
      onNavTap: (index) => _handleNavTap(context, ref, index),
      child: dashboardState.when(
        loading: () => const AFLoadingView(message: 'Montando dashboard...'),
        error: (error, _) => AFEmptyState(title: 'Erro no dashboard', message: error.toString()),
        data: (dashboard) {
          final useFallback = dashboard.items.isEmpty;
          final fallbackAthletes = MockData.athletes();
          final fallbackCheckins = MockData.checkins();
          final items = useFallback
              ? [
                  for (final athlete in fallbackAthletes)
                    _CoachListItem(
                      id: athlete.id,
                      name: athlete.name,
                      subtitle: athlete.sport,
                      latest: fallbackCheckins.firstWhere((checkin) => checkin.athleteId == athlete.id),
                    ),
                ]
              : [
                  for (final item in dashboard.items)
                    _CoachListItem(
                      id: item.athlete.id,
                      name: item.athlete.name,
                      subtitle: item.athlete.sport,
                      latest: item.latestCheckin,
                    ),
                ];

          final ready = useFallback ? 1 : dashboard.readyCount;
          final attention = useFallback ? 2 : dashboard.attentionCount;
          final risk = useFallback ? 1 : dashboard.riskCount;

          return RefreshIndicator(
            onRefresh: () async => ref.invalidate(coachDashboardProvider),
            child: ListView(
              padding: const EdgeInsets.fromLTRB(16, 16, 16, 24),
              children: [
                _CoachHeader(name: user?.name ?? 'Coach', alertsCount: alertsCount),
                const SizedBox(height: 14),
                Row(
                  children: [
                    Expanded(child: AFStatTile(label: 'Prontos', value: ready.toString(), color: AppColors.green)),
                    const SizedBox(width: 8),
                    Expanded(child: AFStatTile(label: 'Atenção', value: attention.toString(), color: AppColors.attention)),
                    const SizedBox(width: 8),
                    Expanded(child: AFStatTile(label: 'Risco', value: risk.toString(), color: AppColors.risk)),
                  ],
                ),
                if (useFallback) ...[
                  const SizedBox(height: 10),
                  const Text(
                    'Exibindo dados de exemplo até seus atletas enviarem check-ins.',
                    style: TextStyle(color: AppColors.textSecondary, fontSize: 12),
                  ),
                ],
                const SizedBox(height: 18),
                Row(
                  children: [
                    const Expanded(
                      child: Text(
                        'ATLETAS',
                        style: TextStyle(
                          color: AppColors.textSecondary,
                          fontSize: 11,
                          fontWeight: FontWeight.w900,
                          letterSpacing: 1,
                        ),
                      ),
                    ),
                    TextButton(
                      onPressed: () => context.go('/dashboard'),
                      child: const Text('Dashboard'),
                    ),
                  ],
                ),
                const SizedBox(height: 8),
                if (items.isEmpty)
                  const AFEmptyState(
                    title: 'Nenhum atleta vinculado',
                    message: 'Quando atletas forem associados ao seu ID de coach, eles aparecerão aqui.',
                    icon: Icons.groups_rounded,
                  )
                else
                  ...items.map(
                    (item) {
                      final score = item.latest?.afScore;
                      final color = _statusColor(score);
                      final status = score == null ? 'Sem dados' : _shortStatus(score);
                      return Padding(
                        padding: const EdgeInsets.only(bottom: 8),
                        child: AFAthleteSummaryTile(
                          name: item.name,
                          subtitle: item.subtitle,
                          score: score,
                          status: status,
                          color: color,
                          onTap: item.id.startsWith('mock-') ? null : () => context.go('/athlete-detail/${item.id}'),
                        ),
                      );
                    },
                  ),
              ],
            ),
          );
        },
      ),
    );
  }

  Future<void> _handleNavTap(BuildContext context, WidgetRef ref, int index) async {
    switch (index) {
      case 0:
      case 1:
        context.go('/coach-home');
      case 2:
        context.go('/daily-checkin');
      case 3:
        context.go('/dashboard');
      case 4:
        context.go('/profile');
      default:
        await ref.read(authRepositoryProvider).signOut();
        if (context.mounted) context.go('/login');
    }
  }

  Color _statusColor(double? score) {
    if (score == null) return AppColors.neutral;
    return switch (afScoreStatus(score)) {
      AFScoreStatus.ready => AppColors.green,
      AFScoreStatus.attention => AppColors.attention,
      AFScoreStatus.risk => AppColors.risk,
    };
  }

  String _shortStatus(double score) {
    return switch (afScoreStatus(score)) {
      AFScoreStatus.ready => 'OK',
      AFScoreStatus.attention => '!',
      AFScoreStatus.risk => '!',
    };
  }
}

class _CoachHeader extends StatelessWidget {
  const _CoachHeader({required this.name, required this.alertsCount});

  final String name;
  final int alertsCount;

  @override
  Widget build(BuildContext context) {
    final today = DateFormat("EEEE · dd MMM yyyy", 'pt_BR').format(DateTime.now());
    return Row(
      children: [
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Olá, Coach $name',
                style: Theme.of(context).textTheme.titleMedium?.copyWith(fontWeight: FontWeight.w900),
              ),
              const SizedBox(height: 3),
              Text(today, style: const TextStyle(color: AppColors.textSecondary, fontSize: 12)),
            ],
          ),
        ),
        if (alertsCount > 0)
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
            decoration: BoxDecoration(
              color: AppColors.risk.withValues(alpha: .12),
              borderRadius: BorderRadius.circular(AppRadius.pill),
              border: Border.all(color: AppColors.risk.withValues(alpha: .35)),
            ),
            child: Text(
              '$alertsCount alertas',
              style: const TextStyle(color: AppColors.risk, fontWeight: FontWeight.w900, fontSize: 11),
            ),
          ),
      ],
    );
  }
}

class _CoachListItem {
  const _CoachListItem({
    required this.id,
    required this.name,
    required this.subtitle,
    required this.latest,
  });

  final String id;
  final String name;
  final String subtitle;
  final DailyCheckin? latest;
}
