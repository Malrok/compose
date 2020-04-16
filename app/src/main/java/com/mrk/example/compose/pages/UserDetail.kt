package com.mrk.example.compose.pages

import android.app.Activity
import android.content.Intent
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.*
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.Scaffold
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.mrk.example.compose.PICK_IMAGE
import com.mrk.example.compose.R
import com.mrk.example.compose.ambients.ViewModelAmbient
import com.mrk.example.compose.components.EditTextField
import com.mrk.example.compose.components.IconButton
import com.mrk.example.compose.components.ImageNetwork
import com.mrk.example.compose.models.FirestoreQuery
import com.mrk.example.compose.models.UserModel
import com.mrk.example.compose.navigation.Root
import com.mrk.example.compose.navigation.navigateTo
import com.mrk.example.compose.viewmodels.MainViewModel

interface UserDetail {
    companion object {
        @Composable
        fun Content(id: String) {
            val viewModel = ViewModelAmbient.current
            val query by viewModel.getUserById(id).observeAsState()

            Scaffold(
                topAppBar = {
                    TopAppBar(
                        title = {
                            query.let {
                                if (it != null && !it.loading) {
                                    Text("${it.data.firstName} ${it.data.lastName}")
                                } else {
                                    Text(stringResource(id = R.string.detail_title))
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                id = R.drawable.ic_back
                            ) {
                                navigateTo(Root.Routing.List)
                            }
                        }
                    )
                },
                bodyContent = {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        userDetail(query, viewModel)
                    }
                }
            )
        }
    }
}

@Composable
fun userDetail(query: FirestoreQuery<UserModel>?, viewModel: MainViewModel? = null) {
    val activity = ContextAmbient.current as Activity

    if (query != null && !query.loading) {
        val user by state { query.data }

        ConstraintLayout(
            constraintSet = ConstraintSet {
                val scroller = tag("scroller")
                val button = tag("button")

                button.apply {
                    left constrainTo parent.left
                    right constrainTo parent.right
                    bottom constrainTo parent.bottom
                }

                scroller.apply {
                    top constrainTo parent.top
                    bottom constrainTo button.top
                }
            }
        ) {
            VerticalScroller(
                modifier = Modifier.tag("scroller")
            ) {
                Column {
                    Clickable(
                        modifier = Modifier.padding(16.dp),
                        onClick = {
                            val intent = Intent(Intent.ACTION_PICK)
                            intent.type = "image/*"
                            activity.startActivityForResult(intent, PICK_IMAGE)
                        }
                    ) {
                        ImageNetwork(url = user.picture, width = 128.dp, height = 128.dp)
                    }
                    EditTextField(
                        value = TextFieldValue(user.firstName),
                        onValueChange = {
                            user.firstName = it.text
                        }
                    )
                    EditTextField(
                        value = TextFieldValue(user.lastName),
                        onValueChange = {
                            user.lastName = it.text
                        }
                    )
                    EditTextField(
                        value = TextFieldValue(user.description),
                        onValueChange = {
                            user.description = it.text
                        }
                    )
                }
            }
            TextButton(
                onClick = {
                    viewModel?.pushUser(user)?.observeForever {
                        navigateTo(Root.Routing.List)
                    }
                },
                modifier = Modifier.tag("button") + Modifier.padding(16.dp)
            ) {
                Text(stringResource(R.string.validate_action))
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize() + Modifier.wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun userDetailPreview() {
    val user = UserModel("-1", "Bob", "Léponge", "Carrée", "bob@yopmail.com", "https://pbs.twimg.com/profile_images/844143700414533633/mSNsw4g0_400x400.jpg")
    val query = FirestoreQuery(loading = false, error = false, data = user)
    userDetail(query = query)
}