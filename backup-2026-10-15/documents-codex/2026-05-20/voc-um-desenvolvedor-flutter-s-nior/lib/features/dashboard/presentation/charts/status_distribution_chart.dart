import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';

import '../../../../app/theme.dart';

class StatusDistributionChart extends StatelessWidget {
  const StatusDistributionChart({
    required this.ready,
    required this.attention,
    required this.risk,
    this.height = 180,
    super.key,
  });

  final int ready;
  final int attention;
  final int risk;
  final double height;

  @override
  Widget build(BuildContext context) {
    final total = ready + attention + risk;
    final safeTotal = total == 0 ? 1 : total;
    final readyPercent = (ready / safeTotal * 100).round();
    return SizedBox(
      height: height,
      child: Row(
        children: [
          Expanded(
            child: Stack(
              alignment: Alignment.center,
              children: [
                PieChart(
                  PieChartData(
                    centerSpaceRadius: 46,
                    sectionsSpace: 2,
                    sections: [
                      PieChartSectionData(value: ready.toDouble(), color: AppColors.green, showTitle: false, radius: 22),
                      PieChartSectionData(value: attention.toDouble(), color: AppColors.attention, showTitle: false, radius: 22),
                      PieChartSectionData(value: risk.toDouble(), color: AppColors.risk, showTitle: false, radius: 22),
                      if (total == 0) PieChartSectionData(value: 1, color: AppColors.gray700, showTitle: false, radius: 22),
                    ],
                  ),
                ),
                Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Text('$readyPercent%', style: AppTypography.score(size: 28)),
                    const Text('Pronto', style: TextStyle(color: AppColors.green, fontWeight: FontWeight.w900)),
                  ],
                ),
              ],
            ),
          ),
          const SizedBox(width: 16),
          _Legend(color: AppColors.green, label: 'Pronto', value: ready),
          const SizedBox(width: 10),
          _Legend(color: AppColors.attention, label: 'Atenção', value: attention),
          const SizedBox(width: 10),
          _Legend(color: AppColors.risk, label: 'Risco', value: risk),
        ],
      ),
    );
  }
}

class _Legend extends StatelessWidget {
  const _Legend({required this.color, required this.label, required this.value});

  final Color color;
  final String label;
  final int value;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Container(width: 10, height: 10, decoration: BoxDecoration(color: color, shape: BoxShape.circle)),
        const SizedBox(height: 6),
        Text(label, style: const TextStyle(color: AppColors.gray200, fontSize: 11)),
        Text('$value', style: TextStyle(color: color, fontWeight: FontWeight.w900)),
      ],
    );
  }
}
