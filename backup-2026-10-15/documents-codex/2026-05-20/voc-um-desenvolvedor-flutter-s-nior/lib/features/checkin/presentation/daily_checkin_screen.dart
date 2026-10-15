import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/utils/readiness_calculator.dart';
import '../../../core/widgets/af_app_shell.dart';
import '../../../core/widgets/af_primary_button.dart';
import '../../athlete/data/athlete_repository.dart';
import '../../auth/presentation/current_user_provider.dart';
import '../../coach/data/alert_repository.dart';
import '../../coach/domain/app_alert.dart';
import '../data/checkin_repository.dart';
import '../domain/daily_checkin.dart';

class DailyCheckinScreen extends ConsumerStatefulWidget {
  const DailyCheckinScreen({super.key});

  @override
  ConsumerState<DailyCheckinScreen> createState() => _DailyCheckinScreenState();
}

class _DailyCheckinScreenState extends ConsumerState<DailyCheckinScreen> {
  int sleep = 7;
  int energy = 7;
  int stress = 3;
  int pain = 3;
  int fatigue = 3;
  int mood = 7;
  int rpe = 5;
  int trainingDurationMinutes = 45;
  int waterScore = 7;
  double waterLiters = 2;
  String nutritionQuality = 'Boa';
  bool isLoading = false;

  final notesController = TextEditingController();
  final Set<String> painAreas = {'nenhuma'};

  static const areas = ['nenhuma', 'joelho', 'lombar', 'ombro', 'tornozelo', 'posterior', 'quadril'];
  static const nutritionOptions = ['Ruim', 'Regular', 'Boa', 'Excelente'];

  int get trainingLoad => trainingDurationMinutes * rpe;

  double get score => calculateAFScore(
        sleep: sleep,
        energy: energy,
        mood: mood,
        stress: stress,
        pain: pain,
        fatigue: fatigue,
      );

  List<String> get alertReasons => evaluateAlertReasons(
        afScore: score,
        sleep: sleep,
        pain: pain,
        fatigue: fatigue,
        rpe: rpe,
      );

  @override
  void dispose() {
    notesController.dispose();
    super.dispose();
  }

