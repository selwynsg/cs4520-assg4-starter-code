package com.cs4520.assignment4.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cs4520.assignment4.Database.Product
import com.cs4520.assignment4.R
import com.cs4520.assignment4.ViewModel.ProductViewModel


@Composable
fun ProductListScreen(navController: NavHostController, viewModel: ProductViewModel = viewModel()) {
    val products by viewModel.result.observeAsState(emptyList())
    val isLoading by viewModel.loading.observeAsState(false)
    val errorMessage by viewModel.error.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if (products.isEmpty()) {
                Text(
                    "No products available",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp
                )
            } else {
                ProductList(products = products)
            }
        }
        errorMessage?.let {
            Text(
                text = it,
                modifier = Modifier.align(Alignment.BottomCenter),
                color = MaterialTheme.colors.error
            )
        }
    }
}

@Composable
fun ProductList(products: List<Product>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(products) { product ->
            ProductItem(product)
        }
    }
}
@Composable
fun ProductItem(product: Product) {
    val backgroundColor = if (product.type == "Equipment")  colorResource(id = R.color.equip) else colorResource(id = R.color.food)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = if (product.type == "Food") R.drawable.tomato else R.drawable.hammer),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.h6.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            product.expiryDate?.let { expiryDate ->
                Text(
                    text = "Expiry: $expiryDate",
                    style = MaterialTheme.typography.body2.copy(
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Price: $${product.price}",
                style = MaterialTheme.typography.body1.copy(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )
        }
    }
}
