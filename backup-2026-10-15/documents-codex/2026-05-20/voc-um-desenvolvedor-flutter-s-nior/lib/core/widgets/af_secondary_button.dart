import 'package:flutter/material.dart';

import '../../app/theme.dart';

class AFSecondaryButton extends StatelessWidget {
  const AFSecondaryButton({
    required this.label,
    required this.onPressed,
    this.icon,
    super.key,
  });

  final String label;
  final VoidCallback? onPressed;
  final IconData? icon;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 50,
      width: double.infinity,
      child: OutlinedButton.icon(
        onPressed: onPressed,
        icon: Icon(icon ?? Icons.arrow_forward_rounded),
        label: Text(label),
        style: OutlinedButton.styleFrom(
          side: const BorderSide(color: AppColors.green, width: 1.5),
          foregroundColor: AppColors.green,
          textStyle: const TextStyle(fontWeight: FontWeight.w700, letterSpacing: .4),
        ),
      ),
    );
  }
}
