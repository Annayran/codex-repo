import 'dart:math' as math;

import 'package:flutter/material.dart';

import '../../../../app/theme.dart';
import '../../../../core/utils/score_utils.dart';

class ReadinessRingChart extends StatelessWidget {
  const ReadinessRingChart({
    required this.score,
    this.size = 150,
    super.key,
  });

  final double score;
  final double size;

  @override
  Widget build(BuildContext context) {
    final color = readinessStatusColor(score);
    return TweenAnimationBuilder<double>(
      tween: Tween(begin: 0, end: score.clamp(0, 100).toDouble()),
      duration: const Duration(milliseconds: 650),
      curve: Curves.easeOutCubic,
      builder: (context, value, _) {
        return SizedBox(
          width: size,
          height: size,
          child: CustomPaint(
            painter: _RingPainter(value: value, color: color),
            child: Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(value.round().toString(), style: AppTypography.score(size: size * 0.28)),
                  Text(readinessStatusText(score).toUpperCase(), style: TextStyle(color: color, fontWeight: FontWeight.w900)),
                  const Text('de 100', style: TextStyle(color: AppColors.gray200, fontSize: 12)),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}

class _RingPainter extends CustomPainter {
  const _RingPainter({required this.value, required this.color});

  final double value;
  final Color color;

  @override
  void paint(Canvas canvas, Size size) {
    final stroke = size.width * 0.085;
    final rect = Offset(stroke / 2, stroke / 2) & Size(size.width - stroke, size.height - stroke);
    final bg = Paint()
      ..color = AppColors.gray700
      ..strokeWidth = stroke
      ..strokeCap = StrokeCap.round
      ..style = PaintingStyle.stroke;
    final fg = Paint()
      ..shader = SweepGradient(colors: [color.withValues(alpha: .55), color]).createShader(rect)
      ..strokeWidth = stroke
      ..strokeCap = StrokeCap.round
      ..style = PaintingStyle.stroke;
    canvas.drawArc(rect, -math.pi / 2, math.pi * 2, false, bg);
    canvas.drawArc(rect, -math.pi / 2, math.pi * 2 * (value / 100), false, fg);
  }

  @override
  bool shouldRepaint(covariant _RingPainter oldDelegate) => oldDelegate.value != value || oldDelegate.color != color;
}