  Future<void> _save() async {
    final user = ref.read(currentUserProvider).valueOrNull;
    if (user == null) return;

    setState(() => isLoading = true);
    try {
      final athlete = await ref.read(athleteRepositoryProvider).ensureAthleteProfile(
            userId: user.id,
            name: user.name,
          );
      final now = DateTime.now();
      final id = '${athlete.id}_${now.year}_${now.month}_${now.day}';
      final reasons = alertReasons;
      final status = afScoreStatusLabel(score);
      final alertLevel = reasons.isEmpty ? 'none' : 'risk';

      await ref.read(checkinRepositoryProvider).saveCheckin(
            DailyCheckin(
              id: id,
              athleteId: athlete.id,
              coachId: athlete.coachId,
              date: DateTime(now.year, now.month, now.day),
              sleep: sleep,
              energy: energy,
              stress: stress,
              pain: pain,
              fatigue: fatigue,
              mood: mood,
              rpe: rpe,
              trainingDurationMinutes: trainingDurationMinutes,
              trainingLoad: trainingLoad,
              nutritionQuality: nutritionQuality,
              waterLiters: waterLiters,
              waterScore: waterScore,
              painAreas: painAreas.toList(),
              afScore: score,
              status: status,
              alertLevel: alertLevel,
              alertReasons: reasons,
              notes: notesController.text.trim(),
              createdAt: now,
            ),
          );

      if (reasons.isNotEmpty && athlete.coachId.isNotEmpty) {
        await ref.read(alertRepositoryProvider).saveAlert(
              AppAlert(
                id: id,
                athleteId: athlete.id,
                coachId: athlete.coachId,
                checkinId: id,
                level: 'risk',
                title: 'Alerta de risco',
                message: '${athlete.name} registrou AF Score baixo ou sinais de risco hoje.',
                reasons: reasons,
                isRead: false,
                createdAt: now,
              ),
            );
      }

      if (mounted) {
        final message = reasons.isEmpty
            ? 'Check-in salvo com sucesso.'
            : 'Check-in salvo. Seu AF Score está baixo hoje. Priorize recuperação e converse com seu coach.';
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message)));
        context.go('/athlete-home');
      }
    } catch (error) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Erro ao salvar check-in: $error')),
        );
      }
    } finally {
      if (mounted) setState(() => isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final reasons = alertReasons;
    final statusColor = switch (afScoreStatus(score)) {
      AFScoreStatus.ready => AppColors.green,
      AFScoreStatus.attention => AppColors.attention,
      AFScoreStatus.risk => AppColors.risk,
    };

    return AFAppShell(
      title: 'Check-in Diário',
      currentIndex: 2,
      onBack: () => context.go('/athlete-home'),
      onNavTap: (index) {
        switch (index) {
          case 0:
            context.go('/athlete-home');
          case 2:
            break;
          case 3:
            context.go('/dashboard');
          case 4:
            context.go('/profile');
        }
      },
      child: ListView(
        padding: const EdgeInsets.fromLTRB(16, 16, 16, 24),
        children: [
          Row(
            children: [
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'COMO VOCÊ ESTÁ HOJE?',
                      style: TextStyle(
                        color: AppColors.textSecondary,
                        fontSize: 11,
                        fontWeight: FontWeight.w900,
                        letterSpacing: 1,
                      ),
                    ),
                    const SizedBox(height: 8),
                    Text(
                      '${score.round()} · ${afScoreStatusLabel(score)}',
                      style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                            color: statusColor,
                            fontWeight: FontWeight.w900,
                          ),
                    ),
                  ],
                ),
              ),
              AFCompactScoreRing(score: score, status: afScoreStatusLabel(score), color: statusColor, size: 86),
            ],
          ),
          if (reasons.isNotEmpty) ...[
            const SizedBox(height: 12),
            _InlineAlert(text: reasons.first),
          ],
          const SizedBox(height: 16),
          _CheckinSlider(icon: Icons.nightlight_round, label: 'Sono', value: sleep, onChanged: (value) => setState(() => sleep = value)),
          _CheckinSlider(icon: Icons.bolt_rounded, label: 'Energia', value: energy, onChanged: (value) => setState(() => energy = value)),
          _CheckinSlider(
            icon: Icons.psychology_alt_outlined,
            label: 'Estresse',
            value: stress,
            color: stress >= 6 ? AppColors.attention : AppColors.green,
            onChanged: (value) => setState(() => stress = value),
          ),
          _CheckinSlider(icon: Icons.flash_on_rounded, label: 'Dor muscular', value: pain, onChanged: (value) => setState(() => pain = value)),
          _CheckinSlider(icon: Icons.autorenew_rounded, label: 'Fadiga', value: fatigue, onChanged: (value) => setState(() => fatigue = value)),
          _CheckinSlider(icon: Icons.sentiment_satisfied_alt_rounded, label: 'Humor', value: mood, onChanged: (value) => setState(() => mood = value)),
          _CheckinSlider(icon: Icons.speed_rounded, label: 'RPE do exercício', value: rpe, onChanged: (value) => setState(() => rpe = value)),
          _CheckinSlider(icon: Icons.water_drop_outlined, label: 'Água no dia', value: waterScore, onChanged: (value) => setState(() => waterScore = value)),
          const SizedBox(height: 8),
          _TrainingLoadCard(
            duration: trainingDurationMinutes,
            load: trainingLoad,
            onChanged: (value) => setState(() => trainingDurationMinutes = value),
          ),
          const SizedBox(height: 12),
          _WaterLitersCard(value: waterLiters, onChanged: (value) => setState(() => waterLiters = value)),
          const SizedBox(height: 18),
          const _SectionLabel('Alimentação'),
          Wrap(
            spacing: 7,
            runSpacing: 7,
            children: nutritionOptions.map((option) {
              final selected = nutritionQuality == option;
              return ChoiceChip(
                label: Text(option),
                selected: selected,
                onSelected: (_) => setState(() => nutritionQuality = option),
              );
            }).toList(),
          ),
          const SizedBox(height: 18),
          const _SectionLabel('Áreas de dor'),
          Wrap(
            spacing: 7,
            runSpacing: 7,
            children: areas.map((area) {
              final selected = painAreas.contains(area);
              return FilterChip(
                label: Text(_capitalize(area)),
                selected: selected,
                onSelected: (value) {
                  setState(() {
                    if (area == 'nenhuma') {
                      painAreas
                        ..clear()
                        ..add('nenhuma');
                    } else {
                      painAreas.remove('nenhuma');
                      value ? painAreas.add(area) : painAreas.remove(area);
                      if (painAreas.isEmpty) painAreas.add('nenhuma');
                    }
                  });
                },
              );
            }).toList(),
          ),
          const SizedBox(height: 18),
          TextField(
            controller: notesController,
            minLines: 2,
            maxLines: 4,
            decoration: const InputDecoration(
              labelText: 'Observação opcional',
              hintText: 'Ex.: dor no joelho durante agachamento',
            ),
          ),
          const SizedBox(height: 24),
          AFPrimaryButton(
            label: 'Salvar Check-in',
            icon: Icons.check_circle_outline_rounded,
            onPressed: _save,
            isLoading: isLoading,
          ),
        ],
      ),
    );
  }

  String _capitalize(String value) {
    if (value.isEmpty) return value;
    return value[0].toUpperCase() + value.substring(1);
  }
}

class _CheckinSlider extends StatelessWidget {
  const _CheckinSlider({
    required this.icon,
    required this.label,
    required this.value,
    required this.onChanged,
    this.color = AppColors.green,
  });

