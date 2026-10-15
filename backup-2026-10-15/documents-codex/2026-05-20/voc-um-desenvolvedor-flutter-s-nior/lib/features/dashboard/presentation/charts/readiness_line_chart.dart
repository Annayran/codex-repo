import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';

import '../../../../app/theme.dart';

class ReadinessLineChart extends StatelessWidget {
  const ReadinessLineChart({
    required this.values,
    this.height = 190,
    super.key,
  });

  final List<double> values;
  final double height;

  @override
  Widget build(BuildContext context) {
    final chartValues = values.isEmpty ? const <double>[62, 68, 71, 66, 78, 84, 88] : values.take(7).toList();
    final labels = const ['S', 'T', 'Q', 'Q', 'S', 'S', 'D'];
    return SizedBox(
      height: height,
      child: LineChart(
        LineChartData(
          minY: 0,
          maxY: 100,
          gridData: FlGridData(getDrawingHorizontalLine: (_) => const FlLine(color: AppColors.gray700, strokeWidth: .8)),
          borderData: FlBorderData(show: false),
          titlesData: FlTitlesData(
            topTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
            rightTitles: const AxisTitles(sideTitles: SideTitles(showTitles: false)),
            leftTitles: const AxisTitles(sideTitles: SideTitles(showTitles: true, reservedSize: 34)),
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
          lineBarsData: [
            LineChartBarData(
              spots: [for (var i = 0; i < chartValues.length; i++) FlSpot(i.toDouble(), chartValues[i])],
              color: AppColors.green,
              barWidth: 3,
              isCurved: true,
              dotData: const FlDotData(show: true),
              belowBarData: BarAreaData(show: true, color: AppColors.green.withValues(alpha: .12)),
            ),
          ],
        ),
      ),
    );
  }
}
