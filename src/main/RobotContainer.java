/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  // m_Lighting = new Lighting();
  private final ColorSensor m_ColorSensor = new ColorSensor();
  private final Pneumatics m_Pneumatics = new Pneumatics();
  private final DriveBase m_DriveBase = new DriveBase();
  public static XboxController DriverController, OperatorController;
  public static TalonFX LeftMaster, LeftSlave, RightMaster, RightSlave, ArmMaster, ArmSlave, IntakeMaster, CPI;
  public static VictorSPX IntakeSlave;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureMotorControllers();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    DriverController = new XboxController(0);
    OperatorController = new XboxController(1);
  }

  private void configureMotorControllers(){
    TalonFX LeftMaster= new TalonFX(1);
    TalonFX LeftSlave= new TalonFX(11);
    TalonFX RightMaster= new TalonFX(2);
    TalonFX RightSlave= new TalonFX(22);
    TalonFX ArmMaster= new TalonFX(3);
    TalonFX ArmSlave= new TalonFX(33);
    
    TalonSRX IntakeMaster= new TalonSRX(4);
    VictorSPX IntakeSlave= new VictorSPX(44);
    TalonSRX CPI= new TalonSRX(5);
    
    IntakeSlave.follow(IntakeMaster);
    LeftSlave.follow(LeftMaster);
    RightSlave.follow(RightMaster);
    ArmSlave.follow(ArmMaster);

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
