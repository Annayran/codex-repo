import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../../../app/theme.dart';
import '../../../core/utils/validators.dart';
import '../../../core/widgets/af_primary_button.dart';
import 'auth_controller.dart';
import 'widgets/af_auth_widgets.dart';

class RegisterScreen extends ConsumerStatefulWidget {
  const RegisterScreen({super.key});

  @override
  ConsumerState<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends ConsumerState<RegisterScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  String _role = 'coach';

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _register() async {
    if (!_formKey.currentState!.validate()) return;
    await _run(
      () => ref.read(authControllerProvider.notifier).registerWithEmail(
            name: _nameController.text,
            email: _emailController.text,
            password: _passwordController.text,
            role: _role,
          ),
      success: () {
        _showSnack('Conta criada com sucesso.');
        context.go('/splash');
      },
    );
  }

  Future<void> _googleRegister() async {
    await _run(
      () => ref.read(authControllerProvider.notifier).loginWithGoogle(defaultRole: _role),
      success: () {
        _showSnack('Conta Google conectada com sucesso.');
        context.go('/splash');
      },
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
    final isLoading = ref.watch(authControllerProvider).isLoading;

    return AFAuthScaffold(
      header: AFAuthHeader(
        title: 'Criar ',
        highlight: 'conta',
        subtitle: 'Escolha seu perfil e preencha os dados para começar sua jornada.',
        showBack: true,
        onBack: () => context.go('/auth'),
      ),
      footer: AFAuthSwitchLink(
        text: 'Já tem uma conta?',
        action: 'Entrar',
        onTap: isLoading ? null : () => context.go('/login'),
      ),
      child: Form(
        key: _formKey,
        child: AFAuthPanel(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const Text('Você é:', style: TextStyle(fontWeight: FontWeight.w800, fontSize: 13)),
              const SizedBox(height: 10),
              Row(
                children: [
                  AFRoleCard(
                    title: 'Coach',
                    subtitle: 'Acompanho e oriento alunos e equipes.',
                    icon: Icons.groups_rounded,
                    selected: _role == 'coach',
                    onTap: isLoading ? () {} : () => setState(() => _role = 'coach'),
                  ),
                  const SizedBox(width: 12),
                  AFRoleCard(
                    title: 'Aluno',
                    subtitle: 'Quero evoluir minha performance.',
                    icon: Icons.directions_run_rounded,
                    selected: _role == 'athlete',
                    onTap: isLoading ? () {} : () => setState(() => _role = 'athlete'),
                  ),
                ],
              ),
              const SizedBox(height: 22),
              AFAuthTextField(
                controller: _nameController,
                label: 'Nome completo',
                hint: 'Seu nome completo',
                icon: Icons.person_outline_rounded,
                validator: (value) => Validators.requiredText(value, message: 'Informe seu nome'),
              ),
              const SizedBox(height: 16),
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
                hint: 'Crie uma senha',
                icon: Icons.lock_outline_rounded,
                obscureText: true,
                validator: Validators.password,
              ),
              const SizedBox(height: 8),
              const Row(
                children: [
                  Icon(Icons.check_circle_outline_rounded, size: 16, color: AFTheme.primary),
                  SizedBox(width: 6),
                  Text('Mínimo de 8 caracteres', style: TextStyle(color: AFTheme.muted, fontSize: 12)),
                ],
              ),
              const SizedBox(height: 26),
              AFPrimaryButton(label: 'Cadastrar', onPressed: _register, isLoading: isLoading),
              const SizedBox(height: 22),
              const AFAuthDivider(),
              const SizedBox(height: 22),
              OutlinedButton.icon(
                onPressed: isLoading ? null : _googleRegister,
                icon: const Icon(Icons.g_mobiledata_rounded, size: 30),
                label: Text('Continuar com Google como ${_role == 'coach' ? 'Coach' : 'Aluno'}'),
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
