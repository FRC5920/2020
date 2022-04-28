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
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.Constants;

//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.RobotContainer;

public class Arms extends SubsystemBase {
  /**
   * Creates a new ExampleSubsystem.
   */
TalonFX armMaster = new TalonFX(3);
TalonFX armSlave = new TalonFX(33);
TalonSRX intakeMaster = new TalonSRX(4);
VictorSPX intakeSlave = new VictorSPX(44);
TalonFXConfiguration configs = new TalonFXConfiguration();
//private double SpeedLimit = .25;
private static int[] ArmPosition= {-2000, 38000, 51000, 76000}; //Up, Low Goal, CIP, Floor
public static boolean IsUpright;

public Arms() {
  armMaster.configFactoryDefault();
  armMaster.setSelectedSensorPosition(0, 0, 0);
  armSlave.follow(armMaster);
  configArms();
  intakeMaster.configFactoryDefault();
  intakeSlave.follow(intakeMaster);
 // intakeMaster.setSelectedSensorPosition(0,0,0);
  //intakeMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
  //intakeMaster.setSensorPhase(true);
}

@Override
  public void periodic() {
    if (armMaster.getSelectedSensorPosition(0) < 3000){
      IsUpright = true;
    }else{
      IsUpright = false;
    }
    // This method will be called once per scheduler run
    //SmartDashboard.putNumber("Arm position", armMaster.getSelectedSensorPosition(0));
   // SmartDashboard.putNumber("intake position", intakeMaster.getSelectedSensorPosition(0));
    if(RobotState.isEnabled() && RobotState.isOperatorControl()){
      //armMaster.set(ControlMode.PercentOutput, getJSWithDeadBand(RobotContainer.OperatorController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kRight)));
      intakeMaster.set(ControlMode.PercentOutput, getJSWithDeadBandIntake(RobotContainer.OperatorController.getY(edu.wpi.first.wpilibj.GenericHID.Hand.kLeft))); 
      if(RobotContainer.OperatorController.getYButtonPressed()){
        setArms(0);
      }
      if(RobotContainer.OperatorController.getXButtonPressed()){
        setArms(1);
      }
      if(RobotContainer.OperatorController.getBButtonPressed()){
        setArms(2);
      }
      if(RobotContainer.OperatorController.getAButtonPressed()){
        setArms(3);
      }
      
    }
  }

  private double getJSWithDeadBandIntake(double joystickvalue) {
    final double sensitivity = 0.8;//(values of 0-1) 0:y=input y=input^3 
    double joystickOutput = joystickvalue;
    joystickOutput = ((1-sensitivity)*joystickOutput) + (sensitivity*Math.pow(joystickOutput, 3));
  
    if (Math.abs(joystickvalue)<.1) {
      return 0;
    } else if (joystickvalue > .95) {
      return 1;
    }else if (joystickvalue < -0.95) {
      return -1;
    } else {
      return joystickOutput;
    }
  }
  public void setArms(int GoToPosition){
    armMaster.set(TalonFXControlMode.Position, ArmPosition[GoToPosition]);
  }
  public void setArmsLiteral(int GoToPosition){
    armMaster.set(TalonFXControlMode.Position, GoToPosition);
  }
  public int Eject(){
    intakeMaster.set(ControlMode.PercentOutput, -1);
    //return intakeMaster.getSelectedSensorPosition(0);
    return 0;
  }
  public void StopEject(){
    intakeMaster.set(ControlMode.PercentOutput, 0);
  }
  private void configArms(){
    armSlave.setInverted(true);
    armMaster.setNeutralMode(NeutralMode.Brake);
    armMaster.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    armMaster.setSensorPhase(Constants.kSensorPhase);
    armMaster.setInverted(Constants.kMotorInvert);
    armMaster.configNominalOutputForward(0, Constants.kTimeoutMs);
    armMaster.configNominalOutputReverse(0, Constants.kTimeoutMs);
    armMaster.configPeakOutputForward(1, Constants.kTimeoutMs);
    armMaster.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    armMaster.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs); 
      /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
      armMaster.config_kF(Constants.kPIDLoopIdx, Constants.kArmGains.kF, Constants.kTimeoutMs);
      armMaster.config_kP(Constants.kPIDLoopIdx, Constants.kArmGains.kP, Constants.kTimeoutMs);
      armMaster.config_kI(Constants.kPIDLoopIdx, Constants.kArmGains.kI, Constants.kTimeoutMs);
      armMaster.config_kD(Constants.kPIDLoopIdx, Constants.kArmGains.kD, Constants.kTimeoutMs);
  }
}

//PID for Falcon 500
//https://github.com/CrossTheRoadElec/Phoenix-Examples-Languages/blob/master/Java%20Talon%20FX%20(Falcon%20500)/PositionClosedLoop/src/main/java/frc/robot/Robot.java