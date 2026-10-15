import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

import '../features/athlete/presentation/athlete_home_screen.dart';
import '../features/auth/data/auth_repository.dart';
import '../features/auth/presentation/auth_welcome_screen.dart';
import '../features/auth/presentation/login_screen.dart';
import '../features/auth/presentation/register_screen.dart';
import '../features/auth/presentation/splash_screen.dart';
import '../features/checkin/presentation/daily_checkin_screen.dart';
import '../features/coach/presentation/athlete_detail_screen.dart';
import '../features/coach/presentation/coach_alerts_screen.dart';
import '../features/coach/presentation/coach_home_screen.dart';
import '../features/dashboard/presentation/coach_dashboard_screen.dart';
import '../features/profile/presentation/profile_screen.dart';

final appRouterProvider = Provider<GoRouter>((ref) {
  return GoRouter(
    initialLocation: '/splash',
    redirect: (context, state) {
      final user = ref.read(authRepositoryProvider).currentUser;
      final location = state.matchedLocation;
      final isPublicRoute = location == '/splash' || location == '/auth' || location == '/login' || location == '/register';

      if (user == null && !isPublicRoute) return '/auth';
      if (user != null && (location == '/auth' || location == '/login' || location == '/register')) return '/splash';
      return null;
    },
    routes: [
      GoRoute(path: '/splash', builder: (_, __) => const SplashScreen()),
      GoRoute(path: '/auth', builder: (_, __) => const AuthWelcomeScreen()),
      GoRoute(path: '/login', builder: (_, __) => const LoginScreen()),
      GoRoute(path: '/register', builder: (_, __) => const RegisterScreen()),
      GoRoute(path: '/athlete-home', builder: (_, __) => const AthleteHomeScreen()),
      GoRoute(path: '/coach-home', builder: (_, __) => const CoachHomeScreen()),
      GoRoute(path: '/daily-checkin', builder: (_, __) => const DailyCheckinScreen()),
      GoRoute(path: '/coach-alerts', builder: (_, __) => const CoachAlertsScreen()),
      GoRoute(path: '/dashboard', builder: (_, __) => const CoachDashboardScreen()),
      GoRoute(path: '/profile', builder: (_, __) => const ProfileScreen()),
      GoRoute(
        path: '/athlete-detail/:id',
        builder: (_, state) => AthleteDetailScreen(athleteId: state.pathParameters['id']!),
      ),
    ],
  );
});
