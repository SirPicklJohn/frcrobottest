// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

/*
XBOX ONE S BUTTON ID's
(it's also shown based on what number they light up as)

<!-- Button Mappings in Windows:               -->
<!--                                           -->
<!-- ID              Button                    -->
<!--                                           -->
<!-- 1               A                         -->
<!-- 2               B                         -->
<!-- 3               X                         -->
<!-- 4               Y                         -->
<!-- 5               Left Sholder              -->
<!-- 6               Right Sholder             -->
<!-- 7               Back                      -->
<!-- 8               Start                     -->
<!-- 9               Left Stick Button         -->
<!-- 10              Right Stick Button        -->
<!-- 11              D-Pad Up                  -->
<!-- 12              D-Pad Down                -->
<!-- 13              D-Pad Left                -->
<!-- 14              D-Pad Right               -->
<!-- 15              Back                      -->

<!-- Axis Mappings:                   -->
<!--                                  -->
<!-- ID              Button           -->
<!--                                  -->
<!-- 1               Left Stick L/R   -->
<!-- 2               Left Stick U/D   -->
<!-- 3 limit +1      Left Trigger     -->
<!-- 3 limit -1      Right Trigger    -->
<!-- 4               Right Stick L/R  -->
<!-- 5               Right Stick U/D  -->

*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot; //Base Class
import edu.wpi.first.wpilibj.drive.DifferentialDrive; //Drive Base
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.Timer; //Autonomous
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser; //unused
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; //unused
import edu.wpi.first.wpilibj.system.plant.DCMotor;//For simulation
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpiutil.math.VecBuilder;
import edu.wpi.first.wpilibj.PWMTalonFX; //Motor COntroller
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Joystick; // joystick
import edu.wpi.first.wpilibj.simulation.*; //For Simulation
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
/* UNUSED CODE
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
*/
  //INITIALIZATION
  Joystick myStick;
  JoystickButton myButton;

  ////MOTOR DECLARATION////
  PWMTalonFX m_frontleft = new PWMTalonFX(1);
  PWMTalonFX m_backleft = new PWMTalonFX(2);
  SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontleft, m_backleft);

  PWMTalonFX m_frontright = new PWMTalonFX(3);
  PWMTalonFX m_backright = new PWMTalonFX(4);
  SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontright, m_backright);
  
  DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right); //THE DRIVEBASE
  /////////////////////////
  
  DifferentialDrivetrainSim m_driveSim;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    ////////initialization commands////////

    //RobotDriveBase.setDeadband(.1); ---Understand why you can't do this? You need to create a robotdrivebase object first
    m_drive.setDeadband(.1); //bingo^ - joystick axis positions below .08 will not be registered
    
    ///////////JOYSTICK/XBOX CONTROLLER//////////
    myStick = new Joystick(0);
    myButton = new JoystickButton(myStick, 0);
    /////////////////////////////////////////////

    ////fun stuff////
    System.out.println("Robot Initialized.");

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */

  @Override
  public void autonomousInit() {
    System.out.println("auton activated.");
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
    
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    m_drive.arcadeDrive(-myStick.getY(), myStick.getX());

     

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {
    System.out.println("Welcome to the simulation boyz.");

    ////DRIVEBASE SIMULATION////////
  DifferentialDrivetrainSim m_driveSim = new DifferentialDrivetrainSim(
    //.DifferentialDrivetrainSim(DCMotor driveMotor, double gearing, double jKgMetersSquared, double massKg, double wheelRadiusMeters, double trackWidthMeters, Matrix<N7, N1> measurementStdDevs)
    DCMotor.getNEO(2),       // 2 NEO motors on each side of the drivetrain.
    7.29,                    // 7.29:1 gearing reduction.
    7.5,                     // MOI of 7.5 kg m^2 (from CAD model).
    60.0,                    // The mass of the robot is 60 kg.
    Units.inchesToMeters(3), // The robot uses 3" radius wheels.
    0.7112,                  // The track width is 0.7112 meters.
  
    // The standard deviations for measurement noise:
    // x and y:          0.001 m
    // heading:          0.001 rad
    // l and r velocity: 0.1   m/s
    // l and r position: 0.005 m
    VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005));
    /////////////////////////
  }

  @Override
  public void simulationPeriodic() {
    
    
    /* Trying to figure this out so I can get a simulated playfield, where I can see the robot moving around (learn how to use encoders and this'll work)
    //robotPeriodic();
    // Set the inputs to the system. Note that we need to convert
    // the [-1, 1] PWM signal to voltage by multiplying it by the
    // robot controller voltage.
    m_driveSim.setInputs(m_left.get() * RobotController.getInputVoltage(),
                       m_right.get() * RobotController.getInputVoltage());

    // Advance the model by 20 ms. Note that if you are running this
    // subsystem in a separate thread or have changed the nominal timestep
    // of TimedRobot, this value needs to match it.
    m_driveSim.update(0.02);

    // Update all of our sensors.
    m_leftEncoderSim.setDistance(m_driveSim.getLeftPositionMeters());
    m_leftEncoderSim.setRate(m_driveSim.getLeftVelocityMetersPerSecond());
    m_rightEncoderSim.setDistance(m_driveSim.getRightPositionMeters());
    m_rightEncoderSim.setRate(m_driveSim.getRightVelocityMetersPerSecond());
    m_gyroSim.setAngle(-m_driveSim.getHeading().getDegrees());
    */

  }

} //end of Robot Class -----------------------------------------------------------------
