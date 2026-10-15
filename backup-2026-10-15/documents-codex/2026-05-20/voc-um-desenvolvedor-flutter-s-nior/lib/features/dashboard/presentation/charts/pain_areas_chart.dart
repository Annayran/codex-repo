import 'package:flutter/material.dart';

import '../../../../app/theme.dart';

class PainAreasChart extends StatelessWidget {
  const PainAreasChart({
    this.items = const {
      'Lombar': 9,
      'Joelho': 6,
      'Ombro': 4,
      'Panturrilha': 2,
    },
    super.key,
  });

  final Map<String, int> items;

  @override
  Widget build(BuildContext context) {
    if (items.isEmpty) {
      return const Text('Nenhuma área de dor reportada.', style: TextStyle(color: AppColors.gray200));
    }

    return Column(
      children: [
        for (final entry in items.entries) ...[
          _PainRow(area: entry.key, value: entry.value),
          const SizedBox(height: 10),
        ],
      ],
    );
  }
}

class _PainRow extends StatelessWidget {
  const _PainRow({required this.area, required this.value});

  final String area;
  final int value;

  @override
  Widget build(BuildContext context) {
    final level = value >= 8 ? 'Alta' : value >= 5 ? 'Média' : 'Leve';
    final color = value >= 8 ? AppColors.risk : value >= 5 ? AppColors.attention : AppColors.green;
    return Row(
      children: [
        SizedBox(width: 92, child: Text(area, style: const TextStyle(fontWeight: FontWeight.w700))),
        Expanded(
          child: ClipRRect(
            borderRadius: BorderRadius.circular(999),
            child: LinearProgressIndicator(
              value: (value / 10).clamp(0, 1),
              minHeight: 8,
              backgroundColor: AppColors.gray700,
              color: color,
            ),
          ),
        ),
        const SizedBox(width: 10),
        Container(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 4),
          decoration: BoxDecoration(color: color.withValues(alpha: .12), borderRadius: BorderRadius.circular(999)),
          child: Text(level, style: TextStyle(color: color, fontSize: 11, fontWeight: FontWeight.w900)),
        ),
      ],
    );
  }
}
