import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/constants/mock_data.dart';
import '../../../core/widgets/af_app_bar.dart';
import '../../../core/widgets/af_chart_card.dart';
import '../../../core/widgets/af_empty_state.dart';
import '../../../core/widgets/af_metric_card.dart';
import '../../../core/widgets/af_status_chip.dart';
import '../../coach/data/coach_dashboard_provider.dart';
import 'charts/health_metric_cards.dart';
import 'charts/pain_areas_chart.dart';
import 'charts/readiness_line_chart.dart';
import 'charts/readiness_ring_chart.dart';
import 'charts/status_distribution_chart.dart';
import 'charts/weekly_training_bar_chart.dart';

class CoachDashboardScreen extends ConsumerWidget {
  const CoachDashboardScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final dashboard = ref.watch(coachDashboardProvider);

    return Scaffold(
      appBar: AFAppBar(title: 'Dashboard', onProfile: () => context.go('/profile')),
      body: dashboard.when(
        loading: () => const Center(child: CircularProgressIndicator(color: AppColors.green)),
        error: (_, __) => _DashboardContent(useFallback: true),
        data: (data) => _DashboardContent(
          useFallback: data.items.isEmpty,
          ready: data.readyCount,
          attention: data.attentionCount,
          risk: data.riskCount,
          athletes: data.items
              .map((item) => _DashboardAthlete(
                    name: item.athlete.name,
                    sport: item.athlete.sport,
                    score: item.latestCheckin?.afScore ?? 0,
                    lastCheckin: item.latestCheckin == null ? 'Sem check-in' : 'Check-in hoje',
                  ))
              .toList(),
        ),
      ),
    );
  }
}

class _DashboardContent extends StatelessWidget {
  _DashboardContent({
    required this.useFallback,
    this.ready = 0,
    this.attention = 0,
    this.risk = 0,
    List<_DashboardAthlete>? athletes,
  }) : athletes = athletes ?? _fallbackAthletes();

  final bool useFallback;
  final int ready;
  final int attention;
  final int risk;
  final List<_DashboardAthlete> athletes;

  @override
  Widget build(BuildContext context) {
    final displayAthletes = useFallback ? _fallbackAthletes() : athletes;
    final displayReady = useFallback ? 1 : ready;
    final displayAttention = useFallback ? 2 : attention;
    final displayRisk = useFallback ? 1 : risk;
    final averageScore = displayAthletes.isEmpty ? 0.0 : displayAthletes.map((e) => e.score).reduce((a, b) => a + b) / displayAthletes.length;

    if (!useFallback && displayAthletes.isEmpty) {
      return const AFEmptyState(
        icon: Icons.dashboard_rounded,
        title: 'Nenhum dado disponível',
        message: 'Quando os atletas responderem check-ins, seus indicadores aparecerão aqui.',
      );
    }

    return ListView(
      padding: const EdgeInsets.all(20),
      children: [
        if (useFallback)
          const Padding(
            padding: EdgeInsets.only(bottom: 12),
            child: Text('Exibindo dados mockados de fallback para validação do MVP.', style: TextStyle(color: AppColors.gray200)),
          ),
        Text('Performance dos atletas', style: Theme.of(context).textTheme.headlineSmall),
        const SizedBox(height: 6),
        const Text('Dados. Ciência. Performance.', style: TextStyle(color: AppColors.green, letterSpacing: 2, fontWeight: FontWeight.w800)),
        const SizedBox(height: 20),
        Row(
          children: [
            Expanded(child: AFMetricCard(title: 'Prontos', value: '$displayReady', color: AppColors.green)),
            const SizedBox(width: 10),
            Expanded(child: AFMetricCard(title: 'Atenção', value: '$displayAttention', color: AppColors.attention)),
            const SizedBox(width: 10),
            Expanded(child: AFMetricCard(title: 'Risco', value: '$displayRisk', color: AppColors.risk)),
          ],
        ),
        const SizedBox(height: 16),
        AFChartCard(
          title: 'Prontidão geral',
          subtitle: 'Média atual dos atletas acompanhados',
          child: Center(child: ReadinessRingChart(score: averageScore, size: 170)),
        ),
        const SizedBox(height: 16),
        const AFChartCard(
          title: 'AF Score - últimos 7 dias',
          subtitle: 'Linha com evolução semanal',
          child: ReadinessLineChart(values: [58, 64, 70, 72, 68, 79, 84]),
        ),
        const SizedBox(height: 16),
        const AFChartCard(
          title: 'Carga semanal',
          subtitle: 'Carga subjetiva estimada por duração x RPE',
          child: WeeklyTrainingBarChart(),
        ),
        const SizedBox(height: 16),
        AFChartCard(
          title: 'Distribuição de status',
          child: StatusDistributionChart(ready: displayReady, attention: displayAttention, risk: displayRisk),
        ),
        const SizedBox(height: 16),
        const AFChartCard(title: 'Métricas de saúde', child: HealthMetricCards()),
        const SizedBox(height: 16),
        const AFChartCard(title: 'Áreas de dor', child: PainAreasChart()),
        const SizedBox(height: 16),
        Text('Atletas em prioridade', style: Theme.of(context).textTheme.titleLarge),
        const SizedBox(height: 10),
        for (final athlete in [...displayAthletes]..sort((a, b) => a.score.compareTo(b.score))) ...[
          Card(
            child: ListTile(
              leading: ReadinessRingChart(score: athlete.score, size: 54),
              title: Text(athlete.name),
              subtitle: Text('${athlete.sport} • ${athlete.lastCheckin}'),
              trailing: AFStatusChip.fromScore(athlete.score),
            ),
          ),
          const SizedBox(height: 10),
        ],
      ],
    );
  }

  static List<_DashboardAthlete> _fallbackAthletes() {
    final athletes = MockData.athletes();
    final checkins = MockData.checkins();
    return [
      for (final athlete in athletes)
        _DashboardAthlete(
          name: athlete.name,
          sport: athlete.sport,
          score: checkins.firstWhere((checkin) => checkin.athleteId == athlete.id).afScore,
          lastCheckin: 'Check-in hoje',
        ),
    ];
  }
}

class _DashboardAthlete {
  const _DashboardAthlete({
    required this.name,
    required this.sport,
    required this.score,
    required this.lastCheckin,
  });

  final String name;
  final String sport;
  final double score;
  final String lastCheckin;
}
