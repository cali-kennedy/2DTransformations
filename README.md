# Transformations2D

**Transformations2D** is an interactive Java application demonstrating 2D geometric transformations (scaling, rotation, and translation) on a square using linear algebra principles. The application provides a graphical user interface (GUI) where you can manipulate the square's transformations in real-time.

---

## Features

- **Interactive Sliders**: Dynamically adjust scaling (x and y), rotation angle, and translation (x and y).
- **Live Preview**: Watch the square update instantly as you apply transformations.
- **Random Transformations**: Apply random combinations of transformations for experimentation.
- **Matrix Visualization**: Displays the current scaling and rotation matrices at the top of the GUI.
- **Grid Reference**: A light gray grid helps visualize the transformations relative to the coordinate system.

---

## How It Works

### Transformations Implemented
1. **Scaling**  
   Resizes the square along the x and y axes using the scaling matrix
   
3. **Rotation**  
   Rotates the square around its center using the rotation matrix

4. **Translation**  
   Moves the square by adding translation values directly to the coordinates.

### Sliders
- **Scale X**: Adjust horizontal scaling (range: 0.01–3.0).
- **Scale Y**: Adjust vertical scaling (range: 0.01–3.0).
- **Rotation**: Rotates the square (range: 0–360 degrees).
- **Translate X**: Translates the square horizontally (range: -200 to 200).
- **Translate Y**: Translates the square vertically (range: -200 to 200).

### Random Transformations Button
Applies random values to all sliders for unpredictable transformations.

---

## Class Structure

### `Transformations2D`
- **Purpose**: Manages the GUI setup, rendering, and transformation logic.
- **Key Methods**:
  - `applyTransformations(double[][] points)`: Applies scaling, rotation, and translation to the square's vertices.
  - `multiplyMatrixAndPoint(double[][] matrix, double[] point)`: Multiplies a 2x2 matrix with a 2D point for transformations.
  - `updateMatrixLabel(JLabel matrixLabel)`: Updates the displayed scaling and rotation matrices.
  - `applyRandomTransformations(...)`: Sets random values to sliders for experimentation.

### Main Components
- **Sliders and Labels**: Allow user input and display transformation data.
- **Drawing Panel**: Visualizes the grid and the transformed square.

---

## Example Use Cases
- **Educational Tool**: Demonstrate geometric transformations in a linear algebra or graphics programming class.
- **Experimentation**: Visualize the effects of combining transformations.
- **Debugging**: Test transformation logic for projects like game development or simulations.

---

## Known Limitations
- Only applies transformations to a single shape (a square).
- Translation range is limited to the slider's bounds (-200 to 200).
- No support for skew transformations or non-linear transformations.

---

## Future Enhancements
- Add support for multiple shapes.
- Implement skew transformations for advanced demonstrations.
- Provide the option to save transformation states and export results.

---


---

## Author
Developed as a demonstration of 2D transformations using Java Swing and linear algebra principles.  

Enjoy exploring transformations!
