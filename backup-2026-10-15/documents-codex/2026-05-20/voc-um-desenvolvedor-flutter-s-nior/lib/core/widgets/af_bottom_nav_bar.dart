import 'package:flutter/material.dart';

import '../../app/theme.dart';
import 'af_icons.dart';

class AFBottomNavBar extends StatelessWidget {
  const AFBottomNavBar({
    required this.currentIndex,
    required this.onTap,
    super.key,
  });

  final int currentIndex;
  final ValueChanged<int> onTap;

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: const BoxDecoration(
        color: AppColors.navBlack,
        border: Border(top: BorderSide(color: AppColors.border)),
      ),
      child: NavigationBar(
        selectedIndex: currentIndex,
        onDestinationSelected: onTap,
        backgroundColor: AppColors.navBlack,
        indicatorColor: AppColors.greenGlow,
        elevation: 0,
        destinations: const [
          NavigationDestination(icon: Icon(AFIcons.home), label: 'Início'),
          NavigationDestination(icon: Icon(AFIcons.athlete), label: 'Atletas'),
          NavigationDestination(icon: Icon(AFIcons.dashboard), label: 'Dashboard'),
          NavigationDestination(icon: Icon(AFIcons.checkin), label: 'Check-in'),
          NavigationDestination(icon: Icon(AFIcons.profile), label: 'Perfil'),
        ],
      ),
    );
  }
}
