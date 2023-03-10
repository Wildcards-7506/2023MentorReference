package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.SignalLightTeleopCommand;

public class SignalLight extends SubsystemBase{
    public static Alliance teamColor;
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    // Store what the last hue of the first pixel is
    private int m_firstPixelHue;

    public SignalLight(int pwmPort, int bufferSize){
        m_led = new AddressableLED(pwmPort);
        m_ledBuffer = new AddressableLEDBuffer(bufferSize);

        m_led.setLength(bufferSize);
        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    @Override
    public void periodic(){
        setDefaultCommand(new SignalLightTeleopCommand());
    }

    public void update() {
        m_led.setData(m_ledBuffer);
    }

    public void rainbow() {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (m_firstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
          // Set the value
          m_ledBuffer.setHSV(i, hue, 255, 255);
        }
        // Increase by to make the rainbow "move"
        m_firstPixelHue += 3;
        // Check bounds
        m_firstPixelHue %= 180;
        update();
    }

    public void solid(int hue, int sat) {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            final var val = 255 - (m_firstPixelHue + (i * 255 / m_ledBuffer.getLength())) % 255;
          // Set the value
          m_ledBuffer.setHSV(i, hue, sat, val);
        }
        // Increase by to make the color "move"
        m_firstPixelHue += 3;
        // Check bounds
        m_firstPixelHue %= 255;
        update();
    }

    public void teamColor() {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
            final var val = 255 - (m_firstPixelHue + (i * 255 / m_ledBuffer.getLength())) % 255;
          // Set the value Red or Blue, depending on team color
          int hue = (teamColor == Alliance.Red) ? 0 : 120;
          m_ledBuffer.setHSV(i, hue, 255, val);
        }
        // Increase by to make the color "move"
        m_firstPixelHue += 3;
        // Check bounds
        m_firstPixelHue %= 255;
        update();
    }
}