/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.Constants;

//Full Speed is 20000 units on the encoder.

import frc.robot.RobotContainer;

public class DriveBase extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */

  TalonFX leftMaster = new TalonFX(1);
  TalonFX rightMaster = new TalonFX(2);
  TalonFX leftSlave = new TalonFX(11);
  TalonFX rightSlave = new TalonFX(22);
  
  TalonFXConfiguration configs = new TalonFXConfiguration();
  private double SlowSpeedLimit = 0.25;
  private double StandardSpeedLimit = 0.50;

public DriveBase() {
  leftSlave.follow(leftMaster);
  rightSlave.follow(rightMaster);
  configs.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
  leftMaster.configAllSettings(configs);
  rightMaster.configAllSettings(configs);
  
  rightMaster.setSelectedSensorPosition(0, 0, 0);
  leftMaster.setSelectedSensorPosition(0, 0, 0);
  configDrivebase();
}

@Override
public void periodic() {
  // This method will be called once per scheduler run
  if(RobotState.isEnabled() && RobotState.isOperatorControl()){
    if(RobotContainer.DriverController.getBumper(edu.wpi.first.wpilibj.GenericHID.Hand.kRight)){
      leftMaster.set(TalonFXControlMode.PercentOutput, getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kLeft) * -1));
      rightMaster.set(TalonFXControlMode.PercentOutput, getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kRight)));
   //   SmartDashboard.putString("Speed", "Turbo");

    }else{
      if(RobotContainer.DriverController.getBumper(edu.wpi.first.wpilibj.GenericHID.Hand.kLeft)){
        leftMaster.set(TalonFXControlMode.PercentOutput, (SlowSpeedLimit * getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kLeft) * -1)));
        rightMaster.set(TalonFXControlMode.PercentOutput, (SlowSpeedLimit * getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kRight))));
     //   SmartDashboard.putString("Speed", "Slow");

      }else{
        leftMaster.set(TalonFXControlMode.PercentOutput, (StandardSpeedLimit * getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kLeft) * -1)));
        rightMaster.set(TalonFXControlMode.PercentOutput, (StandardSpeedLimit * getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kRight))));
     //   SmartDashboard.putString("Speed", "Normal");

      }
    }
   // leftMaster.set(TalonFXControlMode.PercentOutput, getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kLeft) * -1));
   // rightMaster.set(TalonFXControlMode.PercentOutput, getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kRight)));
 // 1' is 12000 encoder ticks  aka 1000 per 1"
 //Vision gets us to 25000 ticks from the wall
  }
   // SmartDashboard.putNumber("left",  getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kLeft) * -1));
   // SmartDashboard.putNumber("right", getJSWithDeadBand(RobotContainer.DriverController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kRight)));
   // SmartDashboard.putNumber("left position", leftMaster.getSelectedSensorPosition(0));
   // SmartDashboard.putNumber("Left Speed", leftMaster.getSelectedSensorVelocity(0));
   // SmartDashboard.putNumber("Right position", rightMaster.getSelectedSensorPosition(0));
   // SmartDashboard.putNumber("Right Speed", rightMaster.getSelectedSensorVelocity(0));
    
  
}
public void driveTo(int Distance){
  rightMaster.set(TalonFXControlMode.Position, 1);
  leftMaster.set(TalonFXControlMode.Position, -1 * Distance);

}
public void drivePosition(double leftInput, double rightInput){
  rightMaster.set(TalonFXControlMode.Position, rightInput);
  leftMaster.set(TalonFXControlMode.Position, leftInput);
}
public void drivePercent(double leftInput, double rightInput){
  rightMaster.set(TalonFXControlMode.PercentOutput, rightInput);
  leftMaster.set(TalonFXControlMode.PercentOutput, leftInput);
}

private void configDrivebase(){
  //rightMaster.setNeutralMode(NeutralMode.Brake);
  rightMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
  rightMaster.setSensorPhase(Constants.kSensorPhase);
  rightMaster.setInverted(Constants.kMotorInvert);
  rightMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
  rightMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
  rightMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
  rightMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);
  rightMaster.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 
    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    rightMaster.config_kF(Constants.kPIDLoopIdx, Constants.kDriveGains.kF, Constants.kTimeoutMs);
    rightMaster.config_kP(Constants.kPIDLoopIdx, Constants.kDriveGains.kP, Constants.kTimeoutMs);
    rightMaster.config_kI(Constants.kPIDLoopIdx, Constants.kDriveGains.kI, Constants.kTimeoutMs);
    rightMaster.config_kD(Constants.kPIDLoopIdx, Constants.kDriveGains.kD, Constants.kTimeoutMs);

  //leftMaster.setNeutralMode(NeutralMode.Brake);
  leftMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
  leftMaster.setSensorPhase(Constants.kSensorPhase);
  leftMaster.setInverted(Constants.kMotorInvert);
  leftMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
  leftMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
  leftMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
  leftMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);
  leftMaster.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 
    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    leftMaster.config_kF(Constants.kPIDLoopIdx, Constants.kDriveGains.kF, Constants.kTimeoutMs);
    leftMaster.config_kP(Constants.kPIDLoopIdx, Constants.kDriveGains.kP, Constants.kTimeoutMs);
    leftMaster.config_kI(Constants.kPIDLoopIdx, Constants.kDriveGains.kI, Constants.kTimeoutMs);
    leftMaster.config_kD(Constants.kPIDLoopIdx, Constants.kDriveGains.kD, Constants.kTimeoutMs);
}

public void resetEncoder(){
  rightMaster.setSelectedSensorPosition(0, 0, 0);
  leftMaster.setSelectedSensorPosition(0, 0, 0);
}
public int readEncoder(boolean readRight){
  if (readRight){
    return rightMaster.getSelectedSensorPosition(0);
  }else{
    return leftMaster.getSelectedSensorPosition(0);
  }
}
public void CoastMode(){
  rightMaster.setNeutralMode(NeutralMode.Coast);
  rightSlave.setNeutralMode(NeutralMode.Coast);
  leftMaster.setNeutralMode(NeutralMode.Coast);
  leftSlave.setNeutralMode(NeutralMode.Coast);
}
public void BrakeMode(){
  rightMaster.setNeutralMode(NeutralMode.Brake);
  rightSlave.setNeutralMode(NeutralMode.Brake);
  leftMaster.setNeutralMode(NeutralMode.Brake);
  leftSlave.setNeutralMode(NeutralMode.Brake);

}
private double getJSWithDeadBand(double joystickvalue) {
  final double sensitivity = 0.3;//(values of 0-1) 0:y=input y=input^3 
  double joystickOutput = joystickvalue;
  joystickOutput = ((1-sensitivity)*joystickOutput) + (sensitivity*Math.pow(joystickOutput, 3));

  if (Math.abs(joystickvalue)<.1) {
    return 0;
  } else if (joystickvalue > .95) {
    return 1;
  }else if (joystickvalue < -0.95) {
    return -1;
  } else {
    return (joystickOutput) ;
  }
}
}
// Falcon 500 examples
//https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/tree/master/Java%20Talon%20FX%20(Falcon%20500)