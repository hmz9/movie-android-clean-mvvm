# Movie App Android Clean Architecture

An android movie app that demonstrates Clean Architecture and is written in Kotlin with MVVM

# Features

* This is an Offline-first app that allows users to access the app even without an internet connection.
* Pagination to efficiently load large amounts of data and improve the user experience.
* Search functionality to quickly find specific movies in the app.
* Auto Sync using WorkManager and NetworkConnectivityStream to ensure data is always up-to-date.
* The ability to add movies as favorite.
* I also added light and dark mode in the app

# This app is a take home challenge

## APIs used:
Movie List: "https://api.themoviedb.org/3/movie/popular?api_key=[api_key]"
Search Movie: "https://api.themoviedb.org/3/search/movie?api_key=[api_key]&query=[Movie_name]&page=1"
Poster image: "https://image.tmdb.org/t/p/w92/[poster_path]"

I also explored the tmdb api docs and consumed the getMovieById for better user experience
Get Movie By ID: "https://api.themoviedb.org/3/movie/{movie_id}?api_key=[api_key]"

## Screens and how they're working?

### Movie List Screen
Movie list screen consists of Gridlayout for Recyclerview where movies are loaded with the help of paging 3 library. Movies are fetched from local database first if available and than the pages after the db are loaded from remote server. 

For this I am saving the page numbers that are loaded successfully from remote to local database and only the next pages are loaded from server after that. That way it doesn't always fetch data from server. But on every app start a work request is launched through application class that makes sure to sync data between remote server and local database

### Favorite Screen
I added a seperate screen for favorite movies where you can see the movies you have added as favorites. Favorites are handled by saving ids of movies in "favorite_movies" table

### Movie Details Screen
You can navigate to movie details screen from main movie list screen, favorites list screen and from search movie list screen. Detail Screen has a favorite button which is changed depending upon the status if movie is favorite or not. Movie detail screen fetched data from local database and if there is error than it fetched data from remote and saves it to local database

# Clean Architecture

#### The application code is separated into layers.

These layers define the separation of concerns inside the code base.

# Architecture Layers

The application consists of three layers:

The data layer, domain layer, and presentation layer.

Looking at project’s high-level structure, you’ll see that each layer is represented by a module in the project.

![image](https://user-images.githubusercontent.com/20803775/201078111-39ba8e8d-b116-4312-bee0-df2d3258be71.png)

# Structure
Views communicate with ViewModel and than viewmodels have different usecases these use cases interact with repository and repository decides weather the datasource will be local or remote

## Built using
* Kotlin
* Coroutines
* Flow
* StateFlow

* Android Architecture Components
  - Paging3
  - LiveData
  - ViewModel
  - SavedStateHandle
  - ViewBinding
  - Navigation Components
  - Room
  - WorkManager
  
- Dependency Injection
  - Hilt
- Retrofit
- Mockito

### License
```
   Copyright (C) 2023 Ameer Hamza
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

