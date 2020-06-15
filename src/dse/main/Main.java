package dse.main;

import dse.engine.graphics.Mesh;
import dse.engine.graphics.Renderer;
import dse.engine.graphics.Vertex;
import dse.engine.io.Input;
import dse.engine.io.Window;
import dse.engine.math.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public final int WIDTH = 800, HEIGHT = 600;
    public Input input;

    public Renderer renderer;

    // test data
    public Mesh mesh = new Mesh(new Vertex[]{
        new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f)),
        new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f)),
        new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f))
    }, new int[]{
        0, 1, 2,
        0, 3, 2
    });

    public void start() {
        game = new Thread(this, "game");
        game.run();
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = new Window(WIDTH, HEIGHT, "DSE");
        window.create();

        // Initialize input
        input = new Input();
        // Set input callbacks
        glfwSetKeyCallback(window.getWindow(), input.getKeyboardCallback());
        glfwSetCursorPosCallback(window.getWindow(), input.getMouseMoveCallback());
        glfwSetMouseButtonCallback(window.getWindow(), input.getMouseButtonsCallback());

        // test data
        GL.createCapabilities();
        renderer = new Renderer();
        mesh.create();
    }

    public void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window.getWindow())) {
            if (Input.isKeyReleased(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(window.getWindow(), true);
            }
            update();
            render();
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void update() {
        window.update();
    }

    private void render() {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    @Override
    public void run() {
        System.out.println("Running with LWJGL " + Version.getVersion() + "!");

        init();
        loop();
        destroy();
    }

    public void destroy() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window.getWindow());
        glfwDestroyWindow(window.getWindow());
        // destroy input
        input.destroy();
        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
