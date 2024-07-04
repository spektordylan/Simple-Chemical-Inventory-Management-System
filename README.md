## About
The Simple Chemical Inventory Management System (SCIMS) is a JavaFX desktop application designed to be a simple inventory management solution for small to mid-size chemical laboratories. It is geared towards simplicity, usability, and easy auditing.

I created this application as a final project for a computer science class taken in the Spring 2024 semester. As such, it is currently more of a demo than a standalone application. If I were to continue development of this project, future features would also include more customizable reporting, more sort/filter options, additional data fields in each chemical entry, a detailed changelog, and more safety features.

Additional documentation for this project is accessible here: https://drive.google.com/drive/folders/1wrARQx-N0xNl1-g_hrv2G1U6Zs0XLjYw?usp=drive_link

## Build Instructions
This application uses a vanilla JavaFX build without any build tools and runs in a VSCode instance.
The recommended software versions are JDK 21 and JavaFX 21.

To make the JavaFX libraries accessible to the application, they have been added to the 'lib' folder in this repository.

Alternatively, a settings.json file can be included in the .vscode folder containing the proper paths to the JavaFX .jar files in the 'lib' folder of the downloaded SDK on the host machine.
A sample settings.json file is seen below:

{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx.web.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx-swt.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx.swing.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx.base.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx.controls.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx.fxml.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx.graphics.jar",
        "c:\\javafx-sdk-21.0.2\\lib\\javafx.media.jar"
    ]
}

Finally, the launch.json file should contain the following line for the Main.java class in the src folder:

"vmArgs": "--module-path \"FILEPATH" --add-modules javafx.controls,javafx.fxml"

FILEPATH should be the path to the 'lib' folder of the JavaFX SDK you are using. The launch.json file
can be created through the VSCode "Run and Debug" menu for the Main.java class and should automatically be created with an entry for the Main class. My launch.json file I use to run the application on my machine is included in this repository for reference.

## Running Instructions
Once the project is properly built, open the 'Main.java' file at the top level of the 'src' folder and run it.
This opens the application without requiring any scripts.
