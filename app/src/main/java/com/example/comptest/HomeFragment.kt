package com.example.comptest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.comptest.SampleData.categories
import com.example.comptest.ui.*
import com.example.comptest.ui.theme.CompTestTheme
import com.google.accompanist.flowlayout.FlowRow

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )

            setContent {
                CompTestTheme {
                    Surface {
                        CreateMenu()
                    }
                }
            }
        }
    }

    @Composable
    fun CreateMenu(){

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(20.dp)
        ) {                                                                                                 // Top info row
            Row() {
                BaseCardViewShape(endPadding = 20.dp) {
                    WeightView(value = 0.0)
                }

                BaseCardViewShape {
                    PricePerKGView(0.0)
                }
            }

            Row() {
                BaseCardViewShape(endPadding = 20.dp) {
                    InfoView("Pick a category below")
                }

                BaseCardViewShape {
                    TotalPriceView(0.0)
                }
            }
        }

        Column(horizontalAlignment = CenterHorizontally, verticalArrangement = Arrangement.Center) {                                                     // Categories
            Row(modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
                BaseCardViewShape(clickable = true, endPadding = 20.dp) {
                    ProductItem()
                }
                BaseCardViewShape(clickable = true, endPadding = 20.dp) {
                    ProductItem()
                }
                BaseCardViewShape(clickable = true, endPadding = 20.dp) {
                    ProductItem()
                }
            }

            Row {
                BaseCardViewShape(clickable = true, endPadding = 20.dp) {
                    ProductItem()
                }
                BaseCardViewShape(clickable = true, endPadding = 20.dp) {
                    ProductItem()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CompTestTheme {
            Surface {
                CreateMenu()
            }
        }
    }
}