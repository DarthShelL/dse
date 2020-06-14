package dse.engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {
    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButtons;

    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double mouseX, mouseY;

    public void Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
//                keys
            }
        };
    }
}
