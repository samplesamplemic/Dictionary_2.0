## Dictionary

This dictionary application is built using Kotlin and integrates a free external dictionary API to provide word meanings. 
It uses the Hilt library for dependency injection and is fully covered by unit and instrumented test.
The app has one feature under development, a web-scraper designed to fetch images of searched words from Wikipedia. 

<div><img alt="img" src="./app/src/main/res/drawable/dictionary_layout.png" height="300" width="150"/> </div>

## Features

- **Search Functionality:** Users can search for any word and get its definition, pronunciation, and usage examples.
- **Phonetic player** It has a player to listen the pronunciation of searched words.
- **Web-scraper** Fetch from Wikipedia the images of searched word | in progress.

## Getting Started

### Prerequisites

- Android Studio
- Kotlin
- Gradle

### Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/dictionary-app.git
   cd dictionary-app
2. **Open the project in Android Studio**
3. **Build the project**
4. **Run the app on an emulator or physical device**

### Built With
- [![Kotlin][Kotlin.]][Kotlin-url]
- [![Retrofit][Retrofit.]][Retrofit-url]
- [![Picasso][Picasso.]][Picasso-url]
- [![Jsoup][Jsoup.]][Jsoup-url]
- [![Figma][Figma.]][Figma-url]

<!-- MARKDOWN LINKS & IMAGES -->

[Kotlin.]: https://img.shields.io/badge/Kotlin-4A4A55?style=for-the-badge&logo=kotlin&logoColor=#6db33f

[Kotlin-url]: https://kotlinlang.org/

[Retrofit.]: https://img.shields.io/badge/Retrofit-4A4A55?style=for-the-badge&logo=retrofit&logoColor=#6db33f

[Retrofit-url]: https://square.github.io/retrofit/

[Figma.]: https://img.shields.io/badge/Figma-4A4A55?style=for-the-badge&logo=figma&logoColor=ffffff

[Figma-url]: https://www.figma.com/

[Picasso.]: https://img.shields.io/badge/Picasso-4A4A55?style=for-the-badge&logo=picasso&logoColor=#6db33f

[Picasso-url]: https://square.github.io/picasso/

[Jsoup.]: https://img.shields.io/badge/Jsoup-4A4A55?style=for-the-badge&logo=jsoup&logoColor=#6db33f

[Jsoup-url]: https://jsoup.org/