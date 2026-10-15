import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/widgets/af_logo.dart';
import '../../../core/widgets/af_primary_button.dart';
import '../../../core/widgets/af_secondary_button.dart';
import 'widgets/af_auth_widgets.dart';

class AuthWelcomeScreen extends StatelessWidget {
  const AuthWelcomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: AFAuthBackground(
        child: SafeArea(
          child: Column(
            children: [
              const Spacer(),
              const AFLogo(variant: AFLogoVariant.vertical, size: 154, showSlogan: true),
              const SizedBox(height: 36),
              Text(
                'Sua central de performance humana.',
                textAlign: TextAlign.center,
                style: Theme.of(context).textTheme.headlineSmall?.copyWith(fontWeight: FontWeight.w900),
              ),
              const SizedBox(height: 12),
              const Padding(
                padding: EdgeInsets.symmetric(horizontal: 18),
                child: Text(
                  'Check-ins, AF Score, alertas de risco e evolução em um app premium para coaches e atletas.',
                  textAlign: TextAlign.center,
                  style: TextStyle(color: AFTheme.muted, height: 1.5),
                ),
              ),
              const Spacer(),
              AFPrimaryButton(label: 'Entrar', onPressed: () => context.go('/login')),
              const SizedBox(height: 12),
              AFSecondaryButton(label: 'Criar conta', onPressed: () => context.go('/register')),
              const SizedBox(height: 20),
              const Text(
                'Saúde hoje, performance sempre.',
                style: TextStyle(color: AppColors.gray200, letterSpacing: 2, fontSize: 12),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
