# ceg4110-homework-1

Link to GitHub: https://github.com/aar118/ceg4110-homework-1

This is a homework assignment 1 for Wright State University's Introduction to Software Engineering class. 
The first part of the homework was to create a view that allowed the user to type into a text box, randomly change the colors of the text, and view the colors in both RGB and hex form.
The second part of the homework was to create a simple drawing application that allowed the user to draw with any color, clear the drawing, and save the drawing.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

A phone with the latest Android version installed, which is currently version 9.

### Installing

1. Download the GitHub directory.
2. Extract the folder
3. Navigate to and open bin/app-release.apk
4. Install application

## Built With

* [Kotlin](https://kotlinlang.org/) - The web framework used
* [Android Studio](https://developer.android.com/studio/) - Dependency Management
* [Gradle](https://gradle.org/) - Dependency Management
* [Finger Painter](https://github.com/PicnicSupermarket/FingerPaintView) - Used to build part 2 for drawing app

## Authors

* **Aaron Hammer** - *Developed* - [aar118](https://github.com/aar118)

## Additional Design Notes

This project mainly includes two activity classes: the random color generator and the finger paint application

**Random Color Generator**
This piece really only contains a button action listener that does everything. Once you click the button to generate a new color, it performs all of the random calculations to do that.
Since the functionality was very small and simple, the code of this part was simple too.

**Finger Paint Application**
This piece actually took most of the time. I'd say the first piece contributed to maybe 5% of the time spent on this application, and the main reason was due to file permissions, and the library I chose.
The library is FingerPaintView by PicnicSupermarket. It was honestly the first thing I found when I was doing my search on a library to help me out on the drawing application.
Overall, I had to update a lot of deprecated code, and figure out the functionality of the code inside the class itself due to lack of documentation.

Another thing I had to do was file permissions for my save function. There was no save function for the library I chose, so I had to make my own.
It was working pretty well without permissions because it was only writing the file out to internal storage, but then you couldn't view the image on your phone.
So, I decided to add permissions to write it to a folder inside the Photos gallery or any other built in external image storage a phone might have.

I think the experience was fun, though. I've learned a lot already with many hours of Android Studio debugging, coding, and my brain feeling like it's going to explode.