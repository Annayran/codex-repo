import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/utils/validators.dart';
import '../../../core/widgets/af_primary_button.dart';
import 'auth_controller.dart';
import 'widgets/af_auth_widgets.dart';

class LoginScreen extends ConsumerStatefulWidget {
  const LoginScreen({super.key});

  @override
  ConsumerState<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends ConsumerState<LoginScreen> {
  final _formKey = GlobalKey<FormState>();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _login() async {
    if (!_formKey.currentState!.validate()) return;
    await _run(
      () => ref.read(authControllerProvider.notifier).loginWithEmail(
            email: _emailController.text,
            password: _passwordController.text,
          ),
      success: () => context.go('/splash'),
    );
  }

  Future<void> _googleLogin() async {
    await _run(
      () => ref.read(authControllerProvider.notifier).loginWithGoogle(),
      success: () => context.go('/splash'),
    );
  }

  Future<void> _resetPassword() async {
    final emailError = Validators.email(_emailController.text);
    if (emailError != null) {
      _showSnack('Informe seu e-mail válido para recuperar a senha.');
      return;
    }

    await _run(
      () => ref.read(authControllerProvider.notifier).sendPasswordReset(_emailController.text),
      success: () => _showSnack('Enviamos um link de recuperação para seu e-mail.'),
    );
  }

  Future<void> _run(Future<void> Function() action, {required VoidCallback success}) async {
    try {
      await action();
      if (mounted) success();
    } catch (error) {
      _showSnack(error.toString());
    }
  }

  void _showSnack(String message) {
    if (!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message)));
  }

  @override
  Widget build(BuildContext context) {
    final authState = ref.watch(authControllerProvider);
    final isLoading = authState.isLoading;

    return AFAuthScaffold(
      header: AFAuthHeader(
        title: 'Bem-vindo de ',
        highlight: 'volta!',
        subtitle: 'Acesse sua conta para continuar acompanhando seu desempenho.',
        showBack: true,
        onBack: () => context.go('/auth'),
      ),
      footer: AFAuthSwitchLink(
        text: 'Ainda não tem uma conta?',
        action: 'Criar conta',
        onTap: isLoading ? null : () => context.go('/register'),
      ),
      child: Form(
        key: _formKey,
        child: AFAuthPanel(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              AFAuthTextField(
                controller: _emailController,
                label: 'E-mail',
                hint: 'seu@email.com',
                icon: Icons.mail_outline_rounded,
                keyboardType: TextInputType.emailAddress,
                validator: Validators.email,
              ),
              const SizedBox(height: 16),
              AFAuthTextField(
                controller: _passwordController,
                label: 'Senha',
                hint: 'Digite sua senha',
                icon: Icons.lock_outline_rounded,
                obscureText: true,
                validator: Validators.password,
              ),
              Align(
                alignment: Alignment.centerRight,
                child: TextButton(
                  onPressed: isLoading ? null : _resetPassword,
                  child: const Text('Esqueci minha senha'),
                ),
              ),
              const SizedBox(height: 10),
              AFPrimaryButton(label: 'Entrar', onPressed: _login, isLoading: isLoading),
              const SizedBox(height: 22),
              const AFAuthDivider(),
              const SizedBox(height: 22),
              OutlinedButton.icon(
                onPressed: isLoading ? null : _googleLogin,
                icon: const Icon(Icons.g_mobiledata_rounded, size: 30),
                label: const Text('Continuar com Google'),
                style: OutlinedButton.styleFrom(
                  minimumSize: const Size.fromHeight(52),
                  foregroundColor: AFTheme.text,
                  side: BorderSide(color: Colors.white.withValues(alpha: 0.12)),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
