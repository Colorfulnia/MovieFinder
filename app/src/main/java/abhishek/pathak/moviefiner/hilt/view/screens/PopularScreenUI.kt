package abhishek.pathak.moviefiner.hilt.view.screens

import abhishek.pathak.moviefiner.R
import abhishek.pathak.moviefiner.hilt.model.Constants.IMAGE_ENDPOINT
import abhishek.pathak.moviefiner.hilt.model.local.Result
import abhishek.pathak.moviefiner.hilt.viewmodel.FavouritesMovieViewModel
import abhishek.pathak.moviefiner.hilt.viewmodel.MovieListsViewModel
import abhishek.pathak.moviefiner.navigation.NavigationItem
import abhishek.pathak.moviefiner.ui.theme.White
import abhishek.pathak.moviefiner.ui.theme.dp_10
import abhishek.pathak.moviefiner.ui.theme.dp_4
import abhishek.pathak.moviefiner.ui.theme.sp_12
import abhishek.pathak.moviefiner.ui.theme.sp_14
import abhishek.pathak.moviefiner.ui.theme.sp_20
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder

@Composable
fun PopularScreenUI(
    popularViewModel: MovieListsViewModel = hiltViewModel(),
    navController: NavController
) {
    popularViewModel.fetchPopularMovieDetails()
    val movieImage = popularViewModel.popularLiveData.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = White)
        ) {
            IconButton(
                onClick = { navController.navigate(NavigationItem.WELCOME.route) },
                Modifier.paint(painterResource(id = R.drawable.baseline_arrow_back_24))
            ) { }

            Text(
                text = stringResource(id = R.string.popular),
                fontWeight = FontWeight.Bold,
                fontSize = sp_20,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        val columns = 3
        LazyVerticalGrid(
            GridCells.Fixed(columns),
            modifier = Modifier
                .wrapContentWidth()
                .background(White)
        ) {
            val list = movieImage.value?.results
            if (list != null) {
                items(list.size) { item ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        val data = list[item]
                        ItemView(
                            "${IMAGE_ENDPOINT + list[item].posterPath}.toString()",
                            data.title,
                            data.releaseDate,
                            navController,
                            movieId = data.id.toString(),
                            data
                        )
                    }
                }
            } else {
                Log.e("error", list.toString())
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun ItemView(
    image: String,
    title: String,
    date: String,
    navController: NavController,
    movieId: String,
    data: Result?
) {
    val favouritesMovieViewModel: FavouritesMovieViewModel = hiltViewModel()

    Card(
        modifier = Modifier.padding(dp_10),
        elevation = CardDefaults.cardElevation(dp_4),
        onClick = {
            navController.navigate("${NavigationItem.DETAILS_SCREEN.route}/$movieId")
        }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
        ) {

            Column {

                Surface(modifier = Modifier
                    .clickable {
                        data?.let {
                            favouritesMovieViewModel.add(it)
                        }
                    }
                    .size(80.dp).background(Color.Green),
                    shadowElevation = 30.dp,
                    tonalElevation = 30.dp) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }

                GlideImage(
                    model = image,
                    contentDescription = null,
                    loading = placeholder(R.drawable.ic_launcher_background)
                )
                Text(
                    text = title,
                    fontSize = sp_14,
                    modifier = Modifier
                        .padding(dp_10)
                        .fillMaxWidth(),
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    fontSize = sp_12,
                    modifier = Modifier
                        .padding(start = dp_10, end = dp_10, bottom = dp_10)
                        .fillMaxWidth()
                )
            }
        }
    }
}

