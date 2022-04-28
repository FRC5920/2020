/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous extends CommandBase {
  int driveStage=0;
  int AutoGoal=30000;
  double startTime=0;

  /**
   * Creates a new Autonomous.
   */
  public Autonomous() {

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.m_DriveBase.resetEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
   // SmartDashboard.putNumber("im at", RobotContainer.m_DriveBase.readEncoder(false));

   switch (driveStage) {
    case 0: 
        if (RobotContainer.m_DriveBase.readEncoder(true)> -85000) {
          RobotContainer.m_Arms.setArmsLiteral(AutoGoal);
          RobotContainer.m_DriveBase.drivePercent(0.3, -0.3);
          }else{
            RobotContainer.m_DriveBase.drivePercent(0, 0);
            RobotContainer.m_DriveBase.resetEncoder();
            driveStage = 1;
            startTime = Timer.getFPGATimestamp();
          }
    break;
    case 1:
      if(Timer.getFPGATimestamp()-startTime <2) {
        RobotContainer.m_Arms.Eject();
      }else{
        driveStage = 2;
        RobotContainer.m_Arms.StopEject();
        RobotContainer.m_DriveBase.resetEncoder();
      }
    /*  if(RobotContainer.m_Arms.Eject()<20000){
        RobotContainer.m_Arms.Eject();
      }else{
        driveStage = 2;
        RobotContainer.m_Arms.StopEject();
        RobotContainer.m_DriveBase.resetEncoder();
      }*/
      break;
    case 2:
      if (RobotContainer.m_DriveBase.readEncoder(true)<120000) {
        RobotContainer.m_DriveBase.drivePercent(0, 0.5);
        SmartDashboard.putNumber("im at", RobotContainer.m_DriveBase.readEncoder(true));
      }else{
        driveStage = 3;
        RobotContainer.m_DriveBase.drivePercent(0, 0);
        RobotContainer.m_DriveBase.resetEncoder();
        RobotContainer.m_Arms.setArms(1);
      }
    break;
  }
}


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
