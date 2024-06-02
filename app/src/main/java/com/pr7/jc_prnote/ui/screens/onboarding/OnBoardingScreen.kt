@file:OptIn(ExperimentalFoundationApi::class)

package com.pr7.jc_prnote.ui.screens.onboarding

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pr7.jc_prnote.R
import com.pr7.jc_prnote.ui.navigation.Screens
import com.pr7.jc_prnote.uiutils.CustomButton
import com.pr7.jc_prnote.uiutils.LargeText
import com.pr7.jc_prnote.uiutils.SmallText
import kotlinx.coroutines.launch


@Composable
fun OnBoardingMainScreen(navHostController: NavHostController) {


    val pagerState = rememberPagerState { 3 }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(2f),
            verticalArrangement = Arrangement.Bottom
        ) {
            OnboardingScreen(pagerState = pagerState)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = when (pagerState.currentPage) {
                        0 -> {
                            R.drawable.dot1
                        }

                        1 -> {
                            R.drawable.dot2
                        }

                        2 -> {
                            R.drawable.dot3
                        }

                        else -> {
                            R.drawable.dot1
                        }
                    }
                ),
                contentDescription = "dot",
                modifier = Modifier
                    .size(70.dp)
            )
        }

        CustomButton(

            text = if (pagerState.currentPage == 2) "Начинать" else "Следующий"
        ){
            scope.launch {
                pagerState.animateScrollToPage(
                    pagerState.currentPage + 1
                )
            }

            if (pagerState.currentPage==2){

                navHostController.popBackStack()
                navHostController.navigate(Screens.Home.route)
//                context.startActivity(Intent(context, MainActivity::class.java))
//                SharedPrefManager.saveBoolean(ONBOARDING, true)
//                context.finish()
            }
        }






    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(pagerState: PagerState) {


    val images = arrayOf(
        R.drawable.onboard1,
        R.drawable.onboard2,
        R.drawable.onboard3
    )
    val desc = arrayOf(
        "Управляйте своими задачами",
        "Создайте распорядок дня",
        "Организуйте свои задачи"
    )
    val desc2 = arrayOf(
        "Вы можете легко и бесплатно управлять всеми своими повседневными задачами.",
        "Создать свой индивидуальный распорядок дня, чтобы оставаться продуктивным.",
        "Вы можете упорядочить свои ежедневные задачи, добавив задачи в отдельные категории."
    )

    HorizontalPager(state = pagerState) { page: Int ->
        OnBoardingitem(imagearray = images, pagenumber = page, desc = desc, desc2 = desc2)
    }


}

@Composable
fun OnBoardingitem(
    imagearray: Array<Int>,
    pagenumber: Int,
    desc: Array<String>,
    desc2: Array<String>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 10.dp)

    ) {

        Image(
            painter = painterResource(id = imagearray.get(pagenumber)),
            contentDescription = "onboard1",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(20.dp))


        LargeText(text = desc.get(pagenumber), textAlign = TextAlign.Center)


        SmallText(text = desc2.get(pagenumber), textAlign = TextAlign.Center)


        

    }

}