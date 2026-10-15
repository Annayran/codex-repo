import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

import '../../app/theme.dart';

class AFAssetIcon extends StatelessWidget {
  const AFAssetIcon(
    this.asset, {
    this.size = 24,
    this.color,
    super.key,
  });

  final String asset;
  final double size;
  final Color? color;

  @override
  Widget build(BuildContext context) {
    return SvgPicture.asset(
      asset,
      width: size,
      height: size,
      colorFilter: ColorFilter.mode(color ?? AppColors.green, BlendMode.srcIn),
      placeholderBuilder: (_) => SizedBox(
        width: size,
        height: size,
        child: Icon(Icons.hexagon_outlined, size: size, color: color ?? AppColors.green),
      ),
    );
  }
}
