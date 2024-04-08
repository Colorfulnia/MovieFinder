package abhishek.pathak.moviefiner.navigation

import abhishek.pathak.moviefiner.hilt.view.WelcomeScreen
import abhishek.pathak.moviefiner.hilt.view.screens.NowPlayingScreenUI
import abhishek.pathak.moviefiner.hilt.view.screens.PopularScreenUI
import abhishek.pathak.moviefiner.hilt.view.screens.TopRatedScreenUI
import abhishek.pathak.moviefiner.hilt.view.screens.UpcomingScreenUI
import abhishek.pathak.moviefiner.model.movie_detail.MovieDetailsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String = NavigationItem.WELCOME.route
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.WELCOME.route) {
            WelcomeScreen(navHostController)
        }
       composable(NavigationItem.POPULARLISTSCREEN.route) {
            PopularScreenUI(hiltViewModel(), navHostController)
        }

        composable(NavigationItem.NOWPLAYINGLISTSCREEN.route) {
            NowPlayingScreenUI(hiltViewModel(),navHostController)
        }
        composable(NavigationItem.UPCOMINGLISTSCREEN.route) {
            UpcomingScreenUI(hiltViewModel(),navHostController)
        }
        composable(NavigationItem.TOPRATEDLISTSCREEN.route) {
            TopRatedScreenUI(hiltViewModel(),navHostController)
        }
        composable(
            "DETAILS_SCREEN/{movie_id}",
            arguments = listOf(navArgument(name = "movie_id") { // Notice over here
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movie_id")
            if (movieId != null) {
                MovieDetailsScreen(navHostController, movieId)
            }
        }
    }
}