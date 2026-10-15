import 'dart:math' as math;

import 'package:flutter/material.dart';

import '../../app/theme.dart';
import 'af_icons.dart';
import 'af_logo.dart';

class AFAppShell extends StatelessWidget {
  const AFAppShell({
    required this.child,
    required this.currentIndex,
    required this.onNavTap,
    this.onNotifications,
    this.onProfile,
    this.onBack,
    this.title,
    this.initials,
    super.key,
  });

  final Widget child;
  final int currentIndex;
  final ValueChanged<int> onNavTap;
  final VoidCallback? onNotifications;
  final VoidCallback? onProfile;
  final VoidCallback? onBack;
  final String? title;
  final String? initials;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.black,
      body: DecoratedBox(
        decoration: const BoxDecoration(
          gradient: RadialGradient(
            center: Alignment(0.7, -0.85),
            radius: 1.15,
            colors: [Color(0x1A32D74B), AppColors.black, AppColors.pureBlack],
            stops: [0, .58, 1],
          ),
        ),
        child: Stack(
          children: [
            const Positioned.fill(child: CustomPaint(painter: _AFScreenPatternPainter())),
            SafeArea(
              child: Column(
                children: [
                  AFTopBar(
                    title: title,
                    initials: initials,
                    onBack: onBack,
                    onNotifications: onNotifications,
                    onProfile: onProfile,
                  ),
                  Expanded(child: child),
                ],
              ),
            ),
          ],
        ),
      ),
      bottomNavigationBar: AFDesignBottomNavBar(
        currentIndex: currentIndex,
        onTap: onNavTap,
      ),
    );
  }
}

class AFTopBar extends StatelessWidget {
  const AFTopBar({
    this.title,
    this.initials,
    this.onBack,
    this.onNotifications,
    this.onProfile,
    super.key,
  });

