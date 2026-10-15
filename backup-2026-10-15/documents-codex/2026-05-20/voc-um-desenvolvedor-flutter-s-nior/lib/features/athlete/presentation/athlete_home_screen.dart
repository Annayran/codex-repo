import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/utils/readiness_calculator.dart';
import '../../../core/widgets/af_app_shell.dart';
import '../../../core/widgets/af_empty_state.dart';
import '../../../core/widgets/af_loading_view.dart';
import '../../../core/widgets/af_primary_button.dart';
import '../../auth/data/auth_repository.dart';
import '../../auth/presentation/current_user_provider.dart';
import '../../checkin/data/checkin_repository.dart';
import '../../checkin/domain/daily_checkin.dart';
import '../data/athlete_repository.dart';

class AthleteHomeScreen extends ConsumerWidget {
  const AthleteHomeScreen({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final userState = ref.watch(currentUserProvider);

    return userState.when(
      loading: () => const Scaffold(body: AFLoadingView()),
      error: (error, _) => Scaffold(body: AFEmptyState(title: 'Erro', message: error.toString())),
      data: (user) {
        if (user == null) return const Scaffold(body: AFLoadingView());

        return AFAppShell(
          currentIndex: 0,
          initials: initialsFromName(user.name),
          onProfile: () => context.go('/profile'),
          onNotifications: () {},
          onNavTap: (index) => _handleNavTap(context, ref, index),
          child: ref.watch(athleteByUserProvider(user.id)).when(
                loading: () => const AFLoadingView(message: 'Carregando atleta...'),
                error: (error, _) => AFEmptyState(title: 'Erro', message: error.toString()),
                data: (athlete) {
                  if (athlete == null) {
                    return _CreateAthleteProfile(name: user.name, userId: user.id);
                  }

                  return ref.watch(athleteCheckinsProvider(athlete.id)).when(
                        loading: () => const AFLoadingView(message: 'Buscando check-ins...'),
                        error: (error, _) => AFEmptyState(title: 'Erro', message: error.toString()),
                        data: (checkins) {
                          final latest = checkins.isEmpty ? null : checkins.first;
                          return RefreshIndicator(
                            onRefresh: () async => ref.invalidate(athleteCheckinsProvider(athlete.id)),
                            child: ListView(
                              padding: const EdgeInsets.fromLTRB(18, 18, 18, 24),
                              children: [
                                _AthleteGreeting(name: user.name, planName: athlete.planName),
                                const SizedBox(height: 16),
                                Center(child: _ScoreHero(latest: latest)),
                                const SizedBox(height: 18),
                                _MetricsGrid(latest: latest),
                                const SizedBox(height: 12),
                                AFTrendPreviewCard(values: checkins.map((item) => item.afScore).toList()),
                                const SizedBox(height: 16),
                                AFPrimaryButton(
                                  label: latest == null ? 'Responder check-in' : 'Atualizar check-in',
                                  icon: Icons.fact_check_outlined,
                                  onPressed: () => context.go('/daily-checkin'),
                                ),
                                if (latest?.hasRiskAlert ?? false) ...[
                                  const SizedBox(height: 14),
                                  _RiskWarningCard(latest: latest!),
                                ],
                              ],
                            ),
                          );
                        },
                      );
                },
              ),
        );
      },
    );
  }

  Future<void> _handleNavTap(BuildContext context, WidgetRef ref, int index) async {
    switch (index) {
      case 0:
        context.go('/athlete-home');
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
}

class _AthleteGreeting extends StatelessWidget {
  const _AthleteGreeting({required this.name, required this.planName});

  final String name;
  final String planName;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text('Bom dia,', style: TextStyle(color: AppColors.textSecondary, fontSize: 13)),
        const SizedBox(height: 2),
        Text(
          name,
          style: Theme.of(context).textTheme.titleLarge?.copyWith(fontWeight: FontWeight.w900),
        ),
        const SizedBox(height: 4),
        Text('Plano: $planName', style: const TextStyle(color: AppColors.textSecondary)),
      ],
    );
  }
}

class _ScoreHero extends StatelessWidget {
  const _ScoreHero({required this.latest});

  final DailyCheckin? latest;

