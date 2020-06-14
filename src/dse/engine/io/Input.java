package dse.engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButtons;

    private static final boolean[] pressedKeys = new boolean[GLFW_KEY_LAST];
    private static final boolean[] releasedKeys = new boolean[GLFW_KEY_LAST];
    private static final boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;

    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                pressedKeys[key] = (action != GLFW_RELEASE);
                releasedKeys[key] = (action == GLFW_RELEASE);
            }
        };
        mouseMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtons;
    }

    public static boolean isKeyPressed(int key) {
        return pressedKeys[key];
    }

    public static boolean isKeyReleased(int key) {
        return releasedKeys[key];
    }

    public static boolean isButtonDown(int button) {
        return buttons[button];
    }

    public void destroy() {
//        keyboard.free();
//        mouseMove.free();
//        mouseButtons.free();
    }

}
