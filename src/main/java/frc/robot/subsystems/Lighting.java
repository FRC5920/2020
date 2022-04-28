/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotContainer;

public class Lighting extends SubsystemBase {
  /**
   * Creates a new Lighting Subsystem.
   */
  private static AddressableLED m_led;
  private static AddressableLEDBuffer m_ledBuffer;
  private static int m_rainbowFirstPixelHue;
  
  //Right = 0-60
  //Top = 61-120
  //Left = 121-180
  //Lightsaber = 181-225

  public  Lighting() {
    m_led = new AddressableLED(0);
    m_ledBuffer = new AddressableLEDBuffer(180);
    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);
    m_led.start();
  }


  @Override
  public void periodic() {

    if(RobotState.isAutonomous()){
      LEDRY();
    }
    if(RobotState.isDisabled()){
      All_LEDRainbow();
    }
    
    if(RobotState.isEnabled() && RobotState.isOperatorControl()){
      All_LEDRainbow();
      /*if (RobotContainer.m_ColorSensor.proximity>200){
        switch(RobotContainer.m_ColorSensor.DetectedColor){
          case "R":
            Body_LEDColor(255, 0, 0);
            break;
          case "G":
            Body_LEDColor(0, 255, 0);
            break;
          case "B":
            Body_LEDColor(0, 0, 255);
            break;
          case "Y":
            Body_LEDColor(255, 255, 0);
            break;
          case "U":
            Body_LEDColor(255, 255, 255);
            break;
          default:
            Body_LEDColor(255, 255, 255);
            break;
        }
      }else{
        if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
          Body_ShowAlliance(0,0,255);
        }else {
          Body_ShowAlliance(255,0,0);
        }
      }*/
    }
  }

  

  //Functions that do the different effects

  public void LEDRY(){
      
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      if (i % 2 == 0)
        m_ledBuffer.setRGB(i, 255, 150, 0);
      else
        m_ledBuffer.setRGB(i, 255, 0, 0);
      }     
    m_led.setData(m_ledBuffer);
  }
  public void All_LEDRainbow(){
    //--- make a rainbow pattern on LEDs ---//
    int ShowLEDs = m_ledBuffer.getLength();
    for (var i = 0; i < ShowLEDs; i++) {
        final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
        m_ledBuffer.setHSV(i, hue, 255, 128);
      }
      m_rainbowFirstPixelHue += 3;
      m_rainbowFirstPixelHue %= 180;
    m_led.setData(m_ledBuffer);
  }

  public void LEDRainbow(){
    //--- make a rainbow pattern on LEDs ---//
    int StartLED = 0;
    int ShowLEDs = m_ledBuffer.getLength();
    for (var i = 0; i < ShowLEDs; i++) {
        final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
        m_ledBuffer.setHSV(i+StartLED, hue, 255, 128);
      }
      m_rainbowFirstPixelHue += 3;
      m_rainbowFirstPixelHue %= 180;
    m_led.setData(m_ledBuffer);
  }
  public void Body_LEDRainbow(){
    //--- make a rainbow pattern on LEDs ---//
    int StartLED = 0;
    int ShowLEDs = 60;
    for (var i = 0; i < ShowLEDs; i++) {
        final var hue = (m_rainbowFirstPixelHue + (i * 180 / ShowLEDs)) % 180;
        m_ledBuffer.setHSV(i+StartLED, hue, 255, 128);
        m_ledBuffer.setHSV(120-i+StartLED, hue, 255, 128);
      }
      m_rainbowFirstPixelHue += 3;
      m_rainbowFirstPixelHue %= 180;
    m_led.setData(m_ledBuffer);
  }

  public void LEDColor(int Red, int Green, int Blue){
    int StartLED = 0;
    int ShowLEDs = m_ledBuffer.getLength();
    //--- Set a specific color to the whole string of LEDs ---//
      for (var i = 0; i < ShowLEDs; i++) {
          m_ledBuffer.setRGB(i+StartLED, Red, Green, Blue);
      }
    m_led.setData(m_ledBuffer);
  }
  public void Body_LEDColor(int Red, int Green, int Blue){
    int StartLED = 0;
    int ShowLEDs = 60;
    //--- Set a specific color to the whole string of LEDs ---//
      for (var i = 0; i < ShowLEDs; i++) {
          m_ledBuffer.setRGB(i+StartLED, Red, Green, Blue);
          m_ledBuffer.setRGB(i+StartLED+ShowLEDs, Red, Green, Blue);
      }
    m_led.setData(m_ledBuffer);
  }

  public void LEDLightsaber(int red, int green, int blue){
    int LightSaberStart = 10;
    int BackLightSabreEnd = 130;
    int LightSaberLength = 60;
    int Duration = 200;
    
    Thread t1 = new Thread(new Runnable() {
      public void run() {
        for (var i = 0; i < LightSaberLength; i++) {
            m_ledBuffer.setRGB(i+LightSaberStart, red, green, blue);
            m_ledBuffer.setRGB(BackLightSabreEnd-i, red, green, blue);
            m_led.setData(m_ledBuffer);
          try {
            Thread.sleep(Duration);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
    t1.start();
  }

  public void Body_ShowAlliance(int red, int green, int blue){
    int ShowLEDs = 60;
    int EyeSize = 4;
 
    for(int i = 0; i < ShowLEDs-EyeSize-2; i++) {
      Body_LEDColor(0,0,0);
      m_ledBuffer.setRGB(i, red/10, green/10, blue/10);
      m_ledBuffer.setRGB(120-i, red/10, green/10, blue/10);
      for(int j = 1; j <= EyeSize; j++) {
        m_ledBuffer.setRGB(i+j, red, green, blue);
        m_ledBuffer.setRGB(120-i+j, red, green, blue);
      }
      m_ledBuffer.setRGB(i+EyeSize+1, red/10, green/10, blue/10);
      m_ledBuffer.setRGB(120-i+EyeSize+1, red/10, green/10, blue/10);
      m_led.setData(m_ledBuffer);
    }
  
    for(int i = ShowLEDs-EyeSize-2; i > 0; i--) {
      Body_LEDColor(0,0,0);
      m_ledBuffer.setRGB(i, red/10, green/10, blue/10);
      m_ledBuffer.setRGB(120-i, red/10, green/10, blue/10);
      for(int j = 1; j <= EyeSize; j++) {
        m_ledBuffer.setRGB(i+j, red, green, blue);
        m_ledBuffer.setRGB(120-i+j, red, green, blue);
      }
      m_ledBuffer.setRGB(i+EyeSize+1, red/10, green/10, blue/10);
      m_ledBuffer.setRGB(120-i+EyeSize+1, red/10, green/10, blue/10);
      m_led.setData(m_ledBuffer);
    }


  }


}




// https://first.wpi.edu/FRC/roborio/release/docs/java/edu/wpi/first/wpilibj/DriverStation.html
//getMatchTime() will give approx time.  Turn on lightsaber with arms up AND time < 60 AND is Teleop

// More ideas https://www.tweaking4all.com/hardware/arduino/adruino-led-strip-effects/#LEDStripEffectSparkle