Price Tracker App

The application is designed to track the current prices of products and notification users about new prices changes.
Application is still in development stage and will constantly improvement by new features and bug fixing.



    Description:
        - Init project
        - Add Readme.md file
        - Create basic Authentication views: Login/Registration/ResetPassword Screens
        - Create Dashboard Activity Views supported with bottom navigation bar, fragments and navigation graph JetPack
        - Disable application dark mode

    Pattern and coding styles applied:
        - MVVM
        - navigation between fragments with navigation graph and navigation controller by JetPack
        - view binding / data binding
        - single Activity modules
        - shared activityViewModels provided by delegates


    2 -> task_2/implement_api_repository
    Description:
        - Implement RetrofitService with Okhttp and GsonConverter
        - Implement AuthDatasource and AppDataSource to communicate with API
        - Add api models data classes
        - Implement AuthInterceptor

    Pattern and coding styles applied:
        - Dependency injection with @Provides annotations
        - Separate DataSources
        - Kotlin data classes
        - Single Responsibility classes
        - Separate functions logic by interfaces
        - Extract constants


    3 -> task_3/implement_authentication
    Description:
        - Connect Authentication logic with backend
        - Implement viewModel functions with UiState
        - Observe UIState in activity and fragments
        - Add activity progressBar
        - Add test mocks user inputs
        - Add repository !responce.isSuccessfull checks
        - Global exception handler for Activity

    Pattern and coding styles applied:
        - UiState sealed classes
        - ViewModelScope with exceptionHandler
        - LiveData with observers
        - View onClick listeners for button
        - Extract string