  final IconData icon;
  final String label;
  final int value;
  final Color color;
  final ValueChanged<int> onChanged;

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(bottom: 12),
      padding: const EdgeInsets.fromLTRB(14, 12, 14, 8),
      decoration: BoxDecoration(
        color: AppColors.surface,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: AppColors.border),
      ),
      child: Column(
        children: [
          Row(
            children: [
              Icon(icon, color: color, size: 18),
              const SizedBox(width: 8),
              Expanded(
                child: Text(
                  label.toUpperCase(),
                  style: const TextStyle(
                    color: AppColors.textSecondary,
                    fontSize: 11,
                    fontWeight: FontWeight.w900,
                    letterSpacing: .6,
                  ),
                ),
              ),
              Text(value.toString(), style: TextStyle(color: color, fontWeight: FontWeight.w900, fontSize: 16)),
            ],
          ),
          SliderTheme(
            data: SliderTheme.of(context).copyWith(
              activeTrackColor: color,
              inactiveTrackColor: AppColors.border,
              thumbColor: color,
              overlayColor: color.withValues(alpha: .12),
              trackHeight: 3,
            ),
            child: Slider(
              value: value.toDouble(),
              min: 0,
              max: 10,
              divisions: 10,
              label: value.toString(),
              onChanged: (newValue) => onChanged(newValue.round()),
            ),
          ),
        ],
      ),
    );
  }
}

class _TrainingLoadCard extends StatelessWidget {
  const _TrainingLoadCard({
    required this.duration,
    required this.load,
    required this.onChanged,
  });

  final int duration;
  final int load;
  final ValueChanged<int> onChanged;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: AppColors.surface,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: AppColors.border),
      ),
      child: Row(
        children: [
          const Icon(Icons.timer_outlined, color: AppColors.green),
          const SizedBox(width: 10),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text('DURAÇÃO DO TREINO', style: TextStyle(color: AppColors.textSecondary, fontSize: 11, fontWeight: FontWeight.w900)),
                const SizedBox(height: 3),
                Text('Carga calculada: $load', style: const TextStyle(color: AppColors.textSecondary, fontSize: 12)),
              ],
            ),
          ),
          IconButton(onPressed: duration <= 0 ? null : () => onChanged(duration - 5), icon: const Icon(Icons.remove_rounded)),
          Text('$duration min', style: const TextStyle(fontWeight: FontWeight.w900)),
          IconButton(onPressed: () => onChanged(duration + 5), icon: const Icon(Icons.add_rounded)),
        ],
      ),
    );
  }
}

class _WaterLitersCard extends StatelessWidget {
  const _WaterLitersCard({required this.value, required this.onChanged});

  final double value;
  final ValueChanged<double> onChanged;

  @override
  Widget build(BuildContext context) {
    return _SliderCard(
      label: 'Água em litros',
      valueLabel: '${value.toStringAsFixed(1)} L',
      child: Slider(
        value: value,
        min: 0,
        max: 5,
        divisions: 10,
        label: '${value.toStringAsFixed(1)} L',
        onChanged: onChanged,
      ),
    );
  }
}

class _SliderCard extends StatelessWidget {
  const _SliderCard({required this.label, required this.valueLabel, required this.child});

  final String label;
  final String valueLabel;
  final Widget child;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.fromLTRB(14, 12, 14, 8),
      decoration: BoxDecoration(
        color: AppColors.surface,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: AppColors.border),
      ),
      child: Column(
        children: [
          Row(
            children: [
              Expanded(child: Text(label.toUpperCase(), style: const TextStyle(color: AppColors.textSecondary, fontSize: 11, fontWeight: FontWeight.w900))),
              Text(valueLabel, style: const TextStyle(color: AppColors.green, fontWeight: FontWeight.w900)),
            ],
          ),
          child,
        ],
      ),
    );
  }
}

class _SectionLabel extends StatelessWidget {
  const _SectionLabel(this.label);

  final String label;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(bottom: 8),
      child: Text(
        label.toUpperCase(),
        style: const TextStyle(
          color: AppColors.textSecondary,
          fontSize: 11,
          fontWeight: FontWeight.w900,
          letterSpacing: .8,
        ),
      ),
    );
  }
}

class _InlineAlert extends StatelessWidget {
  const _InlineAlert({required this.text});

  final String text;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: AppColors.risk.withValues(alpha: .08),
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: AppColors.risk.withValues(alpha: .35)),
      ),
      child: Row(
        children: [
          const Icon(Icons.warning_amber_rounded, color: AppColors.risk, size: 20),
          const SizedBox(width: 10),
          Expanded(child: Text(text, style: const TextStyle(color: AppColors.white, fontWeight: FontWeight.w700))),
        ],
      ),
    );
  }
}
