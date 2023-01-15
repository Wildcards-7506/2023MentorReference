package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.LEDTOCom;

public class LEDs extends SubsystemBase{
    public static Alliance teamColor;
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    // Store what the last hue of the first pixel is
    private int m_rainbowFirstPixelHue;

    //Shooter constructor - creates a shooter in robot memory
    public LEDs(int pwmPort, int bufferSize){
        m_led = new AddressableLED(pwmPort);
        m_ledBuffer = new AddressableLEDBuffer(bufferSize);

        m_led.setLength(bufferSize);
        m_led.setData(m_ledBuffer);
        m_led.start();
    }

    @Override
    public void periodic(){
        setDefaultCommand(new LEDTOCom());
    }

    public void update() {
        m_led.setData(m_ledBuffer);
    }

    public void rainbow() {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Calculate the hue - hue is easier for rainbows because the color
          // shape is a circle so only one value needs to precess
          final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
          // Set the value
          m_ledBuffer.setHSV(i, hue, 255, 255);
        }
        // Increase by to make the rainbow "move"
        m_rainbowFirstPixelHue += 3;
        // Check bounds
        m_rainbowFirstPixelHue %= 180;
        update();
    }

    public void solid(int hue, int sat, int val) {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Set the value
          m_ledBuffer.setHSV(i, hue, sat, val);
        }
        update();
    }

    public void teamColor() {
        // For every pixel
        for (var i = 0; i < m_ledBuffer.getLength(); i++) {
          // Set the value Red or Blue, depending on team color
          int hue = (teamColor == Alliance.Red) ? 0 : 120;
          m_ledBuffer.setHSV(i, hue, 255, 255);
        }
        update();
    }
}