import 'package:flutter/material.dart';

import '../../app/theme.dart';

class AFTextButton extends StatelessWidget {
  const AFTextButton({
    required this.label,
    required this.onPressed,
    super.key,
  });

  final String label;
  final VoidCallback? onPressed;

  @override
  Widget build(BuildContext context) {
    return TextButton(
      onPressed: onPressed,
      style: TextButton.styleFrom(foregroundColor: AppColors.green),
      child: Text(label),
    );
  }
}
