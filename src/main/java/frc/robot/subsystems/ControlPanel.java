/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControlPanel extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */

  TalonSRX CPI= new TalonSRX(5);

public ControlPanel() {
 
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(RobotState.isEnabled() && RobotState.isOperatorControl()){
      CPI.set(ControlMode.PercentOutput, RobotContainer.OperatorController.getTriggerAxis(edu.wpi.first.wpilibj.GenericHID.Hand.kRight));
    
      SmartDashboard.putNumber("operatortrigger", RobotContainer.OperatorController.getTriggerAxis(edu.wpi.first.wpilibj.GenericHID.Hand.kRight));
    }
  }
}
/*
import edu.wpi.first.wpilibj.DriverStation;

String gameData;
gameData = DriverStation.getInstance().getGameSpecificMessage();
if(gameData.length() > 0)
{
  switch (gameData.charAt(0))
  {
    case 'B' :
      //Blue case code
      break;
    case 'G' :
      //Green case code
      break;
    case 'R' :
      //Red case code
      break;
    case 'Y' :
      //Yellow case code
      break;
    default :
      //This is corrupt data
      break;
  }
} else {
  //Code for no data received yet
}

*/