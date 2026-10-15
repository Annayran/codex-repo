import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

import 'routes.dart';
import 'theme.dart';

class AFHealthLabApp extends ConsumerWidget {
  const AFHealthLabApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final router = ref.watch(appRouterProvider);

    return MaterialApp.router(
      title: 'AF Health Lab',
      debugShowCheckedModeBanner: false,
      locale: const Locale('pt', 'BR'),
      theme: AFTheme.dark,
      routerConfig: router,
    );
  }
}
