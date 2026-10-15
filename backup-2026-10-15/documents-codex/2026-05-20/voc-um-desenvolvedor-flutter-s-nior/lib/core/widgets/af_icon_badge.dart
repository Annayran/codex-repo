import 'package:flutter/material.dart';

import '../../app/theme.dart';
import 'af_asset_icon.dart';

class AFIconBadge extends StatelessWidget {
  const AFIconBadge({
    this.icon,
    this.asset,
    this.label,
    this.selected = false,
    this.statusColor,
    this.onTap,
    super.key,
  });

  final IconData? icon;
  final String? asset;
  final String? label;
  final bool selected;
  final Color? statusColor;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) {
    final color = statusColor ?? (selected ? AppColors.green : AppColors.gray200);
    final child = Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 10),
      decoration: BoxDecoration(
        color: selected ? color.withValues(alpha: 0.10) : AppColors.panelAlt,
        borderRadius: BorderRadius.circular(14),
        border: Border.all(color: selected ? color.withValues(alpha: 0.55) : AppColors.border),
        boxShadow: selected
            ? [
                BoxShadow(
                  color: color.withValues(alpha: 0.18),
                  blurRadius: 14,
                ),
              ]
            : null,
      ),
      child: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          if (asset != null)
            AFAssetIcon(asset!, color: color, size: 20)
          else
            Icon(icon ?? Icons.hexagon_outlined, color: color, size: 20),
          if (label != null) ...[
            const SizedBox(width: 8),
            Text(label!, style: TextStyle(color: color, fontWeight: FontWeight.w700, letterSpacing: .2)),
          ],
        ],
      ),
    );

    if (onTap == null) return child;
    return InkWell(onTap: onTap, borderRadius: BorderRadius.circular(14), child: child);
  }
}
