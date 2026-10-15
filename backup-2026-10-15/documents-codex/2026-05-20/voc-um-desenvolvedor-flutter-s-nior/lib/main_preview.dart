import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'app/theme.dart';
import 'core/constants/asset_paths.dart';
import 'core/utils/validators.dart';
import 'core/widgets/af_logo.dart';
import 'core/widgets/af_primary_button.dart';
import 'core/widgets/af_score_card.dart';
import 'core/widgets/af_secondary_button.dart';
import 'features/auth/presentation/widgets/af_auth_widgets.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const ProviderScope(child: AFHealthLabPreviewApp()));
}

class AFHealthLabPreviewApp extends StatelessWidget {
  const AFHealthLabPreviewApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'AF Health Lab Preview',
      debugShowCheckedModeBanner: false,
      theme: AFTheme.dark,
      home: const _AuthPrototype(),
    );
  }
}

enum _PreviewStep { welcome, login, register, home }

class _PreviewAccount {
  const _PreviewAccount({
    required this.name,
    required this.email,
    required this.password,
    required this.role,
  });

  final String name;
  final String email;
  final String password;
  final String role;

  bool get isCoach => role == 'coach';
  String get roleLabel => isCoach ? 'Coach' : 'Aluno';
}

class _PreviewAuthException implements Exception {
  const _PreviewAuthException(this.message);

  final String message;

  @override
  String toString() => message;
}

class _AuthPrototype extends StatefulWidget {
  const _AuthPrototype();

  @override
  State<_AuthPrototype> createState() => _AuthPrototypeState();
}

class _AuthPrototypeState extends State<_AuthPrototype> {
  _PreviewStep _step = _PreviewStep.welcome;
  final Map<String, _PreviewAccount> _accounts = {};
  _PreviewAccount? _currentAccount;
  String? _lastEmail;

  String _normalizeEmail(String email) => email.trim().toLowerCase();

  Future<_PreviewAccount> _register({
    required String name,
    required String email,
    required String password,
    required String role,
  }) async {
    await Future<void>.delayed(const Duration(milliseconds: 500));
    final normalizedEmail = _normalizeEmail(email);
    if (_accounts.containsKey(normalizedEmail)) {
      throw const _PreviewAuthException(
          'Este e-mail já está cadastrado. Use Entrar para acessar.');
    }

    final account = _PreviewAccount(
      name: name.trim(),
      email: normalizedEmail,
      password: password,
      role: role,
    );
    setState(() {
      _accounts[normalizedEmail] = account;
      _currentAccount = account;
      _lastEmail = normalizedEmail;
      _step = _PreviewStep.home;
    });
    return account;
  }

  Future<_PreviewAccount> _login({
    required String email,
    required String password,
  }) async {
    await Future<void>.delayed(const Duration(milliseconds: 450));
    final normalizedEmail = _normalizeEmail(email);
    final account = _accounts[normalizedEmail];
    if (account == null) {
      throw const _PreviewAuthException(
          'Não encontramos uma conta com este e-mail. Crie sua conta primeiro.');
    }
    if (account.password != password) {
      throw const _PreviewAuthException('Senha incorreta para este cadastro.');
    }

    setState(() {
      _currentAccount = account;
      _lastEmail = normalizedEmail;
      _step = _PreviewStep.home;
    });
    return account;
  }

  Future<_PreviewAccount> _googleLogin() async {
    await Future<void>.delayed(const Duration(milliseconds: 550));
    const email = 'google.af@afhealthlab.app';
    final account = _accounts[email] ??
        const _PreviewAccount(
          name: 'Usuário Google',
          email: email,
          password: '',
          role: 'athlete',
        );

    setState(() {
      _accounts[email] = account;
      _currentAccount = account;
      _lastEmail = email;
      _step = _PreviewStep.home;
    });
    return account;
  }

  String _sendPasswordReset(String email) {
    final normalizedEmail = _normalizeEmail(email);
    if (!_accounts.containsKey(normalizedEmail)) {
      throw const _PreviewAuthException(
          'Cadastre este e-mail antes de recuperar a senha.');
    }
    return 'Link de recuperação enviado para $normalizedEmail.';
  }

  void _logout() {
    setState(() {
      _currentAccount = null;
      _step = _PreviewStep.login;
    });
  }

