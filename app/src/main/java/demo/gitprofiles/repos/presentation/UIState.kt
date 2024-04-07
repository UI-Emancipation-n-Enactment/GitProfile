package demo.gitprofiles.repos.presentation


sealed class UIState<T> (
    val data: T? = null,
    val error: String? = null
){
    class SuccessState<T>(data: T?) : UIState<T>(data)
    class ErrorState<T>(error: String?, data: T? = null) : UIState<T>(data, error){
        fun displayError(error: String) {
            println("This is an error: $error")
        }
    }
    class LoadingState<T>(val isLoadingState: Boolean) : UIState<T>()
}
