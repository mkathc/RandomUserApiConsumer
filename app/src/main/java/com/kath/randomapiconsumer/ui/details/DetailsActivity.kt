package com.kath.randomapiconsumer.ui.details

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.kath.randomapiconsumer.R
import com.kath.randomapiconsumer.domain.model.RandomUser
import com.kath.randomapiconsumer.ui.utils.downloader.AndroidDownloader
import com.kath.randomapiconsumer.ui.theme.RandomApiConsumerTheme
import com.kath.randomapiconsumer.ui.utils.email.SendEmail

class DetailsActivity : ComponentActivity() {

    private lateinit var user: RandomUser
    private var isDownloaded = false

    companion object {
        private const val USER = "USER"
        private const val TEL_COD = 100

        fun getIntent(
            context: Context,
            user: RandomUser,
        ): Intent =
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(USER, user)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomApiConsumerTheme {
                intent.getParcelableExtra<RandomUser>(USER)?.let {
                    user = it
                    DetailsScreen(user = it)
                }
            }
        }
    }

    @Composable
    fun DetailsScreen(user: RandomUser) {
        val context = LocalContext.current
        Scaffold(modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background, topBar = {
                Row(Modifier
                    .background(MaterialTheme.colors.primary)
                    .fillMaxWidth()) {
                    IconButton(onClick = { finish() }) {
                        Image(painter = rememberImagePainter(data = android.R.drawable.arrow_up_float),
                            contentDescription = null,
                            modifier = Modifier
                                .rotate(270f))
                    }
                    Text(text = stringResource(id = R.string.title_user_data),
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(10.dp), color = Color.White)
                }
            }) {
            Column {
                Spacer(modifier = Modifier.size(10.dp))
                AsyncImage(
                    model = user.pictureLarge,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            Toast
                                .makeText(applicationContext,
                                    "Descargando imagen",
                                    Toast.LENGTH_SHORT)
                                .show()
                            val downloader = AndroidDownloader(applicationContext)
                            downloader.downloadFile(user.pictureLarge, user.name)
                            isDownloaded = true
                        }
                )
                Text(text = "${stringResource(id = R.string.gender)} ${user.genre}",
                    modifier = Modifier.padding(10.dp))

                Text(text = "${stringResource(id = R.string.name)} ${user.name}",
                    modifier = Modifier.padding(10.dp))

                Text(text = "${stringResource(id = R.string.user)} ${user.userName}",
                    modifier = Modifier.padding(10.dp))

                Text(text = "${stringResource(id = R.string.email)} ${user.email}+ \uD83D\uDCE9",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            SendEmail(context).sendEmailTo(user.email,
                                "${user.name}.jpg",
                                isDownloaded)
                        })

                Text(text = "${stringResource(id = R.string.phone)} ${user.phone} + \uD83D\uDCF1",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            if (checkPermission(Manifest.permission.CALL_PHONE)) {
                                val phoneNumber = user.phone
                                val intentCall =
                                    Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                                startActivity(intentCall)
                            } else {
                                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),
                                    TEL_COD)
                            }

                        })

                Text(text = "${stringResource(id = R.string.address)} ${user.direction}",
                    modifier = Modifier.padding(10.dp))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            TEL_COD -> {
                val permisos = permissions[0]
                val result = grantResults[0]
                if (permisos == Manifest.permission.CALL_PHONE) {
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        val phoneNumber = user.phone
                        val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                        if (ActivityCompat.checkSelfPermission(this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                        ) return
                        startActivity(intentCall)
                    } else {
                        Toast.makeText(this,
                            "Acceso no autorizado",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        val result = checkCallingOrSelfPermission(permission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}