  @override
  Widget build(BuildContext context) {
    final child = switch (_step) {
      _PreviewStep.welcome => _WelcomeMockV2(
          onLogin: () => setState(() => _step = _PreviewStep.login),
          onRegister: () => setState(() => _step = _PreviewStep.register),
        ),
      _PreviewStep.login => _LoginMock(
          onBack: () => setState(() => _step = _PreviewStep.welcome),
          onRegister: () => setState(() => _step = _PreviewStep.register),
          onLogin: _login,
          onGoogleLogin: _googleLogin,
          onPasswordReset: _sendPasswordReset,
          initialEmail: _lastEmail,
        ),
      _PreviewStep.register => _RegisterMock(
          onBack: () => setState(() => _step = _PreviewStep.welcome),
          onLogin: () => setState(() => _step = _PreviewStep.login),
          onRegister: _register,
        ),
      _PreviewStep.home => _HomeMock(
          account: _currentAccount,
          onLogout: _logout,
        ),
    };

    return Scaffold(
      body: Center(
        child: Container(
          width: 420,
          height: 860,
          constraints:
              BoxConstraints(maxHeight: MediaQuery.sizeOf(context).height),
          decoration: BoxDecoration(
            color: Colors.black,
            borderRadius: BorderRadius.circular(44),
            border: Border.all(color: AppColors.gray600, width: 2),
            boxShadow: const [
              BoxShadow(color: Color(0x66000000), blurRadius: 40)
            ],
          ),
          clipBehavior: Clip.antiAlias,
          child: child,
        ),
      ),
    );
  }
}

class _WelcomeMockV2 extends StatelessWidget {
  const _WelcomeMockV2({required this.onLogin, required this.onRegister});

  final VoidCallback onLogin;
  final VoidCallback onRegister;

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Positioned.fill(
          child: Image.asset(
            AssetPaths.runnerAuthFullPng,
            fit: BoxFit.cover,
            alignment: const Alignment(0, -0.06),
          ),
        ),
        Positioned.fill(
          child: DecoratedBox(
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.topCenter,
                end: Alignment.bottomCenter,
                colors: [
                  Colors.black.withValues(alpha: 0.08),
                  Colors.black.withValues(alpha: 0.00),
                  Colors.black.withValues(alpha: 0.20),
                  Colors.black.withValues(alpha: 0.48),
                  Colors.black.withValues(alpha: 0.88),
                ],
                stops: const [0, 0.26, 0.56, 0.76, 1],
              ),
            ),
          ),
        ),
        Positioned.fill(
          child: DecoratedBox(
            decoration: BoxDecoration(
              gradient: RadialGradient(
                center: const Alignment(0.02, -0.50),
                radius: 0.68,
                colors: [
                  Colors.transparent,
                  Colors.black.withValues(alpha: 0.18),
                  Colors.black.withValues(alpha: 0.54),
                ],
                stops: const [0, 0.58, 1],
              ),
            ),
          ),
        ),
        SafeArea(
          child: LayoutBuilder(
            builder: (context, constraints) {
              final compact = constraints.maxHeight < 820;
              final logoSize = compact ? 154.0 : 176.0;

              return Padding(
                padding: const EdgeInsets.symmetric(horizontal: 24),
                child: Column(
                  children: [
                    SizedBox(height: compact ? 86 : 104),
                    AFLogo(variant: AFLogoVariant.vertical, size: logoSize),
                    SizedBox(height: compact ? 30 : 40),
                    _WelcomeSlogan(compact: compact),
                    const Spacer(),
                    SizedBox(height: compact ? 12 : 18),
                    AFPrimaryButton(label: 'Entrar', onPressed: onLogin),
                    const SizedBox(height: 10),
                    AFSecondaryButton(
                        label: 'Criar conta', onPressed: onRegister),
                    SizedBox(height: compact ? 12 : 18),
                    const Text('Saúde hoje, performance sempre.',
                        style: TextStyle(color: AFTheme.muted, fontSize: 12)),
                  ],
                ),
              );
            },
          ),
        ),
      ],
    );
  }
}

class _WelcomeSlogan extends StatelessWidget {
  const _WelcomeSlogan({required this.compact});

  final bool compact;

  @override
  Widget build(BuildContext context) {
    return FittedBox(
      fit: BoxFit.scaleDown,
      child: RichText(
        textAlign: TextAlign.center,
        text: TextSpan(
          style: TextStyle(
            color: AFTheme.text,
            fontSize: compact ? 14 : 16,
            fontWeight: FontWeight.w800,
            letterSpacing: 4,
          ),
          children: const [
            TextSpan(text: 'DADOS. CIÊNCIA. '),
            TextSpan(
              text: 'PERFORMANCE.',
              style: TextStyle(color: AFTheme.primary),
            ),
          ],
        ),
      ),
    );
  }
}

