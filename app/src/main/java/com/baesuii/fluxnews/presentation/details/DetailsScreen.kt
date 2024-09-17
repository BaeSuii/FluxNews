package com.baesuii.fluxnews.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.baesuii.fluxnews.R
import com.baesuii.fluxnews.domain.model.Article
import com.baesuii.fluxnews.domain.model.Source
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingExtraLarge
import com.baesuii.fluxnews.presentation.theme.Dimensions.paddingMedium
import com.baesuii.fluxnews.presentation.theme.FluxNewsTheme

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
){
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            DetailsBar(
                onBrowsingClick = {
                    Intent(Intent.ACTION_VIEW).also {
                        it.data = Uri.parse(article.url)
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                onShareClick = {
                    Intent(Intent.ACTION_SEND).also {
                        it.putExtra(Intent.EXTRA_TEXT, article.url)
                        it.type = "text/plain"
                        if (it.resolveActivity(context.packageManager) != null) {
                            context.startActivity(it)
                        }
                    }
                },
                onBookmarkClick = { event(DetailsEvent.UpsertDeleteArticle(article)) },
                onBackClick = navigateUp
            )
        }
    ) { innerPadding ->
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            // Image in the background
            AsyncImage(
                model = ImageRequest.Builder(context = context).data(article.urlToImage).build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.TopCenter)
                    .background(MaterialTheme.colorScheme.surface),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_image_error)
            )

            // Scrollable Article content
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = paddingExtraLarge, topEnd = paddingExtraLarge)) // Adjust the corner size here
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(
                    start = paddingMedium,
                    end = paddingMedium,
                    top = paddingMedium
                )
            ){
                item {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    Spacer(modifier = Modifier.height(paddingMedium))

                    // Content
                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 30.sp),
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

//@Preview (showBackground = true)
//@Composable
//fun DetailsScreenPreview(){
//    FluxNewsTheme {
//        DetailsScreen(
//            article = Article(
//                author = "Joel Khalili",
//                title = "The World’s Biggest Bitcoin Mine Is Rattling This Texas Oil Town",
//                description = "A cash-strapped city in rural Texas will soon be home to the world’s largest bitcoin mine. Local protesters are “raising hell.”",
//                content = "In April 2022, Sawicky happened across a Facebook livestream hosted by the Corsicana city government, announcing plans for the Riot mine. The pitch was to expand the local tax base and bring jobs to people in the area. “This will spur economic growth, which will benefit all of us who live here,” says John Boswell, economic development director for both Corsicana and Navarro County.\n" +
//                        "\n" +
//                        "Sawicky wasn’t sold. Horrified at Riot’s vision, Sawicky spun up a Facebook group to coordinate a protest campaign. Within a week, hundreds of people had joined. Though TCAC has only a handful of members who attend every monthly Zoom meetup and scheduled protest, the online group now has 800 participants. For Sawicky, TCAC has practically become a full-time job; she never got around to developing a full-fledged farm.\n" +
//                        "\n" +
//                        "As we rumbled around Corsicana in Sawicky’s truck—bright red, with a chipped windshield and a footwell scattered with papers and other detritus—she entered into a long and winding criticism of Navarro County’s flirtation with the bitcoin mining industry. Sawicky frequently lurches from one thought to the next, or gets lost in a tangent, a trait she attributes partly to ADHD and partly to the depth of her anger. “One of my sayings is that anger is a renewable resource,” she says.\n" +
//                        "\n" +
//                        "“As a certified hippie and lifelong environmentalist, it was the stupidest, most disgusting waste of energy I’d ever heard of,” says Sawicky. But in Republican Navarro County, where environmental arguments hold little sway, Sawicky focuses her campaigning on the potential for the Riot facility to produce disruptive noise, pull from local water resources, and strain the power grid.",
//                publishedAt = "2024-09-11T10:00:00Z",
//                source = Source(id = "", name = "Wired"),
//                url = "https://www.wired.com/story/the-worlds-biggest-bitcoin-mine-is-rattling-this-texas-oil-town/",
//                urlToImage = "https://media.wired.com/photos/66c5ecc5724cee849e3104da/191:100/w_1280,c_limit/WIRED_BTC_EC_B-Elena-Chudoba.jpg"
//            ),
//            event = {}
//        ) {}
//    }
//}