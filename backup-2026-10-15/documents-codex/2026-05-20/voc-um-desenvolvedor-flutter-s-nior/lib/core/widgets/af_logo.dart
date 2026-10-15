import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

import '../../app/theme.dart';
import '../constants/app_strings.dart';
import '../constants/asset_paths.dart';

enum AFLogoVariant { symbol, horizontal, vertical, wordmark }

enum AFLogoColorMode { normal, monochrome, green }

class AFLogo extends StatelessWidget {
  const AFLogo({
    this.variant = AFLogoVariant.horizontal,
    this.size = 120,
    this.showSlogan = false,
    this.colorMode = AFLogoColorMode.normal,
    super.key,
  });

  final AFLogoVariant variant;
  final double size;
  final bool showSlogan;
  final AFLogoColorMode colorMode;

  @override
  Widget build(BuildContext context) {
    final asset = switch ((variant, colorMode)) {
      (AFLogoVariant.symbol, AFLogoColorMode.monochrome) =>
        AssetPaths.logoMonoWhite,
      (AFLogoVariant.symbol, AFLogoColorMode.green) => AssetPaths.logoMonoGreen,
      (AFLogoVariant.symbol, _) => AssetPaths.logoSymbol,
      (AFLogoVariant.vertical, _) => AssetPaths.logoVerticalV2,
      (AFLogoVariant.wordmark, _) => AssetPaths.logoWordmark,
      _ => AssetPaths.logoHorizontalV2,
    };
    final dimensions = _dimensionsForVariant(variant, size);

    final logo = SvgPicture.asset(
      asset,
      width: dimensions.width,
      height: dimensions.height,
      fit: BoxFit.contain,
      placeholderBuilder: (_) => _TextLogo(size: size, colorMode: colorMode),
    );

    if (!showSlogan) return logo;

    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        logo,
        const SizedBox(height: 12),
        const _Slogan(),
      ],
    );
  }

  Size _dimensionsForVariant(AFLogoVariant variant, double size) {
    return switch (variant) {
      AFLogoVariant.symbol => Size.square(size),
      AFLogoVariant.vertical => Size(size * 480 / 420, size),
      AFLogoVariant.horizontal => Size(size * 740 / 220, size),
      AFLogoVariant.wordmark => Size(size * 3.2, size),
    };
  }
}

class AFAppIconPreview extends StatelessWidget {
  const AFAppIconPreview({this.size = 96, super.key});

  final double size;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: size,
      height: size,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(AppRadius.appIcon),
        boxShadow: const [BoxShadow(color: Color(0x4432D74B), blurRadius: 26)],
      ),
      clipBehavior: Clip.antiAlias,
      child: Image.asset(AssetPaths.appIcon512, fit: BoxFit.cover),
    );
  }
}

class AFBrandHeader extends StatelessWidget {
  const AFBrandHeader({this.title, this.subtitle, super.key});

  final String? title;
  final String? subtitle;

  @override
  Widget build(BuildContext context) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        const AFLogo(variant: AFLogoVariant.symbol, size: 58),
        const SizedBox(width: 16),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(title ?? AppStrings.appName,
                  style: Theme.of(context).textTheme.titleLarge),
              const SizedBox(height: 4),
              Text(
                subtitle ?? AppStrings.slogan,
                style: const TextStyle(
                    color: AppColors.gray200, letterSpacing: 2, fontSize: 11),
              ),
            ],
          ),
        ),
      ],
    );
  }
}

class _TextLogo extends StatelessWidget {
  const _TextLogo({required this.size, required this.colorMode});

  final double size;
  final AFLogoColorMode colorMode;

  @override
  Widget build(BuildContext context) {
    final color =
        colorMode == AFLogoColorMode.green ? AppColors.green : AppColors.white;
    return Text(
      'AF HEALTH LAB',
      style: TextStyle(
        color: color,
        fontSize: size * 0.22,
        fontWeight: FontWeight.w900,
        letterSpacing: 3,
      ),
    );
  }
}

class _Slogan extends StatelessWidget {
  const _Slogan();

  @override
  Widget build(BuildContext context) {
    return const Column(
      children: [
        Text(
          AppStrings.slogan,
          textAlign: TextAlign.center,
          style: TextStyle(
              color: AppColors.white,
              fontWeight: FontWeight.w800,
              letterSpacing: 3),
        ),
        SizedBox(height: 6),
        Text(
          AppStrings.secondarySlogan,
          textAlign: TextAlign.center,
          style: TextStyle(
              color: AppColors.gray200, fontSize: 12, letterSpacing: 3),
        ),
      ],
    );
  }
}
