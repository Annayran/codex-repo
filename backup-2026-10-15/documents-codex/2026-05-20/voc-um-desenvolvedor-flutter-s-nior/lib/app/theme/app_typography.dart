import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

import 'app_colors.dart';

class AppTypography {
  const AppTypography._();

  static TextTheme get textTheme {
    final base = GoogleFonts.interTextTheme(ThemeData.dark().textTheme);
    final heading = GoogleFonts.montserratTextTheme(ThemeData.dark().textTheme);

    return base.copyWith(
      displayLarge: heading.displayLarge?.copyWith(color: AppColors.white, fontWeight: FontWeight.w900),
      displayMedium: heading.displayMedium?.copyWith(color: AppColors.white, fontWeight: FontWeight.w900),
      headlineLarge: heading.headlineLarge?.copyWith(color: AppColors.white, fontWeight: FontWeight.w900),
      headlineMedium: heading.headlineMedium?.copyWith(color: AppColors.white, fontWeight: FontWeight.w900),
      headlineSmall: heading.headlineSmall?.copyWith(color: AppColors.white, fontWeight: FontWeight.w800),
      titleLarge: heading.titleLarge?.copyWith(color: AppColors.white, fontWeight: FontWeight.w800),
      titleMedium: heading.titleMedium?.copyWith(color: AppColors.white, fontWeight: FontWeight.w700),
      bodyLarge: base.bodyLarge?.copyWith(color: AppColors.gray200),
      bodyMedium: base.bodyMedium?.copyWith(color: AppColors.gray200),
      bodySmall: base.bodySmall?.copyWith(color: AppColors.gray300),
      labelLarge: base.labelLarge?.copyWith(fontWeight: FontWeight.w800),
    );
  }

  static TextStyle score({double size = 44}) {
    return GoogleFonts.montserrat(
      color: AppColors.white,
      fontSize: size,
      fontWeight: FontWeight.w900,
      fontFeatures: const [FontFeature.tabularFigures()],
    );
  }
}