  final String? title;
  final String? initials;
  final VoidCallback? onBack;
  final VoidCallback? onNotifications;
  final VoidCallback? onProfile;

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 58,
      padding: const EdgeInsets.symmetric(horizontal: 14),
      decoration: const BoxDecoration(
        color: AppColors.navBlack,
        border: Border(bottom: BorderSide(color: AppColors.border)),
      ),
      child: Stack(
        alignment: Alignment.center,
        children: [
          Align(
            alignment: Alignment.centerLeft,
            child: IconButton(
              tooltip: onBack == null ? 'Menu' : 'Voltar',
              onPressed: onBack,
              icon: Icon(onBack == null ? Icons.menu_rounded : Icons.arrow_back_rounded),
            ),
          ),
          Center(
            child: title == null
                ? const AFLogo(variant: AFLogoVariant.wordmark, size: 20)
                : Text(
                    title!,
                    style: Theme.of(context).textTheme.labelLarge?.copyWith(
                          fontWeight: FontWeight.w900,
                          letterSpacing: 1.4,
                        ),
                  ),
          ),
          Align(
            alignment: Alignment.centerRight,
            child: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                IconButton(
                  tooltip: 'Notificações',
                  onPressed: onNotifications,
                  icon: const Icon(AFIcons.notifications, size: 21),
                ),
                if (initials != null)
                  InkWell(
                    onTap: onProfile,
                    borderRadius: BorderRadius.circular(999),
                    child: Container(
                      width: 30,
                      height: 30,
                      alignment: Alignment.center,
                      decoration: BoxDecoration(
                        color: AppColors.green.withValues(alpha: .08),
                        shape: BoxShape.circle,
                        border: Border.all(color: AppColors.green, width: 1.5),
                      ),
                      child: Text(
                        initials!,
                        style: const TextStyle(
                          color: AppColors.green,
                          fontSize: 10,
                          fontWeight: FontWeight.w900,
                        ),
                      ),
                    ),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class AFDesignBottomNavBar extends StatelessWidget {
  const AFDesignBottomNavBar({
    required this.currentIndex,
    required this.onTap,
    super.key,
  });

  final int currentIndex;
  final ValueChanged<int> onTap;

  @override
  Widget build(BuildContext context) {
    const items = [
      _NavItem(AFIcons.home, 'Início'),
      _NavItem(AFIcons.athlete, 'Atletas'),
      _NavItem(AFIcons.checkin, 'Check-in'),
      _NavItem(AFIcons.dashboard, 'Dashboard'),
      _NavItem(AFIcons.profile, 'Perfil'),
    ];

    return SafeArea(
      top: false,
      child: Container(
        height: 70,
        padding: const EdgeInsets.symmetric(horizontal: 6),
        decoration: const BoxDecoration(
          color: AppColors.navBlack,
          border: Border(top: BorderSide(color: AppColors.border)),
        ),
        child: Row(
          children: [
            for (var i = 0; i < items.length; i++)
              Expanded(
                child: _BottomNavButton(
                  item: items[i],
                  selected: currentIndex == i,
                  elevated: i == 2,
                  onTap: () => onTap(i),
                ),
              ),
          ],
        ),
      ),
    );
  }
}

class AFCompactScoreRing extends StatelessWidget {
  const AFCompactScoreRing({
    required this.score,
    required this.status,
    required this.color,
    this.size = 112,
    super.key,
  });

  final double score;
  final String status;
  final Color color;
  final double size;

  @override
  Widget build(BuildContext context) {
    return SizedBox.square(
      dimension: size,
      child: CustomPaint(
        painter: _AFRingPainter(score: score, color: color),
        child: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                score.round().toString(),
                style: Theme.of(context).textTheme.headlineMedium?.copyWith(
                      fontWeight: FontWeight.w900,
                      height: .95,
                    ),
              ),
              Text(
                status.toUpperCase(),
                style: TextStyle(
                  color: color,
                  fontSize: 10,
                  fontWeight: FontWeight.w900,
                  letterSpacing: .7,
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class AFMiniMetricCard extends StatelessWidget {
  const AFMiniMetricCard({
    required this.icon,
    required this.label,
    required this.value,
    this.color = AppColors.white,
    super.key,
  });

  final IconData icon;
  final String label;
  final String value;
  final Color color;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
        color: AppColors.surfaceAlt,
        borderRadius: BorderRadius.circular(9),
        border: Border.all(color: AppColors.border),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Icon(icon, color: color == AppColors.white ? AppColors.green : color, size: 17),
          const SizedBox(height: 6),
          Text(
            label.toUpperCase(),
            style: const TextStyle(
              color: AppColors.textSecondary,
              fontSize: 9,
              fontWeight: FontWeight.w800,
              letterSpacing: .8,
            ),
          ),
          const SizedBox(height: 3),
          Text(
            value,
            style: TextStyle(
              color: color,
              fontSize: 15,
              fontWeight: FontWeight.w900,
            ),
          ),
        ],
      ),
    );
  }
}

class AFTrendPreviewCard extends StatelessWidget {
  const AFTrendPreviewCard({required this.values, super.key});

  final List<double> values;

  @override
  Widget build(BuildContext context) {
    final data = values.isEmpty ? const <double>[58, 65, 63, 74, 70, 82, 84] : values.take(7).toList();
    return Container(
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: AppColors.surfaceAlt,
        borderRadius: BorderRadius.circular(9),
        border: Border.all(color: AppColors.border),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text(
            'ÚLTIMOS 7 DIAS',
            style: TextStyle(
              color: AppColors.textSecondary,
              fontSize: 9,
              fontWeight: FontWeight.w800,
              letterSpacing: .8,
            ),
          ),
          const SizedBox(height: 8),
          SizedBox(
            height: 54,
            width: double.infinity,
            child: CustomPaint(painter: _AFLinePreviewPainter(data)),
          ),
        ],
      ),
    );
  }
}

class AFStatTile extends StatelessWidget {
  const AFStatTile({
    required this.label,
    required this.value,
    required this.color,
    super.key,
  });

  final String label;
  final String value;
  final Color color;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 8),
      decoration: BoxDecoration(
        color: AppColors.surfaceAlt,
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: color.withValues(alpha: .24)),
      ),
      child: Column(
        children: [
          Text(
            value,
            style: Theme.of(context).textTheme.titleLarge?.copyWith(
                  color: color,
                  fontWeight: FontWeight.w900,
                ),
          ),
          const SizedBox(height: 2),
          Text(label, style: const TextStyle(color: AppColors.textSecondary, fontSize: 11)),
        ],
      ),
    );
  }
}

