package de.oberien.adbremotecontrol.thread;

import de.oberien.adbremotecontrol.Config;
import de.oberien.adbremotecontrol.adb.AdbDevice;
import de.oberien.adbremotecontrol.view.ScreenPanel;

import java.awt.image.BufferedImage;

public class ScreenshotThread extends Thread {
    private ScreenPanel screenPanel;
    private AdbDevice adbDevice;

    public ScreenshotThread(ScreenPanel screenPanel, AdbDevice adbDevice) {
        super("ScreenshotThread");
        this.screenPanel = screenPanel;
        this.adbDevice = adbDevice;
    }
    
    private static final long REFRESH_INTERVAL_MS = 0;
    
    private void runGameLoop() {
        // update the game repeatedly
        while (true) {
            long durationMs = redraw();
            try {
                Thread.sleep(Math.max(0, REFRESH_INTERVAL_MS - durationMs));
            } catch (InterruptedException e) {
            }
        }
    }

    private long redraw() {

        long t = System.currentTimeMillis();

        draw();

        // return time taken to do redraw in ms
        return System.currentTimeMillis() - t;
    }
    
    private void draw() {
    	BufferedImage screenshot = adbDevice.screenshot();
        screenPanel.setScreenshot(screenshot);
        try {
            Thread.sleep(Config.timeout);
        } catch (InterruptedException ex) {
            //break;
        }
    }

    public void run() {
    	runGameLoop();
    }

}
