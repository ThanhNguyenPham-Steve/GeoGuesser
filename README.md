# GeoGuesser

GeoGuesser is a geographic trivia quiz Android app that tests users' knowledge of the world. Players are presented with various geographic-related questions, such as capitals, countries, landmarks, and more, and they must select the correct answer from multiple choices.

The app is built with Android Studio using Kotlin and Android's native UI components.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Acknowledgement](#acknowledgement)

## Installation

To run the Pong game on your local machine, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/ThanhNguyenPham-Steve/GeoGuesser.git
2. Open the project in Android Studio:

    Launch Android Studio and select Open an existing project.
    Navigate to the cloned project directory and open it.
3. Install dependencies: The app uses standard Android dependencies, so ensure that Android Studio syncs all necessary Gradle files.

4. Run the app:

  Connect an Android device or launch an emulator.
  Click on the Run button (or use Shift + F10) to build and run the app on your device.
  The app will now open on your Android device or emulator, and you can start playing the GeoGuesser quiz!

   

## Usage
  Usage
  When you open the GeoGuesser app, you'll be prompted to select a difficulty level (Easy, Medium, Hard). Each difficulty has its own high score tracking.
  
  How to Play:
  Lives: You start with 3 lives. Each time you answer a question incorrectly, you lose one life. Once you lose all 3 lives, the game ends.
  
  Question Categories: The app presents a series of questions related to geographic trivia (e.g., capitals, flags, landmarks, countries).
  
  Difficulty Levels:
  
  Easy: Simple questions with fewer possible answers.
  Medium: Moderate questions that require more knowledge.
  Hard: Challenging questions for advanced players.
  Scoring:

  You earn 1 point for each correct answer.
  Try to answer as many questions correctly as possible to achieve the highest score before losing all 3 lives.
  High Score: After completing the quiz, your score will be displayed, and the app will store the highest score for each difficulty level. You can try to beat your high score each time you play.
  
  Game Over: The game ends when you lose all 3 lives. Your score will be displayed, and you can choose to play again.


## Contributing
If you'd like to contribute to this Pong game, feel free to fork the repository and submit pull requests. Here's how to contribute:

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Make your changes.
Commit your changes (git commit -m 'Add new feature or fix bug').
Push to the branch (git push origin feature-branch).
Open a pull request.

## Acknowledgement
Android Studio for providing an excellent development environment.
Material Design for creating an intuitive and beautiful user interface framework.
Geographic APIs and Data Providers for supplying accurate and up-to-date trivia questions (e.g., Open Trivia Database, GeoNames).
Open Source Libraries: Gson for JSON parsing, and Retrofit for networking (if you fetch questions from an online source).
