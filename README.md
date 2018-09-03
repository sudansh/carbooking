# Carbooking

Sample app which shows the live cab locations and also cabs available between a certain time period. 
- You can choose time period from pre populated options.
- Option to show only the cabs which are not on trip i.e. available cars



# Salient Features
- No use of xml for layout inflation
- koin for dependency injection
- MVVM Architecture with latest android components livedata, viewmodel, room for caching
- written in Kotlin
- Tests with mockito and espresso
- Snackbars show any information like warnings/erros to the use.

Generate a maps key from https://developers.google.com/maps/documentation/android/start#get-key and put it in google_maps_api.xml (debug/release builds)