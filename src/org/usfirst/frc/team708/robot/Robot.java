
package org.usfirst.frc.team708.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import org.usfirst.frc.team708.robot.commands.autonomous.DoNothing;
import org.usfirst.frc.team708.robot.subsystems.Drivetrain;
import org.usfirst.frc.team708.robot.subsystems.VisionProcessor;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * @author omn0mn0m
 */
public class Robot extends IterativeRobot {
	
	/*
	WPI_TalonSRX motor1 = new WPI_TalonSRX(11);
	WPI_TalonSRX motor2 = new WPI_TalonSRX(12);
	WPI_TalonSRX motor3 = new WPI_TalonSRX(13);
	WPI_TalonSRX motor4 = new WPI_TalonSRX(14);
	
	SpeedControllerGroup m_right = new SpeedControllerGroup(motor1, motor2);
	SpeedControllerGroup m_left = new SpeedControllerGroup(motor3, motor4);
	
	private DifferentialDrive m_robotDrive
	        = new DifferentialDrive(m_left, m_right);
	private Joystick m_stick = new Joystick(1);
	private Timer m_timer = new Timer();
	*/
    Timer statsTimer;										// Timer used for Smart Dash statistics
    
    public static Drivetrain 		drivetrain;
	public static VisionProcessor 	visionProcessor;
    
	public static OI 				oi;

	public static double defenceNumber;
	public static double turnDirection;
	public static double driveThroughDefenceTime;
 
	SendableChooser<Command> autonomousMode = new SendableChooser<>();
    Command 			autonomousCommand;
    Preferences			prefs;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
        statsTimer = new Timer();	// Initializes the timer for sending Smart Dashboard data
        statsTimer.start();		// Starts the timer for the Smart Dashboard
        
		
        
// Subsystem Initialization

    drivetrain 		= new Drivetrain();
        
	oi 				= new OI();		// Initializes the OI. 
									// This MUST BE LAST or a NullPointerException will be thrown
	
	
	sendDashboardSubsystems();		// Sends each subsystem's currently running command to the Smart Dashboard
		
	queueAutonomousModes();			// Adds autonomous modes to the selection box
        
    }
	
    /**
     * Runs periodically while the robot is enabled
     */
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		sendStatistics();
		prefs = Preferences.getInstance();
	}

	/**
	 * Runs at the start of autonomous mode
	 */
    	public void autonomousInit() {
    	
    //	turnDirection = prefs.getDouble("TurnDirection", 4.0);
    		
    	// schedule the autonomous command (example)   		
    	autonomousCommand = (Command)autonomousMode.getSelected();
        if (autonomousCommand != null) autonomousCommand.start();

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {	
        Scheduler.getInstance().run();
        sendStatistics();
    }

    /**
     * Runs when teleop mode initializes
     */
    public void teleopInit() {
	    // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        sendStatistics();
   //     m_robotDrive.arcadeDrive(m_stick.getRawAxis(1), m_stick.getRawAxis(0));
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
        sendStatistics();
    }
    
    /**
     * Sends data from each subsystem periodically as set by sendStatsIntervalSec
     */
    
    
    
    private void sendStatistics() {
        if (statsTimer.get() >= Constants.SEND_STATS_INTERVAL) {
            statsTimer.reset();

            // Various debug information

        }
    }
    
    /**
     * Adds every autonomous mode to the selection box and adds the box to the Smart Dashboard
     */
    private void queueAutonomousModes() {
    	
    	autonomousMode.addObject("Test Auto 1", null);
    	autonomousMode.addObject("Do Nothing", new DoNothing());				//Added in "Do nothing" to try and reset the robot	-Viet
//		autonomousMode.addObject("Find Target", new DriveToTarget());
//		autonomousMode.addObject("Drive in Square", new DriveInSquare());

    	SmartDashboard.putData("Autonomous Selection", autonomousMode);    	   	
    }
    
    /**
     * Sends every subsystem to the Smart Dashboard
     */
    private void sendDashboardSubsystems() {
    	
    }
}


