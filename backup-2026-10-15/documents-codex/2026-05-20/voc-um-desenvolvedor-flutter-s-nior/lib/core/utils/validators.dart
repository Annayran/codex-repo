class Validators {
  const Validators._();

  static String? requiredText(String? value, {String message = 'Campo obrigatório'}) {
    if (value == null || value.trim().isEmpty) return message;
    return null;
  }

  static String? email(String? value) {
    final text = value?.trim() ?? '';
    if (!text.contains('@') || !text.contains('.')) return 'Informe um e-mail válido';
    return null;
  }

  static String? password(String? value) {
    if ((value ?? '').length < 8) return 'Mínimo de 8 caracteres';
    return null;
  }
}
