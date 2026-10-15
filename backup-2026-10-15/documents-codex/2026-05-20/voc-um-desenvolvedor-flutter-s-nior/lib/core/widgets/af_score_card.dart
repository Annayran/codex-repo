import 'package:flutter/material.dart';

import '../utils/readiness_calculator.dart';
import '../../app/theme.dart';

class AFScoreCard extends StatelessWidget {
  const AFScoreCard({
    required this.score,
    this.title = 'AF Score',
    super.key,
  });

  final double score;
  final String title;

  Color get _color {
    final status = afScoreStatus(score);
    return switch (status) {
      AFScoreStatus.ready => AFTheme.primary,
      AFScoreStatus.attention => AFTheme.warning,
      AFScoreStatus.risk => AFTheme.danger,
    };
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: AppColors.surface,
        borderRadius: BorderRadius.circular(AppRadius.card),
        border: Border.all(color: AppColors.border),
        boxShadow: const [
          BoxShadow(
            color: Color(0x66000000),
            blurRadius: 22,
            offset: Offset(0, 12),
          ),
        ],
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(AppRadius.card),
        child: Padding(
        padding: const EdgeInsets.all(20),
        child: Row(
          children: [
            SizedBox(
              width: 86,
              height: 86,
              child: Stack(
                fit: StackFit.expand,
                children: [
                  CircularProgressIndicator(
                    value: score / 100,
                    strokeWidth: 8,
                    color: _color,
                    strokeCap: StrokeCap.round,
                    backgroundColor: AppColors.surfaceAlt,
                  ),
                  Center(
                    child: Text(
                      score.round().toString(),
                      style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                            fontWeight: FontWeight.w900,
                            color: AFTheme.text,
                          ),
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(width: 18),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(title, style: Theme.of(context).textTheme.titleMedium),
                  const SizedBox(height: 6),
                  Text(
                    afScoreStatusLabel(score),
                    style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                          color: _color,
                          fontWeight: FontWeight.w900,
                          letterSpacing: .8,
                        ),
                  ),
                  const SizedBox(height: 6),
                  const Text(
                    'de 100',
                    style: TextStyle(color: AppColors.textSecondary, fontSize: 12),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
      ),
    );
  }
}