class AFAthleteSummaryTile extends StatelessWidget {
  const AFAthleteSummaryTile({
    required this.name,
    required this.subtitle,
    required this.score,
    required this.status,
    required this.color,
    this.onTap,
    super.key,
  });

  final String name;
  final String subtitle;
  final double? score;
  final String status;
  final Color color;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) {
    final initials = initialsFromName(name);
    return InkWell(
      onTap: onTap,
      borderRadius: BorderRadius.circular(AppRadius.card),
      child: Container(
        padding: const EdgeInsets.all(10),
        decoration: BoxDecoration(
          color: AppColors.surface,
          borderRadius: BorderRadius.circular(AppRadius.card),
          border: Border.all(color: AppColors.border),
        ),
        child: Row(
          children: [
            Container(
              width: 38,
              height: 38,
              alignment: Alignment.center,
              decoration: BoxDecoration(
                color: color.withValues(alpha: .08),
                shape: BoxShape.circle,
                border: Border.all(color: color, width: 1.8),
              ),
              child: Text(
                initials,
                style: TextStyle(color: color, fontSize: 12, fontWeight: FontWeight.w900),
              ),
            ),
            const SizedBox(width: 10),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(name, style: const TextStyle(fontWeight: FontWeight.w900, fontSize: 13)),
                  const SizedBox(height: 2),
                  Text(subtitle, style: const TextStyle(color: AppColors.textSecondary, fontSize: 11)),
                ],
              ),
            ),
            Text(
              score == null ? '--' : score!.round().toString(),
              style: TextStyle(color: color, fontSize: 18, fontWeight: FontWeight.w900),
            ),
            const SizedBox(width: 8),
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 7, vertical: 3),
              decoration: BoxDecoration(
                color: color.withValues(alpha: .12),
                borderRadius: BorderRadius.circular(AppRadius.pill),
                border: Border.all(color: color.withValues(alpha: .3)),
              ),
              child: Text(
                status.toUpperCase(),
                style: TextStyle(color: color, fontSize: 8, fontWeight: FontWeight.w900),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

String initialsFromName(String name) {
  final parts = name.trim().split(RegExp(r'\s+')).where((part) => part.isNotEmpty).toList();
  if (parts.isEmpty) return 'AF';
  if (parts.length == 1) return parts.first.characters.take(2).toString().toUpperCase();
  return '${parts.first.characters.first}${parts.last.characters.first}'.toUpperCase();
}

class _NavItem {
  const _NavItem(this.icon, this.label);
  final IconData icon;
  final String label;
}

class _BottomNavButton extends StatelessWidget {
  const _BottomNavButton({
    required this.item,
    required this.selected,
    required this.elevated,
    required this.onTap,
  });

  final _NavItem item;
  final bool selected;
  final bool elevated;
  final VoidCallback onTap;

  @override
  Widget build(BuildContext context) {
    final color = selected || elevated ? AppColors.green : AppColors.neutral;
    return InkWell(
      onTap: onTap,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          if (elevated)
            Container(
              width: 42,
              height: 42,
              margin: const EdgeInsets.only(bottom: 2),
              decoration: BoxDecoration(
                color: AppColors.green,
                shape: BoxShape.circle,
                border: Border.all(color: AppColors.navBlack, width: 3),
                boxShadow: const [BoxShadow(color: AppColors.greenGlow, blurRadius: 14)],
              ),
              child: const Icon(AFIcons.checkin, color: AppColors.black, size: 22),
            )
          else
            Icon(item.icon, color: color, size: 22),
          Text(
            item.label,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
            style: TextStyle(
              color: color,
              fontSize: elevated ? 8 : 9,
              fontWeight: FontWeight.w800,
              letterSpacing: .2,
            ),
          ),
        ],
      ),
    );
  }
}

