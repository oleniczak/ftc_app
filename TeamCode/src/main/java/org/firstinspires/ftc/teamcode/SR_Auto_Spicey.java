package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * 2/12/2017 9019
 */

@Autonomous(name="Spicey (Config Alliance & Position)", group="Autonomous")
//@Disabled
public class SR_Auto_Spicey extends LinearOpMode {
    private Hardware robot = new Hardware(telemetry);
    private Configuration configs = new Configuration(telemetry);
    private Commands cmds = new Commands(telemetry);

    @Override
    public void runOpMode() {
        telemetry.addData("BEGIN", "Autonomous Starting...");
        telemetry.update();

        robot.init(hardwareMap);

        configs.loadParameters();

        cmds.InitializeHW(robot);

        telemetry.addData("Config", "Configured for " + Configuration.ALLIANCE + " Alliance.");
        telemetry.addData("Config", "Configured for " + Configuration.START_POSITION + " Starting Position.");
        telemetry.update();

        waitForStart();

        //Wait for alliance moves or to avoid penalty for early cross
        telemetry.addData("Status", "Delay before driving ...");
        telemetry.update();

        sleep(Configuration.AUTO_DELAY_TIME);

        telemetry.addData("Status", "Delay Complete!");
        telemetry.update();

        //Move close enough to shoot balls
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_DIST_TO_SHOOT, Configuration.LONG_DIST_TO_SHOOT, 5.0);
        }
        else //SHORT
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.SHORT_DIST_TO_SHOOT, Configuration.SHORT_DIST_TO_SHOOT, 5.0);
        }

        robot.motorLaunch.setPower(Configuration.LAUNCH_POWER);

        //Use delay until ball launch is ready for use
        sleep(500);

        robot.motorCollect.setPower(1.0);

        cmds.Shoot(robot);
        //cmds.Shoot(robot);

        robot.motorCollect.setPower(0);

        //Drive to line up with closest beacon
        if (Configuration.START_POSITION.equals("LONG"))
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, Configuration.LONG_FIRST_BEACON_AIM_DIST, Configuration.LONG_FIRST_BEACON_AIM_DIST, 5.0);
        }
        else
        {
            cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 30, 30, 5.0);
        }

        //Turn 45 degrees to face beacon
        if(Configuration.ALLIANCE.equals("RED"))
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.FORTYFIVE_DEGREE_TURN_INCHES, Configuration.FORTYFIVE_DEGREE_TURN_INCHES, 3.0);
        }
        else    //BLUE
        {
            cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.FORTYFIVE_DEGREE_TURN_INCHES, -Configuration.FORTYFIVE_DEGREE_TURN_INCHES, 3.0);
        }

        //Drive into beacon, pressing button
        cmds.EncoderDrive(robot, Configuration.DRIVE_POWER,  Configuration.LONG_FIRST_BEACON_DIST,  Configuration.LONG_FIRST_BEACON_DIST, 5.0);

        //Backup 3 inches to assess color
        //cmds.EncoderDrive(robot,Configuration.APPROACH_SPEED, -3, -3, 5.0);

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
        cmds.SenseBeacon(robot);

        //Turn 90 degrees to drive to second beacon
        //if(Configuration.ALLIANCE.equals("RED"))
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}
        //else    //BLUE
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}

        //Drive to second beacon
        //cmds.EncoderDrive(robot, Configuration.DRIVE_POWER, 40, 40, 5.0);

        //Turn 90 degrees to face beacon
        //if(Configuration.ALLIANCE.equals("RED"))
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, -Configuration.NINETY_DEGREE_TURN_INCHES, Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}
        //else    //BLUE
        //{
        //    cmds.EncoderDrive(robot, Configuration.TURN_POWER, Configuration.NINETY_DEGREE_TURN_INCHES, -Configuration.NINETY_DEGREE_TURN_INCHES, 3.0);
        //}

        //Sensing beacon will also invoke a 3 inch drive if the opposite color is detected
//        cmds.SenseBeacon(robot);

        cmds.StopDriving(robot);

        telemetry.addData("Status", "Autonomous Complete!");
        telemetry.update();
    }
}
