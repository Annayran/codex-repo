import 'package:flutter/material.dart';

class AppColors {
  const AppColors._();

  static const green = Color(0xFF32D74B);
  static const greenDark = Color(0xFF1E7F3C);
  static const greenSoft = Color(0x8C32D74B);
  static const greenGlow = Color(0x2E32D74B);
  static const pureBlack = Color(0xFF000000);
  static const black = Color(0xFF0E1116);
  static const navBlack = Color(0xFF0A0D11);
  static const panel = Color(0xFF060708);
  static const panelAlt = Color(0xFF0B0D0F);
  static const surface = Color(0xFF1A1F27);
  static const surfaceAlt = Color(0xFF141820);
  static const border = Color(0xFF2A303B);
  static const brandLine = Color(0x5932D74B);
  static const brandLineSoft = Color(0x2E32D74B);
  static const white = Color(0xFFFFFFFF);

  static const success = Color(0xFF22C55E);
  static const attention = Color(0xFFF59E0B);
  static const warning = attention;
  static const risk = Color(0xFFEF4444);
  static const neutral = Color(0xFF64748B);
  static const textSecondary = Color(0xFFB3BAC5);
  static const brandMuted = Color(0xFF8A8F97);
  static const brandTextSoft = Color(0xFFD6D8DB);

  static const gray100 = Color(0xFFFFFFFF);
  static const gray200 = Color(0xFFB3BAC5);
  static const gray300 = Color(0xFF747D8C);
  static const gray400 = Color(0xFF5C6574);
  static const gray500 = Color(0xFF47515F);
  static const gray600 = Color(0xFF343C48);
  static const gray700 = Color(0xFF2A303B);
  static const gray800 = Color(0xFF212733);
  static const gray900 = Color(0xFF141820);

  static const primaryGradient = LinearGradient(
    colors: [green, greenDark],
    begin: Alignment.topLeft,
    end: Alignment.bottomRight,
  );
}
