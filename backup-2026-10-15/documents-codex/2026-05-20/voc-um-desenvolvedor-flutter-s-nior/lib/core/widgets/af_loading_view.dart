import 'package:flutter/material.dart';

import '../../app/theme.dart';
import '../constants/asset_paths.dart';
import 'af_logo.dart';

class AFLoadingView extends StatelessWidget {
  const AFLoadingView({this.message = 'Carregando...', super.key});

  final String message;

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(color: AFTheme.background),
      child: Stack(
        children: [
          Positioned.fill(
            child: Opacity(
              opacity: 0.18,
              child: Image.asset(
                AssetPaths.runnerAuthFullPng,
                fit: BoxFit.cover,
                alignment: const Alignment(0, -0.08),
              ),
            ),
          ),
          Positioned.fill(
            child: DecoratedBox(
              decoration: BoxDecoration(
                gradient: LinearGradient(
                  begin: Alignment.topCenter,
                  end: Alignment.bottomCenter,
                  colors: [
                    Colors.black.withValues(alpha: 0.72),
                    AFTheme.background.withValues(alpha: 0.88),
                    Colors.black,
                  ],
                ),
              ),
            ),
          ),
          Center(
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 40),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const AFLogo(variant: AFLogoVariant.vertical, size: 120),
                  const SizedBox(height: 34),
                  const _AFLoadingBar(),
                  const SizedBox(height: 18),
                  Text(
                    message,
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                      color: AFTheme.muted,
                      fontSize: 13,
                      fontWeight: FontWeight.w600,
                      letterSpacing: 0.2,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class _AFLoadingBar extends StatelessWidget {
  const _AFLoadingBar();

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 190,
      child: ClipRRect(
        borderRadius: BorderRadius.circular(999),
        child: LinearProgressIndicator(
          minHeight: 4,
          backgroundColor: Colors.white.withValues(alpha: 0.08),
          color: AFTheme.primary,
        ),
      ),
    );
  }
}
