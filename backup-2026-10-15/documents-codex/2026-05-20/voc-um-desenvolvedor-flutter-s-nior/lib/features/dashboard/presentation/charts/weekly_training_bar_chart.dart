import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';

import '../../../../app/theme.dart';

class WeeklyTrainingBarChart extends StatelessWidget {
  const WeeklyTrainingBarChart({
    this.values = const [280, 420, 260, 510, 390, 560, 360],
    this.height = 190,
    super.key,
  });

  final List<double> values;
  final double height;

  @override
  Widget build(BuildContext context) {
    final labels = const ['S', 'T', 'Q', 'Q', 'S', 'S', 'D'];
    final maxValue = values.fold<double>(0, (max, value) => value > max ? value : max);
    return SizedBox(
      height: height,
      child: BarChart(
        BarChartData(
          maxY: (maxValue + 120).clamp(500, 1000),
          gridData: FlGridData(getDrawingHorizontalLine: (_) => const FlLine(color: AppColors.gray700, strokeWidth: .8)),
          borderData: FlBorderData(show: false),
          titlesData: FlTitlesData(
            topTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
            rightTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
            leftTitles: const AxisTitles(sideTitles: SideTitles(showTitles: true, reservedSize: 38)),
            bottomTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: true,
                getTitlesWidget: (value, _) {
                  final index = value.toInt();
                  if (index < 0 || index >= labels.length) return const SizedBox.shrink();
                  return Text(labels[index], style: const TextStyle(color: AppColors.gray200, fontSize: 12));
                },
              ),
            ),
          ),
          barGroups: [
            for (var i = 0; i < values.length; i++)
              BarChartGroupData(
                x: i,
                barRods: [
                  BarChartRodData(
                    toY: values[i],
                    width: 18,
                    borderRadius: BorderRadius.circular(5),
                    gradient: AppColors.primaryGradient,
                  ),
                ],
              ),
          ],
        ),
      ),
    );
  }
}