class _LoginMock extends StatefulWidget {
  const _LoginMock({
    required this.onBack,
    required this.onRegister,
    required this.onLogin,
    required this.onGoogleLogin,
    required this.onPasswordReset,
    this.initialEmail,
  });

  final VoidCallback onBack;
  final VoidCallback onRegister;
  final Future<_PreviewAccount> Function({
    required String email,
    required String password,
  }) onLogin;
  final Future<_PreviewAccount> Function() onGoogleLogin;
  final String Function(String email) onPasswordReset;
  final String? initialEmail;

  @override
  State<_LoginMock> createState() => _LoginMockState();
}

class _LoginMockState extends State<_LoginMock> {
  final _formKey = GlobalKey<FormState>();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _emailController.text = widget.initialEmail ?? '';
  }

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) return;
    setState(() => _isLoading = true);
    try {
      final account = await widget.onLogin(
        email: _emailController.text,
        password: _passwordController.text,
      );
      if (!mounted) return;
      _showSnack('Bem-vindo, ${account.name}.');
    } catch (error) {
      if (!mounted) return;
      _showSnack(error.toString());
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  Future<void> _google() async {
    setState(() => _isLoading = true);
    try {
      final account = await widget.onGoogleLogin();
      if (!mounted) return;
      _showSnack('Bem-vindo, ${account.name}.');
    } catch (error) {
      if (!mounted) return;
      _showSnack(error.toString());
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  void _resetPassword() {
    final error = Validators.email(_emailController.text);
    if (error != null) {
      _showSnack('Informe um e-mail válido para recuperar a senha.');
      return;
    }
    try {
      _showSnack(widget.onPasswordReset(_emailController.text));
    } catch (error) {
      _showSnack(error.toString());
    }
  }

  void _showSnack(String message) {
    ScaffoldMessenger.of(context)
        .showSnackBar(SnackBar(content: Text(message)));
  }

  @override
  Widget build(BuildContext context) {
    return AFAuthScaffold(
      showIllustration: false,
      header: AFAuthHeader(
        title: 'Bem-vindo de ',
        highlight: 'volta!',
        subtitle:
            'Acesse sua conta para continuar acompanhando seu desempenho.',
        showBack: true,
        onBack: widget.onBack,
      ),
      footer: AFAuthSwitchLink(
        text: 'Ainda não tem uma conta?',
        action: 'Criar conta',
        onTap: _isLoading ? null : widget.onRegister,
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
                  onPressed: _isLoading ? null : _resetPassword,
                  child: const Text('Esqueci minha senha'),
                ),
              ),
              const SizedBox(height: 10),
              AFPrimaryButton(
                  label: 'Entrar', onPressed: _submit, isLoading: _isLoading),
              const SizedBox(height: 22),
              const AFAuthDivider(),
              const SizedBox(height: 22),
              OutlinedButton.icon(
                onPressed: _isLoading ? null : _google,
                icon: const Icon(Icons.g_mobiledata_rounded, size: 30),
                label: const Text('Continuar com Google'),
                style: OutlinedButton.styleFrom(
                    minimumSize: const Size.fromHeight(52)),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _RegisterMock extends StatefulWidget {
  const _RegisterMock({
    required this.onBack,
    required this.onLogin,
    required this.onRegister,
  });

  final VoidCallback onBack;
  final VoidCallback onLogin;
  final Future<_PreviewAccount> Function({
    required String name,
    required String email,
    required String password,
    required String role,
  }) onRegister;

  @override
  State<_RegisterMock> createState() => _RegisterMockState();
}

class _RegisterMockState extends State<_RegisterMock> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  String _role = 'coach';
  bool _isLoading = false;

  @override
  void dispose() {
    _nameController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  Future<void> _submit() async {
    if (!_formKey.currentState!.validate()) return;
    setState(() => _isLoading = true);
    try {
      final account = await widget.onRegister(
        name: _nameController.text,
        email: _emailController.text,
        password: _passwordController.text,
        role: _role,
      );
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
            content: Text('Conta ${account.roleLabel} criada e autenticada.')),
      );
    } catch (error) {
      if (!mounted) return;
      ScaffoldMessenger.of(context)
          .showSnackBar(SnackBar(content: Text(error.toString())));
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return AFAuthScaffold(
      showIllustration: false,
      header: AFAuthHeader(
        title: 'Criar ',
        highlight: 'conta',
        subtitle:
            'Escolha seu perfil e preencha os dados para começar sua jornada.',
        showBack: true,
        onBack: widget.onBack,
      ),
      footer: AFAuthSwitchLink(
        text: 'Já tem uma conta?',
        action: 'Entrar',
        onTap: _isLoading ? null : widget.onLogin,
      ),
      child: Form(
        key: _formKey,
        child: AFAuthPanel(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              const Text('Você é:',
                  style: TextStyle(fontWeight: FontWeight.w800, fontSize: 13)),
              const SizedBox(height: 10),
              Row(
                children: [
                  AFRoleCard(
                    title: 'Coach',
                    subtitle: 'Acompanho e oriento alunos e equipes.',
                    icon: Icons.groups_rounded,
                    selected: _role == 'coach',
                    onTap: _isLoading
                        ? () {}
                        : () => setState(() => _role = 'coach'),
                  ),
                  const SizedBox(width: 12),
                  AFRoleCard(
                    title: 'Aluno',
                    subtitle: 'Quero evoluir minha performance.',
                    icon: Icons.directions_run_rounded,
                    selected: _role == 'athlete',
                    onTap: _isLoading
                        ? () {}
                        : () => setState(() => _role = 'athlete'),
                  ),
                ],
              ),
              const SizedBox(height: 22),
              AFAuthTextField(
                controller: _nameController,
                label: 'Nome completo',
                hint: 'Seu nome completo',
                icon: Icons.person_outline_rounded,
                validator: (value) =>
                    Validators.requiredText(value, message: 'Informe seu nome'),
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
                  Icon(Icons.check_circle_outline_rounded,
                      size: 16, color: AFTheme.primary),
                  SizedBox(width: 6),
                  Text('Mínimo de 8 caracteres',
                      style: TextStyle(color: AFTheme.muted, fontSize: 12)),
                ],
              ),
              const SizedBox(height: 26),
              AFPrimaryButton(
                  label: 'Cadastrar',
                  onPressed: _submit,
                  isLoading: _isLoading),
            ],
          ),
        ),
      ),
    );
  }
}

class _HomeMock extends StatelessWidget {
  const _HomeMock({
    required this.account,
    required this.onLogout,
  });

  final _PreviewAccount? account;
  final VoidCallback onLogout;

  @override
  Widget build(BuildContext context) {
    final current = account ??
        const _PreviewAccount(
            name: 'Aluno AF', email: '', password: '', role: 'athlete');
    final isCoach = current.isCoach;
    return AFAuthBackground(
      child: SafeArea(
        child: ListView(
          children: [
            Row(
              children: [
                const AFLogo(variant: AFLogoVariant.symbol, size: 54),
                const Spacer(),
                IconButton(
                  tooltip: 'Sair',
                  onPressed: onLogout,
                  icon: const Icon(Icons.logout_rounded),
                ),
              ],
            ),
            const SizedBox(height: 28),
            Text('Olá, ${current.name}',
                style: Theme.of(context).textTheme.headlineSmall),
            const SizedBox(height: 6),
            Text(
              isCoach
                  ? 'Seu painel Coach está pronto.'
                  : 'Sua área de aluno está pronta.',
              style: const TextStyle(color: AFTheme.muted),
            ),
            const SizedBox(height: 22),
            const AFScoreCard(score: 84, title: 'AF Score de hoje'),
            const SizedBox(height: 16),
            AFPrimaryButton(
              label: isCoach ? 'Ver alunos' : 'Responder check-in',
              icon: isCoach ? Icons.groups_rounded : Icons.fact_check_outlined,
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                      content: Text(isCoach
                          ? 'Navegação para alunos validada.'
                          : 'Navegação para check-in validada.')),
                );
              },
            ),
            const SizedBox(height: 16),
            Card(
              child: ListTile(
                leading: Icon(
                    isCoach
                        ? Icons.verified_user_outlined
                        : Icons.directions_run_rounded,
                    color: AFTheme.primary),
                title: Text('Conta ${current.roleLabel} autenticada'),
                subtitle: Text(current.email.isEmpty
                    ? 'Fluxo de autenticação concluído com sucesso.'
                    : current.email),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
