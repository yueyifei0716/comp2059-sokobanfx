# AE2DMS-CW-20125896

Yifei YUE 20125896    **Words Count:** 499

## New Features

1. Add high score pop-up
    1. If the current mark is the highest, then a medal will be offered
    2. Show only the top eight scores with the name of the player

2. Attack walls
    1. Add keeper direction
    2. If the user press "A", only the wall towards the keeper's face will be removed, and the moves count will be automatically increase by 10

3. Two types of trap
    1. If the keeper steps on the reverse trap, then it will move in the reverse direction
    2. If the keeper steps on the monster trap, the game will be over.

4. Pressure pad
    1. If a crate is pushed on it, then the gate will open
    2. If the crate is removed from it, then the gate will close

5. Pipes
    1. The keeper can pass through pipes
    2. If the exit is not a floor, then the keeper can not enter the pipe

6. Status bar
    1. The status bar will automatically show the user name, current level, and moves count.

7. Input Name window

8. Level Selection Window

## Refactor

1. Design pattern
    1. Factory pattern
        1. Use DirectionFactory to get the object and check the keeper direction to the object
    2. Singleton pattern
        1. Change GameEngine and GameLogger to singleton classes because they only contain one object that can be accessed directly.
    3. Strategy pattern
        1. Use RenderStrategy to render every kind of game object instead of GraphicObjects
        2. The RenderStrategy is implemented by all kinds of object strategy to load the corresponding images.
    4. State pattern
        1. A GameState interface is created to define the current game state, which includes StartState and StopState
        2. The StopState is used to stop the game and show the High Score list.

2. MVC pattern
    1. Model, View, Controller are separated into three packages, and all FXML files are placed in the resources

        1. Model represents an object. It can also have logic to update the controller if its data changes. The objects in Model can be used to initialize the view and also change the view based on their properties

        2. The view is regarded as FXML files and the render method to show the data from the Model.

        3. Controller connects the Model and view. It controls the data flow into the model object and updates the view whenever data changes. It keeps view and Model separate.

3. Design principle

    1. The handleKey() method was extracted into a class because a class should only have one responsibility.
    2. The GraphicObjects() method has much duplicate code with similar logic, so a strategy pattern was applied to handle this.
    3. The loadGameFile() method in GameEngine was extracted into the FileHandler class. Because the responsibility of loadGameFile can also be used in other classes. So single responsibility was applied.
    4. The move() method in GameEngine has lots of duplicated logic, so it was sperated into 3 methods for different target objects.
