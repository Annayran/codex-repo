import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import '../../../app/theme.dart';
import '../../../core/utils/date_utils.dart';
import '../../../core/utils/readiness_calculator.dart';
import '../../../core/widgets/af_empty_state.dart';
import '../../../core/widgets/af_loading_view.dart';
import '../../../core/widgets/af_score_card.dart';
import '../../athlete/data/athlete_repository.dart';
import '../../checkin/data/checkin_repository.dart';

class AthleteDetailScreen extends ConsumerWidget {
  const AthleteDetailScreen({required this.athleteId, super.key});

  final String athleteId;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final athleteState = ref.watch(athleteProvider(athleteId));
    final checkinsState = ref.watch(athleteCheckinsProvider(athleteId));

    return Scaffold(
      appBar: AppBar(title: const Text('Detalhe do atleta')),
      body: athleteState.when(
        loading: () => const AFLoadingView(),
        error: (error, _) => AFEmptyState(title: 'Erro', message: error.toString()),
        data: (athlete) {
          if (athlete == null) {
            return const AFEmptyState(title: 'Atleta não encontrado', message: 'Verifique o vínculo do atleta.');
          }

          return checkinsState.when(
            loading: () => const AFLoadingView(message: 'Carregando histórico...'),
            error: (error, _) => AFEmptyState(title: 'Erro', message: error.toString()),
            data: (checkins) {
              final latest = checkins.isEmpty ? null : checkins.first;
              final chartData = checkins.reversed.toList();

              return ListView(
                padding: const EdgeInsets.all(20),
                children: [
                  Card(
                    child: Padding(
                      padding: const EdgeInsets.all(20),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(athlete.name, style: Theme.of(context).textTheme.headlineSmall),
                          const SizedBox(height: 8),
                          Text('${athlete.sport} • ${athlete.level}'),
                          const SizedBox(height: 4),
                          Text(athlete.goal),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(height: 16),
                  if (latest == null)
                    const AFEmptyState(
                      title: 'Sem check-in',
                      message: 'Ainda não há dados de AF Score para este atleta.',
                    )
                  else ...[
                    AFScoreCard(score: latest.afScore, title: 'Último AF Score'),
                    const SizedBox(height: 16),
                    Card(
                      child: Padding(
                        padding: const EdgeInsets.all(20),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text('Evolução do AF Score', style: Theme.of(context).textTheme.titleLarge),
                            const SizedBox(height: 18),
                            SizedBox(
                              height: 220,
                              child: LineChart(
                                LineChartData(
                                  minY: 0,
                                  maxY: 100,
                                  gridData: const FlGridData(show: true),
                                  borderData: FlBorderData(show: false),
                                  titlesData: const FlTitlesData(
                                    topTitles: AxisTitles(sideTitles: SideTitles(showTitles: false)),
                                    rightTitles: AxisTitles(sideTitles: SideTitles(showTitles: false)),
                                  ),
                                  lineBarsData: [
                                    LineChartBarData(
                                      isCurved: true,
                                      color: AFTheme.primary,
                                      barWidth: 4,
                                      dotData: const FlDotData(show: true),
                                      spots: [
                                        for (var i = 0; i < chartData.length; i++)
                                          FlSpot(i.toDouble(), chartData[i].afScore),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(height: 20),
                    Text('Histórico', style: Theme.of(context).textTheme.titleLarge),
                    const SizedBox(height: 10),
                    ...checkins.map(
                      (checkin) => Card(
                        child: ListTile(
                          title: Text(AFDateUtils.fullDate.format(checkin.date)),
                          subtitle: Text(
                            '${afScoreStatusLabel(checkin.afScore)} • Dor ${checkin.pain}/10 • RPE ${checkin.rpe}/10',
                          ),
                          trailing: Text(
                            '${checkin.afScore.round()}%',
                            style: Theme.of(context).textTheme.titleLarge,
                          ),
                        ),
                      ),
                    ),
                  ],
                ],
              );
            },
          );
        },
      ),
    );
  }
}
