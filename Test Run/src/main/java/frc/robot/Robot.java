// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  Joystick stick1 = new Joystick(0);
  Joystick stick2 = new Joystick(1);
  XboxController controller = new XboxController(2);

  Talon left_drive_motor_front = new Talon(1);
  Talon left_drive_motor_back = new Talon(2);
  Talon right_drive_motor_front = new Talon(5);
  Talon right_drive_motor_back = new Talon(6);
  Talon vertical_shooter_motor = new Talon(3);
  Talon horizontal_shooter_motor = new Talon(4);

  Solenoid confetti1 = new Solenoid(1);
  Solenoid confetti2 = new Solenoid(2);
  Solenoid confetti3 = new Solenoid(3);
  Solenoid confetti4 = new Solenoid(4);
  Solenoid confetti5 = new Solenoid(5);

  Solenoid shooter = new Solenoid(0);
  Compressor airCompressor = new Compressor(0);
  
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    left_drive_motor_front.setInverted(true);
    left_drive_motor_back.setInverted(true);

    shooter.set(true);

    confetti1.set(false);
    confetti2.set(false);
    confetti3.set(false);
    confetti4.set(false);
    confetti5.set(false);

    airCompressor.start();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

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
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    //old button controls, axis controls used for xbox controller

    // boolean shoot = controller.getRawButtonPressed(2);
    // boolean sshoot = controller.getRawButtonReleased(2);

    boolean bconfetti1 = controller.getRawButtonPressed(1);
    boolean sconfetti1 = controller.getRawButtonReleased(1);

    boolean bconfetti2 = controller.getRawButtonPressed(2);
    boolean sconfetti2 = controller.getRawButtonReleased(2);

    boolean bconfetti3 = controller.getRawButtonPressed(3);
    boolean sconfetti3 = controller.getRawButtonReleased(3);

    boolean bconfetti4 = controller.getRawButtonPressed(4);
    boolean sconfetti4 = controller.getRawButtonReleased(4);

    //not using confetti #5
    // boolean bconfetti5 = controller.getRawButtonPressed(3);
    // boolean sconfetti5 = controller.getRawButtonReleased(3);

    //old button controls, xbox uses pov (D-pad) controls
    // boolean up_vertical = controller.getRawButtonPressed(4);
    // boolean up_vertical_stop = controller.getRawButtonReleased(4);
    // boolean down_vertical = controller.getRawButtonPressed(1);
    // boolean down_vertical_stop = controller.getRawButtonReleased(1);

    // boolean right_horizontal = stick1.getRawButtonPressed(5);
    // boolean right_horizontal_stop = stick1.getRawButtonReleased(5);
    // boolean left_horizontal = stick1.getRawButtonPressed(4);
    // boolean left_horizontal_stop = stick1.getRawButtonReleased(4);

    //deadzone for controllers SEEMS to be 0.5, change back to 0.2 if that doesn't work.
    //no X axis in the OG code, if the driving is wonky add it.
    
    if (controller.getRawAxis(1) > 0.5 || controller.getRawAxis(1) < 0.5) 
    {
      left_drive_motor_front.set(controller.getRawAxis(1)/1.5);
      left_drive_motor_back.set(controller.getRawAxis(1)/1.5);
    }

    if (controller.getRawAxis(1) > 0.48 && controller.getRawAxis(1) < 0.52)
    {
      left_drive_motor_front.set(0);
      left_drive_motor_back.set(0);
    }

    if (controller.getRawAxis(5) > 0.5 || controller.getRawAxis(5) < 0.5) 
    {
      right_drive_motor_front.set(controller.getRawAxis(5));
      right_drive_motor_back.set(controller.getRawAxis(5));
    }

    if (controller.getRawAxis(5) > 0.48 && controller.getRawAxis(5) < 0.52)
    {
      right_drive_motor_front.set(0);
      right_drive_motor_back.set(0);
    }

    // if (stick1.getRawAxis(1) > 0.2 || stick1.getRawAxis(1) < -0.2) 
    // {
    //   left_drive_motor_front.set(stick1.getRawAxis(1)/1.5);
    //   left_drive_motor_back.set(stick1.getRawAxis(1)/1.5);
    // }

    // if (stick1.getRawAxis(1) < 0.2 && stick1.getRawAxis(1) > -0.2)
    // {
    //   left_drive_motor_front.set(0);
    //   left_drive_motor_back.set(0);
    // }

    // if (stick2.getRawAxis(1) > 0.2 || stick2.getRawAxis(1) < -0.2) 
    // {
    //   right_drive_motor_front.set(stick2.getRawAxis(1));
    //   right_drive_motor_back.set(stick2.getRawAxis(1));
    // }

    // if (stick2.getRawAxis(1) < 0.2 && stick2.getRawAxis(1) > -0.2)
    // {
    //   right_drive_motor_front.set(0);
    //   right_drive_motor_back.set(0);
    // }
    
    //double solenoid code; useless as of right now.

    /*
    if (stick.getRawAxis(3) > 0.4)
    {
      shooter.set(DoubleSolenoid.Value.kReverse);
    }
    if (stick.getRawAxis(3) < 0.4)
    {
      shooter.set(DoubleSolenoid.Value.kForward);
    }

    if (stick.getRawAxis(2) > 0.4)
    {
      confetti1.Solenoid.set(false);
    }
    if (stick.getRawAxis(2) < 0.4)
    {
      confetti1.Solenoid.set(true);
    }
    */
    if (controller.getPOV() == 0) //up_vertical
    {
      vertical_shooter_motor.set(-.3);
    }

    if (controller.getPOV() == 180) //down_vertical
    {
      vertical_shooter_motor.set(.3);
    }

    if (controller.getPOV() == -1) //up and down vertical_stop
    {
      vertical_shooter_motor.set(0);
    }

    if (controller.getPOV() == 270) //left_horizontal
    {
      horizontal_shooter_motor.set(-.3);
    }

    if (controller.getPOV() == 90) //right_horizontal
    {
      horizontal_shooter_motor.set(.3);
    }

    if (controller.getPOV() == -1) //left and right horizontal_stop
    {
      horizontal_shooter_motor.set(0);
    }

    //button oriented code for the turret motor
    
    // if (up_vertical)
    // {
    //   vertical_shooter_motor.set(-.3);
    // }
    // if (up_vertical_stop)
    // {
    //   vertical_shooter_motor.set(0);
    // }

    // if (down_vertical)
    // {
    //   vertical_shooter_motor.set(.3);
    // }
    // if (down_vertical_stop)
    // {
    //   vertical_shooter_motor.set(0);
    // }

    // if (left_horizontal)
    // {
    //   horizontal_shooter_motor.set(-.3);
    // }
    // if (left_horizontal_stop)
    // {
    //   horizontal_shooter_motor.set(0);
    // }

    // if (right_horizontal)
    // {
    //   horizontal_shooter_motor.set(.3);
    // }
    // if (right_horizontal_stop)
    // {
    //   horizontal_shooter_motor.set(0);
    // }
    
    if (controller.getRawAxis(2) > 0.55 || controller.getRawAxis(3) > 0.55)
    {
      shooter.set(false);
    }

    if (controller.getRawAxis(2) <= 0.50 || controller.getRawAxis(3) <= 0.50)
    {
      shooter.set(true);
    }

    // button oriented shoot controls

    // if (shoot)
    // {
    //   shooter.set(true);
    // }
    // if (sshoot)
    // {
    //   shooter.set(false);
    // }
    

     if (bconfetti1)
    {
      confetti1.set(true);
    }
    if (sconfetti1)
    {
      confetti1.set(false);
    }

     if (bconfetti2)
    {
      confetti2.set(true);
    }
    if (sconfetti2)
    {
      confetti2.set(false);
    }

    if (bconfetti3)
    {
      confetti3.set(true);
    }
    if (sconfetti3)
    {
      confetti3.set(false);
    }

     if (bconfetti4)
    {
      confetti4.set(true);
    }
    if (sconfetti4)
    {
      confetti4.set(false);
    }

    //not using confetti cannon 5 at the moment
    
    //  if (bconfetti5)
    // {
    //   confetti5.set(true);
    // }
    // if (sconfetti5)
    // {
    //   confetti5.set(false);
    // }
    
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
}
