import 'package:flutter/material.dart';

import '../../app/theme.dart';

class AFPrimaryButton extends StatelessWidget {
  const AFPrimaryButton({
    required this.label,
    required this.onPressed,
    this.icon,
    this.isLoading = false,
    super.key,
  });

  final String label;
  final VoidCallback? onPressed;
  final IconData? icon;
  final bool isLoading;

  @override
  Widget build(BuildContext context) {
    final enabled = onPressed != null && !isLoading;
    return SizedBox(
      width: double.infinity,
      height: 50,
      child: DecoratedBox(
        decoration: BoxDecoration(
          gradient: enabled ? AppColors.primaryGradient : null,
          color: enabled ? null : AppColors.gray700,
          borderRadius: BorderRadius.circular(AppRadius.button),
          boxShadow: enabled
              ? const [
                  BoxShadow(
                    color: Color(0x3832D74B),
                    blurRadius: 18,
                    offset: Offset(0, 0),
                  ),
                ]
              : null,
        ),
        child: FilledButton.icon(
          style: FilledButton.styleFrom(
            backgroundColor: Colors.transparent,
            disabledBackgroundColor: Colors.transparent,
            shadowColor: Colors.transparent,
          ),
          onPressed: enabled ? onPressed : null,
          icon: isLoading
              ? const SizedBox(
                  width: 20,
                  height: 20,
                  child: CircularProgressIndicator(strokeWidth: 2, color: Colors.black),
                )
              : Icon(icon ?? Icons.arrow_forward_rounded),
          label: Text(label),
        ),
      ),
    );
  }
}
