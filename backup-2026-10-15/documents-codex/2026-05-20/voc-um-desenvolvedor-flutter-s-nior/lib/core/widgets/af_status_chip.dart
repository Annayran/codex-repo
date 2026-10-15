import 'package:flutter/material.dart';

import '../../app/theme.dart';
import '../utils/score_utils.dart';

class AFStatusChip extends StatelessWidget {
  const AFStatusChip({
    required this.label,
    required this.color,
    this.icon,
    super.key,
  });

  factory AFStatusChip.fromScore(double score) {
    return AFStatusChip(
      label: readinessStatusText(score),
      color: readinessStatusColor(score),
      icon: score >= 80 ? Icons.check_rounded : score >= 60 ? Icons.priority_high_rounded : Icons.warning_amber_rounded,
    );
  }

  final String label;
  final Color color;
  final IconData? icon;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 11, vertical: 5),
      decoration: BoxDecoration(
        color: color.withValues(alpha: 0.12),
        border: Border.all(color: color.withValues(alpha: 0.30)),
        borderRadius: BorderRadius.circular(AppRadius.pill),
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          if (icon != null) ...[
            Icon(icon, color: color, size: 16),
            const SizedBox(width: 6),
          ],
          Text(
            label.toUpperCase(),
            style: TextStyle(
              color: color,
              fontWeight: FontWeight.w900,
              fontSize: 11,
              letterSpacing: .5,
            ),
          ),
        ],
      ),
    );
  }
}
