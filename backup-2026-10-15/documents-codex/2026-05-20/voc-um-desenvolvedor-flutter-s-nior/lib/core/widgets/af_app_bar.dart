import 'package:flutter/material.dart';

import '../../app/theme.dart';
import 'af_icons.dart';
import 'af_logo.dart';

class AFAppBar extends StatelessWidget implements PreferredSizeWidget {
  const AFAppBar({
    this.title,
    this.showLogo = false,
    this.onNotifications,
    this.onProfile,
    super.key,
  });

  final String? title;
  final bool showLogo;
  final VoidCallback? onNotifications;
  final VoidCallback? onProfile;

  @override
  Size get preferredSize => const Size.fromHeight(72);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      toolbarHeight: 72,
      title: showLogo ? const AFLogo(variant: AFLogoVariant.symbol, size: 46) : Text(title ?? ''),
      actions: [
        IconButton(
          tooltip: 'Notificações',
          onPressed: onNotifications,
          icon: const Icon(AFIcons.notifications),
        ),
        Padding(
          padding: const EdgeInsets.only(right: 12),
          child: CircleAvatar(
            radius: 18,
            backgroundColor: AppColors.surface,
            child: IconButton(
              tooltip: 'Perfil',
              onPressed: onProfile,
              icon: const Icon(AFIcons.profile, size: 18),
            ),
          ),
        ),
      ],
    );
  }
}
