/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Pneumatics extends SubsystemBase {
  
  // --- Pneumatics ---//
  public static Compressor airSupply;
  public static DoubleSolenoid ClimbPneumaticA; 
  public static DoubleSolenoid ClimbPneumaticB;

  public  Pneumatics() {
    //--- Pneumatics ---//
    airSupply = new Compressor(60);
    ClimbPneumaticA = new DoubleSolenoid(60, 0, 1);
    ClimbPneumaticB = new DoubleSolenoid(60, 2, 3);
  }


  @Override
  public void periodic() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    if(Arms.IsUpright){
    if (RobotContainer.OperatorController.getStartButton() && RobotContainer.OperatorController.getBackButton()){
      ClimbPneumaticA.set(DoubleSolenoid.Value.kForward); 
      ClimbPneumaticB.set(DoubleSolenoid.Value.kForward);     
    }else{
      ClimbPneumaticA.set(DoubleSolenoid.Value.kReverse);
      ClimbPneumaticB.set(DoubleSolenoid.Value.kReverse);
    }
  }
}
}
