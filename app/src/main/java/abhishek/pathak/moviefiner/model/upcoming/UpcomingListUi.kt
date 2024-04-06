package abhishek.pathak.moviefiner.model.upcoming

import abhishek.pathak.moviefiner.R
import abhishek.pathak.moviefiner.model.api.Constants.IMAGE_ENDPOINT
import abhishek.pathak.moviefiner.navigation.NavigationItem
import abhishek.pathak.moviefiner.popular.ItemView
import abhishek.pathak.moviefiner.ui.theme.White
import abhishek.pathak.moviefiner.ui.theme.sp_20
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun UpcomingListUiPrev() {
    UpcomingListUi(UpcomingViewModel(), rememberNavController())
}

@Composable
fun UpcomingListUi(upcomingViewModel: UpcomingViewModel = viewModel(), navController: NavController) {
    upcomingViewModel.fetchUpcomingMovieData()
    val movieImage = upcomingViewModel.upcomingLiveData.observeAsState()
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
                onClick = {  navController.navigate(NavigationItem.WELCOME.route)},
                Modifier.paint(painterResource(id = R.drawable.baseline_arrow_back_24))
            ) { }

            Text(
                text = stringResource(id = R.string.upcoming),
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
                        ItemView(
                            "${IMAGE_ENDPOINT + list[item].poster_path}.toString()",
                            list[item].title.toString(),
                            list[item].release_date.toString()
                        )
                    }
                }
            } else {
                Log.e("error", list.toString())
            }
        }
    }
}



@Preview
@Composable
private fun ItemViewPrev() {
    //ItemView()
}