  @override
  Widget build(BuildContext context) {
    final score = latest?.afScore ?? 0;
    final status = latest == null ? 'Sem dados' : afScoreStatusLabel(score);
    final color = latest == null
        ? AppColors.neutral
        : switch (afScoreStatus(score)) {
            AFScoreStatus.ready => AppColors.green,
            AFScoreStatus.attention => AppColors.attention,
            AFScoreStatus.risk => AppColors.risk,
          };

    return Column(
      children: [
        AFCompactScoreRing(score: score, status: status, color: color, size: 118),
        const SizedBox(height: 10),
        Text(
          latest == null ? 'Responda seu check-in para gerar seu AF Score.' : _scoreMessage(score),
          textAlign: TextAlign.center,
          style: const TextStyle(color: AppColors.textSecondary, height: 1.4),
        ),
      ],
    );
  }

  String _scoreMessage(double score) {
    if (score >= 80) return 'Muito bem! Seu corpo está respondendo bem.';
    if (score >= 60) return 'Atenção aos sinais. Ajuste a intensidade se necessário.';
    return 'Risco elevado. Priorize recuperação e fale com seu coach.';
  }
}

class _MetricsGrid extends StatelessWidget {
  const _MetricsGrid({required this.latest});

  final DailyCheckin? latest;

  @override
  Widget build(BuildContext context) {
    return GridView(
      shrinkWrap: true,
      physics: const NeverScrollableScrollPhysics(),
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        crossAxisSpacing: 8,
        mainAxisSpacing: 8,
        childAspectRatio: 1.75,
      ),
      children: [
        AFMiniMetricCard(icon: Icons.nightlight_round, label: 'Sono', value: latest == null ? '--' : '${latest!.sleep}/10'),
        AFMiniMetricCard(icon: Icons.bolt_rounded, label: 'Energia', value: latest == null ? '--' : '${latest!.energy * 10}'),
        AFMiniMetricCard(icon: Icons.sentiment_satisfied_alt_rounded, label: 'Humor', value: latest == null ? '--' : '${latest!.mood * 10}'),
        AFMiniMetricCard(
          icon: Icons.psychology_alt_outlined,
          label: 'Estresse',
          value: latest == null ? '--' : '${latest!.stress * 10}',
          color: (latest?.stress ?? 0) >= 6 ? AppColors.attention : AppColors.white,
        ),
      ],
    );
  }
}

class _RiskWarningCard extends StatelessWidget {
  const _RiskWarningCard({required this.latest});

  final DailyCheckin latest;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: AppColors.risk.withValues(alpha: .08),
        borderRadius: BorderRadius.circular(AppRadius.card),
        border: Border.all(color: AppColors.risk.withValues(alpha: .35)),
      ),
      child: Row(
        children: [
          const Icon(Icons.warning_amber_rounded, color: AppColors.risk),
          const SizedBox(width: 12),
          Expanded(
            child: Text(
              latest.alertReasons.isEmpty
                  ? 'Seu AF Score está baixo hoje. Priorize recuperação.'
                  : latest.alertReasons.first,
              style: const TextStyle(color: AppColors.white, fontWeight: FontWeight.w700),
            ),
          ),
        ],
      ),
    );
  }
}

class _CreateAthleteProfile extends ConsumerStatefulWidget {
  const _CreateAthleteProfile({required this.userId, required this.name});

  final String userId;
  final String name;

  @override
  ConsumerState<_CreateAthleteProfile> createState() => _CreateAthleteProfileState();
}

class _CreateAthleteProfileState extends ConsumerState<_CreateAthleteProfile> {
  bool _isLoading = false;

  Future<void> _create() async {
    setState(() => _isLoading = true);
    try {
      await ref.read(athleteRepositoryProvider).ensureAthleteProfile(
            userId: widget.userId,
            name: widget.name,
          );
      ref.invalidate(athleteByUserProvider(widget.userId));
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(24),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const AFEmptyState(
            title: 'Perfil de atleta',
            message: 'Crie seu perfil interno para começar a registrar check-ins.',
          ),
          const SizedBox(height: 20),
          AFPrimaryButton(label: 'Criar perfil', onPressed: _create, isLoading: _isLoading),
        ],
      ),
    );
  }
}
