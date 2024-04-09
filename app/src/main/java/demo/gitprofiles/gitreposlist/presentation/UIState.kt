package demo.gitprofiles.gitreposlist.presentation


sealed class UIState<T> (
    val data: T? = null,
    val error: String? = null
){
    class SuccessState<T>(data: T?) : UIState<T>(data)
    class ErrorState<T>(error: String?, data: T? = null) : UIState<T>(data, error)
    class LoadingState<T>(val isLoadingState: Boolean) : UIState<T>()
}
