package dev.kotl.malayah

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.kotl.malayah.ui.ChatAppProper
import dev.kotl.malayah.ui.ChatUiModel
import dev.kotl.malayah.ui.ChatViewModel
import dev.kotl.malayah.ui.LandingPage
import dev.kotl.malayah.ui.LoginPage
import dev.kotl.malayah.ui.RegisterPage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.converter.gson.GsonConverterFactory

lateinit var users: Users

interface ApiService {
    @POST("api/register")
    fun registerUser(@Body user: User): Call<UserResponse>

    @POST("api/login")
    fun loginUser(@Body credentials: LoginCredentials): Call<LoginResponse>
}

data class UserResponse(
    val success: Boolean,
    val message: String
)

data class LoginResponse(
    val success: Boolean,
    val token: String? = null,  // Example response data
    val message: String
)

data class LoginCredentials(
    val username: String,
    val password: String
)


sealed class Routes(val route: String) {
    object Landing : Routes("landing")
    object Login : Routes("login")
    object Register : Routes("register")
}

class Users {

    private val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://malayah-api.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun register(user: User, onSuccess: (UserResponse) -> Unit, onFailure: (String) -> Unit) {
        api.registerUser(user).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody != null) {
                        onSuccess(responseBody)
                    } else {
                        onFailure("No response from server")
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onFailure("Network error: ${t.message}")
            }
        })
    }

    fun validate(
        username: String,
        password: String,
        onSuccess: (LoginResponse) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val credentials = LoginCredentials(username, password)

        api.loginUser(credentials).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        onSuccess(responseBody)
                    } else {
                        onFailure("No response from server")
                    }
                } else {
                    onFailure("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onFailure("Network error: ${t.message}")
            }
        })
    }
}

class User(
    val username: String,
    val password: String,
    val email: String
)

@Composable
fun AppController(viewModel: ChatViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Landing.route) {
        composable(Routes.Landing.route) {
            LandingPage(navController)
        }
        composable(Routes.Login.route) {
            LoginPage(navController)
        }
        composable(Routes.Register.route) {
            RegisterPage(navController)
        }
        composable(
            route = "chat/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            val conversation = viewModel.conversation.collectAsState()
            ChatAppProper(
                navController,
                username!!,
                ChatUiModel(
                    messages = conversation.value,
                    addressee = "MalayahBot"
                ),
                onSend = { msg -> viewModel.send(ChatUiModel.Message(msg, username)) },
                clear = { viewModel.clear() }
            )
        }
    }
}