package com.example.ecommerceapp.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.model.Product

@Composable
fun CartItemCard(item: Product,
                onRemoveItem: ()->Unit ){
    Card (
        modifier = Modifier.fillMaxWidth().
        padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ){

       Row(
           modifier= Modifier.fillMaxWidth()
               .padding(16.dp),
           verticalAlignment= Alignment.CenterVertically
       ){

           Image(
               painter = rememberAsyncImagePainter(model= item.imageUrl),
               contentDescription = "Product Image",
               modifier = Modifier.size(80.dp)
                   .clip(RoundedCornerShape(8.dp)),
               contentScale = ContentScale.Crop
           )

           Spacer(modifier = Modifier.width(16.dp))

           Column (
               modifier = Modifier.weight(1f)
           ){
               Text(
                   text= item.name,
                   style= MaterialTheme.typography.titleMedium)
               Text(text = "$${item.price}",
                   style = MaterialTheme.typography.titleMedium)
           }

           Column (horizontalAlignment = Alignment.CenterHorizontally){

               Row (
                   verticalAlignment = Alignment.CenterVertically
               ){
                   Text(
                       text = item.name,
                       modifier = Modifier.padding(horizontal = 8.dp),
                       style = MaterialTheme.typography.bodyLarge
                   )

                   IconButton(onClick = onRemoveItem,
                       modifier= Modifier.size(24.dp)){
                       Icon(
                           imageVector= Icons.Default.Delete,
                           contentDescription= "Remove Items"
                       )
                   }
               }
           }
           
       }
    }
}