package com.mrk.example.compose.ui.pages

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.Scaffold
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.mrk.example.compose.R
import com.mrk.example.compose.ambients.ViewModelAmbient
import com.mrk.example.compose.effects.observe
import com.mrk.example.compose.models.FirestoreQuery
import com.mrk.example.compose.models.UserModel
import com.mrk.example.compose.navigation.Root
import com.mrk.example.compose.navigation.navigateTo
import com.mrk.example.compose.ui.components.EditTextField
import com.mrk.example.compose.ui.components.IconButton
import com.mrk.example.compose.viewmodels.MainViewModel

interface UserDetail {
    companion object {
        @Composable
        fun Content(id: String) {
            val viewModel = ViewModelAmbient.current
            val query = observe(data = viewModel.getUserById(id))

            Scaffold(
                topAppBar = {
                    TopAppBar(
                        title = {
                            if (query != null && !query.loading) {
                                Text("${query.data.firstName} ${query.data.lastName}")
                            } else {
                                Text(stringResource(id = R.string.detail_title))
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

    if (query != null && !query.loading) {
        val user = state { query.data }

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
                    EditTextField(
                        value = user.value.firstName,
                        onValueChange = {
                            user.value.firstName = it
                        }
                    )
                    EditTextField(
                        value = user.value.lastName,
                        onValueChange = {
                            user.value.lastName = it
                        }
                    )
                    EditTextField(
                        value = user.value.description,
                        onValueChange = {
                            user.value.description = it
                        }
                    )
                }
            }
            TextButton(
                onClick = {
                    viewModel?.pushUser(user.value)?.observeForever {
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