import 'package:flutter/material.dart';

import '../../../../app/theme.dart';
import '../../../../core/widgets/af_icons.dart';

class HealthMetricCards extends StatelessWidget {
  const HealthMetricCards({
    this.sleep = 8,
    this.energy = 82,
    this.mood = 78,
    this.stress = 36,
    super.key,
  });

  final int sleep;
  final int energy;
  final int mood;
  final int stress;

  @override
  Widget build(BuildContext context) {
    return GridView.count(
      crossAxisCount: 2,
      childAspectRatio: 1.25,
      shrinkWrap: true,
      physics: const NeverScrollableScrollPhysics(),
      mainAxisSpacing: 10,
      crossAxisSpacing: 10,
      children: [
        _Metric(icon: AFIcons.sleep, label: 'Sono', value: '${sleep}h', status: 'Bom', color: AppColors.green),
        _Metric(icon: AFIcons.energy, label: 'Energia', value: '$energy', status: 'Alta', color: AppColors.green),
        _Metric(icon: AFIcons.mood, label: 'Humor', value: '$mood', status: 'Bom', color: AppColors.attention),
        _Metric(icon: AFIcons.stress, label: 'Estresse', value: '$stress', status: 'Baixo', color: AppColors.green),
      ],
    );
  }
}

class _Metric extends StatelessWidget {
  const _Metric({required this.icon, required this.label, required this.value, required this.status, required this.color});

  final IconData icon;
  final String label;
  final String value;
  final String status;
  final Color color;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: AppColors.surface,
        borderRadius: BorderRadius.circular(AppRadius.card),
        border: Border.all(color: AppColors.border),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Icon(icon, color: color),
          Text(label.toUpperCase(), style: const TextStyle(fontSize: 11, color: AppColors.gray200, fontWeight: FontWeight.w800)),
          Text(value, style: AppTypography.score(size: 24)),
          Text(status, style: TextStyle(color: color, fontWeight: FontWeight.w800)),
        ],
      ),
    );
  }
}