class _AFScreenPatternPainter extends CustomPainter {
  const _AFScreenPatternPainter();

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..color = AppColors.green.withValues(alpha: .06)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    for (var i = 0; i < 5; i++) {
      final center = Offset(size.width * (.08 + i * .24), size.height * (.10 + (i.isEven ? .02 : .76)));
      final path = Path();
      for (var s = 0; s < 6; s++) {
        final angle = math.pi / 3 * s + math.pi / 6;
        final point = Offset(center.dx + math.cos(angle) * 34, center.dy + math.sin(angle) * 34);
        if (s == 0) {
          path.moveTo(point.dx, point.dy);
        } else {
          path.lineTo(point.dx, point.dy);
        }
      }
      path.close();
      canvas.drawPath(path, paint);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) => false;
}

class _AFRingPainter extends CustomPainter {
  const _AFRingPainter({required this.score, required this.color});

  final double score;
  final Color color;

  @override
  void paint(Canvas canvas, Size size) {
    final center = Offset(size.width / 2, size.height / 2);
    final radius = size.shortestSide / 2 - 7;
    final base = Paint()
      ..color = AppColors.surfaceAlt
      ..style = PaintingStyle.stroke
      ..strokeWidth = 8;
    final arc = Paint()
      ..color = color
      ..style = PaintingStyle.stroke
      ..strokeCap = StrokeCap.round
      ..strokeWidth = 8;
    canvas.drawCircle(center, radius, base);
    canvas.drawArc(
      Rect.fromCircle(center: center, radius: radius),
      -math.pi / 2,
      (score.clamp(0, 100) / 100) * math.pi * 2,
      false,
      arc,
    );
  }

  @override
  bool shouldRepaint(covariant _AFRingPainter oldDelegate) {
    return oldDelegate.score != score || oldDelegate.color != color;
  }
}

class _AFLinePreviewPainter extends CustomPainter {
  const _AFLinePreviewPainter(this.values);

  final List<double> values;

  @override
  void paint(Canvas canvas, Size size) {
    if (values.isEmpty) return;
    final linePaint = Paint()
      ..color = AppColors.green
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2
      ..strokeCap = StrokeCap.round
      ..strokeJoin = StrokeJoin.round;
    final fillPaint = Paint()
      ..shader = const LinearGradient(
        begin: Alignment.topCenter,
        end: Alignment.bottomCenter,
        colors: [Color(0x4032D74B), Color(0x0032D74B)],
      ).createShader(Offset.zero & size);

    final path = Path();
    for (var i = 0; i < values.length; i++) {
      final x = values.length == 1 ? size.width : i / (values.length - 1) * size.width;
      final y = size.height - (values[i].clamp(0, 100) / 100) * size.height;
      if (i == 0) {
        path.moveTo(x, y);
      } else {
        path.lineTo(x, y);
      }
    }

    final fill = Path.from(path)
      ..lineTo(size.width, size.height)
      ..lineTo(0, size.height)
      ..close();
    canvas.drawPath(fill, fillPaint);
    canvas.drawPath(path, linePaint);
    final lastX = size.width;
    final lastY = size.height - (values.last.clamp(0, 100) / 100) * size.height;
    canvas.drawCircle(Offset(lastX, lastY), 3.5, Paint()..color = AppColors.green);
  }

  @override
  bool shouldRepaint(covariant _AFLinePreviewPainter oldDelegate) => oldDelegate.values != values;
}
