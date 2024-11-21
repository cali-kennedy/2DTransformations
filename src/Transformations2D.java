import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.util.Random;

/**
 * AdvancedTransformations2D demonstrates 2D transformations (scaling, rotation, translation)
 * on a square using linear algebra matrices in Java. Users can interact with sliders
 * to apply transformations in real-time.
 */
public class Transformations2D extends JPanel {

    // Initial square defined by its corner points, centered at the origin (for simplicity)
    private double[][] squarePoints = {{-50, -50}, {50, -50}, {50, 50}, {-50, 50}};
    private double scaleX = 1.0, scaleY = 1.0;  // Initial scale factors for x and y
    private double rotationAngle = 0.0;         // Initial rotation angle in radians
    private double translateX = 0.0, translateY = 0.0; // Initial translation values for x and y

    /**
     * Constructor for setting up the transformation controls, panel, and frame.
     */
    public Transformations2D() {
        setPreferredSize(new Dimension(400, 400)); // Size of the drawing panel

        // Create sliders for controlling scale, rotation, and translation values
        JSlider scaleXSlider = new JSlider(1, 300, 100); // Scale X (0.01 to 3.0)
        JSlider scaleYSlider = new JSlider(1, 300, 100); // Scale Y (0.01 to 3.0)
        JSlider rotationSlider = new JSlider(0, 360, 0); // Rotation angle (0 to 360 degrees)
        JSlider translateXSlider = new JSlider(-200, 200, 0); // Translate X (-200 to 200)
        JSlider translateYSlider = new JSlider(-200, 200, 0); // Translate Y (-200 to 200)

        // Label to display the current transformation matrices (updated with each change)
        JLabel matrixLabel = new JLabel();
        updateMatrixLabel(matrixLabel); // Initialize matrix display

        // Button to apply random transformations for exploration mode
        JButton randomButton = new JButton("Random Transformations");
        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyRandomTransformations(scaleXSlider, scaleYSlider, rotationSlider, translateXSlider, translateYSlider);
            }
        });

        // Adding listeners to update transformations when sliders change
        scaleXSlider.addChangeListener(e -> {
            scaleX = scaleXSlider.getValue() / 100.0; // Convert slider value to scale factor (0.01 to 3.0)
            updateMatrixLabel(matrixLabel); // Update matrix display
            repaint(); // Redraw shape with new scale
        });
        scaleYSlider.addChangeListener(e -> {
            scaleY = scaleYSlider.getValue() / 100.0;
            updateMatrixLabel(matrixLabel);
            repaint();
        });
        rotationSlider.addChangeListener(e -> {
            rotationAngle = Math.toRadians(rotationSlider.getValue()); // Convert degrees to radians
            updateMatrixLabel(matrixLabel);
            repaint();
        });
        translateXSlider.addChangeListener(e -> {
            translateX = translateXSlider.getValue(); // Set translation directly from slider
            updateMatrixLabel(matrixLabel);
            repaint();
        });
        translateYSlider.addChangeListener(e -> {
            translateY = translateYSlider.getValue();
            updateMatrixLabel(matrixLabel);
            repaint();
        });

        // Set up control panel for sliders and button
        JPanel controls = new JPanel(new GridLayout(6, 2));
        controls.add(new JLabel("Scale X"));
        controls.add(scaleXSlider);
        controls.add(new JLabel("Scale Y"));
        controls.add(scaleYSlider);
        controls.add(new JLabel("Rotation"));
        controls.add(rotationSlider);
        controls.add(new JLabel("Translate X"));
        controls.add(translateXSlider);
        controls.add(new JLabel("Translate Y"));
        controls.add(translateYSlider);
        controls.add(randomButton); // Add random transformations button

        // Main frame setup
        JFrame frame = new JFrame("Interactive 2D Transformations");
        frame.add(this, BorderLayout.CENTER);  // Main drawing panel
        frame.add(controls, BorderLayout.SOUTH); // Control panel
        frame.add(matrixLabel, BorderLayout.NORTH); // Matrix display at the top
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Paints the panel, drawing a grid for reference and the transformed shape.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear previous drawings
        drawGrid(g); // Draw a reference grid
        Graphics2D g2d = (Graphics2D) g;

        // Apply the transformations to the square's points
        double[][] transformedPoints = applyTransformations(squarePoints);

        // Draw the transformed square on the panel
        drawShape(g2d, transformedPoints);
    }

    /**
     * Draws a light gray grid in the background to help visualize transformations.
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x < getWidth(); x += 20) g.drawLine(x, 0, x, getHeight());
        for (int y = 0; y < getHeight(); y += 20) g.drawLine(0, y, getWidth(), y);
    }

    /**
     * Applies scaling, rotation, and translation to each point of the square.
     * Uses linear algebra matrices for scaling and rotation.
     *
     * @param points The original points of the square
     * @return A new array of transformed points
     */
    private double[][] applyTransformations(double[][] points) {
        double[][] transformedPoints = new double[points.length][2]; // Holds transformed coordinates

        // Scaling matrix for scaling transformation:
        //     [scaleX, 0     ]
        //     [0     , scaleY]
        double[][] scalingMatrix = {{scaleX, 0}, {0, scaleY}};

        // Rotation matrix for rotation transformation:
        //     [cos(theta), -sin(theta)]
        //     [sin(theta), cos(theta) ]
        // where theta is the rotation angle in radians.
        double[][] rotationMatrix = {
                {Math.cos(rotationAngle), -Math.sin(rotationAngle)},
                {Math.sin(rotationAngle), Math.cos(rotationAngle)}
        };

        // Apply scaling, then rotation, then translation to each point
        for (int i = 0; i < points.length; i++) {
            double[] point = points[i];

            // Step 1: Scale the point using scaling matrix
            double[] scaledPoint = multiplyMatrixAndPoint(scalingMatrix, point);

            // Step 2: Rotate the scaled point using rotation matrix
            double[] rotatedPoint = multiplyMatrixAndPoint(rotationMatrix, scaledPoint);

            // Step 3: Translate by adding translation values directly to the coordinates
            transformedPoints[i][0] = rotatedPoint[0] + translateX;
            transformedPoints[i][1] = rotatedPoint[1] + translateY;
        }
        return transformedPoints;
    }

    /**
     * Multiplies a 2x2 matrix with a 2D point (vector) to get the transformed point.
     *
     * For example:
     *   Scaling: [sx * x, sy * y]
     *   Rotation: [cos(theta) * x - sin(theta) * y, sin(theta) * x + cos(theta) * y]
     *
     * @param matrix 2x2 transformation matrix (scaling or rotation)
     * @param point  2D point to transform
     * @return A new transformed 2D point
     */
    private double[] multiplyMatrixAndPoint(double[][] matrix, double[] point) {
        double[] result = new double[2];
        result[0] = matrix[0][0] * point[0] + matrix[0][1] * point[1]; // x coordinate transformation
        result[1] = matrix[1][0] * point[0] + matrix[1][1] * point[1]; // y coordinate transformation
        return result; // Return transformed point
    }

    /**
     * Draws the transformed shape on the panel.
     */
    private void drawShape(Graphics2D g2d, double[][] points) {
        Path2D path = new Path2D.Double();
        path.moveTo(points[0][0] + 200, points[0][1] + 200); // Offset for centering

        // Connect each point to form the square
        for (int i = 1; i < points.length; i++) {
            path.lineTo(points[i][0] + 200, points[i][1] + 200);
        }
        path.closePath();

        g2d.setColor(Color.BLUE); // Set shape color
        g2d.draw(path); // Draw the shape outline
    }

    /**
     * Updates the transformation matrix label with the current scaling and rotation matrices.
     *
     * @param matrixLabel The label to display the matrices
     */
    private void updateMatrixLabel(JLabel matrixLabel) {
        String matrixText = "<html>Scaling Matrix:<br>[" + scaleX + ", 0]<br>[0, " + scaleY + "]<br><br>" +
                "Rotation Matrix:<br>[" + String.format("%.2f", Math.cos(rotationAngle)) + ", " + String.format("%.2f", -Math.sin(rotationAngle)) + "]<br>" +
                "[" + String.format("%.2f", Math.sin(rotationAngle)) + ", " + String.format("%.2f", Math.cos(rotationAngle)) + "]</html>";
        matrixLabel.setText(matrixText);
    }

    /**
     * Applies random transformations to the sliders.
     *
     * @param scaleXSlider The scale X slider
     * @param scaleYSlider The scale Y slider
     * @param rotationSlider The rotation slider
     * @param translateXSlider The translate X slider
     * @param translateYSlider The translate Y slider
     */
    private void applyRandomTransformations(JSlider scaleXSlider, JSlider scaleYSlider, JSlider rotationSlider, JSlider translateXSlider, JSlider translateYSlider) {
        Random rand = new Random();
        scaleXSlider.setValue(50 + rand.nextInt(200)); // Random scale between 0.5 and 2.5
        scaleYSlider.setValue(50 + rand.nextInt(200));
        rotationSlider.setValue(rand.nextInt(360)); // Random rotation angle between 0 and 360 degrees
        translateXSlider.setValue(-100 + rand.nextInt(200)); // Random translation between -100 and 100
        translateYSlider.setValue(-100 + rand.nextInt(200));
    }

    public static void main(String[] args) {
        new Transformations2D();
    }
}
